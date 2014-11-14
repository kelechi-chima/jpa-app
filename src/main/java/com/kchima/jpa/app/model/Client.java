package com.kchima.jpa.app.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = Client.ALL_CLIENTS, query = "SELECT c FROM Client c"),
        @NamedQuery(name = Client.CLIENT_BY_NAME, query = "SELECT c FROM Client c WHERE c.name = :name")
})
public abstract class Client {

    public static final String ALL_CLIENTS = "allClients";
    public static final String CLIENT_BY_NAME = "clientByName";

    private Long id;
    private String name;
    private Address address;
    private List<PurchaseInvoice> purchaseInvoices;
    private List<Phone> phones;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public abstract ClientCompany getClientCompany();

    public abstract void setClientCompany(ClientCompany clientCompany);

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public List<PurchaseInvoice> getPurchaseInvoices() {
        return purchaseInvoices;
    }

    public void setPurchaseInvoices(List<PurchaseInvoice> purchaseInvoices) {
        this.purchaseInvoices = purchaseInvoices;
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                append("id", getId()).append("name", getName()).build();
    }
}
