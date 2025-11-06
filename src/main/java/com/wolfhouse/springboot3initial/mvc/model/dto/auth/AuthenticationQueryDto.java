package com.wolfhouse.springboot3initial.mvc.model.dto.auth;

import com.wolfhouse.springboot3initial.common.result.PageQueryDto;
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
public class AuthenticationQueryDto extends PageQueryDto {
    private JsonNullable<Long> parentId;
    private JsonNullable<String> code;
    private JsonNullable<String> description;
}
