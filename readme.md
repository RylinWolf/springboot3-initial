# Spring Boot 3 项目模板

为简化开发，根据自己的常用技术栈设计了该模板。

该模板整合并封装了常用工具类、hutool 工具类、caffeine 本地缓存、ES、Redis、RabbitMQ 等内容，适合用于构建中型项目。

项目提供用户模块、权限验证模块。

详细技术栈与已有功能如下。

# 技术栈

## 基础依赖

该项目的已有功能（用户模块等）需要以下基础依赖才可正常运行。

- JDK 21
- MySQL 8
- Spring Boot 3.5.7
- Spring MVC

## 深度依赖

该项目的已有功能高度依赖以下模块，若要移除需要较大的重构工作量。

- Lombok
- Apache Commons Validator
- Spring Boot Validation
- hutool
- Redis
- Spring Security
- Jackson Databind Nullable

## 可选依赖

以下依赖项在项目已有功能中没有使用，可以根据项目后续计划选择是否保留。

- Caffeine
- ElasticSearch
- Spring AOP
- Spring AMQP

以下依赖项在项目中有使用，但可通过简单重构移除或更换。

- Spring Data Jdbc
- Spring Session Redis
    - netty
- MyBatis Flex
- knife4j

# 初始数据库

项目已提供的功能需要数据库支持，本项目提供了用于初始数据库的 SQL 脚本。可以根据业务需求自行修改。

位于 resources/sql，共包含以下两个文件:

- schema.sql：用于构建数据库及表结构
- init.sql：用于插入初始数据

若要使用项目内置的用户功能，需先依次运行以上两个文件。

需要注意，确保 `application.yaml` 或自定义激活的配置文件中，配置的数据库的名称与目标数据库名称一致。

# 项目规范

## 异常处理

项目定义了多个异常类的实现，在业务编码需要抛出异常时，应当根据场景和语义抛出相对应的异常，方便 AOP 或全局异常处理器统一处理。

名称及介绍如下：

- `ServiceException` 业务异常类
    - 业务方法范畴内的异常，较为通用
    - 业务方法执行时，出现了业务相关的问题时，将会抛出该种异常。
- `VerifyException` 验证异常类
    - 验证工具类使用的异常
    - 在校验参数时，若校验未通过，且校验节点的策略为抛出异常，则会默认抛出该种类型的异常
- `BeanUtilException` Bean 工具异常类
    - Bean 工具使用的异常
    - 在 Bean 工具进行操作时，若抛出了异常，则默认抛出该种类型的异常。该异常应仅限于 Bean 工具内部使用，其为不可控的、非业务流程中的异常。
    - 注意，这也是 ThrowUtil 默认抛出的异常类型，应根据业务需要修改 ThrowUtil 要抛出的异常类型。

## 响应类型

项目定义了统一的响应类型 `HttpResult`，位于 `common.result` 包下，格式如下:

- success: bool
- message: str
- code: int
- data: T

其中封装了多个静态工厂方法，用于构建不同的响应请求。并提供了特殊的返回值类型为 ` ResponseEntity` 的工厂方法，用于自定义 Http 状态码。



# 项目结构

## 概览

下述为当前项目的主要目录与文件结构，并附带各目录职责的简要说明，便于快速理解与导航。

