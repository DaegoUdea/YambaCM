package co.edu.udea.cmovil.gr2.yamba;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import java.util.List;
import java.util.Vector;

public class RefreshService extends IntentService {
    public static final String TAG = "refreshService";
    public RefreshService() {
        super("RefreshService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onStarted");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("username", "");
        String password = prefs.getString("password", "");

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Log.e(TAG, "Please update your username and password");
            return;
        }

        ContentValues values = new ContentValues();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        YambaClient Cloud = new YambaClient(username, password);
        try {
            int count = 0;
            List<Status> timeLine = Cloud.getTimeline(20);
            for (Status status : timeLine) {

                values.clear();
                values.put(StatusContract.Column.ID, status.getId());
                values.put(StatusContract.Column.USER, status.getUser());
                values.put(StatusContract.Column.MESSAGE, status.getMessage());
                values.put(StatusContract.Column.CREATED_AT, status.getCreatedAt().getTime());

                Uri uri = getContentResolver().insert(StatusContract.CONTENT_URI, values);
                if (uri != null) {
                    count++;
                    Log.d(TAG, String.format("%s: %s", status.getUser(), status.getMessage()));
                    db.insertWithOnConflict(StatusContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                }
            }

            if (count > 0) {
                sendBroadcast(new Intent("co.edu.udea.cmovil.gr2.yamba.action.NEW_STATUSES").putExtra("count", count));
            }
        } catch (YambaClientException e) {
            Log.e(TAG, "Failed to fetch the timeline");
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
