package com.wolfhouse.springboot3initial.mvc.service.user.impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyun.oss.model.CannedAccessControlList;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.common.constant.OssUploadLogConstant;
import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.enums.oss.FileType;
import com.wolfhouse.springboot3initial.common.enums.user.GenderEnum;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.util.beanutil.BeanUtil;
import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.common.util.imageutil.ImgCompressor;
import com.wolfhouse.springboot3initial.common.util.imageutil.ImgValidException;
import com.wolfhouse.springboot3initial.common.util.imageutil.ImgValidator;
import com.wolfhouse.springboot3initial.common.util.oss.BucketClient;
import com.wolfhouse.springboot3initial.common.util.redisutil.RedisKey;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyTool;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.exception.ServiceExceptionConstant;
import com.wolfhouse.springboot3initial.mvc.mapper.user.UserAuthMapper;
import com.wolfhouse.springboot3initial.mvc.mapper.user.UserMapper;
import com.wolfhouse.springboot3initial.mvc.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.UserAuth;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.*;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.mvc.service.OssUploadLogService;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import com.wolfhouse.springboot3initial.security.SecurityContextUtil;
import com.wolfhouse.springboot3initial.task.UserTask;
import com.wolfhouse.springboot3initial.util.redisutil.ServiceRedisUtil;
import com.wolfhouse.springboot3initial.util.redisutil.constant.UserRedisConstant;
import com.wolfhouse.springboot3initial.util.redisutil.service.RedisOssService;
import com.wolfhouse.springboot3initial.util.redisutil.service.RedisUserService;
import com.wolfhouse.springboot3initial.util.verifynode.common.PhoneVerifyNode;
import com.wolfhouse.springboot3initial.util.verifynode.common.ServiceVerifyNode;
import com.wolfhouse.springboot3initial.util.verifynode.user.UserVerifyNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.wolfhouse.springboot3initial.mvc.model.domain.user.table.UserTableDef.USER;

/**
 * @author Rylin Wolf
 */
