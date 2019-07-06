package com.ylkj.shopproject.adapter.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ylkj.shopproject.R;
import com.zxdc.utils.library.view.CircleImageView;
import java.util.List;

public class PinTuanStatusAdapter extends RecyclerView.Adapter<PinTuanStatusAdapter.MyHolder> {

	private Context context;
	private List<String> data;
	private OnItemClickListener onItemClickListener;
	public PinTuanStatusAdapter(Context context, List<String> data, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.data = data;
		this.onItemClickListener=onItemClickListener;
	}

	public interface OnItemClickListener{
		void onItemClick(int position);
	}

	public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View inflate = LayoutInflater.from(context).inflate(R.layout.item_pintuan_status_img, viewGroup,false);
		MyHolder holder = new MyHolder(inflate);
		inflate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onItemClickListener.onItemClick((Integer) v.getTag());
			}
		});
		return holder;
	}

	public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
		MyHolder holder = (MyHolder) myHolder;
		holder.itemView.setTag(i);
//		myHolder.tvName.setText("非常好的机床");
	}

	@Override
	public int getItemCount() {
		return 7;
	}

	public class MyHolder extends RecyclerView.ViewHolder {
		CircleImageView img;
		public MyHolder(@NonNull View itemView) {
			super(itemView);
			img=itemView.findViewById(R.id.img_icon);
		}
	}
}