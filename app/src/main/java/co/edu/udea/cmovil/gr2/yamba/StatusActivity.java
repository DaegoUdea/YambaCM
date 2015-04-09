package co.edu.udea.cmovil.gr2.yamba;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.*;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;


public class StatusActivity extends ActionBarActivity implements OnClickListener {

    private static final String TAG = "StatusActivity";
    private Button buttonTweet;
    private EditText editStatus;
    private TextView textCount;
    private int defaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        editStatus = (EditText) findViewById(R.id.editStatus);
        textCount = (TextView) findViewById(R.id.textCount);
        defaultTextColor = textCount.getTextColors().getDefaultColor();

        buttonTweet.setOnClickListener(this);
        editStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = 140 - editStatus.length();
                textCount.setText(Integer.toString(count));
                if (count>10 && count<140){
                    textCount.setTextColor(Color.GREEN);
                }
               else if(count<=10 && count >0){
                    textCount.setTextColor(Color.YELLOW);
                }
                else if(count==0){
                    textCount.setTextColor(Color.RED);
                }
                else{
                    textCount.setTextColor(defaultTextColor);
                }
            }
        });

        defaultTextColor = textCount.getTextColors().getDefaultColor();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        Log.d(TAG,"nClicked with Status: " + status);

        new PostTask().execute(status);
    }

    private final class PostTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            YambaClient yambaCloud = new YambaClient("student","password");
            try{
                yambaCloud.postStatus(params[0]);
                return "Success on posting";
            }
            catch (YambaClientException e){
                e.printStackTrace();
                return "Failed on posting";
            }
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
        }

        /*@Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu_status, menu);
            return true;
        }*/
    }
}
