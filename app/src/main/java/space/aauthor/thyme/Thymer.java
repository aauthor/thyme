package space.aauthor.thyme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class Thymer {

    private Context context;
    private int delay = 30 * 60 * 1000; // thirty minutes
    private AlarmManager alarmManager;
    private Notification notification;
    private Intent notificationIntent;

    Thymer(Context context) {
        this.context = context;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        notification = createNotification();
        notificationIntent = new Intent(context, NotificationPublisher.class)
            .putExtra(NotificationPublisher.NOTIFICATION_ID, 1)
            .putExtra(NotificationPublisher.NOTIFICATION, notification);
    }

    public boolean isEnabled(){
        PendingIntent notifyUserAfterTime = PendingIntent.getBroadcast(
                context, 0, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return notifyUserAfterTime != null;
    }

    public void toggle() {
        if(isEnabled()){
            disable();
        } else {
            enable();
        }
    }

    public void enable() {
        PendingIntent notifyUserAfterTime = PendingIntent.getBroadcast(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, delay, notifyUserAfterTime);
    }

    public void disable() {
        PendingIntent notifyUserAfterTime = PendingIntent.getBroadcast(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(notifyUserAfterTime);
        notifyUserAfterTime.cancel();
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

}
