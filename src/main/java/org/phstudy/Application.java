package org.phstudy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.phstudy.model.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.Properties;

public class Application {
    static Log logger = LogFactory.getLog(Application.class);

    private static final String PERSISTENCE_ECLIPSELINK_UNIT_NAME = "pu-eclipselink";

    private static final String DATABASE_JDBC_URL = "jdbc:postgresql:study";
    private static final String DATABASE_USERNAME = "study";
    private static final String DATABASE_PASSWORD = "";

    private static EntityManagerFactory factory;

    public static void main(String[] args) throws Exception {
        execute(PERSISTENCE_ECLIPSELINK_UNIT_NAME);
    }

    private static void execute(String pu) {
        factory = Persistence.createEntityManagerFactory(pu, getProperties());

        // persist
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        Company company = new Company();
        company.setTasks(Arrays.asList("a", "b"));
        em.persist(company);

        em.getTransaction().commit();
        em.close();


        em = factory.createEntityManager();
        Company company2 = em.find(Company.class, company.getId());
        logger.info(company2.getTasks());

        em.getTransaction().begin();
        company2.setTasks(Arrays.asList("a", "b", "111")); // this seems require to re-set list, or will change nothing.
        em.persist(company2);
        em.getTransaction().commit();
        logger.info(company2.getTasks());

        em.close();

        factory.close();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties
                .put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
        properties.put("javax.persistence.database-major-version", "9");
        properties.put("javax.persistence.database-minor-version", "3");
        properties.put("javax.persistence.jdbc.url", DATABASE_JDBC_URL);
        properties.put("javax.persistence.jdbc.user", DATABASE_USERNAME);
        properties.put("javax.persistence.jdbc.password", DATABASE_PASSWORD);
        properties.put("javax.persistence.database-product-name", "PostgreSQL");
        properties.put("javax.persistence.schema-generation.database.action",
                "drop-and-create");
        properties.put(
                "javax.persistence.schema-generation.create-database-schemas",
                "true");
        return properties;
    }
}
