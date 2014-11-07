package com.kchima.jpa.app.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Client {

    private Long id;
    private String name;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(id).append(name).append(getClientCompany()).build();
    }
}
