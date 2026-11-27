package com.projet.listener;

import com.projet.config.SpringContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SpringContext.getContexte();
        sce.getServletContext().log("Clinique : contexte Spring initialise.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SpringContext.fermer();
        sce.getServletContext().log("Clinique : contexte Spring ferme.");
    }
}
