package myapplication.guigushangchang.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.community.bean.NewPostBean;
import myapplication.guigushangchang.utils.Constants;

/**
 * Created by zhouzhou on 2017/6/15.
 */

public class NewPostListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewPostBean.ResultBean> result;
    private List<String> comment_list;

    public NewPostListViewAdapter(Context mContext, List<NewPostBean.ResultBean> result) {
        this.mContext = mContext;
        this.result = result;
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_listview_newpost, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        //根据位置得到对应的数据
        NewPostBean.ResultBean resultBean = result.get(position);
        holder.tvCommunityUsername.setText(resultBean.getUsername());
        Glide.with(mContext)
                .load(Constants.BASE_URL_IMAGE + resultBean.getFigure())
                .into(holder.ivCommunityFigure);

        holder.tvCommunitySaying.setText(resultBean.getSaying());
        holder.tvCommunityLikes.setText(resultBean.getLikes());
        holder.tvCommunityComments.setText(resultBean.getComments());
        //设置头像
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + resultBean.getAvatar()).into(holder.ibNewPostAvatar);
        //设置弹幕
        comment_list =  resultBean.getComment_list();
        if (comment_list != null && comment_list.size() > 0) {
            holder.danmakuView.setVisibility(View.VISIBLE);

            List<IDanmakuItem> list = new ArrayList<>();
            for (int i = 0; i < comment_list.size(); i++) {
                IDanmakuItem item = new DanmakuItem(mContext, comment_list.get(i), holder.danmakuView.getWidth());
                list.add(item);
            }
            //随机
            Collections.shuffle(list);
            holder.danmakuView.addItem(list, true);
            holder.danmakuView.show();
        } else {
            holder.danmakuView.setVisibility(View.GONE);
        }


        return convertView;
    }



    //自动生成的
    static class ViewHolder {
        @BindView(R.id.tv_community_username)
        TextView tvCommunityUsername;
        @BindView(R.id.tv_community_addtime)
        TextView tvCommunityAddtime;
        @BindView(R.id.ib_new_post_avatar)
        ImageView ibNewPostAvatar;
        @BindView(R.id.iv_community_figure)
        ImageView ivCommunityFigure;
        @BindView(R.id.tv_community_saying)
        TextView tvCommunitySaying;
        @BindView(R.id.tv_community_likes)
        TextView tvCommunityLikes;
        @BindView(R.id.tv_community_comments)
        TextView tvCommunityComments;
        @BindView(R.id.danmakuView)
        DanmakuView danmakuView;



        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

