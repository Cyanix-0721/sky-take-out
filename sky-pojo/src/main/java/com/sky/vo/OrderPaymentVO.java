package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Order Payment View Object")
public class OrderPaymentVO implements Serializable {

	@ApiModelProperty(value = "随机字符串", example = "randomString123")
	private String nonceStr; //随机字符串

	@ApiModelProperty(value = "签名", example = "signature123")
	private String paySign; //签名

	@ApiModelProperty(value = "时间戳", example = "2023-10-01T12:34:56")
	private String timeStamp; //时间戳

	@ApiModelProperty(value = "签名算法", example = "HMAC-SHA256")
	private String signType; //签名算法

	@ApiModelProperty(value = "统一下单接口返回的 prepay_id 参数值", example = "prepay_id=wx201410272009395522657a690389285100")
	private String packageStr; //统一下单接口返回的 prepay_id 参数值

}