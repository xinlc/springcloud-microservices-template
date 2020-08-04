package com.example.common.emum;

/**
 * 组织类型枚举类
 *
 * @author Leo
 * @since 2020.08.01
 */
public enum OrgTypeEnum {

    /**
     * 采购方
     */
    PURCHASER(1),
    /**
     * 供应方
     */
    SUPPLIER(2),
    /**
     * 审核机关
     */
    AUDIT(3),
    /**
     * CRM
     */
    CRM(4);

    private Integer value;

    OrgTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static OrgTypeEnum valueOf(Integer value) {
        for (OrgTypeEnum orgType : OrgTypeEnum.values()) {
            if (orgType.value.equals(value)) {
                return orgType;
            }
        }
        return null;
    }

}
