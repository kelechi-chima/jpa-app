package com.kchima.jpa.app.model;

import javax.persistence.*;

@Entity
@NamedQueries(@NamedQuery(name = LimitedClient.ALL_LIMITED_CLIENTS, query = "SELECT l FROM LimitedClient l"))
@NamedNativeQueries(@NamedNativeQuery(name= LimitedClient.ALL_LIMITED_CLIENTS_NATIVE, query = "SELECT * FROM limited_client", resultClass = LimitedClient.class))
public class LimitedClient extends Client {

    public static final String ALL_LIMITED_CLIENTS = "allLimitedClients";
    public static final String ALL_LIMITED_CLIENTS_NATIVE = "allLimitedClientsNative";

    private ClientCompany clientCompany;

    @OneToOne(mappedBy = "client", cascade = CascadeType.PERSIST)
    @Override
    public ClientCompany getClientCompany() {
        return clientCompany;
    }

    @Override
    public void setClientCompany(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }
}
