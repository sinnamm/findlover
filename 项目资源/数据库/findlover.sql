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
   id                   int not null,
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
   id                   int not null,
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
   id                   int not null,
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
   id                   int not null,
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
   id                   int not null,
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
   id                   int not null,
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
   id                   int not null,
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
   id                   int not null,
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
   id                   int not null,
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
   vip_deadline         datetime,
   star_dealline        datetime,
   asset                int,
   cost                 double,
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
   id                   int not null,
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
   birthday             varchar(50),
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
   id                   int not null,
   user_id              int,
   essay_id             int,
   like_time            datetime,
   primary key (id)
);

alter table admin_role add constraint FK_fk_araid foreign key (admin_id)
      references admin (id) on delete restrict on update restrict;

alter table admin_role add constraint FK_fk_arrid foreign key (role_id)
      references role (id) on delete restrict on update restrict;

alter table complain add constraint FK_fk_comadminid foreign key (admin_id)
      references admin (id) on delete restrict on update restrict;

alter table complain add constraint FK_fk_comobjid foreign key (com_obj)
      references user_basic (id) on delete restrict on update restrict;

alter table complain add constraint FK_fk_comuserid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table essay add constraint FK_fk_essayadminid foreign key (admin_id)
      references admin (id) on delete restrict on update restrict;

alter table essay add constraint FK_fk_wewid foreign key (writer_id)
      references writer (id) on delete restrict on update restrict;

alter table follow add constraint FK_fk_uffollowid foreign key (follow_id)
      references user_basic (id) on delete restrict on update restrict;

alter table follow add constraint FK_fk_ufuserid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table letter add constraint FK_fk_letterrecieveid foreign key (recieve_id)
      references user_basic (id) on delete restrict on update restrict;

alter table letter add constraint FK_fk_lettersendid foreign key (send_id)
      references user_basic (id) on delete restrict on update restrict;

alter table message add constraint FK_fk_msguserid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table message_like add constraint FK_fk_msgrlikemid foreign key (message_id)
      references message (id) on delete restrict on update restrict;

alter table message_like add constraint FK_fk_msgruid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table message_reply add constraint FK_fk_mrmsgid foreign key (message_id)
      references message (id) on delete restrict on update restrict;

alter table message_reply add constraint FK_fk_msgruserid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table notice add constraint FK_fk_noticeaaid foreign key (admin_id)
      references admin (id) on delete restrict on update restrict;

alter table notice_user add constraint FK_fk_unnid foreign key (notice_id)
      references notice (id) on delete restrict on update restrict;

alter table notice_user add constraint FK_fk_unuid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table role_permission add constraint FK_fk_rmmid foreign key (permission_id)
      references permission (id) on delete restrict on update restrict;

alter table role_permission add constraint FK_fk_rmrid foreign key (role_id)
      references role (id) on delete restrict on update restrict;

alter table success_story add constraint FK_fk_ssadminid foreign key (admin_id)
      references admin (id) on delete restrict on update restrict;

alter table success_story add constraint FK_fk_ucleftid foreign key (left_user)
      references user_basic (id) on delete restrict on update restrict;

alter table success_story add constraint FK_fk_ucrightid foreign key (right_user)
      references user_basic (id) on delete restrict on update restrict;

alter table success_story_like add constraint FK_fk_sslikesid foreign key (success_story_id)
      references success_story (id) on delete restrict on update restrict;

alter table success_story_like add constraint FK_fk_sslikeuid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table success_story_reply add constraint FK_fk_ssrssid foreign key (ss_id)
      references success_story (id) on delete restrict on update restrict;

alter table success_story_reply add constraint FK_fk_ssruserid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table user_asset add constraint FK_fk_uaid foreign key (id)
      references user_basic (id) on delete restrict on update restrict;

alter table user_detail add constraint FK_fk_udid foreign key (id)
      references user_basic (id) on delete restrict on update restrict;

alter table user_label add constraint FK_fk_ullabelid foreign key (label_id)
      references label (id) on delete restrict on update restrict;

alter table user_label add constraint FK_fk_uluserid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table user_life add constraint FK_fk_ulid foreign key (id)
      references user_basic (id) on delete restrict on update restrict;

