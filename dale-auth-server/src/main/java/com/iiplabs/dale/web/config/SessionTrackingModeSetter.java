package com.iiplabs.dale.web.config;

import java.util.EnumSet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.SessionTrackingMode;
import javax.servlet.annotation.WebListener;

@WebListener
public class SessionTrackingModeSetter implements ServletContextListener {

    @Override
    public void contextInitialized (ServletContextEvent event) {
        event.getServletContext()
             .setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
    }

}
