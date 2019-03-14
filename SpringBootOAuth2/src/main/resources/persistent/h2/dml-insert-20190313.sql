-- 客户端信息
insert into oauth2_client(id, bzid, client_id, client_secret, kwm_name, logo, kwm_state, grant_type, kwm_scope, redirect_url, created_time)
values(1, '8559112fc38048b7a081897b6cfa359a', '92498c196acc4c74a346f0235c3b9c4c', '{bcrypt}$2a$10$uUQbQS3fAV3jjfCiRulm7.ses2bfOVV5dizbfSrEbwj.5F4dGzHHq', '我的OAuth2应用', '/images/oauth2/client/logo/8559112fc38048b7a081897b6cfa359a.jpg',
0, 'authorization_code,client_credentials,password,implicit,refresh_token','read,write', 'http://127.0.0.1:9080/oauth2', 1552459147000);

-- 用户信息
insert into oauth2_user(id, bzid, kwm_username, kwm_pwd, show_name, head_picture, kwm_state, created_time, login_ip, login_time)
values(1, '8559112fc38048b7a081897b6cfa359a', 'test_user', '{bcrypt}$2a$10$uUQbQS3fAV3jjfCiRulm7.ses2bfOVV5dizbfSrEbwj.5F4dGzHHq', '测试用户A', '/images/oauth2/user/head_picture/8559112fc38048b7a081897b6cfa359a.jpg',
0, 1552459147000, '', 1552459147000);