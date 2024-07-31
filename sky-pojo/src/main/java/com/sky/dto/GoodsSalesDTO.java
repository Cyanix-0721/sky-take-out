package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "商品销售数据传输对象")
public class GoodsSalesDTO implements Serializable {

    @ApiModelProperty(value = "商品名称", example = "苹果")
    private String name;

    @ApiModelProperty(value = "销量", example = "100")
    private Integer number;
}