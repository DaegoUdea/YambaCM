package co.edu.udea.cmovil.gr2.yamba;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class StatusActivity extends ActionBarActivity {

    private static final String TAG = "StatusActivity";
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       if(savedInstanceState == null){
           StatusFragment fragment = new StatusFragment();

           FragmentManager fragmentManager = getFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.add(android.R.id.content,fragment,fragment.getClass().getSimpleName());
           fragmentTransaction.commit();

      /*     getFragmentManager()
                   .beginTransaction()
                   .add(android.R.id.content,fragment,fragment.getClass().getSimpleName())
                   .commit();
      */
       }
   }
}
