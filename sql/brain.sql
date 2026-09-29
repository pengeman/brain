-- ----------------------------
-- 智域大脑(智力游戏)模块
-- 用途:脑力游戏成绩表 + 系统菜单
-- 执行:mysql -upeng -ppeng ruoyi < brain.sql
-- ----------------------------

-- ----------------------------
-- 1. 脑力游戏成绩表
-- ----------------------------
drop table if exists brain_test_result;
create table brain_test_result (
  id           bigint(20)   not null auto_increment comment '主键ID',
  user_id      bigint(20)   not null                 comment '用户ID',
  game_type    varchar(32)  not null                 comment '游戏类型(schulte/memory/match/pattern/reaction/sudoku)',
  test_type    varchar(64)  default ''               comment '测试类型名称(专注力/记忆力/逻辑力/反应力)',
  score        int(11)      default 0                comment '得分',
  duration     int(11)      default 0                comment '用时(秒)',
  level        int(4)       default 1                comment '难度等级',
  extra        varchar(500) default ''               comment '扩展信息(JSON,如失误次数)',
  test_time    datetime     default null             comment '测试时间',
  create_time  datetime     default null             comment '创建时间',
  update_time  datetime     default null             comment '更新时间',
  primary key (id),
  key idx_user_game (user_id, game_type),
  key idx_test_time (test_time)
) engine=innodb auto_increment=1 comment = '脑力游戏成绩表';

-- ----------------------------
-- 2. 系统菜单:智域大脑目录 + 游戏大厅
-- ----------------------------
insert into sys_menu values('2100', '智域大脑', '0', '3', '#', '', 'M', '0', '1', '', 'fa fa-graduation-cap', 'admin', sysdate(), '', null, '智域大脑目录');
insert into sys_menu values('2101', '游戏大厅', '2100', '1', '/index', '', 'C', '0', '1', 'brain:game:view', 'fa fa-gamepad', 'admin', sysdate(), '', null, '智力游戏大厅');
