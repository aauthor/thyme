package space.aauthor.thyme;

import android.os.Vibrator;
import android.widget.TextView;

public class Thyme {

    public static void viewThymer(TextView statusView, Thymer thymer){
        String statusText = thymer.isEnabled() ? "On" : "Off";
        statusView.setText(statusText);
    }

    public static void toggleNotifications(TextView statusView, Thymer thymer, Vibrator vibrator) {
        thymer.toggle();
        viewThymer(statusView, thymer);
        if(thymer.isEnabled()){
            vibrate(vibrator);
        }
    }

    public static void vibrate(Vibrator vibrator){
        long[] pattern = { 0, 200, 100, 100, 100, 100, 100, 1000};
        vibrator.vibrate(pattern, -1);
    }

}
