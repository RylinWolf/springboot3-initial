package com.wolfhouse.springboot3initial.mvc.service.user.impl;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.util.beanutil.BeanUtil;
import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyException;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyStrategy;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyTool;
import com.wolfhouse.springboot3initial.common.util.verify.impl.EmptyVerifyNode;
import com.wolfhouse.springboot3initial.common.util.verify.servicenode.common.ServiceVerifyNode;
import com.wolfhouse.springboot3initial.common.util.verify.servicenode.user.UserVerifyNode;
import com.wolfhouse.springboot3initial.exception.ServiceException;
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
import com.wolfhouse.springboot3initial.util.LocalLoginUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wolfhouse.springboot3initial.mvc.model.domain.user.table.UserTableDef.USER;

/**
 * @author Rylin Wolf
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserAuthMapper userAuthMapper;
    private final PasswordEncoder passwordEncoder;


    // region 登录相关

    @Override
    public Boolean login(String certificate, String password, HttpServletRequest request) {
        // 1. 检查参数
        ThrowUtil.throwOnCondition(BeanUtil.isAnyBlank(certificate, password),
                                   HttpCode.PARAM_ERROR.message);
        // 2. 验证用户信息
        if (!verify(certificate, password)) {
            return false;
        }
        // 3. 保存至 session
        User user = getByCertificate(certificate);
        UserLocalDto localDto = BeanUtil.copyProperties(user, UserLocalDto.class);
        request.getSession()
               .setAttribute(UserConstant.LOGIN_USER_SESSION_KEY,
                             localDto);
        // 4. 保存至本地状态存储
        LocalLoginUtil.setUser(localDto);
        return true;
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession()
               .setAttribute(UserConstant.LOGIN_USER_SESSION_KEY, null);
    }

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
                      UserVerifyNode.birth(dto.getBirth()),
                      // 校验邮箱是否已存在
                      new EmptyVerifyNode<String>().target(dto.getEmail())
                                                   .predicate(e -> !this.isUserEmailExist(e))
                                                   .setCustomException(new VerifyException(UserConstant.EXIST_EMAIL))
                                                   .setStrategy(VerifyStrategy.WITH_CUSTOM_EXCEPTION))
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
    public UserVo update(UserUpdateDto dto) {
        // 1. 获取当前登录用户
        UserLocalDto user = LocalLoginUtil.getUser();
        // 2. 构建更新条件
        QueryWrapper wrapper = QueryWrapper.create()
                                           .from(User.class)
                                           .eq(User::getId, user.getId());
        // 用户名
        dto.getUsername()
           .ifPresent(u -> {
               genAccount(u);
               wrapper.eq(User::getUsername, u);
           });
        // 3. 执行更新


        return null;
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
