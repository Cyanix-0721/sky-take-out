package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "分类分页查询数据传输对象")
public class CategoryPageQueryDTO implements Serializable {

    @ApiModelProperty(value = "页码", example = "1")
    private int page;

    @ApiModelProperty(value = "每页记录数", example = "10")
    private int pageSize;

    @ApiModelProperty(value = "分类名称", example = "主菜")
    private String name;

    @ApiModelProperty(value = "分类类型 1菜品分类 2套餐分类", example = "1", allowableValues = "1, 2")
    private Integer type;
}