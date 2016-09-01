package com.wcchoi.ruletest;

import com.wcchoi.ruletest.controller.RuleController;
import com.wcchoi.ruletest.model.Condition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 1002639 on 16. 8. 25..
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/wcchoi/ruletest/applicationContext.xml");


        RuleController cont = ctx.getBean(RuleController.class);

        String[] params = new String[args.length-1];
        for(int i=0;i<args.length;i++) {

            if (i != 0)
                params[i-1] = args[i];
        }


//        cont.printConditions(args[0],params);


        List<Condition> conditions = java.util.Arrays.asList(
          new Condition(ConditionKind.DERIVED, "so_order_hist_exist.so_order_hist_exist_tf", "false" )
        );




        System.out.println(cont.isAllowed(args[0], params, conditions));



    }
}
