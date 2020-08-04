/*
 * Copyright 2019 Xuyang Huang.
 * Copyright 2020 Leo Xin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.common.utils.equator;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 基于属性的比对器
 *
 * @author dadiyang
 * @author Leo
 * @date 2020.04.30
 */
public class FieldBaseEquator extends AbstractEquator {
	public FieldBaseEquator() {
	}

	/**
	 * 指定包含或排除某些字段
	 *
	 * @param includeFields 包含字段，若为 null 或空集，则不指定
	 * @param excludeFields 排除字段，若为 null 或空集，则不指定
	 */
	public FieldBaseEquator(List<String> includeFields, List<String> excludeFields) {
		super(includeFields, excludeFields);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<FieldInfo> getDiffFields(Object first, Object second) {
		if (first == second) {
			return Collections.emptyList();
		}

		// 先尝试判断是否为简单数据类型
		if (isSimpleField(first, second)) {
			return compareSimpleField(first, second);
		}

		Object obj = first == null ? second : first;
		Class<?> clazz = obj.getClass();
		List<FieldInfo> diffField = new LinkedList<>();

		// 获取所有字段
		Field[] fields = clazz.getDeclaredFields();

		// 遍历所有的字段
		for (Field field : fields) {
			String fieldName = field.getName();
			try {
				// 开启访问权限，否则获取私有字段会报错
				field.setAccessible(true);
				Object firstVal = first == null ? null : field.get(first);
				Object secondVal = second == null ? null : field.get(second);

				// 封装字段信息
				FieldInfo fieldInfo = new FieldInfo(fieldName, field.getType(), firstVal, secondVal);

				// 获取注解信息
				EquatorField equatorField = field.getAnnotation(EquatorField.class);
				if (null != equatorField) {
					fieldInfo.setDescription(equatorField.description());
					fieldInfo.setOrdinal(equatorField.ordinal());
				}

				boolean eq = isFieldEquals(fieldInfo);
				if (!eq) {
					// 记录不相等的字段
					diffField.add(fieldInfo);
				}
			} catch (IllegalAccessException e) {
				// 只要调用了 field.setAccessible(true) 就不会报这个异常
				throw new IllegalStateException("获取属性进行比对发生异常: " + fieldName, e);
			}
		}

		// 排序
//		Collections.sort(diffField, (o1, o2) -> Integer.compare(o1.getOrdinal(), o2.getOrdinal()));
		diffField.sort(Comparator.comparingInt(FieldInfo::getOrdinal));
		return diffField;
	}


}
