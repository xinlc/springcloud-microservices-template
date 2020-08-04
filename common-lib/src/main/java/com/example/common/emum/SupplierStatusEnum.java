package com.example.common.emum;

/**
 * 供应商状态枚举类
 *
 * @author Leo
 * @date 2020.04.06
 */
public enum SupplierStatusEnum {

    /** 审核申请中 */
    APPLY(1),
    /** 未审核 */
    UNAUDIT(2),
    /** 审核禁用 */
    FORBID(3),
    /** 审核驳回 */
    REJECT(4),
    /** 审核通过 */
    PASS(5);

    private Integer value;

    SupplierStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static SupplierStatusEnum valueOf(Integer value) {
        for (SupplierStatusEnum status : SupplierStatusEnum.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

}
