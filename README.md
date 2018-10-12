# myblog-1
## 个人博客系统V1.0
本项目采用springboot-1.5.8.RELEASE+jpa+thymeleaf实现，前端搜索引擎采用Lucene5.3.1，用户初始密码1234
一共分为五个项目
1. api
  主要存放一些公共的实体类，index类，dto,这是一个不用启动的项目
2. provider
  服务提供者类，REST接口，数据库连接类
3. consumer
  后台系统消费者类，能独立启动，但是必须发送请求到服务提供者提供登录和查询等后台功能（未提供权限角色授权页面功能，以及登录界面密码修改功能）
4. consumer-web
  前端显示项目，能独立启动，目前数据来源自Lucene,但是数据来源自provider项目（未完善功能）
5. security
  安全模块（未完成）
 
- 页面截图
