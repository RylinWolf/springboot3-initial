package com.wolfhouse.springboot3initial.mvc.model.dto.auth;

import com.wolfhouse.springboot3initial.mvc.model.service.ServicePageQueryDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * 权限查询 Dto
 *
 * @author Rylin Wolf
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "权限分页查询 DTO")
public class AuthenticationQueryDto extends ServicePageQueryDto {
    @Schema(type = "Long", example = "1")
    private JsonNullable<Long> parentId = JsonNullable.undefined();
    @Schema(type = "String", example = "service:admin")
    private JsonNullable<String> code = JsonNullable.undefined();
    @Schema(type = "String", example = "管理员")
    private JsonNullable<String> description = JsonNullable.undefined();
}
