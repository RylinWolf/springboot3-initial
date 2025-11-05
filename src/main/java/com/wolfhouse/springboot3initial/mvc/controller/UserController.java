package com.wolfhouse.springboot3initial.mvc.controller;

import com.mybatisflex.core.paginate.Page;
import com.wolfhouse.springboot3initial.common.result.HttpResult;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserRegisterDto;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import com.wolfhouse.springboot3initial.mvc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public HttpResult<UserVo> register(@RequestBody UserRegisterDto dto) {
        // 1. 调用业务方法
        UserVo vo = userService.register(dto);
        // 2. 返回结果
        return HttpResult.failedIfBlank(vo);
    }

    /**
     * 根据主键删除用户表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return userService.removeById(id);
    }


    /**
     * 根据用户表主键获取详细信息。
     *
     * @param id user主键
     * @return 用户表详情
     */
    @GetMapping("/getInfo/{id}")
    public UserVo getInfo(@PathVariable Long id) {
        return userService.getVoById(id);
    }


    /**
     * 分页查询用户表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<User> page(Page<User> page) {
        return userService.page(page);
    }
}