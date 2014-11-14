package com.kchima.jpa.app.dao;

import com.kchima.jpa.app.model.*;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ClientDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDaoTest.class);

    private static final String PERSISTENT_UNIT_NAME = "demo";

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
    private final EntityManager entityManager = emf.createEntityManager();

    private static final String CLIENT_NAME_ONE = "demo company";
    private static final String CLIENT_NAME_TWO = "soap inc";

    @Before
    public void setUp() {
        persistTestData(CLIENT_NAME_ONE);
        persistTestData(CLIENT_NAME_TWO);
    }

    @After
    public void tearDown() {
        removeTestData(CLIENT_NAME_ONE);
        removeTestData(CLIENT_NAME_TWO);

        if (entityManager != null) {
            entityManager.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void getAllClients() {
        ClientDao dao = new ClientDao(entityManager);
        List<Client> allClients = dao.getAllClients();
        assertNotNull(allClients);
        assertEquals(2, allClients.size());
        assertEquals(CLIENT_NAME_ONE, allClients.get(0).getName());
        assertEquals(CLIENT_NAME_TWO, allClients.get(1).getName());
    }

    @Test
    public void getAllClientNames() {
        ClientDao dao = new ClientDao(entityManager);
        List<String> allClientNames = dao.getAllClientNames();
        assertNotNull(allClientNames);
        assertEquals(2, allClientNames.size());
        assertTrue(allClientNames.contains(CLIENT_NAME_ONE));
        assertTrue(allClientNames.contains(CLIENT_NAME_TWO));
    }

    @Test
    public void getClientNamesMatching() {
        ClientDao dao = new ClientDao(entityManager);
        List<String> clientNames = dao.getClientNamesMatching("demo%");
        assertEquals(1, clientNames.size());
        assertEquals(CLIENT_NAME_ONE, clientNames.get(0));
    }

    @Test
    public void getClientsWithPhones() {
        entityManager.getTransaction().begin();

        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c WHERE c.name = :name", Client.class);
        query.setParameter("name", CLIENT_NAME_ONE);
        Client client = query.getSingleResult();

        Phone phone = new Phone();
        phone.setNumber("01273200100");
        phone.setType("Landline");
        phone.setClient(client);
        client.setPhones(Arrays.asList(phone));

        entityManager.persist(client);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        @SuppressWarnings("unchecked")
        List<Phone> phones = entityManager.createQuery("SELECT p FROM Phone p").getResultList();
        LOGGER.info("retrieved phones: {}", phones);
        entityManager.getTransaction().rollback();

        List<Client> clients = new ClientDao(entityManager).findClientsWithPhones();
        assertEquals(1, clients.size());
        assertEquals(CLIENT_NAME_ONE, clients.get(0).getName());
    }

    private Client getLimitedClient(String name) {
        LimitedClient client = new LimitedClient();
        client.setName(name);
        return client;
    }

    private ClientCompany getClientCompany() {
        ClientCompany clientCompany = new ClientCompany();
        clientCompany.setIncorporationDate(LocalDate.now().minusMonths(1).toDate());
        clientCompany.setTradingStartDate(new Date());
        return clientCompany;
    }

    private PurchaseInvoice getPurchaseInvoice() {
        PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
        purchaseInvoice.setInvoiceDate(LocalDate.now().toDate());
        return purchaseInvoice;
    }

    private Address getAddress() {
        Address address = new Address();
        address.setLine1("Flat 4");
        address.setLine2("123 Sackville Road");
        address.setLine3("Foobar");
        address.setLine4("East Sussex");
        address.setLine5("AB12 3CD");
        return address;
    }

    private void persistTestData(String clientName) {
        if (entityManager != null) {
            entityManager.getTransaction().begin();
            Client client = getLimitedClient(clientName);
            client.setClientCompany(getClientCompany());
            client.setPurchaseInvoices(Arrays.asList(getPurchaseInvoice()));
            client.setAddress(getAddress());
            entityManager.persist(client);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
    }

    private void removeTestData(String clientName) {
        if (entityManager != null) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            Client client = new ClientDao(entityManager).findClientByName(clientName);
            entityManager.getTransaction().begin();
            entityManager.remove(client);
            entityManager.getTransaction().commit();

            LOGGER.info("deleted client with name {}", clientName);
        }
    }
}
