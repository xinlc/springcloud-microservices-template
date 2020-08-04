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

import java.util.List;

/**
 * 对象比对器，用于对比两个对象的所有属性是否完全相等
 *
 * @author dadiyang
 * @author Leo
 * @date 2020.04.30
 */
public interface Equator {
	/**
	 * 两个对象是否全相等
	 *
	 * @param first  对象1
	 * @param second 对象2
	 * @return 两个对象是否全相等
	 */
	boolean isEquals(Object first, Object second);

	/**
	 * 获取不相等的属性
	 *
	 * @param first  对象1
	 * @param second 对象2
	 * @return 不相等的属性，键为属性名，值为属性类型
	 */
	List<FieldInfo> getDiffFields(Object first, Object second);
}
