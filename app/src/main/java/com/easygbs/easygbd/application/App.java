package com.easygbs.easygbd.application;import android.app.Application;import android.content.Context;import android.content.res.AssetManager;import android.util.Log;import java.io.File;import java.io.FileOutputStream;import java.io.InputStream;public class App extends Application{	private final static String TAG= App.class.getSimpleName();	public static final String CHANNEL_CAMERA = "camera";	public static App instance;	public void onCreate() {		super.onCreate();		instance=this;		File youyuan = getFileStreamPath("SIMYOU.ttf");		if (!youyuan.exists()) {			AssetManager mAssetManager = getAssets();			try{				InputStream is = mAssetManager.open("zk/SIMYOU.ttf");				FileOutputStream os = openFileOutput("SIMYOU.ttf",MODE_PRIVATE);				byte[] buffer = new byte[1024];				int len;				while((len = is.read(buffer)) != -1){					os.write(buffer,0,len);				}				os.close();				is.close();			}catch(Exception e){				Log.e(TAG,"Exception  "+e.toString());			}		}	}	public static App getInstance() {		return instance;	}} 