```
project-root/
├─ pom.xml                        # Maven 项目依赖与构建配置
├─ readme.md                      # 项目说明文档（本文件）
├─ src/
│  └─ main/
│     ├─ java/
│     │  └─ com/wolfhouse/springboot3initial/
│     │     ├─ Springboot3InitialApplication.java   # Spring Boot 启动类
│     │     ├─ common/                              # 通用模块（常量、枚举、统一返回体、工具类等）
│     │     │  ├─ constant/                         # 业务常量（如认证/用户相关常量）
│     │     │  ├─ enums/                            # 通用与用户相关枚举
│     │     │  ├─ result/                           # 统一响应模型 HttpResult、分页结果等
│     │     │  └─ util/                             # 通用工具（bean 工具、验证链工具等）
│     │     │     ├─ beanutil/                      # Bean 工具、ThrowUtil 等
│     │     │     └─ verify/                        # 参数校验链框架及内置校验节点
│     │     ├─ config/                              # 全局配置（MVC、Redis、ObjectMapper 等）
│     │     │  └─ objectmapper/                     # 序列化、消息转换等 ObjectMapper 相关配置
│     │     ├─ exception/                           # 业务异常定义
│     │     ├─ handler/                             # 全局异常处理器
│     │     ├─ mediator/                            # 领域中介（如用户、管理员、权限之间的协调）
│     │     ├─ mvc/                                 # 典型三层目录（Controller/Service/Mapper/Model）
│     │     │  ├─ controller/                       # Web 层：User、Admin、Authentication 等控制器
│     │     │  ├─ service/                          # 业务层接口与实现
│     │     │  │  ├─ auth/                          # 管理员/权限相关服务接口
│     │     │  │  │  └─ impl/                       # 管理员/权限相关服务实现
│     │     │  │  └─ user/                          # 用户相关服务接口
│     │     │  │     └─ impl/                       # 用户相关服务实现
│     │     │  ├─ mapper/                           # MyBatis-Flex 持久层 Mapper 接口
│     │     │  │  ├─ auth/                          # 管理员、权限相关 Mapper
│     │     │  │  └─ user/                          # 用户与用户-权限关联 Mapper
│     │     │  └─ model/                            # DO/DTO/VO 等模型
│     │     │     ├─ domain/                        # 数据库实体（DO）
│     │     │     │  ├─ auth/                       # Admin、Authentication 等表实体
│     │     │     │  └─ user/                       # User、UserAuth 等表实体
│     │     │     ├─ dto/                           # 业务入参与查询对象（DTO）
│     │     │     │  ├─ auth/                       # 权限/管理员相关 DTO
│     │     │     │  └─ user/                       # 用户相关 DTO（注册、登录、查询、更新等）
│     │     │     └─ vo/                            # 出参视图对象（VO）
│     │     ├─ security/                            # 安全模块（权限常量、上下文工具等）
│     │     │  ├─ config/                           # Spring Security 配置（认证管理、入口点等）
│     │     │  └─ filter/                           # 登录/存储等过滤器
│     │     └─ util/                                # 其他通用工具（如 JsonNullable 工具）
│     └─ resources/
│        ├─ mapper/                                 # MyBatis-Flex 映射文件（如有）
│        └─ sql/                                    # 初始化与演示 SQL（schema.sql、init.sql、test.sql 等）
```

- 启动类与配置
  - Springboot3InitialApplication.java：应用入口。
  - config：MVC、Redis、Jackson/ObjectMapper 等全局配置。
- 通用模块 common
  - constant/enums：平台与业务常量、枚举。
  - result：统一返回结构 `HttpResult` 与分页模型。
  - util：Bean 工具、验证链（链路式参数校验）等通用工具。
- 异常与处理
  - exception：`ServiceException` 等异常类型。
  - handler：全局异常处理器，统一异常响应。
- 安全模块 security
  - 权限常量、上下文工具；包含 Spring Security 配置与过滤器。
- 业务 MVC
  - controller：对外 REST 接口（用户、管理员、权限）。
  - service：业务逻辑实现；`impl` 目录为具体实现。
  - mapper：持久层接口，对应 `resources/mapper` 中的映射文件（如有）。
  - model：DO/DTO/VO 分类清晰，入参/出参与数据库实体解耦。
- 资源目录 resources
  - sql：初始化与演示数据脚本，按需执行。

---

以下，将对部分包进行简介。

## common

通用模块，包含业务中广泛使用的常量、枚举类、结果封装类、工具类等内容。

这些内容被业务所依赖，但相对独立，对于各业务都可使用，不依赖于业务本身。

包含以下目录：

- constant：包含业务通用的常量类

- enums：包含业务通用的枚举类

- result：封装了通用返回类型的工具包
  - HttpCode：自定义 Http 返回状态枚举类，包含自定义 code（子状态码）、message（错误信息），目前包含以下状态：
  
    ```
    SUCCESS(20000, ""),
    BAD_REQUEST(40000, "请求错误"),
    PARAM_ERROR(40010, "参数错误"),
    UN_AUTHORIZED(40100, "未登录"),
    FORBIDDEN(40300, "访问被拒绝"),
    NO_PERMISSION(40311, "无操作权限"),
    MEDIA_TYPE_NOT_ACCEPTABLE(40600, "不可接受的媒体类型"),
    UNSUPPORTED_MEDIA_TYPE(41500, "不支持的媒体类型"),
    UNKNOWN(50099, "未知错误"),
    SQL_ERROR(50011, "SQL 错误"),
    ```
  
  - HttpMediaTypeConstant：自定义 Http 媒体类型封装常量，便于新增自定义的 Content-Type
  
  - HttpResult：统一封装的响应类型
  
    - success：是否处理成功
    - message：响应信息
    - code：响应子状态码
    - data：响应体数据
  
- util：通用的工具类
  - beanutil：包含自定义的 BeanUtil 工具、ThrowUtil 异常工具
  - verify：自定义的验证节点工具类，可以动态构建验证链

## config

全局配置包，集中放置框架级与横切关注点的配置，避免分散在业务代码中。

目前包含以下配置：

- Spring MVC 跨域配置

- Jackson/ObjectMapper：序列化与反序列化、空值/日期/命名策略等统一配置
- Redis/Redisson：连接、序列化器、线程池等客户端配置

