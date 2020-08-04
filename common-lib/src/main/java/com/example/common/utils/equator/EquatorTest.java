package com.example.common.utils.equator;

import com.example.common.bo.UserInfoBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * 属性比对器
 *
 * 一个用于比较两个对象的属性是否相等，并且可以获取所有不相等的属性的比对器
 *
 * 原理：
 *  使用反射得到两个对象的属性(field或者getter方法)，比对该属性的值
 *
 * 注意事项：
 *  1. 如果有一个对象为空，则认为该对象的所有属性都为空
 *  2. 基于getter方法的对比器，会忽略 getClass() 方法
 *  3. 属性比对默认使用 `Objects.deepEquals` 方法进行比对，如果是集合类，会转换为数组进行对比
 *
 * 调用方法：
 * 	这里提供了GetterBaseEquator和FieldBaseEquator两个实现类，分别对应基于getter方法和基于属性的比对器，可以根据自己的需要进行选择
 *
 * 扩展：
 *  1. AbstractEquator 中定义了 `isFieldEquals` 方法，如果你需要对某些特殊属性进行特殊的比对，则可以覆盖此方法
 *  2. @EquatorField 支持定义字段描述和排序，可用于 Log 记录
 *
 * @author Leo
 * @date 2020.04.30
 */
public class EquatorTest {
	private static final Logger logger = LoggerFactory.getLogger(EquatorTest.class);

	public static void main(String[] args) {

//		Equator equator = new GetterBaseEquator();
//		Equator equator = new FieldBaseEquator(Collections.singletonList("userId"), null);
		Equator equator = new FieldBaseEquator();
		UserInfoBo user1 = new UserInfoBo();
		user1.setUserId(1L);
		user1.setOrgId(2L);

		UserInfoBo user2 = new UserInfoBo();
		user1.setUserId(2L);
		user1.setOrgId(3L);

		// 判断属性是否完全相等
		logger.info("{}", equator.isEquals(user1, user2));

		// 获取不同的属性
		List<FieldInfo> diff = equator.getDiffFields(user1, user2);
		logger.info("{}", diff);

	}
}
