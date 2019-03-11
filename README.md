# lq186-oauth2
基于SpringBoot的Oauth2授权示例


##### 用户名密码模式
http://127.0.0.1:8080/oauth/token?username=admin&password=123456&grant_type=password&scope=read&client_id=3aa1f466-c67d-4f72-a8a8-62ed94d7d638&client_secret=123456

返回数据示例：
``` json
{
    "code": 0,
    "data": {
        "access_token": "f6acd0e4-5809-4ec1-ac60-2a7733959620",
        "token_type": "bearer",
        "refresh_token": "c98d829c-6bb5-4044-b69f-7aecbf1a7ed5",
        "expires_in": 7189,
        "scope": "read"
    }
}
```

##### 授权码模式

1) 请求授权码

http://127.0.0.1:8080/oauth/authorize?response_type=code&client_id=3aa1f466-c67d-4f72-a8a8-62ed94d7d638&redirect_uri=http://www.baidu.com/&scope=read&state=09

2) 会跳转至登录页面

http://127.0.0.1:8080/login

3) 登录后跳转到重定向页面，并且附加上code和state

https://www.baidu.com/?code=MUrvG5&state=09

4) 根据授权码获取access_token

http://127.0.0.1:8080/oauth/token?grant_type=authorization_code&scope=read&client_id=3aa1f466-c67d-4f72-a8a8-62ed94d7d638&client_secret=123456&code=MUrvG5&redirect_uri=http://www.baidu.com/

返回数据示例：
``` json
{
    "code": 0,
    "data": {
        "access_token": "f6acd0e4-5809-4ec1-ac60-2a7733959620",
        "token_type": "bearer",
        "refresh_token": "c98d829c-6bb5-4044-b69f-7aecbf1a7ed5",
        "expires_in": 7189,
        "scope": "read"
    }
}
```


##### 客户端认证模式

http://127.0.0.1:8080/oauth/token?grant_type=client_credentials&scope=read&client_id=apply&client_secret=123456

返回数据示例：
``` json
{
    "code": 0,
    "data": {
        "access_token": "cf5ca68b-fdba-4e78-a2fd-994b0149516e",
        "token_type": "bearer",
        "expires_in": 7186,
        "scope": "read"
    }
}
```

##### 简化模式

1) 请求Token

http://127.0.0.1:8080/oauth/authorize?response_type=token&client_id=3aa1f466-c67d-4f72-a8a8-62ed94d7d638&redirect_uri=http://www.baidu.com/&scope=read&state=09

2) 会跳转至登录页面

http://127.0.0.1:8080/login

3) 登录后跳转到重定向页面并带上token

https://www.baidu.com/#access_token=0f973db4-f780-4e24-957b-33784198dfb2&token_type=bearer&state=09&expires_in=6222

##### 刷新Token

http://127.0.0.1:8080/oauth/token?grant_type=refresh_token&refresh_token=95a03a9e-bc4f-494a-a752-023594e3cce5&client_id=3aa1f466-c67d-4f72-a8a8-62ed94d7d638&client_secret=123456

返回数据示例：
```json
{
    "code": 0,
    "data": {
        "access_token": "f6acd0e4-5809-4ec1-ac60-2a7733959620",
        "token_type": "bearer",
        "refresh_token": "c98d829c-6bb5-4044-b69f-7aecbf1a7ed5",
        "expires_in": 7189,
        "scope": "read"
    }
}
```



