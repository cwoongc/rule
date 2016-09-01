package com.wcchoi.ruletest.model;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 1002639 on 16. 8. 29..
 */
public class Rule {

    private Long ruleId;
    private String ruleTypeId;
    private String ruleTargetId;
    private Map<String, EntityCondition> entityConditions = new TreeMap<>();
    private Map<String, DerivedCondition> derivedConditions = new TreeMap<>();




    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleTypeId() {
        return ruleTypeId;
    }

    public void setRuleTypeId(String ruleTypeId) {
        this.ruleTypeId = ruleTypeId;
    }

    public String getRuleTargetId() {
        return ruleTargetId;
    }

    public void setRuleTargetId(String ruleTargetId) {
        this.ruleTargetId = ruleTargetId;
    }

    public Map<String, EntityCondition> getEntityConditions() {
        return entityConditions;
    }

    public void setEntityConditions(Map<String, EntityCondition> entityConditions) {
        this.entityConditions = entityConditions;
    }

    public Map<String, DerivedCondition> getDerivedConditions() {
        return derivedConditions;
    }

    public void setDerivedConditions(Map<String, DerivedCondition> derivedConditions) {
        this.derivedConditions = derivedConditions;
    }

    public boolean checkEntityCondition(String conditionEntityTypeId, String conditionEntityInstanceId) {
        EntityCondition ec = this.entityConditions.get(conditionEntityTypeId);
        boolean ret = false;

        if(ec != null) {
            if(conditionEntityInstanceId.equals(ec.getConditionEntityInstatnceId()))
                ret = true;
        }
        return ret;
    }

    private boolean compareConditionTypes(List<Condition> conditions) {
        boolean ret = true;


        Map<String, Boolean> checklist = new HashMap<>();


        entityConditions.forEach((k,v)->{
            checklist.put(k, false);
        });

        derivedConditions.forEach((k,v)->{
            String derivedConditionTypeId = k;
            v.getDerivedConditionValues().keySet().forEach((derivedConditionValueTypeId)->{
                checklist.put(derivedConditionTypeId + "." + derivedConditionValueTypeId, false);
            });
        });

        for (Condition condition : conditions) {
            if (checklist.containsKey(condition.getConditionTypeId()))
                checklist.replace(condition.getConditionTypeId(), true);
        }

        for(Map.Entry<String, Boolean> e : checklist.entrySet()) {
            if(! e.getValue()) {
                ret = false;
                break;
            }
        }

        return ret;
    }

