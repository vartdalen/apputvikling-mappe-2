package vica.apputviklingmappe2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;

public class VicaService extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DB db = new DB();
        Helper helper = new Helper();
        ArrayList<String> friendSelectedListArray = intent.getStringArrayListExtra(getString(R.string.friendSelectedListArray));
        String notificationMessage = intent.getStringExtra(getString(R.string.notify_friends_message));
        Boolean notifyFriends = intent.getExtras().getBoolean(getString(R.string.notify_friends));
        Boolean personalReminder = intent.getExtras().getBoolean(getString(R.string.personal_reminder));
        String restaurantName = intent.getStringExtra(getString(R.string.restaurant_name));
        String restaurantDate = intent.getStringExtra(getString(R.string.date));
        String restaurantTime = intent.getStringExtra(getString(R.string.time));

        if(notifyFriends) {
            String phone;
            for (String friend : friendSelectedListArray) {
                phone = db.getInfo(DB.CONTENT_FRIEND_URI, new String[]{getString(R.string.FRIEND_PHONE)}, getString(R.string.FRIEND_ID) + "=" + helper.parseNumbersFromString(friend), null, this);
                helper.sendSMS(phone, notificationMessage + "\nRestaurant: " + restaurantName + "\nDate: " + restaurantDate + "\nTime: " + restaurantTime);
            }

        }
        if(personalReminder) {
            helper.createNotification(VicaService.this, getString(R.string.personal_reminder_message));
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
