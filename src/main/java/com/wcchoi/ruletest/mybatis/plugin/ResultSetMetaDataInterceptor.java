package com.wcchoi.ruletest.mybatis.plugin;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * MyBatis의 ResultSetHandler.handleResultSets(Statement arg) 메소드의 부가기능(어드바이스).
 * MyBatis 플러그인으로 제공되는 프록시 기반 AOP이다. <br>
 * MyBatis의 경우 returntype 이 hashmap일 경우 result 필드중 null 값이 포함되어있으면 해당행에는 해당 필드 정보가 아예 포함되지 않은 채로 전달된다.
 * 이를 방지하기 위해 MyBatis의 플러그인을 사용하여 결과셋을 받은 바로 직후 미리 ResultSetMetaData를 통해 검색해둔
 * 모든 컬럼명을 결과셋의 첫행의 컬럼명으로 put해 넣는 로직을 추가하도록 하였다.
 *
 * @author wcchoi
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class ResultSetMetaDataInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        Statement statement = (Statement) args[0];

        ResultSet rs = statement.getResultSet();

        Set<String> columnLabels = null;

        while (rs == null) {
            if (statement.getMoreResults()) {
                rs = statement.getResultSet();
            } else {
                if (statement.getUpdateCount() == -1) {
                    break;
                }
            }
        }
        if (rs != null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            columnLabels = new HashSet();

            for (int i = 1; i <= columnCount; i++) {
                columnLabels.add(rsmd.getColumnLabel(i));
            }
        }


        Object obj = invocation.proceed();
        List<Object> results = (List<Object>)obj;

        if(rs != null && results.size() != 0) {

            for(Object res : results) {

                if (res instanceof Map) {

                    Map row = (Map)res;

                    for (String columnLabel : columnLabels) {
                        if (!row.containsKey(columnLabel)) {
                            row.put(columnLabel, null);
                        }
                    }
                }
            }

            if(results.size() == 0) {
                Map emptyrow = new HashMap();
                for(String columnLabel:columnLabels) {
                    emptyrow.put(columnLabel,null);
                }
                results.set(0,emptyrow);
            }
        }

        return results;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
