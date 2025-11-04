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
import com.wolfhouse.springboot3initial.service.AuthenticationService;
import com.wolfhouse.springboot3initial.model.domain.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 权限表 控制层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@RestController
@RequestMapping("/authentication")
@Tag(name = "权限表控制层")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 添加 权限表
     *
     * @param authentication 权限表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary = "添加权限表")
    @Parameters(value = {
        @Parameter(name = "id", description = "权限 ID", required = true),

        @Parameter(name = "parentId", description = "父权限 ID"),

        @Parameter(name = "code", description = "权限标识", required = true),

        @Parameter(name = "description", description = "描述", required = true)
    })

    public boolean save(@RequestBody Authentication authentication) {
        return authenticationService.save(authentication);
    }


    /**
     * 根据主键删除权限表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "根据主键删除权限表")
    @Parameters(value = {
        @Parameter(name = "id", description = "权限 ID", required = true)
    })
    public boolean remove(@PathVariable Serializable id) {
        return authenticationService.removeById(id);
    }


    /**
     * 根据主键更新权限表
     *
     * @param authentication 权限表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新权限表")
    @Parameters(value = {
        @Parameter(name = "id", description = "权限 ID", required = true),

        @Parameter(name = "parentId", description = "父权限 ID"),

        @Parameter(name = "code", description = "权限标识"),

        @Parameter(name = "description", description = "描述")
    })
    public boolean update(@RequestBody Authentication authentication) {
        return authenticationService.updateById(authentication);
    }


    /**
     * 查询所有权限表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有权限表")
    public List<Authentication> list() {
        return authenticationService.list();
    }


    /**
     * 根据权限表主键获取详细信息。
     *
     * @param id authentication主键
     * @return 权限表详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据权限表主键获取详细信息")
    @Parameters(value = {
        @Parameter(name = "id", description = "权限 ID", required = true)
    })
    public Authentication getInfo(@PathVariable Serializable id) {
        return authenticationService.getById(id);
    }


    /**
     * 分页查询权限表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询权限表")
    @Parameters(value = {
        @Parameter(name = "pageNumber", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页大小", required = true)
    })
    public Page<Authentication> page(Page<Authentication> page) {
        return authenticationService.page(page);
    }
}