package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * @author Rylin Wolf
 */
@Data
public class UserQueryDto {
    private JsonNullable<String> username = JsonNullable.undefined();
    private JsonNullable<String> email = JsonNullable.undefined();
    private JsonNullable<String> account = JsonNullable.undefined();
}
