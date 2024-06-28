## 资源共享论坛

### 介绍
一个简单的论坛项目，使用github的api 进行登录，旨在帮助程序员们交流解惑，拓宽交流渠道

### 主要内容
用户登录  
文章上传   
评论  
通知

### 使用的工具
IDEA  
GitHubDesktop (用于管理代码版本)  
springboot (后端框架)  
bootstrap (前端框架)  
mysql   
myBatis-plus (持久层框架)  
lombok (注解插件，可以减少重复代码getter和setter)  
shrio (可能会加入，用于用户的权限管理)


### 使用的资料(补充中)
[sringboot文档](https://spring.io/guides)(springboot指导文档)  
[building oauthapps with git文档](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps)(github登录)  
[bootstrap文档](https://v3.bootcss.com/getting-started/)(boot strap文档)  
[thymeleaf引擎文档](https://fanlychie.github.io/post/thymeleaf.html)  
[mybatis-plus文档](https://baomidou.com/introduce/)  
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)  
[spring mvc](https://docs.spring.io/spring-framework/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-config-interceptors)

### 项目结构/文件夹设计（补充中）
controller(页面控制)  
dto(与外部网站端口进行数据交换而定义的数据结构，用于传递向外部网站的参数)  
mapper(Dao层，用于定义与数据库交换数据的方法，也可以自定义sql)  
model(Entity层，用于定义映射数据库中表的实体类，@Table注解用于映射表)
provider(与外部网站进行交流的具体实现)

### 数据库设计(补充中)
user(用户表) {   
id (主键，用于标注用户序号)  
account_id (账户id)  
name (昵称)  
token (使用 github 登录 api 获取的 token，可以唯一标注用户)  
gmt_create (创建时间)   
gmt_modified (修改时间)  
bio(描述)
avatar_url (头像url)

}  
sql脚本：  
create table user
(
id           bigint auto_increment
primary key,
account_id   varchar(100) null,
name         varchar(50)  null,
token        char(36)     null comment '登录的token',
gmt_create   bigint       null comment '创建时间',
gmt_modified bigint       null,
bio          varchar(100) null,
avatar_url   varchar(100) null
);




question(帖子表) {   
id  
title   (标题)  
description (描述)  
gmt_create (创建时间)   
gmt_modified (修改时间)
creator (创建人)  
comment_count (评论数)   
view_count (查看数)  
like_count (点赞数)  
tag(标签)  

}

sql脚本:
create table question
(
id            bigint auto_increment
primary key,
title         varchar(50)   null,
description   text          null,
gmt_create    bigint        null,
gmt_modified  bigint        null,
creator       bigint        null,
comment_count int default 0 null,
view_count    int default 0 null,
like_count    int default 0 null,
tag           varchar(256)  null
);


comment(回复表){  
id  
parent_id  (父类对象，指明评论对象)  
type  (1/2级回复)  
commentator (评论人)  
like_count (点赞数)  
gmt_create (创建时间)   
gmt_modified (修改时间)  
content (评论内容)  
comment_count (评论数)

}  

create table comment
(
id            bigint auto_increment
primary key,
parent_id     bigint           not null,
type          int              not null,
commentator   bigint           not null,
gmt_create    bigint           not null,
gmt_modified  bigint           not null,
like_count    bigint default 0 null,
content       varchar(1024)    null,
comment_count int    default 0 null
);


notification(通知表){    
id  
notifier (通知发起人)  
receiver (接受消息的人)    
outerid (从哪里来帖子/回复的id)  
type (帖子还是回复)  
gmt_create (创建时间)  
status(是否已经读 0未读 1已经读)  
outer_title (帖子/回复的标题)  
notifier_name (通知发起人的昵称)  
}   
create table notification
(
id            bigint auto_increment
primary key,
notifier      bigint        not null,
receiver      bigint        not null,
outerId       bigint        not null,
type          int           not null,
gmt_create    bigint        not null,
status        int default 0 not null,
notifier_name varchar(100)  null,
outer_title   varchar(256)  null
);



###er图
![er图](erpicture.png)

