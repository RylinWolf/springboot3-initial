package com.wolfhouse.springboot3initial.mvc.controller;

import com.mybatisflex.core.paginate.Page;
import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.result.HttpResult;
import com.wolfhouse.springboot3initial.common.result.PageResult;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Admin;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AdminAddDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AdminService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "添加管理员")
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
    @Operation(summary = "删除管理员")
    public HttpResult<?> remove(@PathVariable Serializable id) {
        return HttpResult.onCondition(HttpCode.UNKNOWN, adminService.removeById(id));
    }


    /**
     * 根据主键更新管理员表
     *
     * @param admin 管理员表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @PreAuthorize("@pm.hasPerm('service:admin:update')")
    @Operation(summary = "更新管理员")
    public HttpResult<?> update(@RequestBody Admin admin) {
        return HttpResult.onCondition(HttpCode.UNKNOWN, adminService.updateById(admin));
    }


    /**
     * 查询所有管理员表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @PreAuthorize("@pm.hasPerm('service:admin:query')")
    @Operation(summary = "查询所有管理员")
    public HttpResult<List<Admin>> list() {
        return HttpResult.success(adminService.list());
    }


    /**
     * 根据管理员表主键获取详细信息。
     *
     * @param id admin主键
     * @return 管理员表详情
     */
    @GetMapping("/getInfo/{id}")
    @PreAuthorize("@pm.hasPerm('service:admin:query')")
    @Operation(summary = "根据 ID 获取管理员")
    public HttpResult<Admin> getInfo(@PathVariable Serializable id) {
        return HttpResult.success(adminService.getById(id));
    }


    /**
     * 分页查询管理员表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @PreAuthorize("@pm.hasPerm('service:admin:query')")
    @Operation(summary = "分页查询管理员")
    public HttpResult<PageResult<Admin>> page(Page<Admin> page) {
        return HttpResult.success(PageResult.ofPage(adminService.page(page)));
    }
}