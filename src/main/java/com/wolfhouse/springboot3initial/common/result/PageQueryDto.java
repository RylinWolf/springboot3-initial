package com.wolfhouse.springboot3initial.common.result;

import lombok.Data;

/**
 * @author Rylin Wolf
 */
@Data
public class PageQueryDto {
    protected Long pageNum = 1L;
    protected Long pageSize = 10L;
}