包含子包：
- objectmapper：与 `ObjectMapper` 相关的配置，包括默认 objectMapper、支持 JsonNullable 的 objectMapper 以及 MVC 消息转换器的配置



## exception

业务异常包，存放自定义的异常类型。注意这里的异常类型与业务高耦合，不应在工具类中使用，也不应存放工具类的异常类型。

- ServiceException：通用业务异常，适用于服务/领域逻辑场景。依赖于 工具包 result。可以使用 HttpCode 构造，
  - code：异常代码，若异常被全局异常处理器捕获，则会作为 HttpResult 中的 code。
  - message：异常信息，若异常被全局异常处理器捕获，会作为 HttpResult 中的 message。




## handler

处理器包，目前仅包含全局异常处理器。

全局异常处理器捕捉 Servlet 请求过程中发生的异常，将不同的异常转换为标准响应体，保持接口一致性。与业务高度耦合。

- 职责：
  - 捕获业务异常（如 `ServiceException`）、参数校验异常、权限异常等
  - 统一封装为 `HttpResult`，并按需携带 `HttpCode` 与 message
  - 记录必要的审计日志与诊断信息（避免输出敏感数据）
- 交互对象：各种异常、`common.result` 中的 `HttpResult`/`HttpCode`



目前会捕捉以下异常并进行特殊处理：

- AuthorizationDeniedException：无权限操作，返回 403，子状态码 40311
- SQLSyntaxErrorException|BadSqlGrammarException：服务器内部的 SQL 问题，返回 500，子状态码 50011
- ServiceException：业务异常，根据业务异常的 code 返回不同状态码和子状态码
- VerifyException|MethodArgumentNotValidException：字段验证异常，包括自定义的验证器以及 Spring Validation 提供的验证。根据不同异常类型构建不同错误信息，返回 400,子状态码 40010（参数错误）
- HttpMediaTypeNotSupported|HttpMediaTypeNotAcceptable：请求媒体类型不支持异常，返回对应的状态码和子状态码。
- Exception：未特殊指定的其他异常，统一返回 500，子状态码 50099（未知错误）



## mediator

领域中介者（Mediator）模式的实现，用于不同业务之间跨领域服务的交互，降低服务之间的直接耦合。

目前包含 `UserAdminAuthMediator`，用于用户、管理员及权限之间业务的交互。

- 职责：
  - 聚合 User/Admin/Authentication 等服务能力，向上提供一致的查询与校验接口
  - 登录尝试、用户/管理员存在性校验、管理员权限聚合查询等
  - 在服务初始化阶段进行自注册（各 Service 在 `@PostConstruct` 中注册到中介者）
- 常见调用方：业务服务（如用户/管理员/权限 Service）、安全模块（认证、权限装载、过滤器）、验证节点



## mvc

典型三层结构与数据模型归档，保持接口、业务、数据职责清晰分离。

- controller：对外 REST 接口层
  - 负责请求参数解析、基础校验、调用服务并包装标准响应
  - 避免编写业务逻辑，聚焦于协议与交互
- service：领域/业务层
  - 接口与实现分离，`impl` 目录存放具体实现
  - 常见能力：参数与业务校验、事务边界、聚合多个仓储/中介者调用
  - 部分服务在 `@PostConstruct` 中向 `mediator` 进行自注册，便于跨域协作
- mapper：持久化层（MyBatis-Flex）
  - 定义表对应的 Mapper 接口，结合 `QueryWrapper`/`Page` 完成查询与分页
  - XML 映射文件位于 `resources/mapper`
- model：数据与传输模型
  - domain：数据库实体（DO）
  - dto：入参与查询对象（DTO）
  - vo：对外展示/出参对象（VO）



## security

安全模块，围绕用户登录、权限校验与安全上下文展开，基于 Spring Security 进行扩展与封装。与业务耦合，可通过重构进行分离。

- 核心能力：
  - 认证：用户帐号/邮箱+ 密码登录（由 Provider 组合实现）
  - 授权：基于权限标识（code）的细粒度鉴权，配合业务侧 `PermissionService` 使用
  - 安全上下文：提供 `SecurityContextUtil` 读取当前登录用户、权限等
- 主要组件：
  - 常量与工具：`SecurityConstant`、`SecurityContextUtil`
    - `SecurityContextUtil`：用于获取和保存登录信息从/至安全上下文的工具
  - 权限服务：`PermissionService`、`PermissionConstant`
  - 配置：`security.config`
    - `SecurityConfig`：HttpSecurity 入口配置、过滤器链
    - `AuthenticationManagerConfig`：认证管理器构建
    - `AuthenticationProviderConfig`：认证提供者（凭证校验、账号装载）
    - `EntryPointConfig`：未认证/鉴权失败时的入口响应
  - 过滤器：`security.filter.LoginStoreFilter` 用于登录后上下文与权限的装载/刷新
