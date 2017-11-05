package com.devaton.cryptotracker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.devaton.cryptotracker.HomeActivity;
import com.devaton.cryptotracker.network.DataConnectionChecker;

/**
 * Created by Ese Udom on 11/5/2017.
 */
public class RatesUpdateReceiver extends BroadcastReceiver {

    private Context context;

    public RatesUpdateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (connectionAvailable()) {
            String cryS = intent.getStringExtra("Cryptos");
            String currS = intent.getStringExtra("Currencies");
            HomeActivity.getInstance().new GetRatesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                    "https://min-api.cryptocompare.com/data/pricemulti?fsyms=" + cryS + "&tsyms=" + currS);
        }
    }

    private boolean connectionAvailable() {
        return new DataConnectionChecker(this.context).isConnected();
    }
}
