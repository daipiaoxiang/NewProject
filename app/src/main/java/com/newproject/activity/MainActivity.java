package com.newproject.activity;

import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.newproject.R;
import com.newproject.base.BaseActivity;
import com.newproject.base.BaseFragment;
import com.newproject.fragment.HomeFragment;
import com.newproject.fragment.ManageFragment;
import com.newproject.fragment.OrderFragment;
import com.newproject.fragment.UserFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.menu_bottom)
    RadioGroup rg;
    private BaseFragment currentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();

        final BaseFragment homefragment=new HomeFragment();
        final BaseFragment managefragment=new ManageFragment();
        final BaseFragment orderfragment=new OrderFragment();
        final BaseFragment userfragment=UserFragment.getInstance(4);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab_1:
                        addOrShowFragment(homefragment);
                        break;
                    case R.id.main_tab_2:
                        addOrShowFragment(managefragment);
                        break;
                    case R.id.main_tab_3:
                        addOrShowFragment(orderfragment);
                        break;
                    case R.id.main_tab_user:
                        addOrShowFragment(userfragment);
                        break;
                    default:
                        break;
                }
            }
        });
        rg.check(R.id.main_tab_1);
    }

    public void addOrShowFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (currentFragment == fragment)
            return;

        if (currentFragment == null) {
            transaction.add(R.id.content, fragment);
        } else if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.content, fragment);
        } else {
            transaction.hide(currentFragment).show(fragment);
        }
        transaction.commitAllowingStateLoss();

        currentFragment = fragment;
    }

    @Override
    public boolean hasActionBar() {
        return false;
    }
}
