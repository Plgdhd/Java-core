package com.plgdhd;

import com.plgdhd.interfaces.Component;
import com.plgdhd.interfaces.InitializingBean;
import com.plgdhd.interfaces.Policeman;

@Component
public class AngryPoliceman implements Policeman, InitializingBean {
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Все вон!!! Пиф паф");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AngryPoliceman has innitialized");
    }
}