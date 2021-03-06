package com.ylkj.shopproject.adapter.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ylkj.shopproject.R;
import com.zxdc.utils.library.bean.JCName;
import java.util.List;

/**
 * 订单消息
 */
public class SelectJCAdapter extends BaseAdapter {

	private Context context;
	private List<JCName.DataBean> list;
	//选中的下标
	public int index=-1;
	public SelectJCAdapter(Context context,List<JCName.DataBean> list) {
		super();
		this.context = context;
		this.list=list;
	}

	@Override
	public int getCount() {
		return list==null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	ViewHolder holder = null;
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view==null){
			holder = new ViewHolder(); 
			view = LayoutInflater.from(context).inflate(R.layout.item_select_jc, null);
			holder.imageView=view.findViewById(R.id.img_select);
			holder.tvName=view.findViewById(R.id.tv_name);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}

		JCName.DataBean dataBean=list.get(position);
		holder.tvName.setText(dataBean.getName());
		if(position==index){
			holder.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.jx_yes_select));
		}else{
			holder.imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.jx_no_select));
		}
		holder.imageView.setTag(position);
		holder.imageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				index=(int)v.getTag();
				SelectJCAdapter.this.notifyDataSetChanged();
			}
		});
		return view;
	}


	private class ViewHolder{
		private ImageView imageView;
		private TextView tvName;
	 }
}
