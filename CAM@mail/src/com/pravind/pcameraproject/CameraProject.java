package com.pravind.pcameraproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



import com.pravind.cameraproject.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraProject extends Activity
{
	
	Button button;
	Bitmap btm;
	Bitmap thumbnail;
	String filePath;
	File pic;
	final static int CameraData = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_project);
		button = (Button) findViewById(R.id.cont);

		
	}

	
	//start camera activity
	public void camera(View v) 
		{
			Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, CameraData);
		}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
		{
			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == RESULT_OK)
			{
				Bundle extras = data.getExtras();
				btm = (Bitmap) extras.get("data");
							
			}
		       if(resultCode == RESULT_OK)
		    	{
		    	//it moves to another activity
				Intent intent1=new Intent (CameraProject.this,INF.class);
								
				startActivity(intent1);
				
			}
		      else 
		    	{
		    	//if discard it reopen camera activity
		    	Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, CameraData);
		    	}
		
		}
	
		public void exit(View v)
			{
				finish();
			}
	
}
