package myapplication.guigushangchang.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplication.guigushangchang.R;

/**
 * Created by zhouzhou on 2017/6/15.
 */

public class TypeLeftAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] datas;

    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};
    private int changeSelected;

    public TypeLeftAdapter(Context context, String[] titles) {
        this.mContext = context;
        this.datas = titles;
    }

    @Override
    public int getCount() {
        return datas==null ? 0 : datas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_type, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(titles[position]);
        if(changeSelected ==position){
            //高亮显示
            convertView.setBackgroundResource(R.drawable.type_item_background_selector);
            //选中项背景
            viewHolder.tvTitle.setTextColor(Color.parseColor("#fd3f3f"));
        }else{
            //设置默认
            convertView.setBackgroundResource(R.drawable.bg2);  //其他项背景
            viewHolder.tvTitle.setTextColor(Color.parseColor("#323437"));
        }

        return convertView;
    }

    public void changeSelected(int position) {
            this.changeSelected=position;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