@Slf4j
@Service
@RequiredArgsConstructor
@RedisKey(secondPrefix = "user")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserAuthMapper userAuthMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserAdminAuthMediator mediator;
    private final ImgValidator avatarValidator;
    private final BucketClient avatarOssClient;
    private final OssUploadLogService ossLogService;
    private final ServiceRedisUtil redisUtil;
    private final RedisUserService redisUserService;
    private final RedisOssService redisOssService;
    private final UserTask task;

    // region 初始化

    /**
     * 初始化方法。
     * 该方法在对象构造完成后调用，用于完成必要的初始化操作。
     * 在此方法中，将当前服务注册到中介者（mediator）中，以便与其他服务进行交互。
     * 此方法通过 @PostConstruct 注解标识，确保在依赖注入完成后执行。
     */
    @PostConstruct
    public void init() {
        this.mediator.registerUserService(this);
    }
    // endregion

    // region 登录相关

    @Override
    public UserLocalDto getLogin() {
        return SecurityContextUtil.getLoginUser();
    }

    @Override
    public UserLocalDto getLoginOrThrow() {
        UserLocalDto login = getLogin();
        ThrowUtil.throwIfBlank(login,
                               HttpCode.UN_AUTHORIZED.code,
                               HttpCode.UN_AUTHORIZED.message,
                               ServiceException.class);

        return login;
    }

    @Override
    public User getLoginUser() {
        UserLocalDto login = getLogin();
        if (login == null) {
            return null;
        }
        return getById(login.getId());
    }

    @Override
    public User getLoginUserOrThrow() {
        User login = getLoginUser();
        ThrowUtil.throwIfBlank(login,
                               HttpCode.UN_AUTHORIZED.code,
                               HttpCode.UN_AUTHORIZED.message,
                               ServiceException.class);
        return login;
    }

    @Override
    public Boolean verify(String certificate, String password) {
        return verify(getByCertificate(certificate), password);
    }

    @Override
    public Boolean verify(Long id, String password) {
        return verify(getById(id), password);
    }

    @Override
    public Boolean verify(User user, String password) {
        // 1. 获取用户
        if (user == null) {
            return false;
        }
        // 2. 获取用户密码密文
        UserAuth auth = userAuthMapper.selectOneById(user.getId());
        // 3. 验证权限
        return passwordEncoder.matches(password, auth.getPasscode());
    }

    // endregion

    // region 查询用户


    @Override
    public User getByCertificate(String certificate) {
        return mapper.selectOneByQuery(QueryWrapper.create()
                                                   .from(User.class)
                                                   // 根据帐号查询
                                                   .where(USER.ACCOUNT.eq(certificate))
                                                   // 根据邮箱查询
                                                   .or(USER.EMAIL.eq(certificate)));
    }

    @Override
    public UserVo getVoById(Long id) {
        return BeanUtil.copyProperties(getById(id), UserVo.class);
    }

    @Override
    public Page<User> queryBy(UserQueryDto dto) {
        QueryWrapper wrapper = QueryChain.create()
                                         .from(User.class);
        // 1. 根据 dto 传递条件构建查询 wrapper
        // 用户名
        dto.getUsername()
           .ifPresent(u -> wrapper.like(User::getUsername, u, true));
        // 帐号
        dto.getAccount()
           .ifPresent(a -> wrapper.like(User::getAccount, a, true));
        // 邮箱
        dto.getEmail()
           .ifPresent(e -> wrapper.eq(User::getEmail, e, true));
        // 2. 根据 page 参数和 wrapper 查询
        return mapper.paginate(dto.getPageNum(), dto.getPageSize(), wrapper);
    }
    // endregion

    // region 指定条件的用户是否存在

    @Override
    public Boolean isUserEmailExist(String email) {
        return exists(QueryWrapper.create()
                                  .eq(User::getEmail, email, true));
    }

    @Override
    public Boolean isUserExist(Long id) {
        return exists(QueryWrapper.create()
                                  .eq(User::getId, id, true));
    }

    @Override
    public Boolean isUserAccountExist(String account) {
        return exists(QueryWrapper.create()
                                  .eq(User::getAccount, account, true));
    }

    @Override
    public Long updateAccessedTime(Map<Long, LocalDateTime> lastLogins) {
        return mapper.updateAccessedTimeByMap(lastLogins);
    }
    // endregion

    // region 业务方法

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserVo register(UserRegisterDto dto) {
        // 1. 校验参数
        ThrowUtil.throwIfBlank(dto, ServiceException.class);
        VerifyTool.of(
                      // 校验用户名
                      UserVerifyNode.username(dto.getUsername()),
                      // 校验密码
                      ServiceVerifyNode.password(dto.getPassword()),
                      // 校验手机号
                      ServiceVerifyNode.phone(dto.getPhone()),
                      // 校验生日
                      UserVerifyNode.birth(dto.getBirth()))
                  .doVerify();
        // 2. 保存密码，获得密码 ID
        UserAuth auth = new UserAuth();
        auth.setPasscode(passwordEncoder.encode(dto.getPassword()));
        // 校验是否保存成功
        ThrowUtil.throwOnCondition(userAuthMapper.insert(auth, true) != 1,
                                   HttpCode.UNKNOWN.code,
                                   UserConstant.REGISTER_FAILED);
        // 创建用户对象并注入 ID
        User user = BeanUtil.copyProperties(dto, User.class);
        user.setId(auth.getId());
        // 3. 保存用户
        // 生成帐号并注入
        user.setAccount(genAccount(dto.getUsername()));
        mapper.insert(user, true);
        // 4. 返回 vo
        return BeanUtil.copyProperties(user, UserVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVo update(UserUpdateDto dto) {
        // 1. 获取当前登录用户
        // 未登录则抛出异常
        User user = getLoginUserOrThrow();
        Long userId = user.getId();
        AtomicBoolean isAvatarUpdate = new AtomicBoolean(false);
        // 2. 构建更新条件
        // 获取字段
        JsonNullable<String> username = dto.getUsername();
        JsonNullable<String> avatar = dto.getAvatar();
        JsonNullable<GenderEnum> gender = dto.getGender();
        JsonNullable<LocalDate> birth = dto.getBirth();
        JsonNullable<String> personalStatus = dto.getPersonalStatus();
        JsonNullable<String> phone = dto.getPhone();
        JsonNullable<String> email = dto.getEmail();
        // 邮箱和用户名若与之前一样，则不修改
        if (user.getUsername()
                .equals(username.orElse(null))) {
            username = JsonNullable.undefined();
        }
        if (user.getEmail()
                .equals(email.orElse(null))) {
            email = JsonNullable.undefined();
        }
        // 校验字段
        VerifyTool.of(
                      // 生日
                      UserVerifyNode.birth(birth.orElse(null)),
                      // 用户名
                      UserVerifyNode.username(username.orElse(null))
                                    .allowNull(true),
                      // 个性签名
                      UserVerifyNode.status(personalStatus.orElse(null)),
                      // 性别
                      UserVerifyNode.gender(gender.orElse(null)),
                      // 邮箱
                      UserVerifyNode.email(mediator, email.orElse(null))
                                    .allowNull(true),
                      // 手机号
                      new PhoneVerifyNode(phone.orElse(null)))
                  .doVerify();

        // 构建更新链
        UpdateChain<User> chain = buildUserUpdateChain(
            dto,
            userId,
            email,
            avatar,
            isAvatarUpdate,
            birth,
            gender,
            phone,
            personalStatus);

        // 3. 执行更新
        // 更新失败则抛出异常，回滚
        // TODO chain.update 报错时，事务未正常回滚
        // 默认状态为已更新
        boolean isErrored = false;
        try {
            chain.update();
        } catch (Exception ignored) {
            isErrored = true;
        }
        // 若代码报错，则手动抛出异常
        ThrowUtil.throwOnCondition(isErrored,
                                   HttpCode.SQL_ERROR.code,
                                   HttpCode.SQL_ERROR.message,
                                   ServiceException.class);

        // 4. 删除所有当前使用的头像后续的记录游标（未考虑并发问题）
        // 获取使用头像
        UserVo vo = getVoById(user.getId());
        String avatarInuse = vo.getAvatar();
        // 删除指定日志后的数据
        Set<String> afterByPath = ossLogService.getAvatarOssPathAfterByPath(avatarInuse);
        redisOssService.addDuplicateAvatar(afterByPath);
        // 直接从缓存中删除，避免后续定时任务可能引发的问题
        task.doCleanCachedDuplicateAvatar();
        
        // 如果头像进行了更新，则执行历史头像清理: 正常情况下，只要头像更新，则使用的是最新的文件
        if (isAvatarUpdate.get()) {
            if (redisOssService.addDuplicateAvatarFromOss(userId,
                                                          Set.of(avatarOssClient.buildPath(new File(avatarInuse).getName())))) {
                log.warn("头像更新成功，但执行清理失败: {}", userId);
            }
        }

        // 5. 返回 Vo
        return vo;
    }

    /** 构建用户更新链 */
    private UpdateChain<User> buildUserUpdateChain(UserUpdateDto dto,
                                                   Long userId,
                                                   JsonNullable<String> email,
                                                   JsonNullable<String> avatar,
                                                   AtomicBoolean isAvatarUpdate,
                                                   JsonNullable<LocalDate> birth,
                                                   JsonNullable<GenderEnum> gender,
                                                   JsonNullable<String> phone,
                                                   JsonNullable<String> personalStatus) {
        UpdateChain<User> chain = new UpdateChain<>(mapper);
        chain.eq(User::getId, userId);
        // 用户名
        dto.getUsername()
           .ifPresent(u -> {
               // 同步更新帐号
               chain.set(User::getAccount, genAccount(u));
               chain.set(User::getUsername, u);
           });
        // 邮箱
        email.ifPresent(e -> chain.set(User::getEmail, e));
        // 头像 反转判断逻辑，先判断有无缓存
        // 从缓存中读取头像地址
        String cachedAvatar = redisUserService.getAndDeleteAvatarPath(userId);
        // 若上传过头像则执行更新检查
        if (cachedAvatar != null && !cachedAvatar.isBlank()) {
            avatar.ifPresent(a -> {
                // 删除头像
                if (a == null) {
                    chain.set(User::getAvatar, null);
                    isAvatarUpdate.set(true);
                    return;
                }
                // 头像地址有效，进行更新
                chain.set(User::getAvatar, cachedAvatar);
                isAvatarUpdate.set(true);
            });
        }

        // 生日 可以为空
        birth.ifPresent(b -> chain.set(User::getBirth, b, true));
        // 性别 可以为空
        gender.ifPresent(b -> chain.set(User::getGender, b, true));
        // 手机号 可以为空
        phone.ifPresent(p -> chain.set(User::getPhone, p, true));
        // 个性签名 可以为空
        personalStatus.ifPresent(s -> chain.set(User::getPersonalStatus, s, true));
        return chain;
    }

    @Override
    public String genAccount(String username) {
        // 用户名统一转为小写
        username = username.toLowerCase();
        String account = "%s#%s".formatted(username, RandomUtil.randomInt(100000, 1000000));
        // 查询帐号是否存在，若存在则重新生成，超出最大次数则抛出异常
        int maxReties = 10;
        while (isUserAccountExist(account) && maxReties-- > 0) {
            // 重新生成
            account = "%s#%s".formatted(username, RandomUtil.randomInt(100000, 1000000));
        }
        if (maxReties <= 0) {
            // 超出最大重试次数
            throw new ServiceException(HttpCode.PARAM_ERROR, UserConstant.UNAVAILABLE_USERNAME);
        }
        return account;
    }

    /**
     * 生成唯一的文件名，格式为 userId_随机字符串.格式。若生成的文件名已存在，
     * 则重新生成，直到文件名唯一或达到最大重试次数。
     *
     * @param userId 用户ID，用于文件名前缀。
     * @param format 文件格式，例如 "jpg"、"png"。
     * @return 生成的唯一文件名。
     * @throws ServiceException 如果在达到最大重试次数后仍无法生成唯一文件名，抛出此异常。
     */
    private String genFilename(Long userId, String format) {
        String filename = String.format("%s_%s.%s", userId, RandomUtil.randomString(16), format);
        int maxReties = 10;
        // 文件是否已存在，若已存在则重新生成名称
        while (avatarOssClient.doesObjectExist(filename) && maxReties-- > 0) {
            filename = String.format("%s_%s.%s", userId, RandomUtil.randomString(16), format);
        }
        if (maxReties <= 0) {
            // 超出最大重试次数
            throw new ServiceException(HttpCode.OSS_UPLOAD_FAILED);
        }
        return filename;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePassword(UserPwdUpdateDto dto) {
        // 1. 获取用户
        Long userId = getLogin().getId();
        User user = getById(userId);
        // 2. 校验密码
        // 旧密码校验
        ThrowUtil.throwOnCondition(!verify(user, dto.getOldPassword()),
                                   HttpCode.PARAM_ERROR.code,
                                   UserConstant.VALID_FAILED);
        // 新密码校验
        VerifyTool.of(ServiceVerifyNode.password(dto.getNewPassword()))
                  .doVerify();
        // 3. 加密新密码并更新
        String encode = passwordEncoder.encode(dto.getNewPassword());
        userAuthMapper.update(UserAuth.builder()
                                      .id(userId)
                                      .passcode(encode)
                                      .build(), true);
        // 4. 获取新密码并验证，验证成功则更新成功
        ThrowUtil.throwOnCondition(!verify(user, dto.getNewPassword()), HttpCode.UNKNOWN.message);
        return true;
    }

    @Override
    public String uploadAvatar(MultipartFile file) throws ImgValidException {
        // 0. 校验登录信息
        UserLocalDto user = getLoginOrThrow();
        // 1. 校验文件
        ImgValidator.Result fileValid = avatarValidator.validate(file);
        if (!fileValid.valid()) {
            // 解析图片失败
            log.error("{}: {}", UserConstant.AVATAR_VALID_FAILED, fileValid.message());
            throw new ServiceException(HttpCode.PARAM_ERROR, UserConstant.AVATAR_VALID_FAILED);
        }

        // 2. 生成文件名称
        // 通过登录用户 ID + 随机字符串生成
        String filename = genFilename(user.getId(), UserConstant.AVATAR_FORMAT);
        // 文件存储路径
        String filepath = avatarOssClient.getFileUploadPath(filename);
        // 文件字节输入流
        ByteArrayInputStream imgIns;
        // 3. 上传文件
        try (var ins = file.getInputStream()) {
            // TODO Redis 获取已上传图片路径，判断是否有已经上传但没有使用的头像指纹，有则删除
            // 3.1 压缩图片大小
            // 构建压缩器
            ImgCompressor compressor = ImgCompressor.of(ins);
            // 判断是否需要压缩质量
            if (fileValid.size() > UserConstant.AVATAR_COMPRESS_SIZE) {
                compressor.quality(UserConstant.AVATAR_COMPRESS_QUALITY);
            }
            // 压缩宽高
            compressor.scale(UserConstant.AVATAR_MAX_WIDTH, UserConstant.AVATAR_MAX_HEIGHT);
            // 转换格式
            compressor.format(UserConstant.AVATAR_FORMAT);
            // 执行压缩，获取字节并转为字节流
            // TODO 不同图片大小使用不同压缩比例
            imgIns = new ByteArrayInputStream(compressor.doCompress()
                                                        .toByteArray());

        } catch (IOException e) {
            log.error("{}: {}", ServiceExceptionConstant.PROCESS_FAILED, e.getMessage(), e);
            throw new ServiceException(HttpCode.IO_ERROR);
        }
        // 3.2 上传文件
        // 上传文件，可公开读
        avatarOssClient.putStream(filename, imgIns, false, CannedAccessControlList.PublicRead);
        // 文件不存在
        if (!avatarOssClient.doesObjectExist(filename)) {
            throw new ServiceException(HttpCode.OSS_UPLOAD_FAILED);
        }
        // 记录上传日志
        // oss 的存储位置为 文件夹/文件名
        String ossPath = avatarOssClient.buildPath(filename);
        if (!ossLogService.log(filename, fileValid.size(), filepath, ossPath, FileType.AVATAR)) {
            log.error("{}: \nname: {}\npath: {}\nossPath:{}\nsize: {}\nuserId: {}\nfileType: {}",
                      OssUploadLogConstant.FAIL_TO_LOG,
                      filename,
                      filepath,
                      ossPath,
                      fileValid.size(),
                      user.getId(),
                      FileType.AVATAR.desc);
        }

        // 4. 保存用户 ID  与地址映射
        // 头像缓存保留 15 分钟
        redisUtil.setValueExpire(UserRedisConstant.USER_AVATAR, filepath, Duration.ofMinutes(15), user.getId());
        // 返回文件名
        return filename;
    }

    // endregion

}
