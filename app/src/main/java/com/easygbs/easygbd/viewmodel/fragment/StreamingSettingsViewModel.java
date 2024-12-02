package com.easygbs.easygbd.viewmodel.fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easygbs.easygbd.R;
import com.easygbs.easygbd.activity.MainActivity;
import com.easygbs.easygbd.adapter.AudiochannelAdapter;
import com.easygbs.easygbd.adapter.AudiocodeAdapter;
import com.easygbs.easygbd.adapter.AudiocoderateAdapter;
import com.easygbs.easygbd.adapter.CameraAdapter;
import com.easygbs.easygbd.adapter.FramerateAdapter;
import com.easygbs.easygbd.adapter.LocationfrequencyAdapter;
import com.easygbs.easygbd.adapter.ResolutionAdapter;
import com.easygbs.easygbd.adapter.SamplingrateAdapter;
import com.easygbs.easygbd.adapter.VideoCodeAdapter;
import com.easygbs.easygbd.adapter.VideocoderateAdapter;
import com.easygbs.easygbd.bean.AudiochannelBean;
import com.easygbs.easygbd.bean.AudiocodeBean;
import com.easygbs.easygbd.bean.AudiocoderateBean;
import com.easygbs.easygbd.bean.CameraBean;
import com.easygbs.easygbd.bean.FramerateBean;
import com.easygbs.easygbd.bean.LocationfrequencyBean;
import com.easygbs.easygbd.bean.ResolutionBean;
import com.easygbs.easygbd.bean.SamplingrateBean;
import com.easygbs.easygbd.bean.VideoCodeBean;
import com.easygbs.easygbd.bean.VideocoderateBean;
import com.easygbs.easygbd.fragment.StreamingSettingsFragment;
import com.easygbs.easygbd.push.MediaStream;
import com.easygbs.easygbd.viewadapter.MultiItemTypeAdapter;
import com.easygbs.easygbd.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class StreamingSettingsViewModel extends BaseObservable{
    private static final String TAG = StreamingSettingsViewModel.class.getSimpleName();
    public MainActivity mMainActivity=null;
    public StreamingSettingsFragment mStreamingSettingsFragment=null;

    public LayoutInflater mLayoutInflater = null;

    public View mViewCamera = null;

    public RecyclerView rvcamera;

    public List<CameraBean> CameraBeanList=null;

    public CameraAdapter mCameraAdapter=null;

    public PopupWindow mPopupCamera = null;

    public View mViewVideoCode = null;

    public RecyclerView rvvideocode;

    public List<VideoCodeBean> VideoCodeBeanList=null;

    public VideoCodeAdapter mVideoCodeAdapter=null;

    public PopupWindow mPopupVideoCode = null;

    public View mViewResolution = null;

    public RecyclerView rvresolution;

    public List<ResolutionBean> ResolutionBeanList=null;

    public ResolutionAdapter mResolutionAdapter=null;

    public PopupWindow mPopupResolution = null;

    public View mViewFramerate = null;

    public RecyclerView rvframerate;

    public List<FramerateBean> FramerateBeanList=null;

    public FramerateAdapter mFramerateAdapter=null;

    public PopupWindow mPopupFramerate = null;

    public View mViewvideocoderate = null;

    public RecyclerView rvvideocoderate;

    public List<VideocoderateBean> VideocoderateBeanList=null;

    public VideocoderateAdapter mVideocoderateAdapter=null;

    public PopupWindow mPopupvideocoderate = null;

    public View mViewaudiocode = null;

    public RecyclerView rvaudiocode;

    public List<AudiocodeBean> AudiocodeBeanList=null;

    public AudiocodeAdapter mAudiocodeAdapter=null;

    public PopupWindow mPopupaudiocode = null;

    public View mViewsamplingrate = null;

    public RecyclerView rvsamplingrate;

    public List<SamplingrateBean> SamplingrateBeanList=null;

    public SamplingrateAdapter mSamplingrateAdapter=null;

    public PopupWindow mPopupsamplingrate = null;

    public View mViewaudiochannel = null;

    public RecyclerView rvaudiochannel;

    public List<AudiochannelBean> AudiochannelBeanList=null;

    public AudiochannelAdapter mAudiochannelAdapter=null;

    public PopupWindow mPopupaudiochannel = null;

    public View mViewaudiocoderate = null;

    public RecyclerView rvaudiocoderate;

    public List<AudiocoderateBean> AudiocoderateBeanList=null;

    public AudiocoderateAdapter mAudiocoderateAdapter=null;

    public PopupWindow mPopupaudiocoderate = null;

    public View mViewlocationfrequency = null;

    public RecyclerView rvlocationfrequency;

    public List<LocationfrequencyBean> LocationfrequencyBeanList=null;

    public LocationfrequencyAdapter mLocationfrequencyAdapter=null;

    public PopupWindow mPopuplocationfrequency = null;

    public ObservableField<String> audiochannelObservableField = new ObservableField<>();

    public StreamingSettingsViewModel(MainActivity mMainActivity, StreamingSettingsFragment mStreamingSettingsFragment) {
        this.mMainActivity=mMainActivity;
        this.mStreamingSettingsFragment=mStreamingSettingsFragment;
        mLayoutInflater = LayoutInflater.from(mMainActivity);

        int isenvideo=SPUtil.getIsenvideo(mMainActivity);
        if(isenvideo==0) {
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.llvideoset.setBackgroundResource(R.mipmap.ic_notselected);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.ivvideoset.setVisibility(View.INVISIBLE);
        }else{
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.llvideoset.setBackgroundResource(R.mipmap.ic_selected);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.ivvideoset.setVisibility(View.VISIBLE);
        }

        int cameraid=SPUtil.getCameraid(mMainActivity);
        if(cameraid==0) {
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvcamera.setText("后置相机");
        }else{
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvcamera.setText("前置相机");
        }

        boolean mHevc = SPUtil.getHevcCodec(mMainActivity);
        String type="";
        if(mHevc) {
            type="H265";
        }else{
            type="H264";
        }
        mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvvideocode.setText(type);

        int videoresolution=SPUtil.getVideoresolution(mMainActivity);
        if(videoresolution==0) {
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvresolution.setText("1920*1080");
        }else if(videoresolution==1) {
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvresolution.setText("1280*720");
        }else if(videoresolution==2) {
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvresolution.setText("640*480");
        }

        int framerate=SPUtil.getFramerate(mMainActivity);
        mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvframerate.setText(""+framerate);

        int videocoderate=SPUtil.getBitrateKbps(mMainActivity);
        mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvvideocoderate.setText(""+videocoderate);

        int isenaudio=SPUtil.getIsenaudio(mMainActivity);
        if(isenaudio==0) {
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.llaudioset.setBackgroundResource(R.mipmap.ic_notselected);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.ivaudioset.setVisibility(View.INVISIBLE);
        }else{
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.llaudioset.setBackgroundResource(R.mipmap.ic_selected);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.ivaudioset.setVisibility(View.VISIBLE);
        }

        int AACCodec=SPUtil.getAACCodec(mMainActivity);
        String str="";
        if(AACCodec==0){
            str="G711A";
        }else if(AACCodec==1){
            str="G711U";
        }else if(AACCodec==2){
            str="AAC";
        }

        mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiocode.setText(str);

        int samplingrate=SPUtil.getSamplingrate(mMainActivity);
        mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvsamplingrate.setText(""+samplingrate);

        int audiochannel=SPUtil.getAudiochannel(mMainActivity);
        String auch="";
        if(audiochannel==0){
            auch="单声道";
        }else{
            auch="立体声道";
        }
        audiochannelObservableField.set(auch);

        int audiocoderate=SPUtil.getAudiocoderate(mMainActivity);
        mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiocoderate.setText(""+audiocoderate);


        int isenlocreport=SPUtil.getIsenlocreport(mMainActivity);
        if(isenlocreport==0) {
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.lllocreportset.setBackgroundResource(R.mipmap.ic_notselected);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.ivlocreportset.setVisibility(View.INVISIBLE);
        }else{
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.lllocreportset.setBackgroundResource(R.mipmap.ic_selected);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.ivlocreportset.setVisibility(View.VISIBLE);
        }

        int locationfreq=SPUtil.getLocationfreq(mMainActivity);
        mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvlocationfrequency.setText(""+locationfreq+"秒");

    }

    public void camera(View view){
        if(mViewCamera==null){
            mViewCamera = mLayoutInflater.inflate(R.layout.po_camera,null);

            rvcamera= mViewCamera.findViewById(R.id.rvcamera);

            rvcamera.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            CameraBeanList = new ArrayList<CameraBean>();
            mCameraAdapter = new CameraAdapter(mMainActivity, mMainActivity, R.layout.adapter_camera,CameraBeanList);
            rvcamera.setAdapter(mCameraAdapter);

            mCameraAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = CameraBeanList.get(position).getName();

                    SPUtil.setCameraid(mMainActivity,position);

                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvcamera.setText(name);

                    for (int i = 0; i < CameraBeanList.size(); i++) {
                        CameraBean mCameraBean = CameraBeanList.get(i);
                        mCameraBean.setIsst(0);
                    }

                    for (int i = 0; i < CameraBeanList.size(); i++) {
                        CameraBean mCameraBean = CameraBeanList.get(i);
                        if (mCameraBean.getName().equals(name)) {
                            mCameraBean.setIsst(1);
                            break;
                        }
                    }

                    mCameraAdapter.notifyDataSetChanged();

                    hidePopCamera();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });


            int cameraid=SPUtil.getCameraid(mMainActivity);
            String str="";
            if(cameraid==0){
                str="后置相机";
            }else{
                str="前置相机";
            }

            String[] cameras=mMainActivity.getResources().getStringArray(R.array.cameraarr);
            for(int i=0;i<cameras.length;i++){
                CameraBean mCameraBean = new CameraBean();
                mCameraBean.setName(cameras[i]);
                if(str.equals(cameras[i])){
                    mCameraBean.setIsst(1);
                }else{
                    mCameraBean.setIsst(0);
                }

                CameraBeanList.add(mCameraBean);
            }

            mCameraAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_150);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupCamera = new PopupWindow(mViewCamera,width,height);

        mPopupCamera.setOutsideTouchable(true);

        mPopupCamera.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopupCamera.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvcamera,x,y);
    }

    public void hidePopCamera(){
        if(mPopupCamera!=null){
            boolean isShowing=mPopupCamera.isShowing();
            if(isShowing){
                mPopupCamera.dismiss();
            }
        }
    }

    public void videocode(View view){
        if(mViewVideoCode==null){
            mViewVideoCode = mLayoutInflater.inflate(R.layout.po_videocode,null);

            rvvideocode= mViewVideoCode.findViewById(R.id.rvvideocode);

            rvvideocode.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            VideoCodeBeanList = new ArrayList<VideoCodeBean>();
            mVideoCodeAdapter = new VideoCodeAdapter(mMainActivity, mMainActivity, R.layout.adapter_videocode,VideoCodeBeanList);
            rvvideocode.setAdapter(mVideoCodeAdapter);

            mVideoCodeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = VideoCodeBeanList.get(position).getName();
                    if(name.equals("H264")){
                        SPUtil.setHevcCodec(mMainActivity,false);
                    }else if(name.equals("H265")){
                        SPUtil.setHevcCodec(mMainActivity,true);
                    }

                    boolean mHevc = SPUtil.getHevcCodec(mMainActivity);

                    Log.i(TAG,"videocode  onItemClick  mHevc  "+mHevc+"   name  "+name);


                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvvideocode.setText(name);

                    for (int i = 0; i < VideoCodeBeanList.size(); i++) {
                        VideoCodeBean mVideoCodeBean = VideoCodeBeanList.get(i);
                        mVideoCodeBean.setIsst(0);
                    }

                    for (int i = 0; i < VideoCodeBeanList.size(); i++) {
                        VideoCodeBean mVideoCodeBean = VideoCodeBeanList.get(i);
                        if (mVideoCodeBean.getName().equals(name)) {
                            mVideoCodeBean.setIsst(1);
                            break;
                        }
                    }

                    mVideoCodeAdapter.notifyDataSetChanged();

                    hidePopVideoCode();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            boolean mHevc = SPUtil.getHevcCodec(mMainActivity);

            Log.i(TAG,"videocode  mHevc  "+mHevc);

            String type="";
            String[] videocodes=mMainActivity.getResources().getStringArray(R.array.videocodearr);
            for(int i=0;i<videocodes.length;i++){
                VideoCodeBean mVideoCodeBean = new VideoCodeBean();
                mVideoCodeBean.setName(videocodes[i]);
                if(mHevc){
                    if(videocodes[i].equals("H265")){
                        mVideoCodeBean.setIsst(1);
                        type=videocodes[i];
                    }else{
                        mVideoCodeBean.setIsst(0);
                    }
                }else{
                    if(videocodes[i].equals("H264")){
                        mVideoCodeBean.setIsst(1);
                        type=videocodes[i];
                    }else{
                        mVideoCodeBean.setIsst(0);
                    }
                }

                VideoCodeBeanList.add(mVideoCodeBean);
            }

            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvvideocode.setText(type);

            mVideoCodeAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_150);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupVideoCode = new PopupWindow(mViewVideoCode,width,height);

        mPopupVideoCode.setOutsideTouchable(true);

        mPopupVideoCode.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopupVideoCode.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvvideocode,x,y);
    }

    public void hidePopVideoCode(){
        if(mPopupVideoCode!=null){
            boolean isShowing=mPopupVideoCode.isShowing();
            if(isShowing){
                mPopupVideoCode.dismiss();
            }
        }
    }

    public void resolution(View view){
        if(mViewResolution==null){
            mViewResolution = mLayoutInflater.inflate(R.layout.po_resolution,null);

            rvresolution= mViewResolution.findViewById(R.id.rvresolution);

            rvresolution.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            ResolutionBeanList = new ArrayList<ResolutionBean>();
            mResolutionAdapter = new ResolutionAdapter(mMainActivity, mMainActivity, R.layout.adapter_resolution,ResolutionBeanList);
            rvresolution.setAdapter(mResolutionAdapter);

            mResolutionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = ResolutionBeanList.get(position).getName();

                    int videoresolution=0;
                    if(name.equals("1920*1080")) {
                        videoresolution=0;
                    }else if(name.equals("1280*720")) {
                        videoresolution=1;
                    }else if(name.equals("640*480")) {
                        videoresolution=2;
                    }
                    SPUtil.setVideoresolution(mMainActivity,videoresolution);

                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvresolution.setText(name);

                    for (int i = 0; i < ResolutionBeanList.size(); i++) {
                        ResolutionBean mResolutionBean = ResolutionBeanList.get(i);
                        mResolutionBean.setIsst(0);
                    }

                    for (int i = 0; i < ResolutionBeanList.size(); i++) {
                        ResolutionBean mResolutionBean = ResolutionBeanList.get(i);
                        if (mResolutionBean.getName().equals(name)) {
                            mResolutionBean.setIsst(1);
                            break;
                        }
                    }

                    mResolutionAdapter.notifyDataSetChanged();

                    hidePopResolution();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int videoresolution=SPUtil.getVideoresolution(mMainActivity);
            String str="";
            if(videoresolution==0) {
                str="1920*1080";
            }else if(videoresolution==1) {
                str="1280*720";
            }else if(videoresolution==2) {
                str="640*480";
            }

            String[] resolutions=mMainActivity.getResources().getStringArray(R.array.resolutionarr);
            for(int i=0;i<resolutions.length;i++){
                ResolutionBean mResolutionBean = new ResolutionBean();
                mResolutionBean.setName(resolutions[i]);
                if(str.equals(resolutions[i])){
                    mResolutionBean.setIsst(1);
                }else{
                    mResolutionBean.setIsst(0);
                }

                ResolutionBeanList.add(mResolutionBean);
            }

            mResolutionAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_120);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_215);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupResolution = new PopupWindow(mViewResolution,width,height);

        mPopupResolution.setOutsideTouchable(true);

        mPopupResolution.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {

            }
        });

        String ystr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_10);

        if(ystr.contains(".")){
            ystr=ystr.substring(0,ystr.indexOf("."));
        }

        int y=Integer.parseInt(""+ystr)+1;

        String xstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_10);

        if(xstr.contains(".")){
            xstr=xstr.substring(0,xstr.indexOf("."));
        }

        int x=(Integer.parseInt(""+xstr)+1)*(-1);

        mPopupResolution.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvresolution,x,y);
    }

    public void hidePopResolution(){
        if(mPopupResolution!=null){
            boolean isShowing=mPopupResolution.isShowing();
            if(isShowing){
                mPopupResolution.dismiss();
            }
        }
    }

    public void framerate(View view){
        if(mViewFramerate==null){
            mViewFramerate = mLayoutInflater.inflate(R.layout.po_framerate,null);

            rvframerate= mViewFramerate.findViewById(R.id.rvframerate);

            rvframerate.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            FramerateBeanList = new ArrayList<FramerateBean>();
            mFramerateAdapter = new FramerateAdapter(mMainActivity, mMainActivity, R.layout.adapter_framerate,FramerateBeanList);
            rvframerate.setAdapter(mFramerateAdapter);

            mFramerateAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = FramerateBeanList.get(position).getName();
                    SPUtil.setFramerate(mMainActivity,Integer.parseInt(name));
                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvframerate.setText(name);

                    for (int i = 0; i < FramerateBeanList.size(); i++) {
                        FramerateBean mFramerateBean = FramerateBeanList.get(i);
                        mFramerateBean.setIsst(0);
                    }

                    for (int i = 0; i < FramerateBeanList.size(); i++) {
                        FramerateBean mFramerateBean = FramerateBeanList.get(i);
                        if (mFramerateBean.getName().equals(name)) {
                            mFramerateBean.setIsst(1);
                            break;
                        }
                    }

                    mFramerateAdapter.notifyDataSetChanged();

                    hidePopFramerate();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int framerate=SPUtil.getFramerate(mMainActivity);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvframerate.setText(""+framerate);

            String[] framerates=mMainActivity.getResources().getStringArray(R.array.frameratearr);
            for(int i=0;i<framerates.length;i++){
                FramerateBean mFramerateBean = new FramerateBean();
                mFramerateBean.setName(framerates[i]);
                if(framerate==Integer.parseInt(framerates[i])){
                    mFramerateBean.setIsst(1);
                }else{
                    mFramerateBean.setIsst(0);
                }

                FramerateBeanList.add(mFramerateBean);
            }

            mFramerateAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_345);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupFramerate = new PopupWindow(mViewFramerate,width,height);

        mPopupFramerate.setOutsideTouchable(true);

        mPopupFramerate.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopupFramerate.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvframerate,x,y);
    }

    public void hidePopFramerate(){
        if(mPopupFramerate!=null){
            boolean isShowing=mPopupFramerate.isShowing();
            if(isShowing){
                mPopupFramerate.dismiss();
            }
        }
    }

    public void videocoderate(View view){
        if(mViewvideocoderate==null){
            mViewvideocoderate = mLayoutInflater.inflate(R.layout.po_videocoderate,null);

            rvvideocoderate= mViewvideocoderate.findViewById(R.id.rvvideocoderate);

            rvvideocoderate.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            VideocoderateBeanList = new ArrayList<VideocoderateBean>();
            mVideocoderateAdapter = new VideocoderateAdapter(mMainActivity, mMainActivity, R.layout.adapter_videocoderate,VideocoderateBeanList);
            rvvideocoderate.setAdapter(mVideocoderateAdapter);

            mVideocoderateAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = VideocoderateBeanList.get(position).getName();
                    SPUtil.setBitrateKbps(mMainActivity,Integer.parseInt(name));

                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvvideocoderate.setText(name);

                    for (int i = 0; i < VideocoderateBeanList.size(); i++) {
                        VideocoderateBean mVideocoderateBean = VideocoderateBeanList.get(i);
                        mVideocoderateBean.setIsst(0);
                    }

                    for (int i = 0; i < VideocoderateBeanList.size(); i++) {
                        VideocoderateBean mVideocoderateBean = VideocoderateBeanList.get(i);
                        if (mVideocoderateBean.getName().equals(name)) {
                            mVideocoderateBean.setIsst(1);
                            break;
                        }
                    }

                    mVideocoderateAdapter.notifyDataSetChanged();

                    hidePopvideocoderate();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int videocoderate=SPUtil.getBitrateKbps(mMainActivity);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvvideocoderate.setText(""+videocoderate);

            String[] videocoderates=mMainActivity.getResources().getStringArray(R.array.videocoderatearr);
            for(int i=0;i<videocoderates.length;i++){
                VideocoderateBean mVideocoderateBean = new VideocoderateBean();
                mVideocoderateBean.setName(videocoderates[i]);
                if(videocoderate==Integer.parseInt(videocoderates[i])){
                    mVideocoderateBean.setIsst(1);
                }else{
                    mVideocoderateBean.setIsst(0);
                }

                VideocoderateBeanList.add(mVideocoderateBean);
            }

            mVideocoderateAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_640);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupvideocoderate = new PopupWindow(mViewvideocoderate,width,height);

        mPopupvideocoderate.setOutsideTouchable(true);

        mPopupvideocoderate.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopupvideocoderate.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvvideocoderate,x,y);
    }

    public void hidePopvideocoderate(){
        if(mPopupvideocoderate!=null){
            boolean isShowing=mPopupvideocoderate.isShowing();
            if(isShowing){
                mPopupvideocoderate.dismiss();
            }
        }
    }

    public void audiocode(View view){
        if(mViewaudiocode==null){
            mViewaudiocode = mLayoutInflater.inflate(R.layout.po_audiocode,null);

            rvaudiocode= mViewaudiocode.findViewById(R.id.rvaudiocode);

            rvaudiocode.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            AudiocodeBeanList = new ArrayList<AudiocodeBean>();
            mAudiocodeAdapter = new AudiocodeAdapter(mMainActivity, mMainActivity, R.layout.adapter_audiocode,AudiocodeBeanList);
            rvaudiocode.setAdapter(mAudiocodeAdapter);

            mAudiocodeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = AudiocodeBeanList.get(position).getName();

                    int value = 0;
                    if(name.equals("G711A")){
                        value=0;
                    }else if(name.equals("G711U")){
                        value=1;
                    }else if(name.equals("AAC")){
                        value=2;
                    }

                    SPUtil.setAACCodec(mMainActivity,value);

                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiocode.setText(name);

                    for (int i = 0; i < AudiocodeBeanList.size(); i++) {
                        AudiocodeBean mAudiocodeBean = AudiocodeBeanList.get(i);
                        mAudiocodeBean.setIsst(0);
                    }

                    for (int i = 0; i < AudiocodeBeanList.size(); i++) {
                        AudiocodeBean mAudiocodeBean = AudiocodeBeanList.get(i);
                        if (mAudiocodeBean.getName().equals(name)) {
                            mAudiocodeBean.setIsst(1);
                            break;
                        }
                    }

                    mAudiocodeAdapter.notifyDataSetChanged();

                    hidePopaudiocode();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int AACCodec=SPUtil.getAACCodec(mMainActivity);
            String str="";
            if(AACCodec==0){
                str="G711A";
            }else if(AACCodec==1){
                str="G711U";
            }else if(AACCodec==2){
                str="AAC";
            }

            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiocode.setText(str);

            String[] audiocodes=mMainActivity.getResources().getStringArray(R.array.audiocodearr);
            for(int i=0;i<audiocodes.length;i++){
                AudiocodeBean mAudiocodeBean = new AudiocodeBean();
                mAudiocodeBean.setName(audiocodes[i]);
                if(str.equals(audiocodes[i])){
                    mAudiocodeBean.setIsst(1);
                }else{
                    mAudiocodeBean.setIsst(0);
                }

                AudiocodeBeanList.add(mAudiocodeBean);
            }

            mAudiocodeAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_215);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupaudiocode = new PopupWindow(mViewaudiocode,width,height);

        mPopupaudiocode.setOutsideTouchable(true);

        mPopupaudiocode.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopupaudiocode.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiocode,x,y);
    }

    public void hidePopaudiocode(){
        if(mPopupaudiocode!=null){
            boolean isShowing=mPopupaudiocode.isShowing();
            if(isShowing){
                mPopupaudiocode.dismiss();
            }
        }
    }

    public void samplingrate(View view){
        if(mViewsamplingrate==null){
            mViewsamplingrate = mLayoutInflater.inflate(R.layout.po_samplingrate,null);

            rvsamplingrate= mViewsamplingrate.findViewById(R.id.rvsamplingrate);

            rvsamplingrate.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            SamplingrateBeanList = new ArrayList<SamplingrateBean>();
            mSamplingrateAdapter = new SamplingrateAdapter(mMainActivity, mMainActivity, R.layout.adapter_samplingrate,SamplingrateBeanList);
            rvsamplingrate.setAdapter(mSamplingrateAdapter);

            mSamplingrateAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = SamplingrateBeanList.get(position).getName();
                    SPUtil.setSamplingrate(mMainActivity,Integer.parseInt(name));

                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvsamplingrate.setText(name);

                    for (int i = 0; i < SamplingrateBeanList.size(); i++) {
                        SamplingrateBean mSamplingrateBean = SamplingrateBeanList.get(i);
                        mSamplingrateBean.setIsst(0);
                    }

                    for (int i = 0; i < SamplingrateBeanList.size(); i++) {
                        SamplingrateBean mSamplingrateBean = SamplingrateBeanList.get(i);
                        if (mSamplingrateBean.getName().equals(name)) {
                            mSamplingrateBean.setIsst(1);
                            break;
                        }
                    }

                    mSamplingrateAdapter.notifyDataSetChanged();

                    hidePopsamplingrate();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int samplingrate=SPUtil.getSamplingrate(mMainActivity);
            mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvsamplingrate.setText(""+samplingrate);

            String[] samplingrates=mMainActivity.getResources().getStringArray(R.array.samplingratearr);
            for(int i=0;i<samplingrates.length;i++){
                SamplingrateBean mSamplingrateBean = new SamplingrateBean();
                mSamplingrateBean.setName(samplingrates[i]);
                if(samplingrate==Integer.parseInt(samplingrates[i])){
                    mSamplingrateBean.setIsst(1);
                }else{
                    mSamplingrateBean.setIsst(0);
                }

                SamplingrateBeanList.add(mSamplingrateBean);
            }

            mSamplingrateAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_590);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupsamplingrate = new PopupWindow(mViewsamplingrate,width,height);

        mPopupsamplingrate.setOutsideTouchable(true);

        mPopupsamplingrate.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopupsamplingrate.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvsamplingrate,x,y);
    }

    public void hidePopsamplingrate(){
        if(mPopupsamplingrate!=null){
            boolean isShowing=mPopupsamplingrate.isShowing();
            if(isShowing){
                mPopupsamplingrate.dismiss();
            }
        }
    }

    public void audiochannel(View view){
        if(mViewaudiochannel==null){
            mViewaudiochannel = mLayoutInflater.inflate(R.layout.po_audiochannel,null);

            rvaudiochannel= mViewaudiochannel.findViewById(R.id.rvaudiochannel);

            rvaudiochannel.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            AudiochannelBeanList = new ArrayList<AudiochannelBean>();
            mAudiochannelAdapter = new AudiochannelAdapter(mMainActivity, mMainActivity, R.layout.adapter_audiochannel,AudiochannelBeanList);
            rvaudiochannel.setAdapter(mAudiochannelAdapter);

            mAudiochannelAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = AudiochannelBeanList.get(position).getName();

                    int value;
                    if(name.equals("单声道")){
                        value=0;
                    }else{
                        value = 1;
                    }
                    SPUtil.setAudiochannel(mMainActivity,value);

                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiochannel.setText(name);

                    for (int i = 0; i < AudiochannelBeanList.size(); i++) {
                        AudiochannelBean mAudiochannelBean = AudiochannelBeanList.get(i);
                        mAudiochannelBean.setIsst(0);
                    }

                    for (int i = 0; i < AudiochannelBeanList.size(); i++) {
                        AudiochannelBean mAudiochannelBean = AudiochannelBeanList.get(i);
                        if (mAudiochannelBean.getName().equals(name)) {
                            mAudiochannelBean.setIsst(1);
                            break;
                        }
                    }

                    mAudiochannelAdapter.notifyDataSetChanged();

                    hidePopaudiochannel();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int audiochannel=SPUtil.getAudiochannel(mMainActivity);
            String str="";
            if(audiochannel==0){
                str="单声道";
            }else{
                str="立体声道";
            }

            String[] audiochannels=mMainActivity.getResources().getStringArray(R.array.audiochannelarr);
            for(int i=0;i<audiochannels.length;i++){
                AudiochannelBean mAudiochannelBean = new AudiochannelBean();
                mAudiochannelBean.setName(audiochannels[i]);
                if(str.equals(audiochannels[i])){
                    mAudiochannelBean.setIsst(1);
                }else{
                    mAudiochannelBean.setIsst(0);
                }

                AudiochannelBeanList.add(mAudiochannelBean);
            }

            mAudiochannelAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_150);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupaudiochannel = new PopupWindow(mViewaudiochannel,width,height);

        mPopupaudiochannel.setOutsideTouchable(true);

        mPopupaudiochannel.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopupaudiochannel.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiochannel,x,y);
    }

    public void hidePopaudiochannel(){
        if(mPopupaudiochannel!=null){
            boolean isShowing=mPopupaudiochannel.isShowing();
            if(isShowing){
                mPopupaudiochannel.dismiss();
            }
        }
    }

    public void audiocoderate(View view){
        if(mViewaudiocoderate==null){
            mViewaudiocoderate = mLayoutInflater.inflate(R.layout.po_audiocoderate,null);

            rvaudiocoderate= mViewaudiocoderate.findViewById(R.id.rvaudiocoderate);

            rvaudiocoderate.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            AudiocoderateBeanList = new ArrayList<AudiocoderateBean>();
            mAudiocoderateAdapter = new AudiocoderateAdapter(mMainActivity, mMainActivity, R.layout.adapter_audiocoderate,AudiocoderateBeanList);
            rvaudiocoderate.setAdapter(mAudiocoderateAdapter);

            mAudiocoderateAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = AudiocoderateBeanList.get(position).getName();
                    SPUtil.setAudiocoderate(mMainActivity,Integer.parseInt(name));

                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiocoderate.setText(name);

                    for (int i = 0; i < AudiocoderateBeanList.size(); i++) {
                        AudiocoderateBean mAudiocoderateBean = AudiocoderateBeanList.get(i);
                        mAudiocoderateBean.setIsst(0);
                    }

                    for (int i = 0; i < AudiocoderateBeanList.size(); i++) {
                        AudiocoderateBean mAudiocoderateBean = AudiocoderateBeanList.get(i);
                        if (mAudiocoderateBean.getName().equals(name)) {
                            mAudiocoderateBean.setIsst(1);
                            break;
                        }
                    }

                    mAudiocoderateAdapter.notifyDataSetChanged();

                    hidePopaudiocoderate();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int audiocoderate=SPUtil.getAudiocoderate(mMainActivity);

            String[] audiocoderates=mMainActivity.getResources().getStringArray(R.array.audiocoderatearr);
            for(int i=0;i<audiocoderates.length;i++){
                AudiocoderateBean mAudiocoderateBean = new AudiocoderateBean();
                mAudiocoderateBean.setName(audiocoderates[i]);
                if(audiocoderate==Integer.parseInt(audiocoderates[i])){
                    mAudiocoderateBean.setIsst(1);
                }else{
                    mAudiocoderateBean.setIsst(0);
                }

                AudiocoderateBeanList.add(mAudiocoderateBean);
            }

            mAudiocoderateAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_280);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopupaudiocoderate = new PopupWindow(mViewaudiocoderate,width,height);

        mPopupaudiocoderate.setOutsideTouchable(true);

        mPopupaudiocoderate.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopupaudiocoderate.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvaudiocoderate,x,y);
    }

    public void hidePopaudiocoderate(){
        if(mPopupaudiocoderate!=null){
            boolean isShowing=mPopupaudiocoderate.isShowing();
            if(isShowing){
                mPopupaudiocoderate.dismiss();
            }
        }
    }

    public void locationfrequency(View view){
        if(mViewlocationfrequency==null){
            mViewlocationfrequency = mLayoutInflater.inflate(R.layout.po_locationfrequency,null);

            rvlocationfrequency= mViewlocationfrequency.findViewById(R.id.rvlocationfrequency);

            rvlocationfrequency.setLayoutManager(new GridLayoutManager(mMainActivity,1, RecyclerView.VERTICAL,false));

            LocationfrequencyBeanList = new ArrayList<LocationfrequencyBean>();
            mLocationfrequencyAdapter = new LocationfrequencyAdapter(mMainActivity, mMainActivity, R.layout.adapter_locationfrequency,LocationfrequencyBeanList);
            rvlocationfrequency.setAdapter(mLocationfrequencyAdapter);

            mLocationfrequencyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    String name = LocationfrequencyBeanList.get(position).getName();
                    SPUtil.setLocationfreq(mMainActivity,Integer.parseInt(name.substring(0,name.length()-1)));

                    mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvlocationfrequency.setText(name);

                    for (int i = 0; i < LocationfrequencyBeanList.size(); i++) {
                        LocationfrequencyBean mLocationfrequencyBean = LocationfrequencyBeanList.get(i);
                        mLocationfrequencyBean.setIsst(0);
                    }

                    for (int i = 0; i < LocationfrequencyBeanList.size(); i++) {
                        LocationfrequencyBean mLocationfrequencyBean = LocationfrequencyBeanList.get(i);
                        if (mLocationfrequencyBean.getName().equals(name)) {
                            mLocationfrequencyBean.setIsst(1);
                            break;
                        }
                    }

                    mLocationfrequencyAdapter.notifyDataSetChanged();

                    hidePoplocationfrequency();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            int locationfreq=SPUtil.getLocationfreq(mMainActivity);

            String[] locationfrequencys=mMainActivity.getResources().getStringArray(R.array.locationfrequencyarr);
            for(int i=0;i<locationfrequencys.length;i++){
                LocationfrequencyBean mLocationfrequencyBean = new LocationfrequencyBean();
                mLocationfrequencyBean.setName(locationfrequencys[i]);
                if(locationfreq==Integer.parseInt(locationfrequencys[i].substring(0,locationfrequencys[i].length()-1))){
                    mLocationfrequencyBean.setIsst(1);
                }else{
                    mLocationfrequencyBean.setIsst(0);
                }

                LocationfrequencyBeanList.add(mLocationfrequencyBean);
            }

            mLocationfrequencyAdapter.notifyDataSetChanged();
        }

        String widthstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_100);
        if(widthstr.contains(".")){
            widthstr=widthstr.substring(0,widthstr.indexOf("."));
        }
        int width = Integer.parseInt(widthstr);

        String heightstr =""+ mMainActivity.getResources().getDimension(R.dimen.dp_650);
        if(heightstr.contains(".")){
            heightstr=heightstr.substring(0,heightstr.indexOf("."));
        }
        int height = Integer.parseInt(heightstr);

        mPopuplocationfrequency = new PopupWindow(mViewlocationfrequency,width,height);

        mPopuplocationfrequency.setOutsideTouchable(true);

        mPopuplocationfrequency.setOnDismissListener(new PopupWindow.OnDismissListener(){
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

        mPopuplocationfrequency.showAsDropDown(mStreamingSettingsFragment.mFragmentStreamingsettingsBinding.tvlocationfrequency,x,y);
    }

    public void hidePoplocationfrequency(){
        if(mPopuplocationfrequency!=null){
            boolean isShowing=mPopuplocationfrequency.isShowing();
            if(isShowing){
                mPopuplocationfrequency.dismiss();
            }
        }
    }

    public void save(View view){
        try{
            int cameraid = SPUtil.getCameraid(mMainActivity);
            int id=mMainActivity.getMBasicSettingsFragment().mMediaStream.getCameraId();
            if(id!=cameraid){
                MediaStream mMediaStream=mMainActivity.getMBasicSettingsFragment().mMediaStream;
                if (mMediaStream != null) {
                if (mMediaStream.isStreaming()) {
                    Toast.makeText(mMainActivity,"正在推流", Toast.LENGTH_LONG).show();
                    return;
                }
                }

                mMainActivity.getMBasicSettingsFragment().mMediaStream.switchCamera(cameraid);
            }

            Toast.makeText(mMainActivity,"已保存", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Log.e(TAG,"save  Exception  "+e.toString());
        }
    }
}