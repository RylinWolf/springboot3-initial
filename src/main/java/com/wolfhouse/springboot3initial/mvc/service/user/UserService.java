package com.wolfhouse.springboot3initial.mvc.service.user;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.wolfhouse.springboot3initial.common.util.imageutil.ImgValidException;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.*;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户表 服务层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
public interface UserService extends IService<User> {
    // region 登录相关

    /**
     * 获取当前登录用户的信息。
     *
     * @return 当前登录用户的传输对象；如果用户未登录，则返回 null
     */
    UserLocalDto getLogin();

    /**
     * 获取当前登录用户的信息。如果用户未登录，则抛出异常。
     *
     * @return 当前登录用户的传输对象
     */
    UserLocalDto getLoginOrThrow();

    /**
     * 验证用户凭证和密码的正确性。
     *
     * @param certificate 用户凭证（如电子邮件、手机号码或用户名），用于唯一标识用户
     * @param password    用户的登录密码，用于身份验证
     * @return 如果验证成功返回 true，否则返回 false
     */
    Boolean verify(String certificate, String password);
    // endregion

    // region 业务方法

    /**
     * 创建用户。根据用户名称自动生成帐号并注入。
     * 返回创建后的用户对象。
     *
     * @param dto 用户 dto
     * @return 创建后的用户视图对象，包含 id、帐号
     */
    UserVo register(UserRegisterDto dto);

    /**
     * 更新用户信息。
     * 根据提供的用户更新数据，更新用户的相关信息。
     *
     * @param dto 用户更新的传输对象，包含需要更新的字段值，例如用户名、邮箱等
     * @return 更新后的用户视图对象，包含用户的详细信息
     */
    UserVo update(UserUpdateDto dto);

    /**
     * 根据用户名生成账号。
     * 将会在用户名之后拼接一串 6 位整型。
     * <p>
     * 示例: test#131222
     *
     * @param username 用户名，用于生成唯一的账号
     * @return 根据提供的用户名生成的账号字符串
     */
    String genAccount(String username);


    /**
     * 更新用户密码。
     * 根据提供的旧密码和新密码更新用户的登录密码。
     *
     * @param dto 包含用户旧密码和新密码的传输对象
     * @return 该方法执行完成恒定返回 true，否则抛出异常
     */
    Boolean updatePassword(UserPwdUpdateDto dto);

    /**
     * 上传用户头像。
     *
     * @param file 需要上传的头像文件，文件类型应为支持的图片格式（如 JPEG、PNG 等）
     * @throws ImgValidException 图像校验失败异常
     */
    void uploadAvatar(MultipartFile file) throws ImgValidException;

    // endregion

    // region 查询用户

    /**
     * 验证指定用户 ID 的密码是否正确。
     *
     * @param id       用户 ID，唯一标识一个用户
     * @param password 待验证的密码
     * @return 如果密码验证成功返回 true，否则返回 false
     */
    Boolean verify(Long id, String password);

    /**
     * 验证指定用户对象的密码是否正确。
     *
     * @param user     用户对象，包含用户的完整信息
     * @param password 待验证的密码
     * @return 如果密码验证成功返回 true，否则返回 false
     */
    Boolean verify(User user, String password);

    /**
     * 根据用户凭证获取用户信息。
     *
     * @param certificate 用户凭证（如电子邮件、手机号码或用户名），用于唯一标识用户
     * @return 对应的用户对象；如果用户不存在，则返回 null
     */
    User getByCertificate(String certificate);

    /**
     * 根据用户 ID 获取对应的用户视图对象。
     *
     * @param id 用户 ID，唯一标识一个用户
     * @return 用户视图对象，包含用户的详细信息；如果用户不存在，则返回 null
     */
    UserVo getVoById(Long id);

    /**
     * 根据提供的查询条件分页查询用户信息。
     *
     * @param dto 用户查询条件的传输对象，包含用户名、邮箱、账号等查询字段和分页信息
     * @return 符合查询条件的用户分页对象，包含分页信息及对应的用户数据
     */
    Page<User> queryBy(UserQueryDto dto);
    // endregion

    // region 指定条件的用户是否存在

    /**
     * 检查具有指定电子邮件地址的用户是否存在。
     *
     * @param email 用户的电子邮件地址，不可为 null
     * @return 如果拥有该电子邮件地址的用户存在，返回 true；否则返回 false
     */
    Boolean isUserEmailExist(String email);

    /**
     * 检查具有指定 ID 的用户是否存在。
     *
     * @param id 用户的唯一标识符，不可为 null
     * @return 如果用户存在，返回 true；否则返回 false
     */
    Boolean isUserExist(Long id);

    /**
     * 检查具有指定账号的用户是否存在。
     *
     * @param account 用户的账号，用于唯一标识用户，不可为 null
     * @return 如果账号对应的用户存在，则返回 true；否则返回 false
     */
    Boolean isUserAccountExist(String account);

    /**
     * 更新用户的最近访问时间。
     *
     * @param lastLogins 包含用户 ID 和对应最后访问时间的映射表，键为用户 ID，值为最后访问时间
     * @return 更新数量
     */
    Long updateAccessedTime(Map<Long, LocalDateTime> lastLogins);

    // endregion
}