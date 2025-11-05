package com.wolfhouse.springboot3initial.mvc.service.user.impl;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.util.beanutil.BeanUtil;
import com.wolfhouse.springboot3initial.common.util.beanutil.ThrowUtil;
import com.wolfhouse.springboot3initial.common.util.verify.VerifyTool;
import com.wolfhouse.springboot3initial.common.util.verify.servicenode.common.ServiceVerifyNode;
import com.wolfhouse.springboot3initial.common.util.verify.servicenode.user.UserVerifyNode;
import com.wolfhouse.springboot3initial.exception.ServiceException;
import com.wolfhouse.springboot3initial.mvc.mapper.user.UserAuthMapper;
import com.wolfhouse.springboot3initial.mvc.mapper.user.UserMapper;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.UserAuth;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserRegisterDto;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rylin Wolf
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserAuthMapper userAuthMapper;
    private final PasswordEncoder passwordEncoder;

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
                      UserVerifyNode.birth(dto.getBirth())
                      // TODO 校验邮箱
                     )
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
        user.setAccount(genAccount(user.getUsername()));
        mapper.insert(user, true);
        // 4. 返回 vo
        return BeanUtil.copyProperties(user, UserVo.class);
    }

    @Override
    public UserVo getVoById(Long id) {
        return null;
    }

    @Override
    public String genAccount(String username) {
        return "%s#%s".formatted(username, RandomUtil.randomInt(100000, 1000000));
    }
}
