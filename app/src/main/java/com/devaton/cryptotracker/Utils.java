package com.devaton.cryptotracker;

import android.content.Context;
import android.widget.Toast;

import com.devaton.cryptotracker.model.BTC;
import com.devaton.cryptotracker.model.Card;
import com.devaton.cryptotracker.model.ETH;

import java.util.List;

/**
 * Created by Ese Udom on 11/5/2017.
 */
public class Utils {

    public static int getSign(String s) {
        switch (s) {
            case "NGN": return R.string.naira_sign;
            case "USD": return R.string.dollar_sign;
            case "GBP": return R.string.british_pounds_sign;
            case "EUR": return R.string.euro_sign;
            case "JPY": return R.string.yen_sign;
            case "CNY": return R.string.yuan_sign;
            case "AUD": return R.string.dollar_sign;
            case "NZD": return R.string.dollar_sign;
            case "RUB": return R.string.russian_ruble_sign;
            case "CHF": return R.string.swiss_franc_sign;
            case "SEK": return R.string.swedish_krona_sign;
            case "NOK": return R.string.nor_krone_sign;
            case "INR": return R.string.indian_rupee_sign;
            case "SGD": return R.string.dollar_sign;
            case "TWD": return R.string.dollar_sign;
            case "TRY": return R.string.turk_lira_sign;
            case "BRL": return R.string.brazil_real_sign;
            case "MXN": return R.string.dollar_sign;
            case "ZAR": return R.string.rand_sign;
            case "ILS": return R.string.shekel_sign;
            case "BTC": return R.string.btc;
            case "ETH": return R.string.eth;
            default: return R.string.naira_sign;
        }
    }

    public static int getCryptoIcon(String s) {
        switch (s) {
            case "BTC": return R.drawable.bitcoin_icon;
            case "ETH": return R.drawable.ethereum_icon;
            default: return R.drawable.bitcoin_icon;
        }
    }

    public static boolean isDuplicate(Card card, Context context) {
        List<Card> cards = Card.getAll();
        boolean r = false;
        if (cards != null) {
            if (!cards.isEmpty()) {
                for (Card c : cards) {
                    if (c.getCryptoType().equals(card.getCryptoType()) && c.getCurrency().equals(card.getCurrency())) {
                        r = true;
                        return true;
                    }
                }
            } else
                r = false;
        } else
            r = false;
        return r;
    }

    public static double getCurrRate(String s, BTC btc, ETH eth) {
        if (btc != null) {
            switch (s) {
                case "NGN":
                    return btc.getNgn();
                case "USD":
                    return btc.getUsd();
                case "GBP":
                    return btc.getGbp();
                case "EUR":
                    return btc.getEur();
                case "JPY":
                    return btc.getJpy();
                case "CNY":
                    return btc.getCny();
                case "AUD":
                    return btc.getAud();
                case "NZD":
                    return btc.getNzd();
                case "RUB":
                    return btc.getRub();
                case "CHF":
                    return btc.getChf();
                case "SEK":
                    return btc.getSek();
                case "NOK":
                    return btc.getNok();
                case "INR":
                    return btc.getInr();
                case "SGD":
                    return btc.getSgd();
                case "TWD":
                    return btc.getTwd();
                case "TRY":
                    return btc.get_try();
                case "BRL":
                    return btc.getBrl();
                case "MXN":
                    return btc.getMxn();
                case "ZAR":
                    return btc.getZar();
                case "ILS":
                    return btc.getIls();
                default:
                    return btc.getNgn();
            }
        } else {
            switch (s) {
                case "NGN":
                    return eth.getNgn();
                case "USD":
                    return eth.getUsd();
                case "GBP":
                    return eth.getGbp();
                case "EUR":
                    return eth.getEur();
                case "JPY":
                    return eth.getJpy();
                case "CNY":
                    return eth.getCny();
                case "AUD":
                    return eth.getAud();
                case "NZD":
                    return eth.getNzd();
                case "RUB":
                    return eth.getRub();
                case "CHF":
                    return eth.getChf();
                case "SEK":
                    return eth.getSek();
                case "NOK":
                    return eth.getNok();
                case "INR":
                    return eth.getInr();
                case "SGD":
                    return eth.getSgd();
                case "TWD":
                    return eth.getTwd();
                case "TRY":
                    return eth.get_try();
                case "BRL":
                    return eth.getBrl();
                case "MXN":
                    return eth.getMxn();
                case "ZAR":
                    return eth.getZar();
                case "ILS":
                    return eth.getIls();
                default:
                    return eth.getNgn();
            }
        }
    }
}
