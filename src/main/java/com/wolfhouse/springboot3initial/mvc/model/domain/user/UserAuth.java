package com.wolfhouse.springboot3initial.mvc.model.domain.user;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 用户认证信息表 实体类。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Data
@Schema(name = "用户认证信息表")
@Table(value = "user_auth")
public class UserAuth {

    /**
     * 用户认证 ID。
     * 使用雪花算法自动生成，后续作为用户 ID
     */
    @Schema(description = "用户认证 ID")
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;

    /**
     * 密码密文
     */
    @Schema(description = "密码密文")
    @Column(value = "passcode")
    private String passcode;

    /**
     * 是否禁用
     */
    @Schema(description = "是否禁用")
    @Column(value = "is_banned")
    private Integer isBanned;

    /**
     * 禁用时间
     */
    @Schema(description = "禁用时间")
    @Column(value = "banned_time")
    private Date bannedTime;

    /**
     * 禁用操作者(用户 ID)
     */
    @Schema(description = "禁用操作者(用户 ID)")
    @Column(value = "banned_from")
    private Long bannedFrom;

    /**
     * 禁用备注
     */
    @Schema(description = "禁用备注")
    @Column(value = "banned_msg")
    private String bannedMsg;


}
