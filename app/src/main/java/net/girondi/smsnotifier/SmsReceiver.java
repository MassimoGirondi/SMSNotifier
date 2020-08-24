package net.girondi.smsnotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        SmsMessage m = msgs[i];

                        PreferencesManager pm = new PreferencesManager(context);

                        EventsHandler handler = new EventsHandler();
                        handler.handle(context,
                                "New SMS",
                                        "From " +  m.getDisplayOriginatingAddress() + " " + m.getOriginatingAddress() +
                                        //"Received at " + formatted +
                                        "\n----\n"+ m.getMessageBody());

                    }
                } catch (Exception e) {
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        } else
        {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String number=intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                new EventsHandler().handle(context, "New Call","Call from " +  number);
            } else if (state.equals(TelephonyManager.EXTRA_STATE)) {

                String number=intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                new EventsHandler().handle(context, "New Call", "Call from " +  number);
        }
        }






    }
}