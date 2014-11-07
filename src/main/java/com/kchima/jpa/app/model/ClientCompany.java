package com.kchima.jpa.app.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ClientCompany {

    private Long id;
    private LimitedClient client;
    private Date incorporationDate;
    private Date tradingStartDate;
    private Date tradingEndDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    public LimitedClient getClient() {
        return client;
    }

    public void setClient(LimitedClient client) {
        this.client = client;
    }

    @Temporal(TemporalType.DATE)
    public Date getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(Date incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    @Temporal(TemporalType.DATE)
    public Date getTradingStartDate() {
        return tradingStartDate;
    }

    public void setTradingStartDate(Date tradingStartDate) {
        this.tradingStartDate = tradingStartDate;
    }

    @Temporal(TemporalType.DATE)
    public Date getTradingEndDate() {
        return tradingEndDate;
    }

    public void setTradingEndDate(Date tradingEndDate) {
        this.tradingEndDate = tradingEndDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(id).append(incorporationDate).append(tradingStartDate).append(tradingEndDate).append(client).build();
    }
}
