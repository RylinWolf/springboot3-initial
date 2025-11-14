package com.wolfhouse.springboot3initial.mvc.model.dto.user;

import com.wolfhouse.springboot3initial.common.enums.user.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

/**
 * @author Rylin Wolf
 */
@Data
@Schema(description = "用户更新 DTO")
public class UserUpdateDto {
    /**
     * 用户名称
     */
    @Schema(example = "Rylin",
            type = "string",
            nullable = true)
    private JsonNullable<String> username = JsonNullable.undefined();

    /**
     * 头像
     */
    @Schema(example = "https://example.com/avatar.jpg",
            type = "string",
            description = "用户头像的指纹，需要先上传头像才可更新",
            nullable = true)
    private JsonNullable<String> avatar = JsonNullable.undefined();

    /**
     * 性别
     */
    @Schema(example = "MALE",
            type = "int",
            nullable = true)
    private JsonNullable<GenderEnum> gender = JsonNullable.undefined();

    /**
     * 生日
     */
    @Schema(example = "2000-01-01",
            type = "string",
            format = "date",
            nullable = true)
    private JsonNullable<LocalDate> birth = JsonNullable.undefined();

    /**
     * 个性标签
     */
    @Schema(example = "Happy coding!",
            type = "string",
            nullable = true)
    private JsonNullable<String> personalStatus = JsonNullable.undefined();

    /**
     * 手机
     */
    @Schema(example = "13800138000",
            type = "string",
            nullable = true)
    private JsonNullable<String> phone = JsonNullable.undefined();

    /**
     * 邮箱
     */
    @Email
    @Schema(example = "user@example.com",
            type = "string",
            nullable = true)
    private JsonNullable<String> email = JsonNullable.undefined();
}
