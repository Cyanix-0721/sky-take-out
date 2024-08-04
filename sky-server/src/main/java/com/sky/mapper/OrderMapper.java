package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
	/**
	 * 查询商品销量排名
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return 销量前十的商品列表
	 */
	List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}