package space.aauthor.thyme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class Thymer {

    private boolean isEnabled;
    private Context context;
    private PendingIntent timePendingIntent;
    private int delay = 15000; // fifteen seconds
    private AlarmManager alarmManager;


    Thymer(Context context) {
        this.context = context;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public void toggle() {
        if(isEnabled()){
            disable();
        } else {
            enable();
        }
    }

    public void enable() {
        Notification notification = createNotification();
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        timePendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, delay, timePendingIntent);
        isEnabled = true;
    }

    private Notification createNotification() {
        long[] vibrationPattern = { 0, 100, 100, 100, 100, 100 };
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Thyme");
        builder.setContentText("Time has passed.");
        builder.setSmallIcon(R.drawable.ic_stat_notification);
        builder.setVibrate(vibrationPattern);
        return builder.build();
    }

    public void disable() {
        alarmManager.cancel(timePendingIntent);
        isEnabled = false;
    }

}
