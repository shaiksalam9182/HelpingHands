package com.example.striker.helpinghands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Contactus extends Fragment {
    EditText etid,etemail,etmsg;
    Button bsubmit;
    String Getid, GetEmail, Getmsg;
    ProgressDialog dialog1 = null;
    String DataParseUrl = "http://10.11.3.53/help/Androhh/hello.php";
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    public static Contactus newInstance() {
        Contactus fragment = new Contactus();
        return fragment;
    }

    public void onStart()
    {
        super.onStart();
        contact();
    }
    public Contactus() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contactus, container, false);
        return rootView;
    }
    public void contact()
    {
        etid = (EditText)getActivity().findViewById(R.id.etid);
        etemail = (EditText)getActivity().findViewById(R.id.etemail);
        etmsg = (EditText)getActivity().findViewById(R.id.etmes);
        bsubmit = (Button)getActivity().findViewById(R.id.bsubmit);
        bsubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String getid = etid.getText().toString();
                String getemail = etemail.getText().toString();
                String getmsg = etmsg.getText().toString();

                dialog1 = ProgressDialog.show(getActivity(), "", "Sending request\n please wait....", true);


                new AsyncRetrieve().execute();
            }
        });
    }
    public void GetDataFromEditText(){

        Getid = etid.getText().toString();
        GetEmail = etemail.getText().toString();
        Getmsg = etmsg.getText().toString();


        if (Getid.matches("") || GetEmail.matches("") || Getid.matches("")){
            dialog1.dismiss();
            Toast.makeText(getActivity(), "PLEASE FILL ALL FILELDS", Toast.LENGTH_SHORT).show();

            return;
        }
        else if((Getid.startsWith("N")|| Getid.startsWith("n"))&& Getid.length()== 7){
            SendDataToServer(Getid, GetEmail, Getmsg);
        }
        else{
            dialog1.dismiss();
            Toast.makeText(getActivity(), "Please Correct the format of the fields", Toast.LENGTH_SHORT).show();
        }


    }
    public void SendDataToServer(final String id, final String mail, final String problem){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String QuickName = id ;
                String QuickEmail = mail ;
                String QuickWebsite = problem;


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("id", QuickName));
                nameValuePairs.add(new BasicNameValuePair("mail", QuickEmail));
                nameValuePairs.add(new BasicNameValuePair("problem", QuickWebsite));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(DataParseUrl);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }

                return "Data Submit Successfully";
            }


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                dialog1.dismiss();
                Toast.makeText(getActivity(), "Your request is\n SUCCESSFULLY posted", Toast.LENGTH_LONG).show();
                etid.setText("");
                etmsg.setText("");
                etemail.setText("");


            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id, mail,problem);

    }

    private class AsyncRetrieve extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        //this method will interact with UI, here display loading message
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        // This method does not interact with UI, You need to pass result to onPostExecute to display
        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL("http://10.11.3.53/help/Androhh/App_CheckConnection.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        // this method will interact with UI, display result sent from doInBackground method
        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            if(result.equals("connection success")) {
                GetDataFromEditText();
            }else{
                // you to understand error returned from doInBackground method
                Toast.makeText(getActivity(), "CHECK YOUR NETWORK", Toast.LENGTH_LONG).show();
                dialog1.dismiss();
            }

        }

    }

}
