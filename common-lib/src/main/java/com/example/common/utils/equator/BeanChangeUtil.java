package com.example.common.utils.equator;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 对比 Bean 差异工具
 *
 * @author Leo
 * @date 2020.04.30
 */
public class BeanChangeUtil {

	/**
	 * 对比 bean 前后差异
	 *
	 * @param beforeObj
	 * @param afterObj
	 * @return
	 * @throws Exception
	 */
	public static List<Comparison> contrastObj(Object beforeObj, Object afterObj) throws Exception {
		List<Comparison> differents = new ArrayList<>();

		if (beforeObj == null) throw new Exception("原对象不能为空");
		if (afterObj == null) throw new Exception("新对象不能为空");
		if (!beforeObj.getClass().isAssignableFrom(afterObj.getClass())) {
			throw new Exception("两个对象不相同，无法比较");
		}

		// 取出属性
		Field[] beforeFields = beforeObj.getClass().getDeclaredFields();
		Field[] afterFields = afterObj.getClass().getDeclaredFields();

		// 可以访问私有属性
		Field.setAccessible(beforeFields, true);
		Field.setAccessible(afterFields, true);

		// 遍历取出差异值
		if (beforeFields.length > 0) {
			for (int i = 0; i < beforeFields.length; i++) {
				// 排除序列化属性
				if ("serialVersionUID".equals(beforeFields[i].getName())) {
					continue;
				}

//				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
//				Method getMethod = pd.getReadMethod();
//				Object o1 = getMethod.invoke(pojo1);
//				Object o2 = getMethod.invoke(pojo2);

				// 获取属性值
				Object beforeValue = beforeFields[i].get(beforeObj);
				Object afterValue = afterFields[i].get(afterObj);

				if ((null != beforeValue && !"".equals(beforeValue) && !beforeValue.equals(afterValue))
						|| ((null == beforeValue || "".equals(beforeValue)) && null != afterValue)) {
					Comparison comparison = new Comparison();
					comparison.setField(beforeFields[i].getName());
					comparison.setBefore(beforeValue);
					comparison.setAfter(afterValue);
					differents.add(comparison);
				}
			}
		}

		return differents;
	}

	/**
	 * 比较器结果
	 *
	 * @author Leo
	 * @date 2020.04.30
	 */
	public static class Comparison {
		// 字段
		private String Field;

		// 字段旧值
		private Object before;

		// 字段新值
		private Object after;

		public String getField() {
			return Field;
		}

		public void setField(String field) {
			Field = field;
		}

		public Object getBefore() {
			return before;
		}

		public void setBefore(Object before) {
			this.before = before;
		}

		public Object getAfter() {
			return after;
		}

		public void setAfter(Object after) {
			this.after = after;
		}
	}
}
