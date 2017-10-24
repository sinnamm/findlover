/*==============================================================*/
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2017/10/10 16:02:15                          */
/*==============================================================*/
drop database if exists findlover;
create database findlover character set utf8;
use findlover;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   id                   int not null auto_increment,
   username             varchar(50) not null,
   password             varchar(50) not null,
   flag                 int not null default 1 comment '0：超级管理员、1：普通管理员',
   create_time          datetime not null,
   last_login           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: admin_role                                            */
/*==============================================================*/
create table admin_role
(
   admin_id             int,
   role_id              int
);

/*==============================================================*/
/* Table: complain                                              */
/*==============================================================*/
create table complain
(
   id                   int not null AUTO_INCREMENT,
   user_id              int not null,
   com_obj              int not null,
   reason               varchar(50) not null,
   content              varchar(50),
   com_time             datetime not null,
   status               int comment '0：待处理、1：忽略、2：警告、3：封号',
   admin_id             int,
   primary key (id)
);

/*==============================================================*/
/* Table: dict                                                  */
/*==============================================================*/
create table dict
(
   id                   int not null auto_increment,
   type                 varchar(50),
   value                varchar(50),
   primary key (id)
);

/*==============================================================*/
/* Table: essay                                                 */
/*==============================================================*/
create table essay
(
   id                   int not null AUTO_INCREMENT,
   writer_id            int,
   title                varchar(50),
   filename             varchar(50),
   pub_time             datetime,
   status               int comment '0：下架，1：审核通过，2：待审核',
   admin_id             int,
   like_count           int,
   visit_count          int,
   primary key (id)
);

