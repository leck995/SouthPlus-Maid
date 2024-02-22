# 一、简介
South-Plus Maid是一个小工具，主要适用于某N+网站的音声区，实现该区贴子的图片化预览功能（图片实在是不好放，简单做了点处理，实际UI更漂亮）。软件使用JavaFX进行开发，支持Windows,Linux,Mac等使用，目前仅提供Windows版，有其他版本需求可以联系我。欢迎加群221915393。
本项目仅供学习交流使用，请在下载后24小时内删除。
最后，在线接单，如果你有需要定制的软件，欢迎联系我。
![image.png](https://cdn.nlark.com/yuque/0/2024/png/22760263/1705184696589-5335320c-ac83-4d7c-9701-9b33a4e3dd82.png#averageHue=%235b576a&clientId=ube57d394-f0c8-4&from=paste&height=989&id=u50fcd0dc&originHeight=989&originWidth=1586&originalType=binary&ratio=1&rotation=0&showTitle=false&size=172481&status=done&style=none&taskId=ud7852a5d-d83e-47ca-9292-597a6350969&title=&width=1586)
# 二、使用说明
## 约定大于配置
这是一款简单的软件，我不会在代码中做过多的异常检测，以及各种防呆设计，只要符合相关要求，则会有良好的体验。
该程序没有设置界面，所有的设置的实现均在软件目录setting.json中，这视为软件使用的门槛，内容格式如下。

请注意，由于内置浏览器出现严重BUG，现已取消内置浏览器，故BROWSER_TYPE暂时废弃，默认值false，不要修改该值。

```json
{
  "WIDTH" : "1600",
  "HEIGHT" : "1000",
  "SOUTH_PLUS_HOST" : "https://www.xxxxx-xxxx.net",
  "COOKIE" : "N+用户Cookie，使用前必须填写,两侧双引号不可少",
  "USER_AGENT" "浏览器UA，使用前必须填写,两侧双引号不可少":
  "PROXY" : true,
  "PROXY_HOST" : "127.0.0.1",
  "PROXY_PORT" : 7890,
  "DLSITE_HOST" : "https://www.xxxxxx.com",
  "SKIP_BBS_ITEM_SIZE" : 4,
  "BBS_ITEM_SHOW_MODEL" : 0,
  "BBS_ITEM_BIG_WIDTH" : 320.0,
  "BROWSER_TYPE" : false,
  "NET_DISKS" : [ {
    "name" : "度盘",
    "keywords" : [ "度盘", "百度", "bd" ]
  }, {
    "name" : "夸克",
    "keywords" : [ "夸克" ]
  }, {
    "name" : "OneDrive",
    "keywords" : [ "OneDrive", "od" ]
  } ]
}

```
其中各种相关设置说明如下

| 关键字 | 说明 |  
| --- | --- | 
| WIDTH | 窗口宽度 |  
| HEIGHT | 窗口高度 |  
| SOUTH_PLUS_HOST | N+网址，使用前必须填写,两侧双引号不可少 |  
| COOKIE | N+用户Cookie，使用前必须填写,两侧双引号不可少 |  
| USER_AGENT | 获取登录COOKIE的浏览器的USER_AGENT| 
| PROXY | 开启代理，true开启，false关闭 |  
| PROXY_HOST | 代理网址 |  
| PROXY_PORT | 代理端口 |  
| DLSITE_HOST | DL网址，使用前必须填写,两侧双引号不可少 |  
| SKIP_BBS_ITEM_SIZE | 跳过N+论坛开头的帖子数量,置顶帖之类的 |  
| BBS_ITEM_SHOW_MODEL | 默认论坛帖子显示模式，0是详情模式，1是简略模式，上图显示的是简略模式 |  
| BBS_ITEM_BIG_WIDTH | 详情模式子项的宽度 |  
| BROWSER_TYPE | 默认false,请不要修改此项，暂时废弃。 |  
| NET_DISKS | 这里用于识别网盘，下文详细说明 |  

### 关于Cookie
软件必须设置Cookie后方可使用，N+Cookie的获取参考[链接](https://blog.csdn.net/u011781521/article/details/87791125)。
### 关于NET_DISKS
该配置格式如下，name代表的是网盘名称，会在程序中显示；keywords代表网盘的简称，比如百度网盘，有人都简称为度盘，还有人简称为bd，如果有拼音爱好者，说不定会简称为dupan，只要在此处填写相应的简称，即可以识别到该帖子使用了百度网盘。keywords不区分大小写。
同样，如果有其他网盘，也可以按格式添加。
```json
 "NET_DISKS" : [ {
    "name" : "度盘",
    "keywords" : [ "度盘", "百度", "bd" ]
  }, {
    "name" : "夸克",
    "keywords" : [ "夸克" ]
  }]

```
```json
  "NET_DISKS" : [ {
    "name" : "度盘",
    "keywords" : [ "度盘", "百度", "bd" ]
  }, {
    "name" : "夸克",
    "keywords" : [ "夸克" ]
  }, {
    "name" : "OneDrive",
    "keywords" : [ "OneDrive", "od" ]
  } ]

```

# 三、感谢以下开源项目

1. [MvvmFX](https://github.com/sialcasa/mvvmFX)
2. [Atlantafx](https://github.com/mkpaz/atlantafx)
3. [Ikonli](https://kordamp.org/ikonli/)
4. [Jackson](https://github.com/FasterXML/jackson)
5. [Jsoup](https://github.com/jhy/jsoup)
6. [Openjfx](https://openjfx.io/)
# 四、为爱发电项目，永久免费
这是我发布的第三款关于音声的软件，倘若本程序对你有所帮助，欢迎请开发者补充一瓶营养快线。
![image.png](https://cdn.nlark.com/yuque/0/2023/png/22760263/1681029296778-da485328-b0a6-4852-97c6-4b09816c3f29.png#averageHue=%23d2d2d2&clientId=u3647d7ac-bba3-4&from=paste&height=191&id=u17a2bc01&originHeight=191&originWidth=553&originalType=binary&ratio=1&rotation=0&showTitle=false&size=53464&status=done&style=none&taskId=ud99c7c33-ac37-4d91-b549-bfddd66f295&title=&width=553)



