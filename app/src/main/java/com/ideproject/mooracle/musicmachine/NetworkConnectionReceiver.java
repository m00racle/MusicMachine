package com.ideproject.mooracle.musicmachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class NetworkConnectionReceiver extends BroadcastReceiver {
    public static final String NOTIFY_NETWORK_CHANGES = "NOTIFY_NETWORK_CHANGES";
    public static final String EXTRA_IS_CONNECTED = "EXTRA_IS_CONNECTED";
    //need to be public to be accessed from main activity

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        //make new intent to broadcast the network status info
        Intent localIntent = new Intent(NOTIFY_NETWORK_CHANGES);

        //put the boolean isOnline return to the extra
        localIntent.putExtra(EXTRA_IS_CONNECTED, isOnline());

        //call the singleton localBroadcastManager
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
