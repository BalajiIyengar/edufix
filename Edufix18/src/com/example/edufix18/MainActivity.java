package com.example.edufix18;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {

	Button button;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        addListenerOnButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void addListenerOnButton() {
    	 
		button = (Button) findViewById(R.id.button1);
 
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				RequestParams params ;
				
				Map<String,String> paramMap = new HashMap<String,String>();
				paramMap.put("something", "abcd");
				
				params = new RequestParams(paramMap);
				
				 AsyncHttpClient client = new AsyncHttpClient();
				 client.get("http://localhost:8080/edufix/search_universities.json", params ,new AsyncHttpResponseHandler() {
			            // When the response returned by REST has Http response code '200'
			             @Override
			             public void onSuccess(String response) {
			                // Hide Progress Dialog
			               
			                 try {
			                          // JSON Object
			                         JSONObject obj = new JSONObject(response);
			                         // When the JSON response has status boolean value assigned with true
			                         if(obj.getBoolean("status")){
			                             // Set Default Values for Edit View controls
			                            // setDefaultValues();
			                             // Display successfully registered message using Toast
			                             Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
			                         } 
			                         // Else display error message
			                         else{
			                            // errorMsg.setText(obj.getString("error_msg"));
			                             Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
			                         }
			                 } catch (JSONException e) {
			                     // TODO Auto-generated catch block
			                     Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
			                     e.printStackTrace();
			 
			                 }
			             }
			             // When the response returned by REST has Http response code other than '200'
			             @Override
			             public void onFailure(int statusCode, Throwable error,
			                 String content) {
			                 // Hide Progress Dialog
			               //  prgDialog.hide();
			                 // When Http response code is '404'
			                 if(statusCode == 404){
			                     Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
			                 } 
			                 // When Http response code is '500'
			                 else if(statusCode == 500){
			                     Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
			                 } 
			                 // When Http response code other than 404, 500
			                 else{
			                     Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
			                 }
			             }
			         });
			}
 
		});
 
	}
    
}
