package net.bruce.zzim.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yang.jianan on 2017/07/20 17:19.
 */

public abstract class Fragment extends android.support.v4.app.Fragment {
    protected View root;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            int layoutId = getContentLayoutId();
            View viewRoot = inflater.inflate(layoutId, container, false);
            initWidget(viewRoot);
            root = viewRoot;
        } else {
            if (root.getParent() != null) {
                ((ViewGroup) root.getParent()).removeView(root);
            }
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //当View创建完成后初始化数据
        initData();
    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数bundle
     * @return 如果参数正确返回true，错误返回false
     */
    protected void initArgs(Bundle bundle) {

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
    protected void initWidget(View viewRoot) {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }
}
