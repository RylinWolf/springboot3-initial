package com.wolfhouse.springboot3initial.mvc.service.user;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.wolfhouse.springboot3initial.mvc.model.domain.user.User;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserQueryDto;
import com.wolfhouse.springboot3initial.mvc.model.dto.user.UserRegisterDto;
import com.wolfhouse.springboot3initial.mvc.model.vo.UserVo;

/**
 * 用户表 服务层。
 *
 * @author Rylin Wolf
 * @since 1.0
 */
public interface UserService extends IService<User> {
    /**
     * 创建用户。根据用户名称自动生成帐号并注入。
     * 返回创建后的用户对象。
     *
     * @param dto 用户 dto
     * @return 创建后的用户视图对象，包含 id、帐号
     */
    UserVo register(UserRegisterDto dto);

    /**
     * 根据用户 ID 获取对应的用户视图对象。
     *
     * @param id 用户 ID，唯一标识一个用户
     * @return 用户视图对象，包含用户的详细信息；如果用户不存在，则返回 null
     */
    UserVo getVoById(Long id);


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
     * 根据提供的查询条件分页查询用户信息。
     *
     * @param dto 用户查询条件的传输对象，包含用户名、邮箱、账号等查询字段和分页信息
     * @return 符合查询条件的用户分页对象，包含分页信息及对应的用户数据
     */
    Page<User> queryBy(UserQueryDto dto);

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
}