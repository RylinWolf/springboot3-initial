package com.wolfhouse.springboot3initial.mvc.controller;

import com.wolfhouse.springboot3initial.common.result.HttpCode;
import com.wolfhouse.springboot3initial.common.result.HttpResult;
import com.wolfhouse.springboot3initial.common.result.PageResult;
import com.wolfhouse.springboot3initial.mvc.model.domain.auth.Authentication;
import com.wolfhouse.springboot3initial.mvc.model.dto.auth.AuthenticationQueryDto;
import com.wolfhouse.springboot3initial.mvc.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 权限表 控制层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@RestController
@RequestMapping("/authentication")
@Tag(name = "权限表接口")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

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
    @PreAuthorize("@pm.hasPerm('service:auth:add')")
    public HttpResult<?> save(@RequestBody Authentication authentication) {
        return HttpResult.onCondition(authenticationService.save(authentication));
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
    @PreAuthorize("@pm.hasPerm('service:auth:delete')")
    public HttpResult<?> remove(@PathVariable Serializable id) {
        return HttpResult.onCondition(authenticationService.removeById(id));
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
    @PreAuthorize("@pm.hasPerm('service:auth:update')")
    public HttpResult<Boolean> update(@RequestBody Authentication authentication) {
        return HttpResult.success(authenticationService.updateById(authentication));
    }


    /**
     * 查询所有权限表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有权限表")
    @PreAuthorize("@pm.hasPerm('service:auth:query')")
    public HttpResult<List<Authentication>> list() {
        return HttpResult.success(authenticationService.list());
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
    @PreAuthorize("@pm.hasPerm('service:auth:query')")
    public HttpResult<Authentication> getInfo(@PathVariable Serializable id) {
        return HttpResult.failedIfBlank(HttpCode.UNKNOWN, authenticationService.getById(id));
    }


    @PostMapping("/page")
    @Operation(summary = "分页查询权限表")
    @PreAuthorize("@pm.hasPerm('service:auth:query')")
    public HttpResult<PageResult<Authentication>> page(@RequestBody @Valid AuthenticationQueryDto dto) {
        return HttpResult.success(PageResult.ofPage(authenticationService.queryBy(dto)));
    }
}