- 交互关系：
  - 与 `mediator` 协作，登录后根据用户/管理员身份加载权限列表
  - 与 `mvc` 控制器/服务配合完成权限校验（如服务入口处调用 `PermissionService.hasPerm`）



## util

通用工具与业务耦合工具集合，对 `common.util` 进行二次封装，或新增其他与业务高度耦合的工具。

- JsonNullableUtil：对 `JsonNullable` 的处理工具
- verifynode：基于“验证节点”工具的验证节点拓展
  - common：通用节点（手机号、密码强度等）
  - user：用户相关校验（邮箱格式/唯一性、生日、性别等）
  - admin：管理员与权限相关校验（是否管理员、权限 ID 合法性等）
  - ServiceVerifyConstant：校验链使用到的常量/消息定义



# 用户模块

项目已包含用户模块相关功能，包括用户创建、修改、删除（逻辑）、查询等。

- 注册用户
- 修改用户
    - 更新缓存（修改同样的用户不需过多考虑并发问题，故直接更新缓存）
- 删除用户（仅管理员）
    - 逻辑删除
- 查询用户详细信息
    - 根据账号、ID、邮箱精确查询
    - 缓存查询结果
- 分页查询用户简略信息
    - 根据用户昵称模糊查询
- 用户登录
- 用户注销登录



## 用户登录

使用 Redisson 保存登录信息时，需要配置序列化器来解决序列化问题。

在配置 Redisson 客户端的 Config 中设置序列化器，注意，应该在 `useSingleServer` 之前。

```java
@Bean
public RedissonClient redissonClient() {
    Config config = new Config();
    config.setCodec(new JsonJacksonCodec(jacksonObjectMapper))
          .useSingleServer()
          .setAddress(String.format("redis://%s:%d", host, port))
          .setDatabase(database)
          .setUsername(username)
          .setPassword(password);

    return Redisson.create(config);
}
```



# 权限模块

权限对应的接口需要具备相关权限才可操作，项目内置的管理员默认包含全部权限。

- 添加/修改/删除 权限
- 查询/分页查询 权限

与管理员双向耦合、依赖于用户模块（用户 ID）。

关联文件或包：

- `mvc/controller/AuthenticationController`

- `mvc/mapper/auth`

- `mvc/model/*/auth`

- `mvc/service/auth`

  

权限校验功能的实现位于 `security` 包下，与 Spring Security 耦合。与业务单向耦合，可以通过较为简单的重构进行剥离。

包 `security`下相关的内容：

- `PermissionService` 权限服务，用于进行权限校验。
- `PermissionConstant` 权限相关常量，定义权限字段
- `filter/LoginStoreFilter` 登录过滤器，从 Session 中获取登录用户并注入至安全上下文



## 内置权限

- 描述: 服务根权限，拥有所有的服务级权限
	- 权限标识: service
	- 父权限: 无

**管理员模块**

- 描述: 管理员模块所有权限
	- 权限标识: service:admin
	- 父权限: service

- 描述: 管理员添加权限
	- 权限标识: service:admin:add
	- 父权限: service:admin

- 描述: 管理员更新权限
	- 权限标识: service:admin:update
	- 父权限: service:admin

- 描述: 管理员删除权限
	- 权限标识: service:admin:delete
	- 父权限: service:admin

- 描述: 管理员查询权限
	- 权限标识: service:admin:query
	- 父权限: service:admin

**用户模块**

- 描述: 用户模块所有权限
	- 权限标识: service:user
	- 父权限: service

- 描述: 用户添加权限
	- 权限标识: service:user:add
	- 父权限: service:user

- 描述: 用户更新权限
	- 权限标识: service:user:update
	- 父权限: service:user

- 描述: 用户删除权限
	- 权限标识: service:user:delete
	- 父权限: service:user

**权限模块**

- 描述: 权限模块所有权限
	- 权限标识: service:auth
	- 父权限: service

- 描述: 权限添加权限
	- 权限标识: service:auth:add
	- 父权限: service:auth

- 描述: 权限更新权限
	- 权限标识: service:auth:update
	- 父权限: service:auth

- 描述: 权限删除权限
	- 权限标识: service:auth:delete
	- 父权限: service:auth

- 描述: 权限查询权限
	- 权限标识: service:auth:query
	- 父权限: service:auth



# 管理员模块

项目已包含并实现管理员模块及相关功能，包括添加、删除、修改等。

- 添加/删除 管理员
- 更新管理员
- 查询/分页查询管理员信息

管理员模块的方法与 Spring Security 、权限模块高度耦合，方法均需要进行权限校验，通过校验后才可调用。