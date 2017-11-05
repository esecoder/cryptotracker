package com.devaton.cryptotracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ese Udom on 11/5/2017.
 */
public class ResultModel implements Serializable {

    @SerializedName("BTC")
    @Expose
    private BTC btc;
    @SerializedName("ETH")
    @Expose
    private ETH eth;

    /**
     * No args constructor for use in serialization
     */
    public ResultModel() {
    }

    /**
     * @param btc
     * @param eth
     */

    public ResultModel(BTC btc, ETH eth) {
        super();
        this.btc = btc;
        this.eth = eth;
    }

    public BTC getBtc() {
        return btc;
    }

    public void setBtc(BTC btc) {
        this.btc = btc;
    }

    public ETH getEth() {
        return eth;
    }

    public void setEth(ETH eth) {
        this.eth = eth;
    }
}
