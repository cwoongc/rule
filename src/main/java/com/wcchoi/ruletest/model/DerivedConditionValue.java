package com.wcchoi.ruletest.model;

/**
 * Created by 1002639 on 16. 8. 29..
 */
public class DerivedConditionValue {

    private String derivedConditionValueTypeId;
    private String derivedConditionValueName;
    private String derivedConditionValue;
    private String derivedConditionValueDataType;

    public String getDerivedConditionValueTypeId() {
        return derivedConditionValueTypeId;
    }

    public void setDerivedConditionValueTypeId(String derivedConditionValueTypeId) {
        this.derivedConditionValueTypeId = derivedConditionValueTypeId;
    }

    public String getDerivedConditionValueName() {
        return derivedConditionValueName;
    }

    public void setDerivedConditionValueName(String derivedConditionValueName) {
        this.derivedConditionValueName = derivedConditionValueName;
    }

    public String getDerivedConditionValue() {
        return derivedConditionValue;
    }

    public void setDerivedConditionValue(String derivedConditionValue) {
        this.derivedConditionValue = derivedConditionValue;
    }

    public String getDerivedConditionValueDataType() {
        return derivedConditionValueDataType;
    }

    public void setDerivedConditionValueDataType(String derivedConditionValueDataType) {
        this.derivedConditionValueDataType = derivedConditionValueDataType;
    }
}
