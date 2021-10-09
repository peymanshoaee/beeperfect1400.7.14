
INSERT INTO bp_user(id,username,password,token,first_name,last_name,nick_name,url,verify_code,image,enabled,business,tos) VALUES (nextval('bp_user_id_seq'),'peyman@gmail.com','1234',null ,'peyman','shoaee','pepe','','',null ,true,true,true);
INSERT INTO bp_status(id, code, description) VALUES(1, '0', 'موفق');
INSERT INTO bp_status(id, code, description) VALUES(2, '400', 'درخواست نامعتبر');
INSERT INTO bp_status(id, code, description) VALUES(3, '900', 'فایل ارسالی دارای مشکل میباشد');
INSERT INTO bp_status(id, code, description) VALUES(4, '1', 'نام کاربری تکراری است');
INSERT INTO bp_status(id, code, description) VALUES(5, '2', 'کاربر یافت نشد');
INSERT INTO bp_status(id, code, description) VALUES(6, '3', 'پسورد اجباری است');
INSERT INTO bp_status(id, code, description) VALUES(7, '4', 'کد وارد شده اشتباه میباشد');
INSERT INTO bp_status(id, code, description) VALUES(8, '5', 'قوانین سایت اجباری است');
INSERT INTO bp_status(id, code, description) VALUES(9, '6', 'عنوان درخواست اجباری است');
INSERT INTO bp_status(id, code, description) VALUES(10, '7', 'درخواست یافت نشد');
INSERT INTO bp_status(id, code, description) VALUES(11, '8', 'دستبه بندی یافت نشد');
INSERT INTO bp_status(id, code, description) VALUES(12, '9', 'کاربر دسترسی ندارد');


INSERT INTO bp_category(id, code, title) VALUES(1, '1', 'تشریفات');
INSERT INTO bp_category(id, code, title) VALUES(2, '2', 'آرایشی بهداشتی');
