package com.wolfhouse.springboot3initial.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.wolfhouse.springboot3initial.service.IUserService;
import com.wolfhouse.springboot3initial.model.domain.UserEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 添加 用户表
     *
     * @param user 用户表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public boolean save(@RequestBody UserEntity user) {
        return userService.save(user);
    }


    /**
     * 根据主键删除用户表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return userService.removeById(id);
    }


    /**
     * 根据主键更新用户表
     *
     * @param user 用户表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public boolean update(@RequestBody UserEntity user) {
        return userService.updateById(user);
    }


    /**
     * 查询所有用户表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<UserEntity> list() {
        return userService.list();
    }


    /**
     * 根据用户表主键获取详细信息。
     *
     * @param id user主键
     * @return 用户表详情
     */
    @GetMapping("/getInfo/{id}")
    public UserEntity getInfo(@PathVariable Serializable id) {
        return userService.getById(id);
    }


    /**
     * 分页查询用户表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<UserEntity> page(Page<UserEntity> page) {
        return userService.page(page);
    }
}