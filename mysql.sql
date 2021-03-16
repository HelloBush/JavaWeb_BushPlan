CREATE DATABASE plan;
==============
CREATE TABLE user(
u_name char(12) NOT NULL,
u_pwd char(18) NOT NULL,
u_email char(25) NULL,
point int DEFAULT 0,
PRIMARY KEY(u_name)
);
=======================
CREATE TABLE dayplan(
u_name char(12),
d_num date,
d_plan varchar(100) NOT NULL,
d_sum varchar(100) NULL,
d_point int DEFAULT 0,
PRIMARY KEY(u_name,d_num),
FOREIGN KEY(u_name) REFERENCES user(u_name)
);
===========================
CREATE TABLE monthplan(
u_name char(12),
m_num int,
m int,
m_plan varchar(100) NOT NULL,
m_sum varchar(100) NULL,
m_point int DEFAULT 0,
PRIMARY KEY(u_name,m_num,m),
FOREIGN KEY(u_name) REFERENCES user(u_name)
);
===========================
CREATE TABLE phaseplan(
u_name char(12),
p_num char(5),
p_begin date NOT NULL,
p_end date NOT NULL,
p_plan varchar(100) NOT NULL,
p_sum varchar(100) NULL,
p_point int DEFAULT 0,
PRIMARY KEY(u_name,p_num),
FOREIGN KEY(u_name) REFERENCES user(u_name)
);
========================================================================
#以下是实现各增删改查功能的函数
========================================================================
-- 用户的注册添加
DELIMITER $
CREATE PROCEDURE add_user(IN u_name char(12),IN u_pwd char(18),IN u_email char(25))
BEGIN 
INSERT INTO user(u_name,u_pwd,u_email) VALUES(u_name,u_pwd,u_email);
END$

CALL add_user('Test','123456','123@qq.com');
-- 日目标的添加
DELIMITER $
CREATE PROCEDURE add_dp(IN u_name char(12),IN d_num date,IN d_plan varchar(100))
BEGIN
INSERT INTO dayplan(u_name,d_num,d_plan) VALUES(u_name,d_num,d_plan);
END$

CALL add_dp('guo','2020/3/4','这是第二个日目标内容');
-- 日目标完成的提交
DELIMITER $
CREATE PROCEDURE sub_dp(IN d_sum varchar(100),IN d_point int,IN u_name char(12),IN d_num date)
BEGIN
UPDATE dayplan dp SET dp.d_sum=d_sum,dp.d_point=d_point 
WHERE (dp.u_name=u_name) AND (dp.d_num=d_num);
SET @o_point=(SELECT point FROM user WHERE user.u_name=u_name);
UPDATE user SET user.point=@o_point+d_point WHERE user.u_name=u_name;
END$

CALL sub_dp('完成3.4',10,'guo','2020-3-4');
-- 月目标的添加
输入用户名、月份、月目标内容
存储用户名、月目标序号(月份-序号)、月目标内容
DELIMITER $
CREATE PROCEDURE add_mp(IN u_name char(12),IN m int,IN m_plan varchar(100))
BEGIN
SET @m_num=(SELECT count(*) FROM monthplan WHERE monthplan.u_name=u_name AND monthplan.m=m)+1;
INSERT INTO monthplan(u_name,m_num,m,m_plan) VALUES(u_name,@m_num,m,m_plan);
END$

CALL add_mp('guo',3,'这是三月目标检测序号是否正确！');
CALL add_mp('guo',1,'这是一月目标检测序号是否正确！');
-- 月目标的修改
DELIMITER $
CREATE PROCEDURE upd_mp(IN u_name char(12),IN m int,In m_num int,IN m_new  int,IN m_plan varchar(100))
BEGIN
IF m=m_new THEN
SET @m_num=m_num;
ELSE SET @m_num=(SELECT count(*) FROM monthplan WHERE monthplan.u_name=u_name AND monthplan.m=m_new)+1;
END IF;
UPDATE 	monthplan mp SET mp.m=m_new,mp.m_num=@m_num,m_plan=m_plan WHERE mp.u_name=u_name AND mp.m=m AND  mp.m_num=m_num;
END$ 

