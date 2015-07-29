package cn.duolah5.common.utils.secret;

import cn.duolah5.common.utils.misc.ValidateUtil;

public class MobileEncryptor {
    public static String encrypt(String mobile) {
        if (ValidateUtil.isInvalidMobile(mobile)) return mobile;
        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }
}
