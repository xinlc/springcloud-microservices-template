package com.example.common.annotation;

import java.lang.annotation.*;

/**
 * 接口幂等
 * <p>
 * 实现思路：
 * 1. 每一次请求创建一个唯一标识 token, 先获取 token, 并将此 token 存入 redis
 * 2. 请求接口时, 将此 token 放到 header 或者作为请求参数请求接口, 后端接口判断 redis 中是否存在此 token
 * 3. 如果存在, 正常处理业务逻辑, 并从 redis 中删除此 token, 如果是重复请求, 由于 token 已被删除, 则不能通过校验
 * 4. 如果不存在, 说明参数不合法或者是重复请求
 *
 * @author Leo
 * @date 2020.04.29
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {
}
