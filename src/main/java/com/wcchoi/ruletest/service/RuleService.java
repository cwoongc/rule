package com.wcchoi.ruletest.service;

import com.wcchoi.ruletest.dao.RuleDao;
import com.wcchoi.ruletest.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by 1002639 on 16. 8. 26..
 */
public class RuleService {

    @Autowired private RuleDao ruleDao;

    public boolean getRuleCheckResult(String ruleTypeId, String[] ruleTargetIds, List<Condition> conditions) {

        Map<Long, Rule> rules = this.selectRules(ruleTypeId, ruleTargetIds);

        for(Map.Entry<Long, Rule> entry : rules.entrySet()) {
            Rule r = entry.getValue();

            boolean checkResult = r.checkConditions(conditions);

            if(checkResult)
                return true;
        }

        return false;

    }








    public Map<Long, Rule> selectRules(String ruleTypeId, String[] ruleTargetIds) {

        List<CheckTargetRule> ctrs = this.selectConditions(ruleTypeId, ruleTargetIds);

        return Rule.getRules(ctrs);
    }


    public Map<Long, Rule> selectRules(String ruleTypeId, String ruleTargetId) {
        return selectRules(ruleTypeId, new String[]{ruleTargetId});
    }



    public List<CheckTargetRule> selectConditions(String ruleTypeId, String[] ruleTargetIds) {

        return ruleDao.selectConditions(ruleTypeId, ruleTargetIds);
    }





}
