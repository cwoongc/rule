package com.wcchoi.ruletest.dao;

import com.wcchoi.ruletest.model.CheckTargetRule;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1002639 on 16. 8. 26..
 */
public class RuleDao {

    @Autowired private SqlSession sqlSession;

    private static final String MAPPER_NAMESPACE = "rule";

//    public List<Map<String,Object>> selectConditions (String ruleTypeId, String ruleTargetId){
//
//        Map<String, Object> params = new HashMap();
//
//        params.put("ruleTypeId", ruleTypeId);
//        params.put("ruleTargetId", ruleTargetId);
//
//        return sqlSession.selectList(RuleDao.MAPPER_NAMESPACE + ".selectConditions",params);
//
//    }


    public List<CheckTargetRule> selectConditions(String ruleTypeId, String[] ruleTargetIds){

        Map<String, Object> params = new HashMap();

        params.put("ruleTypeId", ruleTypeId);
        params.put("ruleTargetIds", ruleTargetIds);

        return sqlSession.selectList(RuleDao.MAPPER_NAMESPACE + ".selectConditions",params);

    }


}
