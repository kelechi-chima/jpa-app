package com.kchima.jpa.app.dao;

import com.kchima.jpa.app.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ClientDao {

    private final EntityManager entityManager;

    public ClientDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Client> getAllClients() {
        entityManager.getTransaction().begin();

        List<Client> clients = entityManager.createNamedQuery(Client.ALL_CLIENTS, Client.class).getResultList();

        entityManager.getTransaction().rollback();
        return clients;
    }

    public Client findClientByName(String name) {
        entityManager.getTransaction().begin();

        Client client = entityManager.createNamedQuery(Client.CLIENT_BY_NAME, Client.class).
                setParameter("name", name).
                getSingleResult();

        entityManager.getTransaction().rollback();

        return client;
    }

    public List<String> getAllClientNames() {
        entityManager.getTransaction().begin();

        @SuppressWarnings("unchecked")
        List<String> clientNames = entityManager.createQuery("SELECT c.name FROM Client c").getResultList();
        entityManager.getTransaction().rollback();

        return clientNames;
    }

    public List<String> getClientNamesMatching(String pattern) {
        entityManager.getTransaction().begin();

        @SuppressWarnings("unchecked")
        List<String> names = entityManager.createQuery(
                "SELECT c.name FROM Client c WHERE c.name LIKE :pattern").
                setParameter("pattern", pattern).
                getResultList();

        entityManager.getTransaction().rollback();
        return names;
    }

    public List<Client> findClientsWithPhones() {
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery(
                "SELECT c FROM Client c WHERE EXISTS " +
                "(SELECT 1 FROM c.phones p)");

        @SuppressWarnings("unchecked")
        List<Client> clients = query.getResultList();

        entityManager.getTransaction().rollback();

        return clients;
    }
}
