package co.edu.udea.cmovil.gr2.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import java.util.List;
import java.util.Vector;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RefreshService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "co.edu.udea.cmovil.gr2.yamba.action.FOO";
    public static final String ACTION_BAZ = "co.edu.udea.cmovil.gr2.yamba.action.BAZ";
    public static final String TAG ="refreshService";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "co.edu.udea.cmovil.gr2.yamba.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "co.edu.udea.cmovil.gr2.yamba.extra.PARAM2";

    public RefreshService() {
        super("RefreshService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("username","");
        String password = prefs.getString("password","");

        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please update your username and password",Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG, "onStarted");

        YambaClient Cloud = new YambaClient(username,password);
        try{
            List<Status> timeLine = Cloud.getTimeline(20);
            for (Status status : timeLine){
                System.out.println("For");
                Log.d(TAG, String.format("%s: %s", status.getUser(),status.getMessage()));
            }
        }
        catch (YambaClientException e){
            Log.e(TAG, "Failed to fetch the timeline");
            e.printStackTrace();            
        }
        return;
    }



    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreated");
    }
    
    @Override
    public  void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
