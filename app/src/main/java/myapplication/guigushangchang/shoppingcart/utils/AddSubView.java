package myapplication.guigushangchang.shoppingcart.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import myapplication.guigushangchang.R;

/**
 * Created by zhouzhou on 2017/6/13.
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;
    private Context mContext;
    private int value = 3;
    private int minValue = 2;
    private int maxValue = 11;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");

    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        View.inflate(mContext,R.layout.add_sub_view,this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_value = (TextView) findViewById(R.id.tv_value);

        //设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                iv_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                iv_sub.setImageDrawable(subDrawable);
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sub:
                subNumber();
                break;
            case R.id.iv_add:
                addNumber();
                break;
        }

        //回调接口
        if(changeListener != null){
            changeListener.numberChange(value);
        }
    }

    private void addNumber() {
        if(value   < maxValue){
            value++;
        }
        tv_value.setText(value+"");

    }

    /**
     * 减
     */
    private void subNumber() {
        if(value > minValue){
            value--;
        }
        tv_value.setText(value+"");
    }


    public interface OnNumberChangeListener {
        /**
         *  当按钮被点击的时候回调
         */
        public void numberChange(int value);
    }

    private OnNumberChangeListener changeListener;

    /**
     * 设置监听数据变化
     * @param l
     */
    public void setOnNumberChangeListener(OnNumberChangeListener l) {
        this.changeListener = l;
    }

}