alter table user_photo add constraint FK_fk_ufid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table user_pick add constraint FK_fk_upid foreign key (id)
      references user_basic (id) on delete restrict on update restrict;

alter table user_login_log add constraint FK_fk_uluuid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table user_status add constraint FK_fk_usid foreign key (id)
      references user_basic (id) on delete restrict on update restrict;

alter table visit_trace add constraint FK_fk_uvuserid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table visit_trace add constraint FK_fk_uvvid foreign key (interviewee_id)
      references user_basic (id) on delete restrict on update restrict;

alter table writer_essay_like add constraint FK_fk_essay_like_userid foreign key (user_id)
      references user_basic (id) on delete restrict on update restrict;

alter table writer_essay_like add constraint FK_fk_welikeeid foreign key (essay_id)
      references essay (id) on delete restrict on update restrict;


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
INSERT INTO dict(type,value) VALUES('education','高中及以下');
INSERT INTO dict(type,value) VALUES('education','中专');
INSERT INTO dict(type,value) VALUES('education','大专');
INSERT INTO dict(type,value) VALUES('education','大学本科');
INSERT INTO dict(type,value) VALUES('education','硕士');
INSERT INTO dict(type,value) VALUES('education','博士');

INSERT INTO dict(type,value) VALUES('live_condition','和家人同住');
INSERT INTO dict(type,value) VALUES('live_condition','已购房');
INSERT INTO dict(type,value) VALUES('live_condition','租房');
INSERT INTO dict(type,value) VALUES('live_condition','打算婚后购房');
INSERT INTO dict(type,value) VALUES('live_condition','单位宿舍');

INSERT INTO dict(type,value) VALUES('marry_status','未婚');
INSERT INTO dict(type,value) VALUES('marry_status','离异');
INSERT INTO dict(type,value) VALUES('marry_status','丧偶');

INSERT INTO dict(type,value) VALUES('job','销售');
INSERT INTO dict(type,value) VALUES('job','客户服务');
INSERT INTO dict(type,value) VALUES('job','计算机/互联网');
INSERT INTO dict(type,value) VALUES('job','通信/电子');
INSERT INTO dict(type,value) VALUES('job','生产/制造');
INSERT INTO dict(type,value) VALUES('job','物流/仓储');
INSERT INTO dict(type,value) VALUES('job','商贸/采购');
INSERT INTO dict(type,value) VALUES('job','人事/行政');
INSERT INTO dict(type,value) VALUES('job','高级管理');
INSERT INTO dict(type,value) VALUES('job','广告/市场');
INSERT INTO dict(type,value) VALUES('job','传媒/艺术');
INSERT INTO dict(type,value) VALUES('job','生物/制药');
INSERT INTO dict(type,value) VALUES('job','医疗/护理');
INSERT INTO dict(type,value) VALUES('job','金融/保险');
INSERT INTO dict(type,value) VALUES('job','建筑/房地产');
INSERT INTO dict(type,value) VALUES('job','咨询/顾问');
INSERT INTO dict(type,value) VALUES('job','法律');
INSERT INTO dict(type,value) VALUES('job','财会/审计');
INSERT INTO dict(type,value) VALUES('job','教育/科研');
INSERT INTO dict(type,value) VALUES('job','服务业');
INSERT INTO dict(type,value) VALUES('job','交通运输');
INSERT INTO dict(type,value) VALUES('job','政府机构');
INSERT INTO dict(type,value) VALUES('job','军人/警察');
INSERT INTO dict(type,value) VALUES('job','农林牧渔');
INSERT INTO dict(type,value) VALUES('job','自由职业');
INSERT INTO dict(type,value) VALUES('job','在校学生');
INSERT INTO dict(type,value) VALUES('job','待业');
INSERT INTO dict(type,value) VALUES('job','其他行业');
-- 鼠、牛、虎、兔、龙、蛇、马、羊、猴、鸡、狗、猪
INSERT INTO dict(type,value) VALUES('animal','鼠');
INSERT INTO dict(type,value) VALUES('animal','牛');
INSERT INTO dict(type,value) VALUES('animal','虎');
INSERT INTO dict(type,value) VALUES('animal','兔');
INSERT INTO dict(type,value) VALUES('animal','龙');
INSERT INTO dict(type,value) VALUES('animal','蛇');
INSERT INTO dict(type,value) VALUES('animal','马');
INSERT INTO dict(type,value) VALUES('animal','羊');
INSERT INTO dict(type,value) VALUES('animal','猴');
INSERT INTO dict(type,value) VALUES('animal','鸡');
INSERT INTO dict(type,value) VALUES('animal','狗');
INSERT INTO dict(type,value) VALUES('animal','猪');

