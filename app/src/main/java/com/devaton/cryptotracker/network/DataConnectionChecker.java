package com.devaton.cryptotracker.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ese Udom on 1/3/2017.
 */
public class DataConnectionChecker {
    private Context _context;

    public DataConnectionChecker(Context context) {
        this._context = context;
    }

    public boolean isConnected() {
        //This method has functions only supported from API 21 above {
        /*ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            Network[] nets = connectivity.getAllNetworks();
            NetworkInfo[] info = new NetworkInfo[nets.length];
            if(nets != null){
                for (int i = 0; i < nets.length; i++){
                    info[i] = connectivity.getNetworkInfo(nets[i]);
                    if (info != null)
                        for (int j = 0; j < info.length; j++)
                            if (info[j].getState() == NetworkInfo.State.CONNECTED)
                            {
                                return true;
                            }
                }
            }
        }
        return false;*/
        //This method has functions only supported fron API 21 above }

        //Best Method for now
        /*ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;*/

        //Official Method from Android Developer website 3-17-2016 {
        ConnectivityManager cm =
                (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        //Official Method from Android Developer website 3-17-2016 }
    }

}
