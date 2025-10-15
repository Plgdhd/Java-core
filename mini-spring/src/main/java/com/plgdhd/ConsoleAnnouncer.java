package com.plgdhd;

import com.plgdhd.interfaces.Announcer;
import com.plgdhd.interfaces.Component;
import com.plgdhd.interfaces.InitializingBean;

@Component
public class ConsoleAnnouncer implements Announcer, InitializingBean {
    @Override
    public void announce(String message) {
        System.out.println(message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ConsoleAnnouncer has innitialized");
    }
}