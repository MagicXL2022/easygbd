package com.easygbs.easygbd.viewmodel.fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easygbs.easygbd.R;
import com.easygbs.easygbd.activity.MainActivity;
import com.easygbs.easygbd.adapter.LoglevelAdapter;
import com.easygbs.easygbd.adapter.TranproAdapter;
import com.easygbs.easygbd.adapter.VerAdapter;
import com.easygbs.easygbd.bean.LoglevelBean;
import com.easygbs.easygbd.bean.TranproBean;
import com.easygbs.easygbd.bean.VerBean;
import com.easygbs.easygbd.common.Constant;
import com.easygbs.easygbd.fragment.BasicSettingsFragment;
import com.easygbs.easygbd.util.SPHelper;
import com.easygbs.easygbd.viewadapter.MultiItemTypeAdapter;
import com.easygbs.easygbd.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class BasicSettingsViewModel extends BaseObservable{
    private static final String TAG = BasicSettingsViewModel.class.getSimpleName();
    public MainActivity mMainActivity=null;
    public BasicSettingsFragment mBasicSettingsFragment=null;

    public LayoutInflater mLayoutInflater = null;

    public View mViewVer = null;

    public RecyclerView rvver;

    public List<VerBean> VerBeanList=null;

    public VerAdapter mVerAdapter=null;

    public PopupWindow mPopupVer = null;

    public View mViewTranpro = null;

    public RecyclerView rvtranpro;

    public List<TranproBean> TranproBeanList=null;

    public TranproAdapter mTranproAdapter=null;

    public PopupWindow mPopupTranpro = null;

    public View mViewLoglevel = null;

    public RecyclerView rvloglevel;

    public List<LoglevelBean> LoglevelBeanList=null;

    public LoglevelAdapter mLoglevelAdapter=null;

    public PopupWindow mPopupLoglevel = null;

    public ObservableField<String> sipserveraddrObservableField = new ObservableField<>();

    public SPHelper mSPHelper;

    public BasicSettingsViewModel(MainActivity mMainActivity,BasicSettingsFragment mBasicSettingsFragment) {
        this.mMainActivity=mMainActivity;
        this.mBasicSettingsFragment=mBasicSettingsFragment;
        mLayoutInflater = LayoutInflater.from(mMainActivity);

        int getver = SPUtil.getVer(mMainActivity);
        mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvver.setText(""+getver);

        mBasicSettingsFragment.mFragmentBasicsettingsBinding.etport.setText(""+SPUtil.getServerport(mMainActivity));

        sipserveraddrObservableField.set(SPUtil.getServerip(mMainActivity));

        mBasicSettingsFragment.mFragmentBasicsettingsBinding.etsipserverid.setText(SPUtil.getServerid(mMainActivity));

        mBasicSettingsFragment.mFragmentBasicsettingsBinding.etsipserverdomain.setText(SPUtil.getServerdomain(mMainActivity));
        mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvsipname.setText(SPUtil.getDeviceid(mMainActivity));
        mBasicSettingsFragment.mFragmentBasicsettingsBinding.etdevicename.setText(SPUtil.getDevicename(mMainActivity));
        mBasicSettingsFragment.mFragmentBasicsettingsBinding.etsippassword.setText(SPUtil.getPassword(mMainActivity));
        mBasicSettingsFragment.mFragmentBasicsettingsBinding.etregistervalidtime.setText(""+SPUtil.getRegexpires(mMainActivity)+"秒");
        mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvheartbeatcycle.setText(""+SPUtil.getHeartbeatinterval(mMainActivity)+"秒");
        mBasicSettingsFragment.mFragmentBasicsettingsBinding.etheartbeatcount.setText(""+SPUtil.getHeartbeatcount(mMainActivity));

        mSPHelper = mMainActivity.getSPHelper();
        int logstatus= (int) mSPHelper.get(Constant.LOGSTATUS,1);
        if(logstatus==0){
            mBasicSettingsFragment.mFragmentBasicsettingsBinding.lllog.setBackgroundResource(R.mipmap.ic_notselected);

            mBasicSettingsFragment.mFragmentBasicsettingsBinding.ivlog.setVisibility(View.INVISIBLE);
        }else{
            mBasicSettingsFragment.mFragmentBasicsettingsBinding.lllog.setBackgroundResource(R.mipmap.ic_selected);

            mBasicSettingsFragment.mFragmentBasicsettingsBinding.ivlog.setVisibility(View.VISIBLE);
        }

        int pro = SPUtil.getProtocol(mMainActivity);
        if(pro==0){
            mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvtranpro.setText("UDP");
        }else{
            mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvtranpro.setText("TCP");
        }

        mSPHelper = mMainActivity.getSPHelper();
        String loglev = (String) mSPHelper.get(Constant.LOGLEV, "DEBUG");
        mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvloglevel.setText(loglev);
    }

    public void ver(View view) {
        if(mViewVer==null){
            mViewVer = mLayoutInflater.inflate(R.layout.po_ver,null);

            rvver= mViewVer.findViewById(R.id.rvver);

            rvver.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            VerBeanList = new ArrayList<VerBean>();
            mVerAdapter = new VerAdapter(mMainActivity, mMainActivity, R.layout.adapter_ver, VerBeanList);
            rvver.setAdapter(mVerAdapter);

            mVerAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String name = VerBeanList.get(position).getName();

                SPUtil.setVer(mMainActivity,Integer.parseInt(name));

                mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvver.setText(name);

                for (int i = 0; i < VerBeanList.size(); i++) {
                    VerBean mVerBean = VerBeanList.get(i);
                    mVerBean.setIsst(0);
                }

                for (int i = 0; i < VerBeanList.size(); i++) {
                    VerBean mVerBean = VerBeanList.get(i);
                    if (mVerBean.getName().equals(name)) {
                        mVerBean.setIsst(1);
                        break;
                    }
                }

                mVerAdapter.notifyDataSetChanged();

                hidePopVer();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
            });


            int getver = SPUtil.getVer(mMainActivity);

            String[] vers=mMainActivity.getResources().getStringArray(R.array.verarr);
            for(int i=0;i<vers.length;i++){
                VerBean mVerBean = new VerBean();
                mVerBean.setName(vers[i]);
                if(getver==Integer.parseInt(vers[i])){
                    mVerBean.setIsst(1);
                }else{
                    mVerBean.setIsst(0);
                }

                VerBeanList.add(mVerBean);
            }

            mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvver.setText(""+getver);

            mVerAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_140);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupVer = new PopupWindow(mViewVer,width,height);

        mPopupVer.setOutsideTouchable(true);

        mPopupVer.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {

            }
        });

        String ystr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_10);

        if(ystr.contains(".")){
            ystr=ystr.substring(0,ystr.indexOf("."));
        }

        int y=Integer.parseInt(""+ystr)+1;

        String xstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_20);

        if(xstr.contains(".")){
            xstr=xstr.substring(0,xstr.indexOf("."));
        }

        int x=(Integer.parseInt(""+xstr)+1)*(-1);

        mPopupVer.showAsDropDown(mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvver,x,y);
    }

    public void hidePopVer(){
        if(mPopupVer!=null){
            boolean isShowing=mPopupVer.isShowing();
            if(isShowing){
                mPopupVer.dismiss();
            }
        }
    }

    public void tranpro(View view) {
        if(mViewTranpro==null){
            mViewTranpro = mLayoutInflater.inflate(R.layout.po_tranpro,null);

            rvtranpro= mViewTranpro.findViewById(R.id.rvtranpro);

            rvtranpro.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            TranproBeanList = new ArrayList<TranproBean>();
            mTranproAdapter = new TranproAdapter(mMainActivity, mMainActivity, R.layout.adapter_tranpro, TranproBeanList);
            rvtranpro.setAdapter(mTranproAdapter);

            mTranproAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = TranproBeanList.get(position).getName();

                    int pro;
                    if(name.equals("UDP")){
                        pro=0;
                    }else{
                        pro=1;
                    }
                    SPUtil.setProtocol(mMainActivity,pro);

                    mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvtranpro.setText(name);

                    for (int i = 0; i < TranproBeanList.size(); i++) {
                        TranproBean mTranproBean = TranproBeanList.get(i);
                        mTranproBean.setIsst(0);
                    }

                    for (int i = 0; i < TranproBeanList.size(); i++) {
                        TranproBean mTranproBean = TranproBeanList.get(i);
                        if (mTranproBean.getName().equals(name)) {
                            mTranproBean.setIsst(1);
                            break;
                        }
                    }

                    mTranproAdapter.notifyDataSetChanged();

                    hidePopTranpro();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int pro = SPUtil.getProtocol(mMainActivity);
            String str="";
            if(pro==0){
                str="UDP";
            }else{
                str="TCP";
            }

            mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvtranpro.setText(str);

            String[] tranpros=mMainActivity.getResources().getStringArray(R.array.tranproarr);
            for(int i=0;i<tranpros.length;i++){
                TranproBean mTranproBean = new TranproBean();
                mTranproBean.setName(tranpros[i]);
                if(str.equals(tranpros[i])){
                    mTranproBean.setIsst(1);
                }else{
                    mTranproBean.setIsst(0);
                }

                TranproBeanList.add(mTranproBean);
            }

            mTranproAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_140);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupTranpro = new PopupWindow(mViewTranpro,width,height);

        mPopupTranpro.setOutsideTouchable(true);

        mPopupTranpro.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {

            }
        });

        String ystr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_10);

        if(ystr.contains(".")){
            ystr=ystr.substring(0,ystr.indexOf("."));
        }

        int y=Integer.parseInt(""+ystr)+1;

        String xstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_20);

        if(xstr.contains(".")){
            xstr=xstr.substring(0,xstr.indexOf("."));
        }

        int x=(Integer.parseInt(""+xstr)+1)*(-1);

        mPopupTranpro.showAsDropDown(mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvtranpro,x,y);
    }

    public void hidePopTranpro(){
        if(mPopupTranpro!=null){
            boolean isShowing=mPopupTranpro.isShowing();
            if(isShowing){
                mPopupTranpro.dismiss();
            }
        }
    }

    public void loglevel(View view) {
        if(mViewLoglevel==null){
            mViewLoglevel = mLayoutInflater.inflate(R.layout.po_loglevel,null);

            rvloglevel= mViewLoglevel.findViewById(R.id.rvloglevel);

            rvloglevel.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            LoglevelBeanList = new ArrayList<LoglevelBean>();
            mLoglevelAdapter = new LoglevelAdapter(mMainActivity, mMainActivity, R.layout.adapter_loglevel,LoglevelBeanList);
            rvloglevel.setAdapter(mLoglevelAdapter);

            mLoglevelAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = LoglevelBeanList.get(position).getName();

                    String loglev = (String) mSPHelper.get(Constant.LOGLEV, "DEBUG");
                    if(loglev.equals(name)){
                        Log.i(TAG,"一样  log");

                        hidePopLoglevel();
                        return;
                    }

                    mBasicSettingsFragment.stop();

                    mSPHelper.put(Constant.LOGLEV,name);

                    mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvloglevel.setText(name);

                    for (int i = 0; i < LoglevelBeanList.size(); i++) {
                        LoglevelBean mLoglevelBean = LoglevelBeanList.get(i);
                        mLoglevelBean.setIsst(0);
                    }

                    for (int i = 0; i < LoglevelBeanList.size(); i++) {
                        LoglevelBean mLoglevelBean = LoglevelBeanList.get(i);
                        if (mLoglevelBean.getName().equals(name)) {
                            mLoglevelBean.setIsst(1);
                            break;
                        }
                    }

                    mLoglevelAdapter.notifyDataSetChanged();

                    mBasicSettingsFragment.running=true;

                    mBasicSettingsFragment.mHandler.removeMessages(Constant.STARTPRINTLOG);

                    mBasicSettingsFragment.mHandler.sendEmptyMessageDelayed(Constant.STARTPRINTLOG,2000);

                    hidePopLoglevel();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            SPHelper mSPHelper = mMainActivity.getSPHelper();
            String loglev = (String) mSPHelper.get(Constant.LOGLEV, "DEBUG");
            mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvloglevel.setText(loglev);

            String[] loglevels=mMainActivity.getResources().getStringArray(R.array.loglevelarr);
            for(int i=0;i<loglevels.length;i++){
                LoglevelBean mLoglevelBean = new LoglevelBean();
                mLoglevelBean.setName(loglevels[i]);
                if(loglev.equals(loglevels[i])){
                    mLoglevelBean.setIsst(1);
                }else{
                    mLoglevelBean.setIsst(0);
                }

                LoglevelBeanList.add(mLoglevelBean);
            }

            mLoglevelAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_270);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupLoglevel = new PopupWindow(mViewLoglevel,width,height);

        mPopupLoglevel.setOutsideTouchable(true);

        mPopupLoglevel.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {

            }
        });

        String ystr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_10);

        if(ystr.contains(".")){
            ystr=ystr.substring(0,ystr.indexOf("."));
        }

        int y=Integer.parseInt(""+ystr)+1;

        String xstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_20);

        if(xstr.contains(".")){
            xstr=xstr.substring(0,xstr.indexOf("."));
        }

        int x=(Integer.parseInt(""+xstr)+1)*(-1);

        mPopupLoglevel.showAsDropDown(mBasicSettingsFragment.mFragmentBasicsettingsBinding.tvloglevel,x,y);
    }

    public void hidePopLoglevel(){
        if(mPopupLoglevel!=null){
            boolean isShowing=mPopupLoglevel.isShowing();
            if(isShowing){
                mPopupLoglevel.dismiss();
            }
        }
    }
}

