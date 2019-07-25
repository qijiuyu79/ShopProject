package com.ylkj.shopproject.activity.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.ylkj.shopproject.R;
import com.ylkj.shopproject.activity.user.tuan.MyTuanActivity;
import com.ylkj.shopproject.activity.user.tuan.MyTuanDetailsActivity;
import com.ylkj.shopproject.adapter.user.MyTuanAdapter;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.MyTuan;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;
public class MyTuanFragment extends BaseFragment  implements MyRefreshLayoutListener {

    private View view;
    private MyRefreshLayout mRefreshLayout;
    private ListView listView;
    private MyTuanAdapter myTuanFragment;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //当前页数
    private int page = 1;
    //我的团购列表的数据集合
    private List<MyTuan.DataBean> listAll=new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        listView = view.findViewById(R.id.listView);
        mRefreshLayout = view.findViewById(R.id.re_list);
        //刷新加载
        mRefreshLayout.setMyRefreshLayoutListener(this);
        //获取待评价数据
        myTuan(HandlerConstant.MY_TUAN_LIST_SUCCESS1);
        return view;
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //查询数据回执
                case HandlerConstant.MY_TUAN_LIST_SUCCESS1:
                    listAll.clear();
                    mRefreshLayout.refreshComplete();
                    refresh((MyTuan) msg.obj);
                    break;
                case HandlerConstant.MY_TUAN_LIST_SUCCESS2:
                    mRefreshLayout.loadMoreComplete();
                    refresh((MyTuan) msg.obj);
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(getString(R.string.net_error));
                    break;
            }
            return false;
        }
    });

    /**
     * 刷新界面数据
     */
    private void refresh(MyTuan myTuan) {
        if (null == myTuan) {
            return;
        }
        if (myTuan.isSussess()) {
            List<MyTuan.DataBean> list = myTuan.getData();
            listAll.addAll(list);
            if (null == myTuanFragment) {
                myTuanFragment=new MyTuanAdapter(mActivity,listAll);
                listView.setAdapter(myTuanFragment);
            } else {
                myTuanFragment.notifyDataSetChanged();
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyTuan.DataBean dataBean=listAll.get(position);
                    Intent intent=new Intent(mActivity, MyTuanDetailsActivity.class);
                    intent.putExtra("ugnum",dataBean.getUgnum());
                    startActivity(intent);
                }
            });
            if (list.size() < 20) {
                mRefreshLayout.setIsLoadingMoreEnabled(false);
            }
        } else {
            ToastUtil.showLong(myTuan.getDesc());
        }
    }

    @Override
    public void onRefresh(View view) {
        page=1;
        HttpMethod.myTuan(MyTuanActivity.index, page, HandlerConstant.MY_TUAN_LIST_SUCCESS1, handler);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        HttpMethod.myTuan(MyTuanActivity.index, page, HandlerConstant.MY_TUAN_LIST_SUCCESS2, handler);
    }


    /**
     * 获取消息数据
     */
    private void myTuan(int index) {
        if (null != view && isVisibleToUser && listAll.size() == 0) {
            HttpMethod.myTuan(MyTuanActivity.index, page, index, handler);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        //获取待评价数据
        myTuan(HandlerConstant.MY_TUAN_LIST_SUCCESS1);
    }

    @Override
    public void onDestroy() {
        removeHandler(handler);
        super.onDestroy();
    }
}
