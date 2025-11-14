package com.wolfhouse.springboot3initial.config.util;

import com.wolfhouse.springboot3initial.common.constant.UserConstant;
import com.wolfhouse.springboot3initial.common.util.imagevalidator.ImgValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rylin Wolf
 */
@Configuration
public class ImageValidateUtilConfig {
    @Bean
    public ImgValidator avatarValidator() {
        return new ImgValidator(
            // 最大 1.2 MB
            (long) (1.2 * 1024 * 1024),
            // 宽度 2048
            2048,
            // 高度 2048
            2048,
            // 最大像素 200 万
            200_000_000,
            // 允许类型
            UserConstant.ALLOWED_AVATAR_TYPE,
            // 媒体类型前缀
            "image/");
    }
}
