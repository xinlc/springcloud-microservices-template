package com.example.common.emum;

/**
 * UserInfo 状态枚举类
 *
 * @author Leo
 * @date 2020.04.06
 */
public enum UserInfoStateEnum {

    /**
     * 有效
     */
    VALID(1),
    /**
     * 锁定
     */
    LOCK(2),
    /**
     * 禁用
     */
    FORBID(3),
    /**
     * 删除
     */
    DELETE(4),
    /**
     * 待审核
     */
    UNAUDITED(5),
    /**
     * 通过
     */
    PASS(6),
    /**
     * 驳回
     */
    REJECT(7);

    private Integer value;

    UserInfoStateEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static UserInfoStateEnum valueOf(Integer value) {
        for (UserInfoStateEnum userState : UserInfoStateEnum.values()) {
            if (userState.value.equals(value)) {
                return userState;
            }
        }
        return null;
    }

}
