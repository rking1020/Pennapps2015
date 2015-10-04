package com.realco.claire.practice;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MyActivity extends ActionBarActivity {

    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String url;

    EditText txtInput;

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void sendName(View view){
        // Send that new name to the DB
        postDBItem();

        // And reprint that DB
        getDBItems();
    }

    public void getDBItems(){
        // This is from the Volley tutorial
        final TextView mTextView = (TextView) findViewById(R.id.text_view);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string (items of the DB)
                        mTextView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // End of tutorial from Volley

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void postDBItem(){

        // This is from the Volley tutorial
        final TextView mTextView = (TextView) findViewById(R.id.text_view2);

        // Get the string from the input edit text box
        txtInput = (EditText) findViewById(R.id.edit_message);
        String inName = txtInput.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getDBItems();
            }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing
            }
        }){
            @Override
        protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", txtInput.getText().toString());

                return params;
            }
        };

        queue.add(postRequest);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Define queue and url
        queue = Volley.newRequestQueue(this);
        url ="http://159.203.68.208:5000/data";

        // Call function to hit the server for DB items
        getDBItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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
}
