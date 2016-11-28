package ca.uoit.dmancini.lab4;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {
    public PowerConnectionReceiver(){
        super();
    }

    public void onReceive(Context context, Intent intent){
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        boolean isFull = status == BatteryManager.BATTERY_STATUS_FULL;

        boolean healthy = health == BatteryManager.BATTERY_HEALTH_GOOD;
        String health_problem = "";
        if(!healthy){
             if(health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
                 health_problem = context.getString(R.string.over_v);
             }
            else if(health == BatteryManager.BATTERY_HEALTH_OVERHEAT){
                 health_problem = context.getString(R.string.batt_overheat);
             }
            else{
                 health_problem = context.getString(R.string.unknown_health);
             }
        }

        Log.i("Battery Status", "isFull: " + isFull + "\nisCharging: " + isCharging + "\nisHealthy: " + healthy + "\nTemp: " + temp + "\nAC: ");

        boolean usbCharge = plugged == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = plugged == BatteryManager.BATTERY_PLUGGED_AC;
        String charge_message = isCharging ? "Charging!" : "Not Charging!";
        charge_message += isFull ? "\nBattery Full!" : "";
        charge_message += usbCharge ? "\nUsing USB" : "";
        charge_message += acCharge ? "\nUsing AC" : "";
        charge_message += healthy ? "\nBattery is healthy." : "Battery is not healthy.\n" + health_problem;
        charge_message += "\nTemperature is " + (temp/10) + "C.";


        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        nBuilder.setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Lab4")
                .setContentText(charge_message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(charge_message));
        Notification notif1 = nBuilder.build();
        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0, notif1);

    }
}
