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

    public TextView output;
    public Spinner dropDownMenu;
    public Spinner dropDownRequesOp;
    public String selectedUri, progress, result;
    //REQUEST TYPES CONST
    public static int REQUES_OP  = 0;
    public final  int POST = 0;
    public final  int GET = 1;
    public final  int PUT = 2;
    public final  int PATCH = 3;
    public final  int DELETE = 4;
    public ArrayAdapter<CharSequence> adapterPOST;
    public ArrayAdapter<CharSequence> adapterGET;
    public ArrayAdapter<CharSequence> adapterPUT;
    public ArrayAdapter<CharSequence> adapterPATCH;
    public ArrayAdapter<CharSequence> adapterDELETE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_connection);

        output = ((TextView) findViewById(R.id.output));
        output.setMovementMethod(new ScrollingMovementMethod());

        //first spinner
        dropDownRequesOp = ((Spinner) findViewById(R.id.spinnerRequestOp));
        ArrayAdapter<CharSequence> adapterROp = ArrayAdapter.createFromResource(this, R.array.request_operations, R.layout.support_simple_spinner_dropdown_item);
        adapterROp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropDownRequesOp.setAdapter(adapterROp);
        dropDownRequesOp.setOnItemSelectedListener(new SpinnerActivityROp());

        //second spinner
        dropDownMenu = ((Spinner) findViewById(R.id.spinner));

        adapterPOST =  ArrayAdapter.createFromResource(this, R.array.post_uri_conn_arraylist, R.layout.support_simple_spinner_dropdown_item);
        adapterPOST.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterGET =  ArrayAdapter.createFromResource(this, R.array.get_uri_conn_arraylist, R.layout.support_simple_spinner_dropdown_item);
        adapterGET.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterPUT =  ArrayAdapter.createFromResource(this, R.array.put_uri_conn_arraylist, R.layout.support_simple_spinner_dropdown_item);
        adapterPUT.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterPATCH =  ArrayAdapter.createFromResource(this, R.array.patch_uri_conn_arraylist, R.layout.support_simple_spinner_dropdown_item);
        adapterPATCH.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterDELETE =  ArrayAdapter.createFromResource(this, R.array.delete_uri_conn_arraylist, R.layout.support_simple_spinner_dropdown_item);
        adapterDELETE.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //dropDownMenu.setAdapter(adapter);
        dropDownMenu.setOnItemSelectedListener(new SpinnerActivity());
    }

    public void sendRequest(String uri){
        output.setText("");
        ConnectionThread asyncThread = new ConnectionThread();
        asyncThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri, progress, result);
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
                response = HttpManager.getData(uri, REQUES_OP);
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
    public class SpinnerActivityROp extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            REQUES_OP = pos;

            switch (pos){
                case POST:
                    dropDownMenu.setAdapter(adapterPOST);
                    break;
                case GET:
                    dropDownMenu.setAdapter(adapterGET);
                    break;
                case PUT:
                    dropDownMenu.setAdapter(adapterPUT);
                    break;
                case PATCH:
                    dropDownMenu.setAdapter(adapterPATCH);
                    break;
                case DELETE:
                    dropDownMenu.setAdapter(adapterDELETE);
                    break;
            }

            //Log.d("DEBUG", selectedUri);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    //Spinner events
    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            selectedUri = String.valueOf( parent.getItemAtPosition(pos));
            //Log.d("DEBUG", selectedUri);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
}
