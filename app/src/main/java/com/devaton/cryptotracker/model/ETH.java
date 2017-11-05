package com.devaton.cryptotracker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ese Udom on 11/5/2017.
 */
public class ETH implements Serializable {

    @SerializedName("NGN")
    @Expose
    private double ngn;
    @SerializedName("USD")
    @Expose
    private double usd;
    @SerializedName("GBP")
    @Expose
    private double gbp;
    @SerializedName("EUR")
    @Expose
    private double eur;
    @SerializedName("JPY")
    @Expose
    private double jpy;
    @SerializedName("CHY")
    @Expose
    private double cny;
    @SerializedName("AUD")
    @Expose
    private double aud;
    @SerializedName("NZD")
    @Expose
    private double nzd;
    @SerializedName("RUB")
    @Expose
    private double rub;
    @SerializedName("CHF")
    @Expose
    private double chf;
    @SerializedName("SEK")
    @Expose
    private double sek;
    @SerializedName("NOK")
    @Expose
    private double nok;
    @SerializedName("INR")
    @Expose
    private double inr;
    @SerializedName("SGD")
    @Expose
    private double sgd;
    @SerializedName("TWD")
    @Expose
    private double twd;
    @SerializedName("TRY")
    @Expose
    private double _try;
    @SerializedName("BRL")
    @Expose
    private double brl;
    @SerializedName("MXN")
    @Expose
    private double mxn;
    @SerializedName("ZAR")
    @Expose
    private double zar;
    @SerializedName("ILS")
    @Expose
    private double ils;

    /**
     * No args constructor for use in serialization
     */
    public ETH() {
    }

    /**
     * @param ngn
     * @param usd
     * @param gbp
     * @param jpy
     * @param cny
     * @param aud
     * @param nzd
     * @param rub
     * @param chf
     * @param sek
     * @param nok
     * @param inr
     * @param sgd
     * @param twd
     * @param _try
     * @param brl
     * @param mxn
     * @param zar
     * @param ils
     * @param eur
     */

    public ETH(double ngn, double usd, double gbp, double jpy, double cny, double aud, double nzd, double rub, double chf, double sek,
               double nok, double inr, double sgd, double twd, double _try, double brl, double mxn, double zar, double ils, double eur) {
        super();
        this.ngn = ngn;
        this.usd = usd;
        this.gbp = gbp;
        this.jpy = jpy;
        this.cny = cny;
        this.aud = aud;
        this.nzd = nzd;
        this.rub = rub;
        this.chf = chf;
        this.sek = sek;
        this.nok = nok;
        this.inr = inr;
        this.sgd = sgd;
        this.twd = twd;
        this._try = _try;
        this.brl = brl;
        this.mxn = mxn;
        this.zar = zar;
        this.ils = ils;
        this.eur = eur;
    }

    public double getNgn() {
        return ngn;
    }

    public void setNgn(double ngn) {
        this.ngn = ngn;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public double getGbp() {
        return gbp;
    }

    public void setGbp(double gbp) {
        this.gbp = gbp;
    }

    public double getEur() {
        return eur;
    }

    public void setEur(double eur) {
        this.eur = eur;
    }

    public double getJpy() {
        return jpy;
    }

    public void setJpy(double jpy) {
        this.jpy = jpy;
    }

    public double getCny() {
        return cny;
    }

    public void setCny(double cny) {
        this.cny = cny;
    }

    public double getAud() {
        return aud;
    }

    public void setAud(double aud) {
        this.aud = aud;
    }

    public double getNzd() {
        return nzd;
    }

    public void setNzd(double nzd) {
        this.nzd = nzd;
    }

    public double getRub() {
        return rub;
    }

    public void setRub(double rub) {
        this.rub = rub;
    }

    public double getChf() {
        return chf;
    }

    public void setChf(double chf) {
        this.chf = chf;
    }

    public double getSek() {
        return sek;
    }

    public void setSek(double sek) {
        this.sek = sek;
    }

    public double getNok() {
        return nok;
    }

    public void setNok(double nok) {
        this.nok = nok;
    }

    public double getInr() {
        return inr;
    }

    public void setInr(double inr) {
        this.inr = inr;
    }

    public double getSgd() {
        return sgd;
    }

    public void setSgd(double sgd) {
        this.sgd = sgd;
    }

    public double getTwd() {
        return twd;
    }

    public void setTwd(double twd) {
        this.twd = twd;
    }

    public double get_try() {
        return _try;
    }

    public void set_try(double _try) {
        this._try = _try;
    }

    public double getBrl() {
        return brl;
    }

    public void setBrl(double brl) {
        this.brl = brl;
    }

    public double getMxn() {
        return mxn;
    }

    public void setMxn(double mxn) {
        this.mxn = mxn;
    }

    public double getZar() {
        return zar;
    }

    public void setZar(double zar) {
        this.zar = zar;
    }

    public double getIls() {
        return ils;
    }

    public void setIls(double ils) {
        this.ils = ils;
    }
}
