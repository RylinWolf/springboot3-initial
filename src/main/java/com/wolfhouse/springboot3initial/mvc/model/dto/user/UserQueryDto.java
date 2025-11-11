package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import com.wolfhouse.springboot3initial.mvc.model.service.ServicePageQueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * @author Rylin Wolf
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQueryDto extends ServicePageQueryDto {
    private JsonNullable<String> username = JsonNullable.undefined();
    private JsonNullable<String> email = JsonNullable.undefined();
    private JsonNullable<String> account = JsonNullable.undefined();
}
