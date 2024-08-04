package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class SetmealDAO {
	@Autowired
	private SetmealMapper setmealMapper;

	/**
	 * 根据条件统计套餐数量
	 *
	 * @param map 包含查询参数的映射
	 *            - "status": 套餐状态
	 *            - "categoryId": 套餐类别ID
	 * @return 套餐数量
	 */
	public Integer countByMap(Map<String, Object> map) {
		LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper
				.eq(map.get("status") != null, Setmeal::getStatus, map.get("status"))
				.eq(map.get("categoryId") != null, Setmeal::getCategoryId, map.get("categoryId"));

		return Math.toIntExact(setmealMapper.selectCount(queryWrapper));
	}
}
