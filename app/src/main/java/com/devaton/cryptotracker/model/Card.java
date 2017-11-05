package com.devaton.cryptotracker.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ese Udom on 11/4/2017.
 */

@Table(name = "Cards")
public class Card extends Model implements Serializable {

    @Column(name = "crypto_type")
    private String cryptoType;
    @Column(name = "last_rate")
    private String lastRate;
    @Column(name = "currency")
    private String currency;

    /**
     * No args constructor for use in serialization
     */
    public Card() {
        super();
    }

    /**
    * @param cryptoType
     * @param lastRate
    * @param currency
    */

    public Card(String cryptoType, String lastRate, String currency) {
        super();
        this.cryptoType = cryptoType;
        this.lastRate = lastRate;
        this.currency = currency;
    }

    public static Card getSingleRecord(String id) {
        return new Select().from(Card.class).where("user_id = ?", id).executeSingle();
    }

    public static List<Card> getAll() {
        return new Select().from(Card.class).execute();
    }

    public static Card deleteSingleRecord(String c, String t) {
        return new Delete().from(Card.class).where("currency = ? AND crypto_type = ?", c, t).executeSingle();
    }

    public String getCryptoType() {
        return cryptoType;
    }

    public void setCryptoType(String cryptoType) {
        this.cryptoType = cryptoType;
    }

    public String getLastRate() {
        return lastRate;
    }

    public void setLastRate(String lastRate) {
        this.lastRate = lastRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
