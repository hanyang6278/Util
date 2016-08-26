# Util --- Android开发工具

gradle依赖

```
  compile 'cn.alien95:util:1.1.1'
```

## 使用方法

### 初始化

>开发中常用的一些方法（如：Log，Toast，SnackBar，getScreenWidth，dip2px等等）。
注意：Log的打印应该在Debug模式下。建议这样使用：   

```
  Utils.initialize(this);
      if(BuildConfig.DEBUG){
          Utils.setDebug(true,"Debug");
      }
```

### 图片工具
>从相机或者相册选取图片
```
ImageUtil.getImageFromAlbum(UploadFileActivity.this);
ImageUtil.getImageFromCamera(UploadFileActivity.this);
```

### TimeTransform 时间戳格式转换类

>在Android开发中常常会用到时间戳转换成容易识别的格式，并且大部分时候都是以毫秒为单位，当然这里也是以毫秒为单位的时间戳。 
如果开发中使用秒为单位，x1000就好了。  

### SqlHelper 数据库辅助类

>数据库也是开发中必不可少的。SqlHelper类提供了表的创建，根据APP版本的更新自动升级表。  
   
```
  SqlHelper.init(this, "AARecord");  //初始化数据库
  SqlHelper.getInstance().addTable(API.TODO_TABLE_NAME, API.TODO_TABLE_SQL);  //添加数据库表
```

### EventAwake 消息通信类，观察者模式

 - 事件的订阅

```
  //注意这里注册的method必须是public，防止在其他类中调用时抛异常

  //没有参数的方法
  public void registerEvent(Object object, Method method)

  //带有参数的方法
  public void registerEvent(Object object, Method method, Object[] args)

```

 - 唤醒事件

```
  public void notifyChange()
```

 - 注销 --- 一定要注销，防止内存泄露
```
public void unRegisterEvent(Object object)
```