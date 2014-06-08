package com.pravind.pcameraproject;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pravind.cameraproject.R;

public class INF extends CameraProject 
{
    Bitmap bitmap;
    String pic,fullPath;
    String[] Paths;
    int era;
    Intent imageReturnedIntent;
    final static int CameraData = 0;
    ArrayList<Uri> uris;
    File fileIn;
	
    
    private static final String TAG = "MyActivity";
    protected void onCreate(Bundle savedInstanceState) 
    	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.in);
		Button bt = (Button)findViewById(R.id.bt);
		preview();
	}
		//to show the image	
    private void preview() 
    {
	getLastImageId();
	File imgFile = new  File(fullPath);
	if(imgFile.exists())
	{
	    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
	    ImageView myImage = (ImageView) findViewById(R.id.imageView1);
	    myImage.setImageBitmap(myBitmap);
		    //generate toast message
	    Context context = getApplicationContext();
	    CharSequence text = "image shown clearly or not ,if not than press back";
	    int duration = Toast.LENGTH_LONG;
        	Toast toast = Toast.makeText(context, text, duration);
			toast.show();
	}
     }

	public void Takepath()
	{
		getLastImageId();
		uris = new ArrayList<Uri>();
		Paths = new String[] {fullPath};
		for(String file : Paths )
		{
	        	fileIn= new File(file);
			Uri u=Uri.fromFile(fileIn);
			uris.add(u);
			
		}
	}
	//perform sending action
			
	public void sedmail(View v
	{
		Takepath();
		final EditText et = (EditText)findViewById(R.id.et1);
		final EditText et2 = (EditText)findViewById(R.id.et2);
		Intent i = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
		i.setType("plain/text");
		i.setType("application/octet-stream");
		i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		i.putExtra(Intent.ACTION_DEFAULT, "test/");
		i.putExtra(Intent.EXTRA_EMAIL, new String[]{"pravind.india@gmail.com"});//here you can put any email adderess where you want to send informatio
		i.putExtra(Intent.EXTRA_SUBJECT, "My Information");
		i.putExtra(Intent.EXTRA_TEXT, "Name:"+et.getText().toString()+'\n'+"Mobile No."+et2.getText().toString());	
		startActivity(Intent.createChooser(i, "send email...."));
	}
	

	public void cam(View v) 
	{
		finish();
	}

			//call camera when back button is pressed
	@Override
	public void onBackPressed() 
	{
		finish();
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
			Intent intent1=new Intent (this,INF.class);
			startActivity(intent1);
		}
	        else 
	        {
		    	Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, CameraData);
	         }
	}
			//get the id of the last image taken by camera			
	private int getLastImageId()
	{
	    final String[] imageColumns = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };
	    final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";
	    Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
	    if(imageCursor.moveToFirst())
	    {
	    	int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
		fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
	        Log.d(TAG, "getLastImageId::id " + id);
	        Log.d(TAG, "getLastImageId::path " + fullPath);
	        imageCursor.close();
	        return id;
	    }
	    else
	    {
	        return 0;
	    }
	 }
	
}


