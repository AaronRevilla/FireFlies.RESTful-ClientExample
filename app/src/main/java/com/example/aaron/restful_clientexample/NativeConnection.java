package com.example.aaron.restful_clientexample;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aaron.restful_clientexample.net.HttpManager;

public class NativeConnection extends AppCompatActivity {

    TextView output;
    Spinner dropDownMenu;
    String selectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_connection);

        output = ((TextView) findViewById(R.id.output));
        output.setMovementMethod(new ScrollingMovementMethod());

        dropDownMenu = ((Spinner) findViewById(R.id.spinner));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.uri_conn_arraylist, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);
        dropDownMenu.setOnItemSelectedListener(new SpinnerActivity());

    }

    public void sendRequest(String uri){
        output.setText("");
        ConnectionThread asyncThread = new ConnectionThread();
        asyncThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri, "Param2", "Param3");
        //asyncThread.execute("Param 1", "Param 2", "Param 3");
    }

    public boolean isOnline(){
        ConnectivityManager cm  = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }

    public void sendNewRequest(View view) {
        sendRequest(selectedUri);
    }

    public class ConnectionThread extends AsyncTask<String, String, String>{

        //UI access
        @Override
        protected void onPreExecute() {
            output.append("Starting Connection\n");
        }

        @Override
        protected String doInBackground(String... params) {
            String uri = params[0];
            String response;
            if(isOnline()){
                response = HttpManager.getData(uri);
            }
            else{
                response = "The device has no connection to Internet";
            }

            return response;
        }

        //UI access
        @Override
        protected void onPostExecute(String s) {
            output.append(s);
        }
    }

    //Spinner events
    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            selectedUri = String.valueOf( parent.getItemAtPosition(pos));
            Log.d("DEBUG", selectedUri);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
}
