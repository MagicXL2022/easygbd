package com.easygbs.easygbd.fragment;
import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.easygbs.easygbd.R;
import com.easygbs.easygbd.common.Constant;
import com.easygbs.easygbd.databinding.FragmentBasicsettingsBinding;
import com.easygbs.easygbd.activity.MainActivity;
import com.easygbs.easygbd.util.PeUtil;
import com.easygbs.easygbd.push.MediaStream;
import com.easygbs.easygbd.util.SPHelper;
import com.easygbs.easygbd.util.SPUtil;
import com.easygbs.easygbd.viewmodel.fragment.BasicSettingsViewModel;
import com.easygbs.easygbd.service.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;

public class BasicSettingsFragment extends Fragment {
	private String TAG= BasicSettingsFragment.class.getSimpleName();
	private MainActivity mMainActivity;
	public FragmentBasicsettingsBinding mFragmentBasicsettingsBinding;
	private BasicSettingsViewModel mBasicSettingsViewModel;

	private int width = 1920;
	private int height = 1080;

	private BackgroundCameraService mService;
	private ServiceConnection conn;

	public MediaStream mMediaStream=null;

	private printThread mprintThread = null;

	public static boolean running = true;

	public List<String> logList=new ArrayList<String>();

	public Handler mHandler=new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case Constant.OPENCAMERA:
					 initCamera();
					 break;
				case Constant.LOG:
					 if(logList.size()>=6){
						logList.remove(0);
					 }

					 logList.add(msg.obj.toString());

