package net.bruce.zzim.push;

import android.text.TextUtils;

/**
 * @author yang.jianan 2017/07/21 14:53. Email:yang.jianan0926@gmail.com
 * @version 1.0.0
 */

public class Presenter implements IPresenter{
    private IView iView;

    public Presenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public void search() {
        String inputString = iView.getInputString();
        if (TextUtils.isEmpty(inputString)) {
            //为空直接返回....
            return;
        }
        int hasCode = inputString.hashCode();
        IUseSrvice service =new UserService();
        String serviceResult = service.search(hasCode);
        String result = "Result: " + inputString + " - " + serviceResult;

        iView.setResultString(result);
    }
}
