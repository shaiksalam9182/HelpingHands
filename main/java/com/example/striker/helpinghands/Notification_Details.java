package com.example.striker.helpinghands;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Notification_Details extends Activity {
	
	
	String title,not,sdby,date,time,welcome,files,mini,sendto,attach=null;
	TextView tv_title,tv_notfi,tv_sdby,tv_date,tv_time,tv_sendto,tv_attach;
	Button downloads;
	
	 String status;
     int st;
    public static final String LOG_TAG = "File-Path";
	private ProgressDialog mProgressDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	String s=null;
     
    //initialize root directory
    File rootDir = Environment.getExternalStorageDirectory();
     
    //defining file name and url
    public String fileName;// = "sdcac.apk";
    public String fileURL;// = "http://10.11.3.11/Android_App_SDCAC/web/sdcac.apk";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_details);

		attach=null;
		//downloads = (Button) findViewById(R.id.attach);
		title = getIntent().getStringExtra("not_title");
		
		
		not = getIntent().getStringExtra("not_det");

		
		sdby = getIntent().getStringExtra("sdby");
		
		date = getIntent().getStringExtra("date");
		
		time = getIntent().getStringExtra("time");
		
		sendto = getIntent().getStringExtra("sendto");

		attach = getIntent().getStringExtra("attach");

        tv_title = (TextView)findViewById(R.id.title);
        tv_attach = (TextView)findViewById(R.id.tv_att);
        
        tv_notfi = (TextView)findViewById(R.id.not);
        
        
        tv_sdby = (TextView)findViewById(R.id.sdby);
        
        tv_date = (TextView)findViewById(R.id.date);
        
        tv_time = (TextView)findViewById(R.id.time);
        
        tv_sendto = (TextView)findViewById(R.id.sendto);



		tv_title.setText(Html.fromHtml(title));
		
		tv_notfi.setText(Html.fromHtml(not));
		
		tv_sdby.setText(" Sd/- "+sdby);
		tv_date.setText(	date);
		tv_time.setText(	time);
		tv_sendto.setText("views:"+sendto);
		String value = "<html><a style=\"text-decoration:none;\" href=\"http://10.11.3.53/help/notice_files/"+attach+"\">&nbsp;&nbsp;&nbsp;</a></html>";

		Pattern p =Pattern.compile("(.*?)"+"."+"(.*?)");
		Matcher m = p.matcher(attach);
		while (m.find())
			s =m.group(1);

		if(s!=null)
		{
			tv_attach.setBackgroundResource(R.drawable.download);
			tv_attach.setText(Html.fromHtml(value));
			tv_attach.setMovementMethod(LinkMovementMethod.getInstance());

		}


		
	}
	
	
	@Override
 	public void onBackPressed() 
	{
		mini="true";
 		finish();
 		return;
 	}
}
