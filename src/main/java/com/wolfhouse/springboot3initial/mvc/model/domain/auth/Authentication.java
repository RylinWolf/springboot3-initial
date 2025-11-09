package com.wolfhouse.springboot3initial.mvc.model.domain.auth;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限表 实体类。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
@Data
@Schema(name = "权限表")
@Table(value = "authentication")
@NoArgsConstructor
@AllArgsConstructor
public class Authentication implements GrantedAuthority {
    /**
     * 权限 ID
     */
    @Schema(description = "权限 ID")
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;
    /**
     * 父权限 ID
     */
    @Schema(description = "父权限 ID")
    @Column(value = "parent_id")
    private Long parentId;
    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    @Column(value = "code")
    private String code;
    /**
     * 描述
     */
    @Schema(description = "描述")
    @Column(value = "description")
    private String description;
    
    public Authentication(String code) {
        this.code = code;
    }

    @Override
    public String getAuthority() {
        return this.code;
    }

    @Override
    public boolean equals(Object obj) {
        if (!GrantedAuthority.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        GrantedAuthority auth = (GrantedAuthority) obj;
        return this.code.equals(auth.getAuthority());
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }
}
