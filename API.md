<h1>API</h1>

- [用户（user）](#用户user)
  - [注册](#注册)
  - [登陆](#登陆)
- [动态（post）](#动态post)
  - [获取首页动态列表](#获取首页动态列表)
  - [获取某个人的动态](#获取某个人的动态)
  - [发布新动态](#发布新动态)
  - [删除某条动态](#删除某条动态)
  - [单独获取某个动态信息](#单独获取某个动态信息)
- [通知（notice）](#通知notice)
  - [获取某个用户的所有通知](#获取某个用户的所有通知)
  - [将某个通知变为已读](#将某个通知变为已读)
- [图片（picture）](#图片picture)
  - [上传一个新图片](#上传一个新图片)
  - [图片地址](#图片地址)
- [评论（comment）](#评论comment)
  - [增加一个评论](#增加一个评论)
  - [查看一个动态下的所有评论](#查看一个动态下的所有评论)
  - [删除某条评论](#删除某条评论)
- [点赞](#点赞)
  - [点赞一个动态（取消的话再传一次就好）](#点赞一个动态取消的话再传一次就好)
  - [查看一个动态下的点赞数](#查看一个动态下的点赞数)

## 用户（user）

### 注册

POST: /api/user/register

用户名不许重复，密码最少6位。这两个参数都是必填项。

```json
{
    "name":"<string>",      // 用户名
    "password":"<string>"   // 密码
} 
```

请求成功会返回一个唯一授权凭证（Bearer Token）。

```json
{
    "data":{
        "token":"<string>", // 唯一授权凭证token
    },
    "code":0,
    "msg":"success"
}
```

### 登陆

POST: /api/user/login

```json
{
    "name":"<string>",      // 用户名
    "password":"<string>"   // 密码
} 
```

请求成功会返回一个唯一授权凭证（Bearer Token）。

```json
{
    "data":{
        "token":"<string>", // 唯一授权凭证token
    },
    "code":0,
    "msg":"success"
}
```

## 动态（post）

### 获取首页动态图片列表

GET: /api/post/hello（带token）

```json
{
    "data":[
        {
            "id":"<string>",
            "content":"<string>",
            "userId":"<string>",
            "pictures":[
                "/api/picture/<pictureId1>",
                "/api/picture/<pictureId2>",
                "/api/picture/<pictureId3>",
                "/api/picture/<pictureId4>"
            ],
            "date":"<number>"
        },
        {
            "id":"<string>",
            "content":"<string>",
            "userId":"<string>",
            "pictures":[
                "/api/picture/<pictureId1>",
                "/api/picture/<pictureId2>"
            ],
            "date":"<number>"
        },
        ...... // 略
    ],
}
```

### 获取某个人的动态

GET: /api/post/profile（带token）

```json
{
    "userId":"<string>"
}
```

**返回的响应数据为与首页的一致。**

### 发布新动态

POST: /api/post（带token）

```json
{
    "content":"<string>",
    "pictures":[
        "/api/picture/<pictureId1>",
        "/api/picture/<pictureId2>",
        "/api/picture/<pictureId3>",
        "/api/picture/<pictureId4>"
    ]
}
```

返回的响应数据为：

```json
{
    "data":{
        "id":"<string>" // 发布动态的id
    },
    "code":"0",
    "msg":"success"
}
```

### 删除某条动态

DELETE: /api/post（带token）

```json
{
    "id":"<string>"
}
```

### 单独获取某个动态信息

GET: /api/post/:id （带token，注意这里的参数id）

返回的响应数据为：

```json
{
    "data":{
        "id":"<string>",
        "content":"<string>",
        "userId":"<string>",
        "pictures":[
            "/api/picture/<pictureId1>",
            "/api/picture/<pictureId2>"
        ],
        "date":"<number>"
    },
    "code":0,
    "msg":"success"
}
```

## 通知（notice）

### 获取某个用户的所有通知

GET: /api/notice/all （带token）


请求成功会返回通知列表，请注意，`data` 里是一个列表

```json
{
    "data":[
        {
            "id":"<string>",
            "sender":"Therainisme",
            "receiver":"<string>",    // 被通知者的id
            "content":"点赞了你的图片", 
            "postId":"<string>",               
            "read":true,
            "date":"<number>"
        },
        {
            "id":"<string>",
            "sender":"GaiZi",
            "receiver":"<string>",    // 被通知者的id
            "content":"评论了你的图片",
            "postId":"<string>",
            "read":false,
            "date":"<number>"
        },
        ... // 略
    ],
    "code":0,
    "msg":"success"
}
```

### 将某个通知变为已读

POST: /api/notice/read（带token）

请求带上token后只需要一个需要变更的通知id。

```json
{
    "id":"<string>"
}
```

## 图片（picture）

先试试转成base64再传过来。

### 上传一个新图片

POST: /api/picture（带token）

```json
{
    "picture":file
}
```

这里返回图片的id，图片本身存在文件目录里

```json
{
    "data":{
        "id":"<string>",     // 图片的id
    },
    "code":"0",
    "msg":"success"
}
```

### 图片地址

GET：/api/picture/:id（带token，注意这里有一个参数`id`）

```json
{
    "data":{
        "id":"<string>",     // 图片的id
    },
    "code":"0",
    "msg":"success"
}
```

## 评论（comment）

### 增加一个评论

POST：/api/comment（带token）

```json
{
    "postId":"<string>",
    "content":"<string>"
}
```

返回的响应数据为：

```json
{
    "data":{
        "id":"<string>",
        "postId":"<string>",
        "userId":"<string>",
        "content":"<string>",
        "date":"<string>"
    },
    "code":0,
    "msg":"success"
}
```

### 查看一个动态下的所有评论

GET：/api/comment/list（带token）

```json
{
    "postId":"<string>"
}
```

返回的响应数据为：

```json
{
    "data":[
        {
            "id":"<string>",
            "content":"<string>",
            "userId":"<string>",
            "postId":"<string>",
            "date":"<number>"
        },
        {
            "id":"<string>",
            "content":"<string>",
            "userId":"<string>",
            "postId":"<string>",
            "date":"<number>"
        },
        ...... // 略
    ],
    "code":0,
    "msg":"success"
}
```

### 删除某条评论

DELETE: /api/comment（带token）

```json
{
    "id":"<string>"
}
```

## 点赞

### 点赞一个动态

取消的话再传一次。

POST：/api/star（带token）

```json
{
    "postId":"<string>",
    "userId":"<string>"
}
```

返回的响应数据为：

```json
{
    "data":{
        "id":"<string>",
        "postId":"<string>",
        "userId":"<string>"
    },
    "code":0,
    "msg":"success"
}
```

### 查看一个动态下的点赞数

GET：/api/star/count（带token）

```json
{
    "postId":"<string>"
}
```

返回的响应数据为：

```json
{
    "data":{
        "count":"<number>"
    },
    "code":0,
    "msg":"success"
}
```