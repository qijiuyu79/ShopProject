package com.ylkj.shopproject.adapter.main;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylkj.shopproject.R;
import com.zxdc.utils.library.bean.PJGoodList;
import com.zxdc.utils.library.util.Util;
import com.zxdc.utils.library.view.OvalImage2Views;

import java.util.List;

/**
 * 商品列表
 */
public class PeiJianListAdapter extends RecyclerView.Adapter<PeiJianListAdapter.MyHolder> {

	private Context context;
	private List<PJGoodList.GoodList> list;
    private OnItemClickListener onItemClickListener;
	public PeiJianListAdapter(Context context, List<PJGoodList.GoodList> list, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.list = list;
		this.onItemClickListener=onItemClickListener;
	}

	public interface OnItemClickListener{
		void onItemClick(int position);
	}

	public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View inflate = LayoutInflater.from(context).inflate(R.layout.item_search_result, viewGroup,false);
		MyHolder holder = new MyHolder(inflate);
		inflate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onItemClickListener.onItemClick((Integer) v.getTag());
			}
		});
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
		MyHolder holder = (MyHolder) myHolder;
		final PJGoodList.GoodList goodList=list.get(i);
		if(null==goodList){
			return;
		}
		if(goodList.getIstg()==0){
			holder.imgPT.setVisibility(View.GONE);
			holder.tvNum.setVisibility(View.GONE);
			holder.tvOldMoney.setVisibility(View.GONE);
		}else{
			holder.imgPT.setVisibility(View.VISIBLE);
			holder.tvNum.setVisibility(View.VISIBLE);
			holder.tvOldMoney.setVisibility(View.VISIBLE);
			holder.tvNum.setText(goodList.getTgcount()+"人团");
			holder.tvOldMoney.setText("¥"+goodList.getOldprice());
			holder.tvOldMoney.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
		}
		holder.itemView.setTag(i);
		holder.tvName.setText(goodList.getName());
		holder.tvMoney.setText(Util.setDouble(goodList.getPrice(),2));
		//展示图片
		String imgUrl=goodList.getImg();
		holder.img.setTag(R.id.imageid,imgUrl);
		if(holder.img.getTag(R.id.imageid)!=null && imgUrl==holder.img.getTag(R.id.imageid)){
			Glide.with(context).load(imgUrl).override(140,140).centerCrop().into(holder.img);
		}
	}

	@Override
	public int getItemCount() {
		return list==null ? 0 : list.size();
	}

	public class MyHolder extends RecyclerView.ViewHolder {
		OvalImage2Views img;
		ImageView imgPT;
		TextView tvName,tvMoney,tvNum,tvOldMoney;
		public MyHolder(@NonNull View itemView) {
			super(itemView);
			img=itemView.findViewById(R.id.img_icon);
			imgPT=itemView.findViewById(R.id.img_pt);
			tvName = itemView.findViewById(R.id.tv_name);
			tvMoney=itemView.findViewById(R.id.tv_money);
			tvNum=itemView.findViewById(R.id.tv_num);
			tvOldMoney=itemView.findViewById(R.id.tv_old_money);
		}
	}
}
