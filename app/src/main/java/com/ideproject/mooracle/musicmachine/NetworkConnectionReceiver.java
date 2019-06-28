package com.ideproject.mooracle.musicmachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // for now just log info about what is going on with network receiver
        Log.i(NetworkConnectionReceiver.class.getSimpleName(), intent.getAction());
    }
}
