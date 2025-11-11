package com.wolfhouse.springboot3initial.mvc.model.service;

import com.wolfhouse.springboot3initial.common.result.PageQueryDto;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 与业务高度耦合的分页查询 Dto
 *
 * @author Rylin Wolf
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServicePageQueryDto extends PageQueryDto {
    @Min(value = 1L, message = "页数必须大于等于 1")
    protected Long pageNum;
    @Min(value = 1L, message = "每页数量必须大于等于 1")
    protected Long pageSize;
}
