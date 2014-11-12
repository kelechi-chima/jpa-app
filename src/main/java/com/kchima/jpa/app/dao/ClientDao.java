package com.kchima.jpa.app.dao;

import com.kchima.jpa.app.model.Client;

import javax.persistence.EntityManager;
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

    public List<String> getAllClientNames() {
        entityManager.getTransaction().begin();
        List<String> clientNames = entityManager.createQuery("SELECT c.name FROM Client c").getResultList();
        entityManager.getTransaction().rollback();
        return clientNames;
    }

    public List<String> getClientNamesMatching(String pattern) {
        entityManager.getTransaction().begin();
        List<String> names = entityManager.createQuery("SELECT c.name FROM Client c WHERE c.name LIKE :pattern").
                setParameter("pattern", pattern).
                getResultList();
        entityManager.getTransaction().rollback();
        return names;
    }
}
