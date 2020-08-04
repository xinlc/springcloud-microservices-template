package com.example.common.utils.equator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * 比对时间，忽略毫秒数
 *
 * @author Leo
 * @date 2020.04.30
 */
public class MmInsensitiveEquator extends FieldBaseEquator {

	@Override
	protected boolean isFieldEquals(FieldInfo fieldInfo) {
		if (fieldInfo.getFirstVal() instanceof LocalDateTime) {
			LocalDateTime first = (LocalDateTime) fieldInfo.getFirstVal();
			LocalDateTime second = (LocalDateTime) fieldInfo.getSecondVal();
			if (Objects.equals(first, second)) {
				return true;
			}
			// 忽略毫秒数
//			return Objects.equals(Math.round(first.getTime() / 1000), Math.round(second.getTime() / 1000));
			return Objects.equals(first.toEpochSecond(ZoneOffset.of("+8")),
					second.toEpochSecond(ZoneOffset.of("+8")));
		}
		return super.isFieldEquals(fieldInfo);
	}
}
