package com.arik.commit;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class CommitNotifier extends Service{

    // Use a layout id for a unique identifier
    private static int COMMIT_NOTIFICATIONS = R.layout.commitment;
    private String FILENAME = "commits.json";

    NotificationManager notiManager;
    // variable which controls the notification thread
    private ConditionVariable mCondition;

    Context app;
    Integer notiID = 0;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals("android.intent.action.TIME_TICK")){
                //notifyUser("");
                Log.d("Commit Notifier", "Received the Broadcast");
            }
        }
    };

    @Override
    public void onCreate(){
        Log.d("CommitNotifier", "Creating the notifier");
        notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mCondition = new ConditionVariable(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.TIME_TICK");
        registerReceiver(receiver, filter);
        Log.d("CommitNotifier", "About to notify the user");
        notifyUser("Are you going to do this today?");
    }

    @Override
    public void onDestroy(){
        notiManager.cancel(COMMIT_NOTIFICATIONS);
        unregisterReceiver(receiver);
        mCondition.open();
    }

    public void notifyUser(String text){
        Log.d("CommitNotifier", "Preparing the notification");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, CommitNotifierView.class), 0);
        //Notification notif = new Notification.Builder(this).setContentIntent(contentIntent).build();

		/*try{
			FileInputStream fis = openFileInput(FILENAME);
			byte [] input = new byte[fis.available()];
			JSONObject storedCommits = new JSONObject();
			while(fis.read(input) != -1){
				String json = new String(input);
				storedCommits.put("sCommits", json);
			}

			Log.d("In notifyUser, The JSONArray of commits loaded from the file: ", storedCommits.toString());

			if(storedCommits.length() > 0){
				JSONArray coms = new JSONArray(storedCommits.getString("sCommits"));
				for(int i = 0; i < coms.length(); i++){
					try {
						JSONObject com = new JSONObject(coms.getString(i));
						String reminder = com.getString("Reminder");
						if(!reminder.equals("No reminder")){
							Notification noti = new Notification(R.drawable.stat_sample, null, System.currentTimeMillis());

							noti.setLatestEventInfo(this, "Commit Notification", "Are you going to " + com.getString("Commit") + "today?", contentIntent);

							noti.vibrate = new long[] { 0, 250, 100, 500};

							notiManager.notify(COMMIT_NOTIFICATIONS, noti);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

		}catch(IOException ioe){
			Log.e("Exception in loading the JSON file" + ioe, ioe.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}*/

        Notification noti = new Notification(R.drawable.stat_sample, null, System.currentTimeMillis());

        noti.setLatestEventInfo(this, "Commit Notification", text, contentIntent);

        noti.vibrate = new long[] { 0, 250, 100, 500};

        Log.d("CommitNotifier", "Before the actual notify");

        notiManager.notify(COMMIT_NOTIFICATIONS, noti);

        Log.d("CommitNotifier", "After the actual notify");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply,
                                     int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };

}

