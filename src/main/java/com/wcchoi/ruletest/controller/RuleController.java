package com.wcchoi.ruletest.controller;

import com.google.gson.GsonBuilder;
import com.wcchoi.ruletest.model.Condition;
import com.wcchoi.ruletest.model.Rule;
import com.wcchoi.ruletest.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * Created by 1002639 on 16. 8. 26..
 */
public class RuleController {

    final static Logger logger = Logger.getLogger(RuleController.class);


    @Autowired
    RuleService ruleService;

    private com.google.gson.Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();


    public void printConditions(String ruleTypeId, String[] ruleTargetIds) {

        Map<Long, Rule> rules = ruleService.selectRules(ruleTypeId, ruleTargetIds);

        logger.info(gson.toJson(rules));

    }

    public boolean isAllowed(String ruleTypeId, String[] ruleTargetIds, List<Condition> conditions) {

        return ruleService.getRuleCheckResult(ruleTypeId, ruleTargetIds, conditions);

    }


}
