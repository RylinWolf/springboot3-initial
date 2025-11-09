package com.wolfhouse.springboot3initial.mvc.controller;

import com.mybatisflex.core.paginate.Page;
import com.wolfhouse.springboot3initial.common.result.HttpResult;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Admin;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AdminAddDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "管理员接口")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    /**
     * 添加 管理员表
     *
     * @param dto 管理员添加 Dto
     * @return 管理员视图对象
     */
    @PostMapping
    @PreAuthorize("@pm.hasPerm('service:admin:add')")
    public HttpResult<Admin> add(@RequestBody AdminAddDto dto) throws Exception {
        return HttpResult.failedIfBlank(adminService.addAdmin(dto));
    }


    /**
     * 根据主键删除管理员表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("@pm.hasPerm('service:admin:delete')")
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
    @PreAuthorize("@pm.hasPerm('service:admin:update')")
    public boolean update(@RequestBody Admin admin) {
        return adminService.updateById(admin);
    }


    /**
     * 查询所有管理员表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @PreAuthorize("@pm.hasPerm('service:admin:query')")
    public List<Admin> list() {
        return adminService.list();
    }


    /**
     * 根据管理员表主键获取详细信息。
     *
     * @param id admin主键
     * @return 管理员表详情
     */
    @GetMapping("/getInfo/{id}")
    @PreAuthorize("@pm.hasPerm('service:admin:query')")
    public Admin getInfo(@PathVariable Serializable id) {
        return adminService.getById(id);
    }


    /**
     * 分页查询管理员表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @PreAuthorize("@pm.hasPerm('service:admin:query')")
    public Page<Admin> page(Page<Admin> page) {
        return adminService.page(page);
    }
}