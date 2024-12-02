package com.easygbs.easygbd.adapter;import android.content.Context;import android.widget.TextView;import com.easygbs.easygbd.R;import com.easygbs.easygbd.activity.MainActivity;import com.easygbs.easygbd.bean.VerBean;import com.easygbs.easygbd.viewadapter.CommonAdapter;import com.easygbs.easygbd.viewadapter.ViewHolder;import java.util.List;public class VerAdapter extends CommonAdapter<VerBean> {    public String TAG= VerAdapter.class.getSimpleName();    private Context context;    private MainActivity mMainActivity;    public List<VerBean> SelectCameraResolutionBeanList;    public VerAdapter(Context context, MainActivity mMainActivity, int layoutId, List<VerBean> SelectCameraResolutionBeanList) {        super(context, layoutId, SelectCameraResolutionBeanList);        this.context = context;        this.mMainActivity=mMainActivity;        this.SelectCameraResolutionBeanList=SelectCameraResolutionBeanList;    }    @Override    protected void convert(ViewHolder holder, VerBean mSelectCameraResolutionBean, int position) {        TextView tvName = holder.getView(R.id.tvname);        String name=mSelectCameraResolutionBean.getName();        tvName.setText(name);        int isst=mSelectCameraResolutionBean.getIsst();        if(isst==0){            tvName.setTextColor(context.getResources().getColor(R.color.color_4d4d4d));        }else if(isst==1){            tvName.setTextColor(context.getResources().getColor(R.color.color_01c9a7));        }    }}