/*==============================================================*/
/* Table: follow                                                */
/*==============================================================*/
create table follow
(
   id                   int not null auto_increment,
   user_id              int,
   follow_id            int,
   follow_time          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: "label"                                               */
/*==============================================================*/
create table label
(
   id                   int not null auto_increment,
   name                 varchar(50),
   primary key (id)
);

/*==============================================================*/
/* Table: letter                                                */
/*==============================================================*/
create table letter
(
   id                   int not null auto_increment,
   send_id              int,
   recieve_id           int,
   content              varchar(255),
   send_time            datetime,
   status               int,
   primary key (id)
);

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table message
(
   id                   int not null AUTO_INCREMENT,
   user_id              int,
   content              varchar(255),
   pub_time             datetime,
   like_count           int,
   reply_count          int,
   primary key (id)
);

/*==============================================================*/
/* Table: message_like                                          */
/*==============================================================*/
create table message_like
(
   id                   int not null AUTO_INCREMENT,
   message_id           int,
   user_id              int,
   like_time            datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: message_reply                                         */
/*==============================================================*/
create table message_reply
(
   id                   int not null AUTO_INCREMENT,
   message_id           int,
   user_id              int,
   content              varchar(255),
   reply_time           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: notice                                                */
/*==============================================================*/
create table notice
(
   id                   int not null AUTO_INCREMENT,
   admin_id             int,
   title                varchar(100),
   content              varchar(255),
   pub_time             datetime,
   pub_obj              int comment '0:所有用户，1:vip，2:星级用户，Id:用户id',
   primary key (id)
);

/*==============================================================*/
/* Table: notice_user                                           */
/*==============================================================*/
create table notice_user
(
   notice_id            int not null,
   user_id              int not null,
   read_time            datetime not null,
   primary key (notice_id)
);

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   id                   int not null auto_increment,
   name                 varchar(50),
   value                char(10),
   primary key (id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   int not null auto_increment,
   name                 varchar(50) not null,
   note                 varchar(100),
   primary key (id)
);

/*==============================================================*/
/* Table: role_permission                                       */
/*==============================================================*/
create table role_permission
(
   role_id              int,
   permission_id        int
);

/*==============================================================*/
/* Table: success_story                                         */
/*==============================================================*/
create table success_story
(
   id                   int not null AUTO_INCREMENT,
   left_user            int,
   right_user           int,
   title                varchar(100),
   content              text,
   success_time         datetime,
   photo                varchar(50),
   like_count           int,
   reply_count          int,
   status               int comment '0：下架，1：审核通过，2：待右手审核，3：待管理员审核',
   admin_id             int,
   primary key (id)
);

/*==============================================================*/
/* Table: success_story_like                                    */
/*==============================================================*/
create table success_story_like
(
   id                   int not null AUTO_INCREMENT,
   success_story_id     int,
   user_id              int,
   like_time            datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: success_story_reply                                   */
/*==============================================================*/
create table success_story_reply
(
   id                   int not null AUTO_INCREMENT,
   ss_id                int,
   user_id              int,
   content              varchar(255),
   reply_id             datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: user_asset                                            */
/*==============================================================*/
create table user_asset
(
   id                   int not null,
   vip_deadline         datetime DEFAULT '1970-01-01 11:11:11',
   star_deadline        datetime DEFAULT '1970-01-01 11:11:11',
   asset                int DEFAULT 0,
   cost                 double DEFAULT 0,
   primary key (id)
);

/*==============================================================*/
/* Table: user_basic                                            */
/*==============================================================*/
create table user_basic
(
   id                   int not null auto_increment,
   email                varchar(50) not null,
   password             varchar(50) not null,
   nickname             varchar(50) not null,
   tel                  varchar(50) not null,
   sex                  char(2) not null,
   birthday             date not null,
   photo                varchar(50) not null,
   marry_status         varchar(50) not null,
   height               int not null,
   sexual               char(2) not null,
   education            varchar(50) not null,
   workplace            varchar(50) not null,
   salary               double(9,2) not null,
   live_condition       int not null,
   authority            int not null default 1 comment '个人资料可见性（0：所有用户不可见，1：所有用户可见，2：仅我关注的人可见）',
   status               int not null comment '账户状态（0：锁定，1：激活，2：未激活）',
   code                 VARCHAR(255) COMMENT '用户激活码',
   reg_time             datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: user_login_log                                        */
/*==============================================================*/
create table user_login_log
(
   id                   int not null AUTO_INCREMENT,
   user_id              int,
   login_time           datetime,
   login_ip             varchar(50),
   primary key (id)
);

/*==============================================================*/
/* Table: user_detail                                           */
/*==============================================================*/
create table user_detail
(
   id                   int not null,
   realname             varchar(50),
   cardnumber           varchar(50),
   birthplace           varchar(50),
   weight               double,
   animal               char(2),
   zodiac               char(6),
   nation               varchar(20),
   religion             varchar(20),
   graduation           varchar(50),
   hobby                varchar(255),
   signature            varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: user_label                                            */
/*==============================================================*/
create table user_label
(
   user_id              int not null,
   label_id             int not null
);

/*==============================================================*/
/* Table: user_life                                             */
/*==============================================================*/
create table user_life
(
   id                   int not null,
   smoke                int,
   drink                int,
   car                  int,
   job                  varchar(50),
   job_time             varchar(50),
   `character`            varchar(255),
   job_brief            varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: user_photo                                            */
/*==============================================================*/
create table user_photo
(
   id                   int not null auto_increment,
   user_id              int,
   photo                varchar(100),
   primary key (id)
);

/*==============================================================*/
/* Table: user_pick                                             */
/*==============================================================*/
create table user_pick
(
   id                   int not null,
   sex                  char(2) not null comment '根据性取向生成，用户不可修改',
   age_low              int not null,
   age_high             int not null,
   workplace            varchar(50),
   birthplace           varchar(50),
   marry_status         varchar(50),
   education            varchar(50),
   salary_low           double,
   salary_high          double,
   height_low           int,
   height_high          int,
   job                  varchar(50),
   drink                int,
   smoke                int,
   primary key (id)
);

/*==============================================================*/
/* Table: user_status                                           */
/*==============================================================*/
create table user_status
(
   id                   int not null,
   love_history         varchar(50),
   marry_time           varchar(50),
   ldr                  int,
   parent_status        varchar(50),
   bro_and_sis          varchar(50),
   family_brief         varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: visit_trace                                           */
/*==============================================================*/
create table visit_trace
(
   id                   int not null auto_increment,
   user_id              int not null,
   interviewee_id       int not null,
   visit_time           datetime not null,
   primary key (id)
);

/*==============================================================*/
/* Table: writer                                                */
/*==============================================================*/
create table writer
(
   id                   int not null auto_increment,
   pseudonym            varchar(50),
   reg_time             datetime,
   essay_count          int,
   primary key (id)
);

/*==============================================================*/
/* Table: writer_essay_like                                     */
/*==============================================================*/
create table writer_essay_like
(
   id                   int not null AUTO_INCREMENT,
   user_id              int,
   essay_id             int,
   like_time            datetime,
   primary key (id)
);

alter table admin_role add constraint FK_fk_araid foreign key (admin_id)
      references admin (id) on delete CASCADE on update CASCADE ;

alter table admin_role add constraint FK_fk_arrid foreign key (role_id)
      references role (id) on delete CASCADE on update CASCADE;

alter table complain add constraint FK_fk_comadminid foreign key (admin_id)
      references admin (id) on delete CASCADE on update CASCADE;

alter table complain add constraint FK_fk_comobjid foreign key (com_obj)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table complain add constraint FK_fk_comuserid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table essay add constraint FK_fk_essayadminid foreign key (admin_id)
      references admin (id) on delete CASCADE on update CASCADE;

alter table essay add constraint FK_fk_wewid foreign key (writer_id)
      references writer (id) on delete CASCADE on update CASCADE;

alter table follow add constraint FK_fk_uffollowid foreign key (follow_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table follow add constraint FK_fk_ufuserid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table letter add constraint FK_fk_letterrecieveid foreign key (recieve_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table letter add constraint FK_fk_lettersendid foreign key (send_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table message add constraint FK_fk_msguserid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table message_like add constraint FK_fk_msgrlikemid foreign key (message_id)
      references message (id) on delete CASCADE on update CASCADE;

alter table message_like add constraint FK_fk_msgruid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table message_reply add constraint FK_fk_mrmsgid foreign key (message_id)
      references message (id) on delete CASCADE on update CASCADE;

alter table message_reply add constraint FK_fk_msgruserid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table notice add constraint FK_fk_noticeaaid foreign key (admin_id)
      references admin (id) on delete CASCADE on update CASCADE;

alter table notice_user add constraint FK_fk_unnid foreign key (notice_id)
      references notice (id) on delete CASCADE on update CASCADE;

alter table notice_user add constraint FK_fk_unuid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table role_permission add constraint FK_fk_rmmid foreign key (permission_id)
      references permission (id) on delete CASCADE on update CASCADE;

alter table role_permission add constraint FK_fk_rmrid foreign key (role_id)
      references role (id) on delete CASCADE on update CASCADE;

alter table success_story add constraint FK_fk_ssadminid foreign key (admin_id)
      references admin (id) on delete CASCADE on update CASCADE;

alter table success_story add constraint FK_fk_ucleftid foreign key (left_user)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table success_story add constraint FK_fk_ucrightid foreign key (right_user)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table success_story_like add constraint FK_fk_sslikesid foreign key (success_story_id)
      references success_story (id) on delete CASCADE on update CASCADE;

alter table success_story_like add constraint FK_fk_sslikeuid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table success_story_reply add constraint FK_fk_ssrssid foreign key (ss_id)
      references success_story (id) on delete CASCADE on update CASCADE;

alter table success_story_reply add constraint FK_fk_ssruserid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table user_asset add constraint FK_fk_uaid foreign key (id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table user_detail add constraint FK_fk_udid foreign key (id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table user_label add constraint FK_fk_ullabelid foreign key (label_id)
      references label (id) on delete CASCADE on update CASCADE;

alter table user_label add constraint FK_fk_uluserid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table user_life add constraint FK_fk_ulid foreign key (id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table user_photo add constraint FK_fk_ufid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table user_pick add constraint FK_fk_upid foreign key (id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table user_login_log add constraint FK_fk_uluuid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table user_status add constraint FK_fk_usid foreign key (id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table visit_trace add constraint FK_fk_uvuserid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table visit_trace add constraint FK_fk_uvvid foreign key (interviewee_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table writer_essay_like add constraint FK_fk_essay_like_userid foreign key (user_id)
      references user_basic (id) on delete CASCADE on update CASCADE;

alter table writer_essay_like add constraint FK_fk_welikeeid foreign key (essay_id)
      references essay (id) on delete CASCADE on update CASCADE;


create trigger Trigger_writer_essay after insert
on essay for each row
update writer set essay_count=essay_count+1 where id=new.writer_id;

create trigger Trigger_msgreplylike after insert
on message_like for each row
update message set like_count=like_count+1 where id=new.message_id;

create trigger Trigger_msg_reply after insert
on message_reply for each row
update message set reply_count=reply_count+1 where id=new.message_id;

create trigger trigger_sslike after insert
on success_story_like for each row
update success_story set like_count=like_count+1 where id=new.success_story_id;

create trigger trigger_ssreply after insert
on success_story_reply for each row
update success_story set reply_count=reply_count+1 where id=new.ss_id;

create trigger trigger_seletterxual after update
on user_basic for each row
update user_pick set sex=new.sexual where id=new.id;

create trigger Trigger_essay_like after insert
on writer_essay_like for each row
update essay set like_count=like_count+1 where id=new.essay_id;

-- 数据字典表数据
INSERT INTO findlover.dict (type, value) VALUES ('education', '高中及以下');
INSERT INTO findlover.dict (type, value) VALUES ('education', '中专');
INSERT INTO findlover.dict (type, value) VALUES ('education', '大专');
INSERT INTO findlover.dict (type, value) VALUES ('education', '大学本科');
INSERT INTO findlover.dict (type, value) VALUES ('education', '硕士');
INSERT INTO findlover.dict (type, value) VALUES ('education', '博士');
INSERT INTO findlover.dict (type, value) VALUES ('marry_status', '未婚');
INSERT INTO findlover.dict (type, value) VALUES ('marry_status', '离异');
INSERT INTO findlover.dict (type, value) VALUES ('marry_status', '丧偶');
INSERT INTO findlover.dict (type, value) VALUES ('live_condition', '和家人同住');
INSERT INTO findlover.dict (type, value) VALUES ('live_condition', '已购房');
INSERT INTO findlover.dict (type, value) VALUES ('live_condition', '租房');
INSERT INTO findlover.dict (type, value) VALUES ('live_condition', '打算婚后购房');
INSERT INTO findlover.dict (type, value) VALUES ('live_condition', '单位宿舍');
INSERT INTO findlover.dict (type, value) VALUES ('job', '销售');
INSERT INTO findlover.dict (type, value) VALUES ('job', '客户服务');
INSERT INTO findlover.dict (type, value) VALUES ('job', '计算机/互联网');
INSERT INTO findlover.dict (type, value) VALUES ('job', '通信/电子');
INSERT INTO findlover.dict (type, value) VALUES ('job', '生产/制造');
INSERT INTO findlover.dict (type, value) VALUES ('job', '物流/仓储');
INSERT INTO findlover.dict (type, value) VALUES ('job', '商贸/采购');
INSERT INTO findlover.dict (type, value) VALUES ('job', '人事/行政');
INSERT INTO findlover.dict (type, value) VALUES ('job', '高级管理');
INSERT INTO findlover.dict (type, value) VALUES ('job', '广告/市场');
INSERT INTO findlover.dict (type, value) VALUES ('job', '传媒/艺术');
INSERT INTO findlover.dict (type, value) VALUES ('job', '生物/制药');
INSERT INTO findlover.dict (type, value) VALUES ('job', '医疗/护理');
INSERT INTO findlover.dict (type, value) VALUES ('job', '金融/保险');
INSERT INTO findlover.dict (type, value) VALUES ('job', '建筑/房地产');
INSERT INTO findlover.dict (type, value) VALUES ('job', '咨询/顾问');
INSERT INTO findlover.dict (type, value) VALUES ('job', '法律');
INSERT INTO findlover.dict (type, value) VALUES ('job', '财会/审计');
INSERT INTO findlover.dict (type, value) VALUES ('job', '教育/科研');
INSERT INTO findlover.dict (type, value) VALUES ('job', '服务业');
INSERT INTO findlover.dict (type, value) VALUES ('job', '交通运输');
INSERT INTO findlover.dict (type, value) VALUES ('job', '政府机构');
INSERT INTO findlover.dict (type, value) VALUES ('job', '军人/警察');
INSERT INTO findlover.dict (type, value) VALUES ('job', '农林牧渔');
INSERT INTO findlover.dict (type, value) VALUES ('job', '自由职业');
INSERT INTO findlover.dict (type, value) VALUES ('job', '在校学生');
INSERT INTO findlover.dict (type, value) VALUES ('job', '待业');
INSERT INTO findlover.dict (type, value) VALUES ('job', '其他行业');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '鼠');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '牛');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '虎');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '兔');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '龙');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '蛇');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '马');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '羊');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '猴');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '鸡');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '狗');
INSERT INTO findlover.dict (type, value) VALUES ('animal', '猪');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '白羊座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '金牛座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '双子座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '巨蟹座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '狮子座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '处女座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '天秤座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '天蝎座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '射手座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '摩羯座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '水平座');
INSERT INTO findlover.dict (type, value) VALUES ('zodiac', '双鱼座');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '不信教');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '佛教');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '道教');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '伊斯兰教');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '基督教');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '天主教');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '儒家门徒');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '不可知论者');
INSERT INTO findlover.dict (type, value) VALUES ('religion', '其他宗教');
INSERT INTO findlover.dict (type, value) VALUES ('job_time', '有双休');
INSERT INTO findlover.dict (type, value) VALUES ('job_time', '工作忙碌');
INSERT INTO findlover.dict (type, value) VALUES ('job_time', '工作清闲');
INSERT INTO findlover.dict (type, value) VALUES ('job_time', '自由工作出差');
INSERT INTO findlover.dict (type, value) VALUES ('job_time', '经常出差');
INSERT INTO findlover.dict (type, value) VALUES ('love_history', '初恋还在');
INSERT INTO findlover.dict (type, value) VALUES ('love_history', '谈过3次以内恋爱');
INSERT INTO findlover.dict (type, value) VALUES ('love_history', '情场高手');
INSERT INTO findlover.dict (type, value) VALUES ('marry_time', '认同闪婚');
INSERT INTO findlover.dict (type, value) VALUES ('marry_time', '一年内');
INSERT INTO findlover.dict (type, value) VALUES ('marry_time', '两年内');
INSERT INTO findlover.dict (type, value) VALUES ('marry_time', '三年内');
INSERT INTO findlover.dict (type, value) VALUES ('marry_time', '时机成熟就结婚');
INSERT INTO findlover.dict (type, value) VALUES ('parent_status', '父母均健在');
INSERT INTO findlover.dict (type, value) VALUES ('parent_status', '只有母亲健在');
INSERT INTO findlover.dict (type, value) VALUES ('parent_status', '只有父亲健在');
INSERT INTO findlover.dict (type, value) VALUES ('parent_status', '父母均已离世');
INSERT INTO findlover.dict (type, value) VALUES ('bro_and_sis', '独生子女');
INSERT INTO findlover.dict (type, value) VALUES ('bro_and_sis', '2');
INSERT INTO findlover.dict (type, value) VALUES ('bro_and_sis', '3');
INSERT INTO findlover.dict (type, value) VALUES ('bro_and_sis', '4');
INSERT INTO findlover.dict (type, value) VALUES ('bro_and_sis', '更多');
INSERT INTO findlover.dict (type, value) VALUES ('com_reason', '违法信息');
INSERT INTO findlover.dict (type, value) VALUES ('com_reason', '有害信息');
INSERT INTO findlover.dict (type, value) VALUES ('com_reason', '人身攻击我');
-- user_basic表
INSERT INTO findlover.user_basic (email, password, nickname, tel, sex, birthday, photo, marry_status, height, sexual, education, workplace, salary, live_condition, authority, status, code, reg_time) VALUES ('gss@qq.com', '202CB962AC59075B964B07152D234B70', 'gsssss', '123', '男', '1997-07-19', 'p7.jpg', '未婚', 175, '男', '大学本科', '山东-济南', 8000, 0, 1, 1, null, '2017-10-17 21:02:53');
INSERT INTO findlover.user_basic (email, password, nickname, tel, sex, birthday, photo, marry_status, height, sexual, education, workplace, salary, live_condition, authority, status, code, reg_time) VALUES ('a@a.com', '202CB962AC59075B964B07152D234B70', 'Tom', '132', '男', '2017-10-16', 'p7.jpg', '未婚', 173, '女', '大学本科', '山东-菏泽', 5000, 1, 1, 1, null, '2017-10-17 15:12:13');
INSERT INTO findlover.user_basic (email, password, nickname, tel, sex, birthday, photo, marry_status, height, sexual, education, workplace, salary, live_condition, authority, status, code, reg_time) VALUES ('sinna@163.com', '202CB962AC59075B964B07152D234B70', 'sinnamm', '123', '女', '1996-08-01', 'p6.jpg', '未婚', 168, '女', '大学本科', '北京-朝阳区', 7000, 1, 1, 1, null, '2017-10-17 15:12:09');
INSERT INTO findlover.user_basic (email, password, nickname, tel, sex, birthday, photo, marry_status, height, sexual, education, workplace, salary, live_condition, authority, status, code, reg_time) VALUES ('sinnamm@163.com', '202CB962AC59075B964B07152D234B70', 'sinnamm', '123', '女', '1996-08-01', 'p6.jpg', '未婚', 168, '女', '大学本科', '北京-海淀区', 7000, 1, 1, 1, null, '2017-10-17 15:12:09');
INSERT INTO findlover.user_basic (email, password, nickname, tel, sex, birthday, photo, marry_status, height, sexual, education, workplace, salary, live_condition, authority, status, code, reg_time) VALUES ('1472610316@qq.com', '202CB962AC59075B964B07152D234B70', 'aaa', '17865166639', '男', '2017-10-06', 'p6.jpg', '未婚', 161, '男', '大专', '山东-淄博', 111111, 0, 2, 1, '50005637-14b3-4599-8416-b1a86639dca8', '2017-10-18 11:48:14');
INSERT INTO findlover.user_basic (email, password, nickname, tel, sex, birthday, photo, marry_status, height, sexual, education, workplace, salary, live_condition, authority, status, code, reg_time) VALUES ('b@a.com', '202CB962AC59075B964B07152D234B70', '昵称请看个性签名', '13222222222', '男', '2017-10-06', 'p6.jpg', '未婚', 164, '女', '中专', '上海-长宁区', 12345, 0, 1, 1, 'b96086a9-f706-41f0-9a3a-33fc167fb7d8', '2017-10-17 18:05:29');
INSERT INTO findlover.user_basic (email, password, nickname, tel, sex, birthday, photo, marry_status, height, sexual, education, workplace, salary, live_condition, authority, status, code, reg_time) VALUES ('gss@gss.com', '202CB962AC59075B964B07152D234B70', 'Gss', '132', '男', '2017-10-16', 'p7.jpg', '未婚', 173, '女', '大学本科', '山东-菏泽', 5000, 1, 1, 1, null, '2017-10-17 15:12:13');
INSERT INTO findlover.user_basic (email, password, nickname, tel, sex, birthday, photo, marry_status, height, sexual, education, workplace, salary, live_condition, authority, status, code, reg_time) VALUES ('zhangsan@163.com', '202CB962AC59075B964B07152D234B70', 'zhangsan', '16527783355', '男', '1992-12-01', 'p6.jpg', '未婚', 177, '女', '硕士', '山东-青岛', 8000, 0, 1, 1, 'a82b2b1f-ee86-43d3-a5d4-67327b91a063', '2017-10-21 15:29:56');
-- user_detail表
INSERT INTO findlover.user_detail (id, realname, cardnumber, birthplace, weight, animal, zodiac, nation, religion, graduation, hobby, signature) VALUES (100001, '帅帅', '4454151515', '山东-济宁', 67, '虎', '天蝎座', '汉族', '佛教', '山东-青岛科技大学', '爱睡觉，爱打撸啊撸，爱跑步', '生命诚可贵，爱情价更高');
INSERT INTO findlover.user_detail (id, realname, cardnumber, birthplace, weight, animal, zodiac, nation, religion, graduation, hobby, signature) VALUES (100002, 'gggg', '123123', '山东-青岛', 12, null, null, null, null, null, null, null);
INSERT INTO findlover.user_detail (id, realname, cardnumber, birthplace, weight, animal, zodiac, nation, religion, graduation, hobby, signature) VALUES (100007, '张三', '452127199903142713', '安徽-亳州', 66, '虎', '双子座', '蒙古族', '佛教', '广东-广州大学', '张三张三张三', '张三张三张三张三');
-- user_pick表
INSERT INTO findlover.user_pick (id, sex, age_low, age_high, workplace, birthplace, marry_status, education, salary_low, salary_high, height_low, height_high, job, drink, smoke) VALUES (100001, '女', 18, 33, '山东-青岛', '山东-菏泽', '未婚', '硕士', 3000, 5000, 185, 195, '客户服务', 0, 0);
INSERT INTO findlover.user_pick (id, sex, age_low, age_high, workplace, birthplace, marry_status, education, salary_low, salary_high, height_low, height_high, job, drink, smoke) VALUES (100002, '女', 20, 25, '北京-东城区', '', '未婚', '大学本科', 3000, 5000, 165, 190, '', null, null);
INSERT INTO findlover.user_pick (id, sex, age_low, age_high, workplace, birthplace, marry_status, education, salary_low, salary_high, height_low, height_high, job, drink, smoke) VALUES (100007, '女', 18, 33, '河南-许昌', '', '离异', '大专', 3000, 8000, 167, 187, '通信/电子', 1, -1);
-- user_asset表
INSERT INTO findlover.user_asset (id, vip_deadline, star_deadline, asset, cost) VALUES (100000, '2017-12-10 16:55:21', '2017-12-29 16:56:16', 100, 0);
INSERT INTO findlover.user_asset (id, vip_deadline, star_deadline, asset, cost) VALUES (100002, '2017-12-10 16:55:21', '2017-11-17 16:55:30', 0, 0);
INSERT INTO findlover.user_asset (id, vip_deadline, star_deadline, asset, cost) VALUES (100003, '2017-10-20 16:56:00', '2017-12-09 20:21:23', 11, 0);
INSERT INTO findlover.user_asset (id, vip_deadline, star_deadline, asset, cost) VALUES (100005, '2020-04-29 16:55:21', '2017-12-31 16:56:35', 948, 3490.8399999999997);
-- user_life表
INSERT INTO findlover.user_life (id, smoke, drink, car, job, job_time, `character`, job_brief) VALUES (100002, 0, 1, 0, '服务业', '工作忙碌', '阳光开朗', '轻松愉快');
INSERT INTO findlover.user_life (id, smoke, drink, car, job, job_time, `character`, job_brief) VALUES (100007, 0, 1, 1, '物流/仓储', '工作清闲', '张三张三张三张三张三', '张三张三张三张三张三张三');
-- user_status表
INSERT INTO findlover.user_status (id, love_history, marry_time, ldr, parent_status, bro_and_sis, family_brief) VALUES (100001, '情场高手', '三年内', 0, '父母均健在', '2', '家庭美满幸福');
INSERT INTO findlover.user_status (id, love_history, marry_time, ldr, parent_status, bro_and_sis, family_brief) VALUES (100007, '初恋还在', '一年内', 0, '只有母亲健在', '4', '张三张三张三张三张三张三张三');
-- label表
INSERT INTO findlover.label (name) VALUES ('高收入');
INSERT INTO findlover.label (name) VALUES ('高学历');
INSERT INTO findlover.label (name) VALUES ('有车一族');
INSERT INTO findlover.label (name) VALUES ('有房一族');
INSERT INTO findlover.label (name) VALUES ('公务员');
-- success_story表
INSERT INTO findlover.success_story (id, left_user, right_user, title, content, success_time, photo, like_count, reply_count, status, admin_id) VALUES (1, 1, 2, '我们的恋爱历程', '
          <h3 style="margin: 30px">我们的恋爱历程</h3>

          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因为身边有朋友在珍爱网服务过，有成功找到对象的，也有没找到的，不过这种个人因素占很大比重的事情，苛求百分百的成功率也不太现实。所以我觉得这还是不错的方式，试试也不错，就去珍爱网深圳地王店线下服务中心加入了会员，想多给自己一些机会。</p>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我还是有挺多顾虑的，害怕碰到熟人，多少有点尴尬，也怕遇到渣男，被欺骗感情。不过事实证明是我多虑了，他还挺好的，第一次见面就觉得无论是着装还是谈吐都很吸引人，相处了快一年，日久见人心，他是个品质优秀也对我很好的人。</p>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;从认识到现在，每次节日，不论东方的西方的，成文的不成文的，除了礼物每次都会送一束玫瑰花，不知道别的女孩子是什么样的，我是很爱花的，觉得谈恋爱一定要有玫瑰的香味氤氲才称得上浪漫。</p>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;他送过我很多小礼物，都是生活中用得上的，很贴心也很用心的一个人，会送手机啊，还有一些保健品，其他的还有太多都想不起来了。</p>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在一起不久我们就出国去旅游了一次，当时刚好碰上都有假期，就来了一场说走就走的旅行，感情里充满了惊喜。</p>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现在感情很稳定，每周都会见个两三次，每天晚上回家了还都会视频聊天，也都很走心，虽然年纪不大，但是奔着结婚去的，都见过了家长。偶尔也会拌拌嘴，不过一般都是我在闹，他在笑。从来没有什么原则性的事情会争吵，都是女生的小情绪，发点小脾气，哄一哄就好了。</p>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除了跟男朋友要磨合，跟红娘之间其实也是有一段时间的磨合期的，真正信任了红娘老师，会对自己的征婚有很大的帮助。我前前后后也见过了几个会员，综合了解之后，发现很多条件并不是像自己制定的那么苛刻，而且红娘老师可能不是在某一个人身上教给了你一些东西，而是在你觉得不满意的时候，帮忙分析原因，这对以后的感情生活也很有帮助，因为两个人不合适往往不是一个人的事情。</p>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们俩年纪都不大，想要再谈几年，双方家庭都比较传统，结了婚就会被催着要小孩，会有各种约束，现在的恋爱状态彼此都很自由很享受，还暂时不想改变。等时间到了，也就会结婚了。</p>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;很感恩珍爱网，愿意分享自己的故事也是基于对珍爱网的信任和感谢，人海茫茫遇到一个合适的人不容易，很多人蹉跎中错过了很多，我庆幸自己迈出了这一步。同时也很感谢红娘朱老师，教给了我很多东西，甚至拿自己跟先生的一些事例来告诉我爱情里和婚姻里的处事之道，真的很贴心。</p>
      ', '2017-10-17 15:15:53', 'wed.jpg', 1, 1, 1, null);

commit;