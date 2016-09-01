package com.wcchoi.ruletest.model;

/**
 * Created by 1002639 on 16. 8. 29..
 */
public class CheckTargetRule {

    public Long ruleId;
    public String ruleTargetId;
    public String ruleTypeId;
    public String conditionKind;
    public String conditionTypeId;
    public String conditionEntityInstanceId;
    public String derivedConditionValueTypeId;
    public String derivedConditionValue;
    public String derivedConditionValueName;
    public String derivedConditionValueDataType;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleTargetId() {
        return ruleTargetId;
    }

    public void setRuleTargetId(String ruleTargetId) {
        this.ruleTargetId = ruleTargetId;
    }

    public String getRuleTypeId() {
        return ruleTypeId;
    }

    public void setRuleTypeId(String ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public String getConditionKind() {
        return conditionKind;
    }

    public void setConditionKind(String conditionKind) {
        this.conditionKind = conditionKind;
    }

    public String getConditionTypeId() {
        return conditionTypeId;
    }

    public void setConditionTypeId(String conditionTypeId) {
        this.conditionTypeId = conditionTypeId;
    }

    public String getConditionEntityInstanceId() {
        return conditionEntityInstanceId;
    }

    public void setConditionEntityInstanceId(String conditionEntityInstanceId) {
        this.conditionEntityInstanceId = conditionEntityInstanceId;
    }

    public String getDerivedConditionValueTypeId() {
        return derivedConditionValueTypeId;
    }

    public void setDerivedConditionValueTypeId(String derivedConditionValueTypeId) {
        this.derivedConditionValueTypeId = derivedConditionValueTypeId;
    }

    public String getDerivedConditionValue() {
        return derivedConditionValue;
    }

    public void setDerivedConditionValue(String derivedConditionValue) {
        this.derivedConditionValue = derivedConditionValue;
    }

    public String getDerivedConditionValueName() {
        return derivedConditionValueName;
    }

    public void setDerivedConditionValueName(String derivedConditionValueName) {
        this.derivedConditionValueName = derivedConditionValueName;
    }

    public String getDerivedConditionValueDataType() {
        return derivedConditionValueDataType;
    }

    public void setDerivedConditionValueDataType(String derivedConditionValueDataType) {
        this.derivedConditionValueDataType = derivedConditionValueDataType;
    }
}
