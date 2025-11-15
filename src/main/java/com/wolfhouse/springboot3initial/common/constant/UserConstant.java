package com.wolfhouse.springboot3initial.common.constant;

import java.util.Set;

/**
 * @author Rylin Wolf
 */
public class UserConstant {

    public static final String ILLEGAL_BIRTH = "生日格式非法";
    public static final String ILLEGAL_USERNAME = "用户名格式非法";
    public static final String ILLEGAL_STATUS = "个性签名格式非法";
    public static final String ILLEGAL_GENDER = "性别格式非法";
    public static final String ILLEGAL_EMAIL = "邮箱格式非法";

    public static final String REGISTER_FAILED = "用户注册失败";
    public static final String EXIST_EMAIL = "邮箱已存在";
    public static final String UNAVAILABLE_USERNAME = "该用户名不可用";
    public static final String LOGIN_FAILED = "用户名或密码不正确";
    public static final String USER_NOT_EXIST = "用户不存在";
    public static final String VALID_FAILED = "验证失败";

    public static final String LOGIN_USER_SESSION_KEY = "loginUser";

    public static final Set<String> ALLOWED_AVATAR_TYPE = Set.of("jpg", "jpeg", "png");
    public static final String AVATAR_VALID_FAILED = "头像验证失败";

    // region 头像常量
    /** 触发压缩阈值的图片大小 0.6 MB */
    public static final long AVATAR_COMPRESS_SIZE = (long) (0.6 * 1024 * 1024);
    /** 压缩质量 0.7 */
    public static final float AVATAR_COMPRESS_QUALITY = 0.7f;
    /** 最大宽度 */
    public static final int AVATAR_MAX_WIDTH = 1024;
    /** 最大高度 */
    public static final int AVATAR_MAX_HEIGHT = 1024;
    /** 转换格式 */
    public static final String AVATAR_FORMAT = "jpg";
    // endregion
}