CALL upd_mp('guo',1,3,1,'一月3改一月');
-- 月目标完成的提交
DELIMITER $
CREATE PROCEDURE sub_mp(IN u_name char(12),IN m int,IN m_num int,IN m_sum varchar(100),IN m_point int)
BEGIN
UPDATE monthplan mp SET mp.m_sum=m_sum,mp.m_point=m_point
WHERE mp.u_name=u_name AND mp.m=m AND mp.m_num=m_num;
SET @o_point=(SELECT point FROM user WHERE user.u_name=u_name);
UPDATE user SET user.point=@o_point+m_point WHERE user.u_name=u_name;
END$

CALL sub_mp('guo','1','2','提交一月第二个月目标！',20);
-- 阶段目标的添加
DELIMITER $
CREATE PROCEDURE add_pp(IN u_name char(12),IN p_begin date,IN p_end date,IN p_plan varchar(100))
BEGIN
IF (SELECT COUNT(*) FROM phaseplan WHERE phaseplan.u_name=u_name)=0 THEN
SET @p_num=1;
ELSE SET @p_num=((SELECT MAX(p_num) FROM phaseplan WHERE phaseplan.u_name=u_name)+1);
END IF;
INSERT INTO phaseplan(u_name,p_num,p_begin,p_end,p_plan) VALUES(u_name,@p_num,p_begin,p_end,p_plan);
END$

CALL add_pp('guo','2020-3-1','2020-4-5','第四个阶段性目标！');
-- 阶段目标的修改
DELIMITER $
CREATE PROCEDURE upd_pp(IN u_name char(12),IN p_num char(5),IN d1_new date,IN d2_new date,IN p_new varchar(100))
BEGIN
UPDATE phaseplan pp SET pp.p_begin=d1_new,pp.p_end=d2_new,pp.p_plan=p_new
WHERE pp.u_name=u_name AND pp.p_num=p_num;
END$

CALL upd_pp('guo','2','1999-7-8','1999-9-18','阶段修改测试');
-- 阶段目标完成的提交
DELIMITER $
CREATE PROCEDURE sub_pp(IN u_name char(12),IN p_num char(5),IN p_sum varchar(100),IN p_point int)
BEGIN
UPDATE phaseplan pp SET pp.p_sum=p_sum,pp.p_point=p_point
WHERE pp.u_name=u_name AND pp.p_num=p_num;
SET @o_point=(SELECT point FROM user WHERE user.u_name=u_name);
UPDATE user SET user.point=@o_point+p_point WHERE user.u_name=u_name;
END$

CALL sub_pp('guo','1','第一个范围目标完成！',100);
-- 总完成目标个数的查询，利用0分鉴别
DELIMITER $
CREATE PROCEDURE count_aim(IN u_name char(12))
BEGIN
SET @count_d=(SELECT COUNT(*) FROM dayplan dp WHERE dp.u_name=u_name AND dp.d_point!=0);
SET @count_m=(SELECT COUNT(*) FROM monthplan mp WHERE mp.u_name=u_name AND mp.m_point!=0);
SET @count_p=(SELECT COUNT(*) FROM phaseplan pp WHERE pp.u_name=u_name AND pp.p_point!=0);
SELECT (@count_d+@count_m+@count_p);
END$

CALL count_aim('guo');

SELECT COUNT(*) FROM dayplan dp WHERE dp.u_name='guo' AND dp.d_point!=0;
SELECT COUNT(*) FROM monthplan mp WHERE mp.u_name='guo' AND mp.m_point!=0;
SELECT COUNT(*) FROM phaseplan pp WHERE pp.u_name='guo' AND pp.p_point!=0;