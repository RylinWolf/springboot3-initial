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
    /** 分阶段压缩，如大于 0.8MB 则按照 0.73 系数压缩 */
    public static final Long[] AVATAR_FILE_SIZE_LEVEL = {(long) (0.8 * 1024 * 1024),
                                                         (long) (0.65 * 1024 * 1024),
                                                         (long) (0.5 * 1024 * 1024),
                                                         (long) (0.4 * 1024 * 1024),
                                                         (long) (0.3 * 1024 * 1024),
                                                         (long) (0.2 * 1024 * 1024),
                                                         (long) (0.15 * 1024 * 1024),
                                                         (long) (0.1 * 1024 * 1024)};
    public static final Float[] AVATAR_FILE_COMPRESS_LEVEL = {0.73f, 0.74f, 0.76f, 0.78f, 0.81f, 0.85f, 0.95f};

    /** 最大宽度 */
    public static final int AVATAR_MAX_WIDTH = 1024;
    /** 最大高度 */
    public static final int AVATAR_MAX_HEIGHT = 1024;
    /** 转换格式 */
    public static final String AVATAR_FORMAT = "jpg";
    // endregion
}
