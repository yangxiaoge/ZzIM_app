package net.bruce.zzim.push;

/**
 * @author yang.jianan 2017/07/21 14:57. Email:yang.jianan0926@gmail.com
 * @version 1.0.0
 */

public class UserService implements IUseSrvice {
    @Override
    public String search(int hasCode) {
        return "User: " + hasCode;
    }
}