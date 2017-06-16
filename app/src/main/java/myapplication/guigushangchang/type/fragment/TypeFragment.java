package myapplication.guigushangchang.type.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.base.BaseFragment;
import myapplication.guigushangchang.home.activity.MainActivity;

/**
 * Created by zhouzhou on 2017/6/12.
 */

/**
 * 作者：尚硅谷-杨光福 on 2016/12/10 11:22
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：分类Fragment
 */
public class TypeFragment extends BaseFragment {

    private static final String TAG = TypeFragment.class.getSimpleName();
    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;
    @BindView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @BindView(R.id.fl_type)
    FrameLayout flType;
    private String[] mTitles = {"分类", "标签"};
    private ArrayList<BaseFragment> fragments;
    private Fragment tempFragment;

    @Override
    public View initView() {
        Log.e(TAG, "分类Fragment初始化ui了");
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "分类Fragment数据绑定了");
        initSegmentTabLayout();
        initFragment();

    }

    private void initSegmentTabLayout() {
        //设置SegmentTabLayout数据
        tl1.setTabData(mTitles);
        //设置TabSelectListener点击监听
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());

        //选中第0个Fragment
        switchFragment(fragments.get(0));

    }


    /**
     * 传入当前要显示的Fragment
     * @param currentFragment
     */
    private void switchFragment(Fragment currentFragment) {
        if(tempFragment != currentFragment){

            if(currentFragment != null){
                //开启事务
                FragmentTransaction ft = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                if(!currentFragment.isAdded()){
                    //隐藏之前显示的
                    if(tempFragment != null){
                        ft.hide(tempFragment);
                    }
                    //判断currentFragment 有没有添加，如果没有就添加
                    ft.add(R.id.fl_type,currentFragment);


                }else{
                    //隐藏之前显示的
                    if(tempFragment != null){
                        ft.hide(tempFragment);
                    }
                    //否则就显示
                    ft.show(currentFragment);
                }

                ft.commit();//统一提交

            }

            tempFragment = currentFragment;
        }

    }



    @OnClick(R.id.iv_type_search)
    public void onClick() {
        Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
    }

}
