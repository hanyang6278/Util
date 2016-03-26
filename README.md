## Utils--Android开发中一些常用操作

  gradle依赖

        compile 'cn.alien95:util:1.0.6'

- 开发中常用的一些方法（如：Log，Toast，SnackBar，getScreenWidth,dip2px）。
  注意：Log的打印应该在Debug模式下。建议这样使用：   

        Utils.initialize(this);
                if(BuildConfig.DEBUG){
                    Utils.setDebug(true,"Debug");
                }

- TimeTransform 时间戳格式转换类  

  在Android开发中常常会用到时间戳转换成容易识别的格式，并且大部分时候都是以毫秒为单位，当然这里也是以毫秒为单位的时间戳。 
  如果开发中使用秒为单位，x1000就好了。  

- SqlHelper 数据库辅助类  

  数据库也是开发中必不可少的。SqlHelper类提供了表的创建，根据APP版本的更新自动升级表。  

  添加表的创建：   
        
        addTable(TableName tableName, String sql) 


- MessageNotify 消息通信类，观察者模式  

  事件的订阅:  

        //注意这里注册的method必须是public，防止在其他类中调用时抛异常
        public void registerEvent(Object object, Method method)

  唤醒事件：  

        public void sendMessage() 
