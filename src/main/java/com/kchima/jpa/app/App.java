package com.kchima.jpa.app;

import com.kchima.jpa.app.model.ClientCompany;
import com.kchima.jpa.app.model.LimitedClient;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager entityManager = null;

        try {
            emf = Persistence.createEntityManagerFactory("demo");
            entityManager = emf.createEntityManager();

            entityManager.getTransaction().begin();

            LimitedClient client = new LimitedClient();
            client.setName("demo company");
            ClientCompany clientCompany = new ClientCompany();
            clientCompany.setIncorporationDate(LocalDate.now().minusMonths(1).toDate());
            clientCompany.setTradingStartDate(LocalDate.now().toDate());
            clientCompany.setClient(client);
            client.setClientCompany(clientCompany);

            entityManager.persist(client);
            entityManager.flush();
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();


        } catch (Exception e) {
            LOGGER.error("Something's amiss!", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
