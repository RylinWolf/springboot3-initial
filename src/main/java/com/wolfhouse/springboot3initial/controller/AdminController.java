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
import com.wolfhouse.springboot3initial.service.IAdminService;
import com.wolfhouse.springboot3initial.model.domain.AdminEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    /**
     * 添加 管理员表
     *
     * @param admin 管理员表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public boolean save(@RequestBody AdminEntity admin) {
        return adminService.save(admin);
    }


    /**
     * 根据主键删除管理员表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return adminService.removeById(id);
    }


    /**
     * 根据主键更新管理员表
     *
     * @param admin 管理员表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public boolean update(@RequestBody AdminEntity admin) {
        return adminService.updateById(admin);
    }


    /**
     * 查询所有管理员表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<AdminEntity> list() {
        return adminService.list();
    }


    /**
     * 根据管理员表主键获取详细信息。
     *
     * @param id admin主键
     * @return 管理员表详情
     */
    @GetMapping("/getInfo/{id}")
    public AdminEntity getInfo(@PathVariable Serializable id) {
        return adminService.getById(id);
    }


    /**
     * 分页查询管理员表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<AdminEntity> page(Page<AdminEntity> page) {
        return adminService.page(page);
    }
}