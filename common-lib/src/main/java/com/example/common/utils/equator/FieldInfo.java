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

import java.util.Objects;

/**
 * 不同的属性
 *
 * @author dadiyang
 * @author Leo
 * @date 2020.04.30
 */
public class FieldInfo {
	private String fieldName;
	private Class<?> fieldType;
	private Object firstVal;
	private Object secondVal;
	private String description;
	private int ordinal;

	public FieldInfo() {
	}

	public FieldInfo(String fieldName, Class<?> fieldType, Object firstVal, Object secondVal) {
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.firstVal = firstVal;
		this.secondVal = secondVal;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	public Object getFirstVal() {
		return firstVal;
	}

	public void setFirstVal(Object firstVal) {
		this.firstVal = firstVal;
	}

	public Object getSecondVal() {
		return secondVal;
	}

	public void setSecondVal(Object secondVal) {
		this.secondVal = secondVal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		FieldInfo fieldInfo = (FieldInfo) o;
		return Objects.equals(fieldName, fieldInfo.fieldName) &&
				Objects.equals(fieldType, fieldInfo.fieldType) &&
				Objects.equals(firstVal, fieldInfo.firstVal) &&
				Objects.equals(secondVal, fieldInfo.secondVal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fieldName, fieldType, firstVal, secondVal);
	}

	@Override
	public String toString() {
		return "FieldInfo{" +
				"fieldName='" + fieldName + '\'' +
				", fieldType=" + fieldType +
				", firstVal=" + firstVal +
				", secondVal=" + secondVal +
				", description=" + description +
				", ordinal=" + ordinal +
				'}';
	}
}
