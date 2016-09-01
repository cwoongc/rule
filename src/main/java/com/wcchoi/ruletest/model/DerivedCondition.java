package com.wcchoi.ruletest.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 1002639 on 16. 8. 29..
 */
public class DerivedCondition {

    private String derivedConditionTypeId;
    private Map<String, DerivedConditionValue> derivedConditionValues = new TreeMap<>();

    public String getDerivedConditionTypeId() {
        return derivedConditionTypeId;
    }

    public void setDerivedConditionTypeId(String derivedConditionTypeId) {
        this.derivedConditionTypeId = derivedConditionTypeId;
    }

    public Map<String, DerivedConditionValue> getDerivedConditionValues() {
        return derivedConditionValues;
    }

    public void setDerivedConditionValues(Map<String, DerivedConditionValue> derivedConditionValues) {
        this.derivedConditionValues = derivedConditionValues;
    }
}
