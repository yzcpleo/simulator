# simulator
上海华信证券银行模拟器2.0

## 改版内容
### 0. 系统上的改变
上一版中，以为项目很轻量，所以使用了小清新`Spring Boot`，随着需求的增长，需要Spring Boot结合一些五花八门的东西。  
这就给开发模拟器的测试妹子造成了暴击伤害，所以本版采用了比较稳妥的经典的web框架。

### 1. 表设计的改进
- 第一版中的`错误码`跟`交易`挂钩的，这是不合理的，本版中的`错误码`是和`银行通道`挂钩的。  
- 新增了`流水日志表`，里面有`请求报文`和`响应报文`, 可供测试人员追查问题。  
- `交易流水表`新增了一些字段比如:`协议号` `出款方账户`等，还新增了`4个备用字段`。  
- 为了`提高查询速度`，新增了一些关键的`索引`。
- 为了`减少关联查询`，还采用了`冗余字段`的方法。
- 干掉了没有什么卵用的银行编号，`减少维护成本`，只保留银行代码和名称。
- 上一版中，由于没去维护防重复提交，加上网络有点卡，导致重复录入，最终查询出错，所以这一版中加强了`唯一约束`。

### 2. 功能上的改进
- `统一的配置界面`。新增银行通道时， 不需要再去配置一堆乱七八糟的东西。
- `导入错误码`。本版中可以下载`导入模板`，然后可以`批量导入`excel种的错误码。当然也保留了单个错误码的crud。
- `自定义交易类型`。上一版中如果要添加新的交易类型，还需要改代码，本版则不需要了，可以任意的添加交易类型。
- `优化对账文件`。本版中的对账文件，可以定时生成，定时推送，手动生成，批量生成，批量推送。
- `定时任务把交易中的变为成功或失败`。上版中，如果配置了交易处理中，过会再来查询时，交易还是处理中，那就需要手动改库了，因此本版提供了定时任务来处理。

### 3. 技术上的改进
- `redis缓存`。aop结合redis实现`缓存智能管理`，不需要人工介入清除缓存，但也做了缓存管理的功能。
- `防重复提交`。前端和后台以及表约束都做了防重复限制。

### 4. 用户体验的改进
- `单页面`。本版中使用了目前比较流行的单页面模式。
- `持久会话`。shiro结合redis实现用户的会话信息持久化，即便重启服务器，也不用重新登录。

> 改版后可能会造成一些银行通道的不稳定，如果遇到请及时通知康永敢，谢谢配合。

# 迁移记录
## 1. 把`民生T+0`从老系统迁移到新系统中
- 迁移时间: 2017年01月11日21:40:58
- 迁移者: 康永敢
- 迁移地点: 家中
- 迁移内容: 民生T+0`实时代付`、`单笔查询`、`对账文件生成`、`对账文件推送`