INSERT INTO dict(type,value) VALUES('zodiac','白羊座');
INSERT INTO dict(type,value) VALUES('zodiac','金牛座');
INSERT INTO dict(type,value) VALUES('zodiac','双子座');
INSERT INTO dict(type,value) VALUES('zodiac','巨蟹座');
INSERT INTO dict(type,value) VALUES('zodiac','狮子座');
INSERT INTO dict(type,value) VALUES('zodiac','处女座');
INSERT INTO dict(type,value) VALUES('zodiac','天秤座');
INSERT INTO dict(type,value) VALUES('zodiac','天蝎座');
INSERT INTO dict(type,value) VALUES('zodiac','射手座');
INSERT INTO dict(type,value) VALUES('zodiac','摩羯座');
INSERT INTO dict(type,value) VALUES('zodiac','水平座');
INSERT INTO dict(type,value) VALUES('zodiac','双鱼座');

INSERT INTO dict(type,value) VALUES('religion','不信教');
INSERT INTO dict(type,value) VALUES('religion','佛教');
INSERT INTO dict(type,value) VALUES('religion','道教');
INSERT INTO dict(type,value) VALUES('religion','伊斯兰教');
INSERT INTO dict(type,value) VALUES('religion','基督教');
INSERT INTO dict(type,value) VALUES('religion','天主教');
INSERT INTO dict(type,value) VALUES('religion','儒家门徒');
INSERT INTO dict(type,value) VALUES('religion','不可知论者');
INSERT INTO dict(type,value) VALUES('religion','其他宗教');

INSERT INTO dict(type,value) VALUES('job_time','有双休');
INSERT INTO dict(type,value) VALUES('job_time','工作忙碌');
INSERT INTO dict(type,value) VALUES('job_time','工作清闲');
INSERT INTO dict(type,value) VALUES('job_time','自由工作出差');
INSERT INTO dict(type,value) VALUES('job_time','经常出差');

INSERT INTO dict(type,value) VALUES('love_history','初恋还在');
INSERT INTO dict(type,value) VALUES('love_history','谈过3次以内恋爱');
INSERT INTO dict(type,value) VALUES('love_history','情场高手');

INSERT INTO dict(type,value) VALUES('marry_time','认同闪婚');
INSERT INTO dict(type,value) VALUES('marry_time','一年内');
INSERT INTO dict(type,value) VALUES('marry_time','两年内');
INSERT INTO dict(type,value) VALUES('marry_time','三年内');
INSERT INTO dict(type,value) VALUES('marry_time','时机成熟就结婚');

INSERT INTO dict(type,value) VALUES('parent_status','父母均健在');
INSERT INTO dict(type,value) VALUES('parent_status','只有母亲健在');
INSERT INTO dict(type,value) VALUES('parent_status','只有父亲健在');
INSERT INTO dict(type,value) VALUES('parent_status','父母均已离世');

INSERT INTO dict(type,value) VALUES('bro_and_sis','独生子女');
INSERT INTO dict(type,value) VALUES('bro_and_sis','2');
INSERT INTO dict(type,value) VALUES('bro_and_sis','3');
INSERT INTO dict(type,value) VALUES('bro_and_sis','4');
INSERT INTO dict(type,value) VALUES('bro_and_sis','更多');

INSERT INTO dict(type,value) VALUES('com_reason','违法信息');
INSERT INTO dict(type,value) VALUES('com_reason','有害信息');
INSERT INTO dict(type,value) VALUES('com_reason','人身攻击我');

commit;