package com.sky.controller.notify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.sky.annotation.IgnoreToken;
import com.sky.properties.WeChatProperties;
import com.sky.service.OrderService;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * 支付回调相关接口
 */
@RestController
@RequestMapping("/notify")
@Slf4j
@Api(tags = "支付回调相关接口")
public class PayNotifyController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private WeChatProperties weChatProperties;

	/**
	 * 支付成功回调
	 *
	 * @param request  HTTP请求对象
	 * @param response HTTP响应对象
	 * @throws Exception 如果处理过程中发生错误
	 */
	@RequestMapping("/paySuccess")
	@ApiOperation("支付成功回调")
	public void paySuccessNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//读取数据
		String body = readData(request);
		log.info("支付成功回调：{}", body);

		//数据解密
		String plainText = decryptData(body);
		log.info("解密后的文本：{}", plainText);

		JSONObject jsonObject = JSON.parseObject(plainText);
		String outTradeNo = jsonObject.getString("out_trade_no");//商户平台订单号
		String transactionId = jsonObject.getString("transaction_id");//微信支付交易号

		log.info("商户平台订单号：{}", outTradeNo);
		log.info("微信支付交易号：{}", transactionId);

		//业务处理，修改订单状态、来单提醒
		orderService.paySuccess(outTradeNo);

		//给微信响应
		responseToWeixin(response);
	}

	/**
	 * 读取HTTP请求中的数据
	 *
	 * @param request HTTP请求对象，包含客户端发送的请求数据
	 * @return 请求体中的数据，作为字符串返回
	 * @throws Exception 如果读取过程中发生错误，抛出异常
	 */
	@ApiOperation("读取数据")
	private String readData(HttpServletRequest request) throws Exception {
		// 创建一个StringBuilder对象，初始容量为1024，以避免在追加数据时频繁调整大小
		StringBuilder result = new StringBuilder(1024);

		// 使用try-with-resources语句确保BufferedReader在使用后被正确关闭
		try (BufferedReader reader = request.getReader()) {
			String line;

			// 逐行读取请求体中的数据
			while ((line = reader.readLine()) != null) {
				// 如果result中已经有数据，先追加一个换行符
				if (result.length() > 0) {
					result.append("\n");
				}
				// 将读取到的行追加到result中
				result.append(line);
			}
		}

		// 返回读取到的完整数据
		return result.toString();
	}

	/**
	 * 数据解密
	 *
	 * @param body 请求体中的加密数据
	 * @return 解密后的明文数据
	 * @throws Exception 如果解密过程中发生错误
	 */
	@ApiOperation("数据解密")
	private String decryptData(String body) throws Exception {
		// 解析请求体中的JSON数据
		JSONObject resultObject = JSON.parseObject(body);
		JSONObject resource = resultObject.getJSONObject("resource");

		// 获取加密数据、随机数和附加数据
		String ciphertext = resource.getString("ciphertext");
		String nonce = resource.getString("nonce");
		String associatedData = resource.getString("associated_data");

		// 创建AesUtil对象，用于解密操作
		AesUtil aesUtil = new AesUtil(weChatProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));

		// 使用AesUtil解密密文，返回解密后的明文数据
		return aesUtil.decryptToString(
				associatedData.getBytes(StandardCharsets.UTF_8),
				nonce.getBytes(StandardCharsets.UTF_8),
				ciphertext
		);
	}

	/**
	 * 给微信响应
	 *
	 * @param response HTTP响应对象
	 * @throws Exception 如果响应过程中发生错误
	 */
	@ApiOperation("给微信响应")
	private void responseToWeixin(HttpServletResponse response) throws Exception {
		// 设置HTTP响应状态码为200，表示请求成功
		response.setStatus(200);

		// 创建一个HashMap对象，用于存储响应数据
		HashMap<Object, Object> map = new HashMap<>();
		map.put("code", "SUCCESS"); // 添加响应码，表示成功
		map.put("message", "SUCCESS"); // 添加响应消息，表示成功

		// 设置响应头的Content-type为application/json，表示响应内容为JSON格式
		response.setHeader("Content-type", ContentType.APPLICATION_JSON.toString());

		// 将map对象转换为JSON字符串，并写入响应的输出流中
		response.getOutputStream().write(JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8));

		// 刷新缓冲区，确保所有数据都被写入输出流
		response.flushBuffer();
	}
}