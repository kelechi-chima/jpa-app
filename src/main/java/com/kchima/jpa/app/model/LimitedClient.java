package com.kchima.jpa.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class LimitedClient extends Client {

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
