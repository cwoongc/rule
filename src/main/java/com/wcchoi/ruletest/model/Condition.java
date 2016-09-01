package com.wcchoi.ruletest.model;

import com.wcchoi.ruletest.ConditionKind;
import com.wcchoi.ruletest.Operator;

/**
 * Created by 1002639 on 16. 8. 29..
 */
public class Condition {

    public ConditionKind conditionKind;
    private String conditionTypeId;
    private String conditionValue;
    private Operator operator = Operator.EQ;




    public Condition(ConditionKind conditionKind, String conditionTypeId, String conditionValue) {
        this.conditionKind = conditionKind;
        this.conditionTypeId = conditionTypeId;
        this.conditionValue = conditionValue;
    }

    public Condition(String conditionTypeId, String conditionValue, Operator operator) {
        this.conditionKind = ConditionKind.DERIVED;
        this.conditionTypeId = conditionTypeId;
        this.conditionValue = conditionValue;
        this.operator = operator;
    }

    public Condition() {};


    public ConditionKind getConditionKind() {
        return this.conditionKind;
    }

    public void setConditionKind(ConditionKind conditionKind) {
        this.conditionKind = conditionKind;
    }

    public String getConditionTypeId() {
        return conditionTypeId;
    }

    public void setConditionTypeId(String conditionTypeId) {
        this.conditionTypeId = conditionTypeId;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