    private boolean compareConditionValues(List<Condition> conditions) {

        boolean ret = true;

        for(Condition condition : conditions) {
            switch (condition.getConditionKind()) {
                case ENTITY:
                    EntityCondition ec = this.entityConditions.get(condition.getConditionTypeId());
                    if(! ec.getConditionEntityInstatnceId().equals(condition.getConditionValue()))
                        return false;
                    break;
                case DERIVED:
                    String derivedConditionTypeId = condition.getConditionTypeId().substring(0,condition.getConditionTypeId().indexOf('.'));
                    DerivedCondition dc = this.derivedConditions.get(derivedConditionTypeId);

                    Map<String, DerivedConditionValue> dcvs = dc.getDerivedConditionValues();
                    String derivedConditionValueTypeId = condition.getConditionTypeId().substring(condition.getConditionTypeId().indexOf('.')+1);
                    DerivedConditionValue dcv = dcvs.get(derivedConditionValueTypeId);
                    String conditionValue = condition.getConditionValue();

                    switch(dcv.getDerivedConditionValueDataType().toUpperCase()) {

                        case "BOOLEAN":
                            boolean vsb = Boolean.parseBoolean(dcv.getDerivedConditionValue());
                            boolean vib = Boolean.parseBoolean(conditionValue);

                            if(vsb != vib)
                                return false;
                            break;

                        case "VARCHAR":
                            if(!dcv.getDerivedConditionValue().equals(conditionValue))
                                return false;
                            break;

                        case "NUMERIC":
                            BigDecimal vsbd = new BigDecimal(dcv.getDerivedConditionValue());
                            BigDecimal vibd = new BigDecimal(conditionValue);

                            switch (condition.getOperator()) {
                                case LT:
                                    if((vibd.compareTo(vsbd) != -1))
                                        return false;
                                    break;
                                case LE:
                                    if((vibd.compareTo(vsbd) == 1))
                                        return false;
                                    break;
                                case EQ:
                                    if((vibd.compareTo(vsbd) != 0))
                                        return false;
                                    break;
                                case GE:
                                    if((vibd.compareTo(vsbd) == -1 ))
                                        return false;
                                    break;
                                case GT:
                                    if((vibd.compareTo(vsbd) != 1))
                                        return false;
                                    break;
                            }
                            break;

                        case "INTEGER":
                            Integer vsint = Integer.parseInt(dcv.getDerivedConditionValue());
                            Integer viint = Integer.parseInt(conditionValue);

                            switch (condition.getOperator()) {
                                case LT:
                                    if((viint.compareTo(vsint) != -1))
                                        return false;
                                    break;
                                case LE:
                                    if((viint.compareTo(vsint) == 1))
                                        return false;
                                    break;
                                case EQ:
                                    if((viint.compareTo(vsint) != 0))
                                        return false;
                                    break;
                                case GE:
                                    if((viint.compareTo(vsint) == -1 ))
                                        return false;
                                    break;
                                case GT:
                                    if((viint.compareTo(vsint) != 1))
                                        return false;
                                    break;
                            }
                            break;
                        case "BIGINT":
                            BigInteger vsbint = new BigInteger(dcv.getDerivedConditionValue());
                            BigInteger vibint = new BigInteger(conditionValue);

                            switch (condition.getOperator()) {
                                case LT:
                                    if((vibint.compareTo(vsbint) != -1))
                                        return false;
                                    break;
                                case LE:
                                    if((vibint.compareTo(vsbint) == 1))
                                        return false;
                                    break;
                                case EQ:
                                    if((vibint.compareTo(vsbint) != 0))
                                        return false;
                                    break;
                                case GE:
                                    if((vibint.compareTo(vsbint) == -1 ))
                                        return false;
                                    break;
                                case GT:
                                    if((vibint.compareTo(vsbint) != 1))
                                        return false;
                                    break;
                            }
                            break;
                        case "DATE":
                            break;
                        case "TIMESTAMPTZ":
                            break;
                        case "TIMETZ":
                            break;




                    }



                    break;
            }
        }

        return ret;
    }

    public boolean checkConditions(List<Condition> conditions) {

        if(! compareConditionTypes(conditions))
            return false;

        if(! compareConditionValues(conditions))
            return false;


        return true;
    }




    public static Map<Long, Rule> getRules(List<CheckTargetRule> ctrs) {
        Map<Long, Rule> rules = new TreeMap<Long, Rule>();

        for(int i=0;i<ctrs.size();i++) {

            CheckTargetRule ctr = ctrs.get(i);
            Long ruleId = ctr.getRuleId();
            String conditionKind = ctr.getConditionKind();

            Rule r = null;

            if(rules.containsKey(ruleId))
                r = rules.get(ruleId);
            else {
                r = new Rule();
                rules.put(ruleId, r);
                r.setRuleId(ruleId);
                r.setRuleTypeId(ctr.getRuleTypeId());
                r.setRuleTypeId(ctr.getRuleTargetId());
            }

            switch (conditionKind) {
                case "entity":
                    EntityCondition ec = new EntityCondition();
                    ec.setConditionEntityTypeId(ctr.getConditionTypeId());
                    ec.setConditionEntityInstatnceId(ctr.getConditionEntityInstanceId());
                    r.getEntityConditions().put(ctr.getConditionTypeId(),ec);
                    break;
                case "derived":

                    DerivedCondition dc = null;

                    if(r.getDerivedConditions().containsKey(ctr.getConditionTypeId())) {
                        dc = r.getDerivedConditions().get(ctr.getConditionTypeId());
                    } else {
                        dc = new DerivedCondition();
                        r.getDerivedConditions().put(ctr.getConditionTypeId(),dc);
                        dc.setDerivedConditionTypeId(ctr.getConditionTypeId());
                    }

                    DerivedConditionValue dcv = new DerivedConditionValue();
                    dcv.setDerivedConditionValueTypeId(ctr.getDerivedConditionValueTypeId());
                    dcv.setDerivedConditionValueName(ctr.getDerivedConditionValueName());
                    dcv.setDerivedConditionValue(ctr.getDerivedConditionValue());
                    dcv.setDerivedConditionValueDataType(ctr.derivedConditionValueDataType);
                    dc.getDerivedConditionValues().put(ctr.getDerivedConditionValueTypeId(), dcv);
                    break;
            }

        }
        return rules;
    }
}
