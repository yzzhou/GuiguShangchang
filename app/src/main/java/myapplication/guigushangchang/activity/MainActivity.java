package myapplication.guigushangchang.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import myapplication.guigushangchang.R;
import myapplication.guigushangchang.base.BaseFragment;
import myapplication.guigushangchang.community.CommunityFragment;
import myapplication.guigushangchang.fragment.HomeFragment;
import myapplication.guigushangchang.fragment.ShoppingCartFragment;
import myapplication.guigushangchang.fragment.TypeFragment;
import myapplication.guigushangchang.user.UserFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_type)
    RadioButton rbType;
    @BindView(R.id.rb_community)
    RadioButton rbCommunity;
    @BindView(R.id.rb_cart)
    RadioButton rbCart;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private BaseFragment tempFragemnt;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rgMain.check(R.id.rb_home);
        initFragment();
        initListener();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());

        //设置默认的Fragemnt
        defaultFragment(getFragment(position));

    }
    private void defaultFragment(BaseFragment fragment) {
        tempFragemnt  = fragment;
        //1.得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ta = fm.beginTransaction();
        //3.添加
        ta.add(R.id.frameLayout,tempFragemnt);
        //4.提交
        ta.commit();
    }
    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:

                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;

                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }
                BaseFragment baseFragment = getFragment(position);
                switchFragment(baseFragment);
            }
        });

        //默认设置首页
        rgMain.check(R.id.rb_home);
    }
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }
    private void switchFragment(Fragment currentFragment) {
        if (currentFragment != tempFragment) {

            if (currentFragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                //如果没有添加就添加
                if (!currentFragment.isAdded()) {
                    //隐藏之前的
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }

                    //添加Fragment
                    ft.add(R.id.frameLayout, currentFragment);

                }
                //如果添加了就隐藏
                else {
                    //隐藏上次显示的
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }

                    //显示
                    ft.show(currentFragment);
                }

                //最后统一提交
                ft.commit();
                //重新赋值
                tempFragment = currentFragment;
            }

        }

    }

}
