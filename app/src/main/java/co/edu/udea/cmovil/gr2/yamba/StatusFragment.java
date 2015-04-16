package co.edu.udea.cmovil.gr2.yamba;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusFragment extends Fragment implements OnClickListener {

    private static final String TAG = "StatusFragment";
    private Button buttonTweet;
    private EditText editStatus;
    private TextView textCount;
    private int defaultTextColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container,false);
        buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
        editStatus = (EditText) view.findViewById(R.id.editStatus);
        textCount = (TextView) view.findViewById(R.id.textCount);
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
        return view;
    }

    @Override
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        Log.d(TAG, "nClicked with Status: " + status);

        new PostTask().execute(status);
    }

    private final class PostTask extends AsyncTask<String, Void, String> {

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
            Toast.makeText(StatusFragment.this.getActivity(), result, Toast.LENGTH_LONG).show();
        }
    }
}
