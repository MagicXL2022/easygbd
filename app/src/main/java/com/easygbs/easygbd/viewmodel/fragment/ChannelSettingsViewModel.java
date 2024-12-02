package com.easygbs.easygbd.viewmodel.fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.easygbs.easygbd.activity.MainActivity;
import com.easygbs.easygbd.dao.DCon;
import com.easygbs.easygbd.dao.bean.Chan;
import com.easygbs.easygbd.fragment.ChannelSettingsFragment;
import com.easygbs.easygbd.push.MediaStream;
import com.easygbs.easygbd.util.SPUtil;

public class ChannelSettingsViewModel extends BaseObservable{
    private static final String TAG = ChannelSettingsViewModel.class.getSimpleName();
    public MainActivity mMainActivity=null;
    public ChannelSettingsFragment mChannelSettingsFragment=null;

    public ChannelSettingsViewModel(MainActivity mMainActivity, ChannelSettingsFragment mChannelSettingsFragment) {
        this.mMainActivity=mMainActivity;
        this.mChannelSettingsFragment=mChannelSettingsFragment;
    }

    public void add(View view){
        try{
            Chan mChan= (Chan) mMainActivity.getMDOpe().OperaDatabase(DCon.Chanquerymaxuid);
            if (mChan == null) {
                Chan mmChan = new Chan();
                mmChan.setUid(1);
                mmChan.setCid("34020000001310005554");
                mmChan.setNa("channel1");
                mmChan.setSta(1);
                mMainActivity.ChanIn(mmChan);
            }else{
                Chan mmChan = new Chan();
                int uid=mChan.getUid()+10;
                mmChan.setUid(uid);
                mmChan.setCid("3402000000131000555"+uid);
                mmChan.setNa("channel"+uid);
                mmChan.setSta(1);
                mMainActivity.ChanIn(mmChan);
            }

            Toast.makeText(mMainActivity,"已添加", Toast.LENGTH_LONG).show();

            mChannelSettingsFragment.showChannels();
        }catch(Exception e){
            Log.e(TAG,"add  Exception  "+e.toString());
        }
    }
}





