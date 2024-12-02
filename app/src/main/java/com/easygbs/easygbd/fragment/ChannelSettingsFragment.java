package com.easygbs.easygbd.fragment;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.easygbs.easygbd.R;
import com.easygbs.easygbd.common.Constant;
import com.easygbs.easygbd.dao.DCon;
import com.easygbs.easygbd.dao.DOpe;
import com.easygbs.easygbd.dao.bean.Chan;
import com.easygbs.easygbd.dialog.ChanDialog;
import com.easygbs.easygbd.util.PeUtil;
import com.easygbs.easygbd.util.ScrUtil;
import com.easygbs.easygbd.viewadapter.MultiItemTypeAdapter;
import com.easygbs.easygbd.activity.MainActivity;
import com.easygbs.easygbd.adapter.ChannelAdapter;
import com.easygbs.easygbd.databinding.FragmentChannelsettingsBinding;
import com.easygbs.easygbd.viewmodel.fragment.ChannelSettingsViewModel;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ChannelSettingsFragment extends Fragment {
	private String TAG= ChannelSettingsFragment.class.getSimpleName();
	private MainActivity mMainActivity;
	private FragmentChannelsettingsBinding mFragmentChannelsettingsBinding;
	private ChannelSettingsViewModel mChannelSettingsViewModel;

	public List<Chan> ChanList;
	public ChannelAdapter mChannelAdapter;

	public Handler mHandler=new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {

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
		mFragmentChannelsettingsBinding = FragmentChannelsettingsBinding.inflate(inflater);

		mChannelSettingsViewModel = new ChannelSettingsViewModel(mMainActivity,ChannelSettingsFragment.this);
		mFragmentChannelsettingsBinding.setViewModel(mChannelSettingsViewModel);

		init();

		View mView=mFragmentChannelsettingsBinding.getRoot();
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
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	public void init(){
		mFragmentChannelsettingsBinding.rvlist.setLayoutManager(new GridLayoutManager(mMainActivity,1, OrientationHelper.VERTICAL,false));

		ChanList = new ArrayList<Chan>();
		mChannelAdapter = new ChannelAdapter(mMainActivity,mMainActivity, R.layout.adapter_channel,ChanList);
		mFragmentChannelsettingsBinding.rvlist.setAdapter(mChannelAdapter);
		mFragmentChannelsettingsBinding.rvlist.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){

			}
		});

		mChannelAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view,RecyclerView.ViewHolder holder,int position) {
				for(int i=0;i<ChanList.size();i++) {
					Chan mChan = ChanList.get(i);
					if(mChan.getUid()==ChanList.get(position).getUid()){
						mChan.setSelected(1);
					}else{
						mChan.setSelected(0);
					}
				}

				mChannelAdapter.notifyDataSetChanged();

				ChanDialog mChanDialog=new ChanDialog(mMainActivity,mMainActivity,ChannelSettingsFragment.this,ChanList.get(position));
				mChanDialog.show();
			}

			@Override
			public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

				return false;
			}
		});

		int height = getScreenHeight(mMainActivity);
		int StatusBarHeight = ScrUtil.getStatusBarHeight(mMainActivity);
		int navigationheight = getNavBarHeight(mMainActivity);
		int diff = height - StatusBarHeight - mMainActivity.getHeight() - navigationheight;

		if (diff == 0) {
			int getheight= height - StatusBarHeight - (int)mMainActivity.getResources().getDimension(R.dimen.dp_220) - navigationheight;
			LinearLayout.LayoutParams  lp= (LinearLayout.LayoutParams) mFragmentChannelsettingsBinding.lllist.getLayoutParams();
			lp.height=getheight;
			mFragmentChannelsettingsBinding.lllist.setLayoutParams(lp);
		}else if(diff < 0){
			int getheight= height - StatusBarHeight - (int)mMainActivity.getResources().getDimension(R.dimen.dp_220) - navigationheight/2;
			LinearLayout.LayoutParams  lp= (LinearLayout.LayoutParams) mFragmentChannelsettingsBinding.lllist.getLayoutParams();
			lp.height=getheight;
			mFragmentChannelsettingsBinding.lllist.setLayoutParams(lp);
		}

		showChannels();
	}

	public static int getNavBarHeight(Context c) {
		Resources resources = c.getResources();
		int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		return resources.getDimensionPixelOffset(identifier);
	}

	public static int getScreenHeight(Context context) {
		int dpi = 0;
		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
		Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, dm);
			dpi = dm.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dpi;
	}

	public void showChannels(){
		ChanList.clear();

		boolean per = PeUtil.hasPermission(mMainActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if(per){
			if(mMainActivity.getMDOpe()==null){
				DOpe mDOpe = DOpe.Instance(mMainActivity, mMainActivity.getExternalFilesDir(null).getAbsolutePath() + File.separator + Constant.DIR);
				List<Chan> ChanListLocal = (List<Chan>)mDOpe.OperaDatabase(DCon.Chanqueryeffect,"");
				if (ChanListLocal.size() >= 1) {
					ChanList.addAll(ChanListLocal);
				}
			}else{
				List<Chan> ChanListLocal = (List<Chan>)mMainActivity.getMDOpe().OperaDatabase(DCon.Chanqueryeffect, "");
				if (ChanListLocal.size() >= 1) {
					ChanList.addAll(ChanListLocal);
				}
			}
		}

		mChannelAdapter.notifyDataSetChanged();
	}

	public void cancel(){
		for(int i=0;i<ChanList.size();i++) {
			Chan mChan = ChanList.get(i);
		    mChan.setSelected(0);
		}

		mChannelAdapter.notifyDataSetChanged();
	}

	public void delete(Chan mChan){
		cancel();

		for(int i=0;i<ChanList.size();i++) {
			Chan mmChan = ChanList.get(i);
			if(mmChan.getUid()==mChan.getUid()){
				mmChan.setSta(0);
				ChanList.remove(i);

			    mMainActivity.getMDOpe().OperaDatabase(DCon.Chanupdatedependuid,mmChan);
				break;
			}
		}

		mChannelAdapter.notifyDataSetChanged();
	}

	public void modify(Chan mChan){
		cancel();

		for(int i=0;i<ChanList.size();i++) {
			Chan mmChan = ChanList.get(i);
			if(mmChan.getUid()==mChan.getUid()){
				mmChan.setCid(mChan.getCid());
				mmChan.setNa(mChan.getNa());
				mMainActivity.getMDOpe().OperaDatabase(DCon.Chanupdatedependuid,mmChan);
				break;
			}
		}

		mChannelAdapter.notifyDataSetChanged();
	}
}