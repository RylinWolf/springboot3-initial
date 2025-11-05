package com.wolfhouse.springboot3initial.common.result;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * @author Rylin Wolf
 */
@Data
public class PageQueryDto {
    protected JsonNullable<Integer> pageNum = JsonNullable.of(1);
    protected JsonNullable<Integer> pageSize = JsonNullable.of(10);
}
