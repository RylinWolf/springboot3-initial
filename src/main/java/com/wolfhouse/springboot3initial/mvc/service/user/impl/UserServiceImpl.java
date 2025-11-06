package com.wolfhouse.springboot3initial.mvc.service.user.impl;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.enums.user.GenderEnum;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.util.beanutil.BeanUtil;
import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyTool;
import com.wolfhouse.springboot3initial.common.util.verify.servicenode.common.PhoneVerifyNode;
import com.wolfhouse.springboot3initial.common.util.verify.servicenode.common.ServiceVerifyNode;
import com.wolfhouse.springboot3initial.common.util.verify.servicenode.user.UserVerifyNode;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mediator.UserAdminAuthMediator;
import com.wolfhouse.springboot3initial.mvc.mapper.user.UserAuthMapper;
import com.wolfhouse.springboot3initial.mvc.mapper.user.UserMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.UserAuth;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserLocalDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserQueryDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserRegisterDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserUpdateDto;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.wolfhouse.springboot3initial.mvc.model.domain.user.table.UserTableDef.USER;

/**
 * @author Rylin Wolf
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserAuthMapper userAuthMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserAdminAuthMediator mediator;

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
    public Boolean verify(String certificate, String password) {
        // 1. 获取用户
        User user = getByCertificate(certificate);
        if (user == null) {
            return false;
        }
        // 2. 获取用户验证信息
        UserAuth auth = userAuthMapper.selectOneById(user.getId());
        // 3. 比对验证信息
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
    // endregion

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
    public UserVo update(UserUpdateDto dto) throws Exception {
        // 1. 获取当前登录用户
        // 未登录则抛出异常
        UserLocalDto user = LocalLoginUtil.getUserOrThrow();

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
                .equals(username.get())) {
            username = JsonNullable.undefined();
        }
        if (user.getEmail()
                .equals(email.get())) {
            email = JsonNullable.undefined();
        }

        // 校验字段
        // TODO 图片校验
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
        UpdateChain<User> chain = new UpdateChain<>(mapper);
        chain.eq(User::getId, user.getId());
        // 用户名
        dto.getUsername()
           .ifPresent(u -> {
               // 同步更新帐号
               chain.set(User::getAccount, genAccount(u));
               chain.set(User::getUsername, u);
           });
        // 邮箱
        email.ifPresent(e -> chain.set(User::getEmail, e));
        // 头像 可以为空
        avatar.ifPresent(a -> chain.set(User::getAvatar, a, true));
        // 生日 可以为空
        birth.ifPresent(b -> chain.set(User::getBirth, b, true));
        // 性别 可以为空
        gender.ifPresent(b -> chain.set(User::getGender, b, true));
        // 手机号 可以为空
        phone.ifPresent(p -> chain.set(User::getPhone, p, true));
        // 个性签名 可以为空
        personalStatus.ifPresent(s -> chain.set(User::getPersonalStatus, s, true));

        // 3. 执行更新
        // 更新失败则抛出异常，回滚
        ThrowUtil.throwOnCondition(!chain.update(),
                                   HttpCode.SQL_ERROR.code,
                                   HttpCode.SQL_ERROR.message,
                                   ServiceException.class);
        // 4. 返回 Vo
        return getVoById(user.getId());
    }

    @Override
    public String genAccount(String username) {
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


}
