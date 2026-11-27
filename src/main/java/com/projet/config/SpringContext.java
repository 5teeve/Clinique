package com.projet.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class SpringContext {

    private static AnnotationConfigApplicationContext contexte;

    private SpringContext() {
    }

    public static synchronized AnnotationConfigApplicationContext getContexte() {
        if (contexte == null || !contexte.isActive()) {
            contexte = new AnnotationConfigApplicationContext(AppConfig.class);
            Runtime.getRuntime().addShutdownHook(new Thread(SpringContext::fermer));
        }
        return contexte;
    }

    public static <T> T getBean(Class<T> type) {
        return getContexte().getBean(type);
    }

    public static synchronized void fermer() {
        if (contexte != null) {
            contexte.close();
            contexte = null;
        }
    }
}
