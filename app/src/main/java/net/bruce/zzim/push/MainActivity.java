package net.bruce.zzim.push;

import android.widget.EditText;
import android.widget.TextView;

import net.bruce.zzim.common.app.Activity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements IView {
    @BindView(R.id.text_test)
    TextView mTestText;
    @BindView(R.id.edit_test)
    EditText mEditText;

    private IPresenter presenter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter = new Presenter(this);
    }

    @OnClick(R.id.submit_test)
    void onSubmit() {
        presenter.search();
    }

    @Override
    public String getInputString() {
        return mEditText.getText().toString();
    }

    @Override
    public void setResultString(String text) {
        mTestText.setText(text);
    }
}
