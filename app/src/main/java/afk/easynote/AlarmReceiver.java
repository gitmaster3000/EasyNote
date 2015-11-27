package afk.easynote;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        PendingIntent pIntent = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder notify1 = new Notification.Builder(context).setContentTitle(intent.getStringExtra("Message")).setContentText(intent.getStringExtra("Details")).setSmallIcon(R.mipmap.easynote).setTicker(intent.getStringExtra("Message")).setContentIntent(pIntent).setVibrate(new long[]{1000, 1000, 1000, 1000, 1000 });

        NotificationManager nMan = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nMan.notify(0,notify1.build());
        //SENT TO SELF, IN ALARM


    }
}
