package net.bruce.zzim.common.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by yang.jianan on 2017/07/20 17:19.
 */

public abstract class Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面初始化之前调用的初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())) {
            setContentView(getContentLayoutId());
            initWidget();
            initData();
        } else {
            finish();
        }

    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {

    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数bundle
     * @return 如果参数正确返回true，错误返回false
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 获取当前页面资源文件Id
     *
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 点击导航返回上一页
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 得到当期那Activity下的所有Fragment
        @SuppressLint("RestrictedApi")
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        // 判断是否为空
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment instanceof net.bruce.zzim.common.app.Fragment) {
                    // 判断是否拦截了返回按钮
                    if (((net.bruce.zzim.common.app.Fragment) fragment).onBackPressed()) {
                        //如果有直接return
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }
}