					 String str="";
					 for(int i=0;i<logList.size();i++){
						 str+=logList.get(i)+"\n\n";
					 }
					 Log.i(TAG,"str  "+str);
					 mFragmentBasicsettingsBinding.tvlog.setText(str);
					 break;
				case Constant.STARTPRINTLOG:
					SPHelper mSPHelper = mMainActivity.getSPHelper();
					int logstatus= (int) mSPHelper.get(Constant.LOGSTATUS,1);
					if(logstatus==1){
					String loglev = (String) mSPHelper.get(Constant.LOGLEV, "DEBUG");
					start(loglev);
					}
					break;

			}
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mMainActivity= (MainActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mFragmentBasicsettingsBinding = FragmentBasicsettingsBinding.inflate(inflater);

		mBasicSettingsViewModel = new BasicSettingsViewModel(mMainActivity,BasicSettingsFragment.this);
		mFragmentBasicsettingsBinding.setViewModel(mBasicSettingsViewModel);

		init();

		View mView=mFragmentBasicsettingsBinding.getRoot();
		return mView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stop();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	public void init(){
		mFragmentBasicsettingsBinding.lllogall.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				SPHelper mSPHelper = mMainActivity.getSPHelper();
				int logstatus= (int) mSPHelper.get(Constant.LOGSTATUS,1);
				if(logstatus==0){
					mSPHelper.put(Constant.LOGSTATUS,1);

					mFragmentBasicsettingsBinding.lllog.setBackgroundResource(R.mipmap.ic_selected);

					mFragmentBasicsettingsBinding.ivlog.setVisibility(View.VISIBLE);

				}else{
					mSPHelper.put(Constant.LOGSTATUS,0);

					mFragmentBasicsettingsBinding.lllog.setBackgroundResource(R.mipmap.ic_notselected);

					mFragmentBasicsettingsBinding.ivlog.setVisibility(View.INVISIBLE);
				}
			}
		});

		boolean hasPermission1  = PeUtil.hasPermission(mMainActivity, Manifest.permission.CAMERA);
		if(hasPermission1){
			boolean hasPermission2= PeUtil.hasPermission(mMainActivity,Manifest.permission.RECORD_AUDIO);
			if(hasPermission2){
				initCamera();
			}
		}

		SPHelper mSPHelper = mMainActivity.getSPHelper();
		int logstatus= (int) mSPHelper.get(Constant.LOGSTATUS,1);
		if(logstatus==1){
		String loglev = (String) mSPHelper.get(Constant.LOGLEV, "DEBUG");
		start(loglev);
		}
	}

	public void start(String lev) {
		if(mprintThread == null){
			int pid = android.os.Process.myPid();
			mprintThread = new printThread(String.valueOf(pid),lev,new printThread.info(){
				@Override
				public void msg(String value) {
					super.msg(value);
					Message msg=new Message();
					msg.what=Constant.LOG;
					msg.obj=value;
					mHandler.sendMessage(msg);
				}
			});
			mprintThread.start();
		}
	}

	public void stop(){
		if (mprintThread != null) {
			mprintThread.stopt();
			mprintThread = null;
		}
	}

	public void initCamera(){
		Intent intent = new Intent(mMainActivity, BackgroundCameraService.class);
		mMainActivity.startService(intent);

		Intent intent1 = new Intent(mMainActivity, UVCCameraService.class);
		mMainActivity.startService(intent1);

		conn = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
				mService = ((BackgroundCameraService.LocalBinder) iBinder).getService();

				if (mFragmentBasicsettingsBinding.tvcamera.isAvailable()) {
					goonWithAvailableTexture(mFragmentBasicsettingsBinding.tvcamera.getSurfaceTexture());
				}
			}

			@Override
			public void onServiceDisconnected(ComponentName componentName) {

			}
		};

		mMainActivity.bindService(new Intent(mMainActivity, BackgroundCameraService.class), conn, 0);
	}

	private void goonWithAvailableTexture(SurfaceTexture surface){
		final File easyPusher = new File(recordPath());
		easyPusher.mkdirs();

		MediaStream ms = mService.getMediaStream();

		if (ms != null) {
			ms.stopPreview();
			mService.inActivePreview();
			ms.setSurfaceTexture(surface);
			ms.startPreview();

			mMediaStream = ms;

			if (ms.getDisplayRotationDegree() != getDisplayRotationDegree()) {
				int orientation = mMainActivity.getRequestedOrientation();

				if (orientation == SCREEN_ORIENTATION_UNSPECIFIED || orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
					mMainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				} else {
					mMainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}
			}
		} else {
			boolean enableVideo = SPUtil.getEnableVideo(mMainActivity);

		    mMediaStream = new MediaStream(mMainActivity.getApplicationContext(),surface,enableVideo);
			String path = easyPusher.getPath();

		    mMediaStream.setRecordPath(path);

			startCamera();

			mService.setMediaStream(mMediaStream);
		}
	}

	public void startCamera(){
		    int cameraid=SPUtil.getCameraid(mMainActivity);
		    mMediaStream.setCameraId(cameraid);

			mMediaStream.updateResolution(width,height);
			mMediaStream.setDisplayRotationDegree(getDisplayRotationDegree());
			mMediaStream.createCamera();
			mMediaStream.startPreview();
	}

	public String recordPath(){
		return mMainActivity.getExternalFilesDir(null).getAbsolutePath() +"/"+ Constant.DIR;
	}

	private int getDisplayRotationDegree() {
		int rotation = mMainActivity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;

		switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0;
				break; // Natural orientation
			case Surface.ROTATION_90:
				degrees = 90;
				break; // Landscape left
			case Surface.ROTATION_180:
				degrees = 180;
				break;// Upside down
			case Surface.ROTATION_270:
				degrees = 270;
				break;// Landscape right
		}

		return degrees;
	}

	public void save(){
		try {
			 String port=mFragmentBasicsettingsBinding.etport.getText().toString();
		     SPUtil.setServerport(mMainActivity,Integer.parseInt(port));

			 String serveraddr=mBasicSettingsViewModel.sipserveraddrObservableField.get();
			 SPUtil.setServerip(mMainActivity,serveraddr);

			 String serverid=mFragmentBasicsettingsBinding.etsipserverid.getText().toString();
			 SPUtil.setServerid(mMainActivity,serverid);

			String serverdomain=mFragmentBasicsettingsBinding.etsipserverdomain.getText().toString();
			SPUtil.setServerdomain(mMainActivity,serverdomain);

			String sipname=mFragmentBasicsettingsBinding.tvsipname.getText().toString();
			SPUtil.setDeviceid(mMainActivity,sipname);

			String devicename=mFragmentBasicsettingsBinding.etdevicename.getText().toString();
			SPUtil.setDevicename(mMainActivity,devicename);

			String sippassword=mFragmentBasicsettingsBinding.etsippassword.getText().toString();
			SPUtil.setPassword(mMainActivity,sippassword);

			String registervalidtime=mFragmentBasicsettingsBinding.etregistervalidtime.getText().toString();
			SPUtil.setRegexpires(mMainActivity,Integer.parseInt(registervalidtime.substring(0,registervalidtime.length()-1)));

			String heartbeatcycle=mFragmentBasicsettingsBinding.tvheartbeatcycle.getText().toString();
			SPUtil.setHeartbeatinterval(mMainActivity,Integer.parseInt(heartbeatcycle.substring(0,heartbeatcycle.length()-1)));

			String heartbeatcount=mFragmentBasicsettingsBinding.etheartbeatcount.getText().toString();
		    SPUtil.setHeartbeatcount(mMainActivity,Integer.parseInt(heartbeatcount));
		}catch(Exception e){
			Log.e(TAG,"save  Exception  "+e.toString());
		}
	}

	public void startOrStopStream(){
    try{
		if (mMediaStream != null){
		if (!mMediaStream.isStreaming()) {
				mMediaStream.startStream();
			} else {
				mMediaStream.stopStream();
			}
		}
	}catch(Exception e){
		Log.e(TAG,"startOrStopStream  Exception  "+e.toString());
	}
	}

	static class printThread extends Thread {
		public String TAG=printThread.class.getSimpleName();
		private BufferedReader mBufferedReader = null;
		private Object lock = new Object();
		private String cmd = null;
		private Process process;
		private String mPID;
		private String lev;
		private info minfo;

		public printThread(String pid,String lev,info minfo){
			mPID = pid;
			this.lev=lev;
			this.minfo=minfo;

			if(lev.equals("DEBUG")){
				cmd = "logcat *:d | grep \"(" + mPID + ")\"";
			}else if(lev.equals("INFO")){
			    cmd = "logcat *:v | grep \"(" + mPID + ")\"";
			}else if(lev.equals("WARNING")){
				cmd = "logcat *:w | grep \"(" + mPID + ")\"";
			}else if(lev.equals("INFO")){
				cmd = "logcat *:e | grep \"(" + mPID + ")\"";
			}
		}

		public void stopt(){
			synchronized(lock){
				lock.notify();
				running = false;
			}
		}

		@Override
		public void run(){
			try{
				super.run();
				process = Runtime.getRuntime().exec(cmd);
				InputStream is=process.getInputStream();
				InputStreamReader isr=new InputStreamReader(is);
				mBufferedReader = new BufferedReader(isr,1024);
				String line = null;

				synchronized(lock){
					while(running && ((line=mBufferedReader.readLine())!=null)){
						if (!running) {
							break;
						}

						if (line.length() == 0) {
							continue;
						}

						boolean islinecontainsmPID = line.contains(mPID);
						if(islinecontainsmPID){
							minfo.msg(line);
						}

						lock.wait(2000);
					}
				}
			}catch(Exception e){
				Log.e(TAG,"run  Exception  "+e.toString());
			}finally{
				try{
					if (process != null) {
						process.destroy();
						process = null;
					}

					if (mBufferedReader != null) {
						mBufferedReader.close();
						mBufferedReader = null;
					}
				}catch(Exception e){
					Log.e(TAG,"run  finally  Exception  "+e.toString());
				}
			}
		}

		static class info{
			public void msg(String value){

			}
		}
	}
}