package com.compensar.cpg1.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {

    private static EntityManagerFactory emf;

    private static EntityManagerFactory buildEMF() {
        Map<String, String> props = new HashMap<>();

        // Lee desde variables de entorno — si no existen usa los valores por defecto (desarrollo local)
        String url  = getEnv("DB_URL",      "jdbc:postgresql://aws-1-us-west-1.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0");
        String user = getEnv("DB_USER",     "postgres.vpxrzsdayfslvhtllqry");
        String pass = getEnv("DB_PASSWORD", "Li53101522#");

        props.put("jakarta.persistence.jdbc.driver",   "org.postgresql.Driver");
        props.put("jakarta.persistence.jdbc.url",      url);
        props.put("jakarta.persistence.jdbc.user",     user);
        props.put("jakarta.persistence.jdbc.password", pass);

        return Persistence.createEntityManagerFactory("CPG_PU", props);
    }

    private static String getEnv(String key, String defaultValue) {
        String val = System.getenv(key);
        return (val != null && !val.isEmpty()) ? val : defaultValue;
    }

    public static EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            emf = buildEMF();
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}