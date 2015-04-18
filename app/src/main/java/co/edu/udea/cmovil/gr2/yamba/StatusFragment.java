package co.edu.udea.cmovil.gr2.yamba;

import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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
    private final int maxChars=140;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,"OnCreateView of " + TAG +" Executed");
        View view = inflater.inflate(R.layout.fragment_status, container,false);
        buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
        editStatus = (EditText) view.findViewById(R.id.editStatus);
        textCount = (TextView) view.findViewById(R.id.textCount);
        textCount.setText(Integer.toString(maxChars));
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


                int count = maxChars - editStatus.length();
                textCount.setText(Integer.toString(count));

                switch ( count ){
                    case maxChars:
                        buttonTweet.setEnabled(false);
                        buttonTweet.setTextColor(Color.argb(255,169,169,169));
                        textCount.setTextColor(Color.WHITE);
                        break;
                    default:
                        buttonTweet.setEnabled(true);
                        buttonTweet.setTextColor(Color.BLACK);
                }

                if (count>10 && count<maxChars){
                    textCount.setTextColor(Color.GREEN);
                }
                else if(count<=10 && count >=0){
                    textCount.setTextColor(Color.YELLOW);
                }
                else if(count<0){
                    buttonTweet.setEnabled(false);
                    buttonTweet.setTextColor(Color.argb(255,169,169,169));
                    textCount.setTextColor(Color.RED);
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        Log.i(TAG,"onAttach of " + TAG +" Executed");
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        Log.i(TAG,"onCreate of " + TAG +" Executed");
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        Log.i(TAG,"onActivityCreated of " + TAG +" Executed");
    }

    @Override
    public void onStart( ){
        super.onStart();
        Log.i(TAG,"onStart of " + TAG +" Executed");
    }
    @Override
    public void onResume( ){
        super.onResume();
        Log.i(TAG,"onResume of " + TAG +" Executed");
    }
    @Override
    public void onPause( ){
        super.onPause();
        Log.i(TAG,"onPause of " + TAG +" Executed");
    }
    @Override
    public void onStop( ){
        super.onStop();
        Log.i(TAG,"onStop of " + TAG +" Executed");
    }
    @Override
    public void onDestroyView( ){
        super.onDestroyView();
        Log.i(TAG,"onDestroyView of " + TAG +" Executed");
    }
    @Override
    public void onDestroy( ){
        super.onDestroy();
        Log.i(TAG,"onDestroy of " + TAG +" Executed");
    }
    @Override
    public void onDetach( ){
        super.onDetach();
        Log.i(TAG,"onDetach of " + TAG +" Executed");
    }

    @Override
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        Log.d(TAG, "nClicked with Status: " + status);

        new PostTask().execute(status);
        InputMethodManager imm = (InputMethodManager) getView().getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editStatus.getWindowToken(), 0);
        editStatus.setText("");

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
