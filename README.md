## MRouter(Android Modularization Router)

[![license](https://img.shields.io/badge/license-apache-brightgreen.svg?style=flat)](https://github.com/chiclaim/MRouter/blob/master/LICENSE)
[![Release Version](https://img.shields.io/badge/release-1.0.6-red.svg)](https://github.com/chiclaim/MRouter)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/chiclaim/MRouter/pulls)


## 主要功能

- 支持不同模块之间的页面跳转

- 支持自动注入传递过来的参数

- 支持简单的依赖注入(用作注入其他 module 的业务类或者 Fragment)

- 支持对 Activity 的管理

- 使用过程中更友好的错误提示，提升开发效率



## 如何使用

### 1. 添加依赖配置
```
在工程的根目录 build.gradle 添加如下配置：

buildscript {
    dependencies {
        classpath "io.github.chiclaim:router-gradle-plugin:1.0.5"
    }
}

//在每个使用了 MRouter 的组件工程中的 build.gradle 添加如下配置：

android {
    javaCompileOptions {
        annotationProcessorOptions {
            arguments = [moduleName: project.getName()]
        }
    }
}

dependencies {
    // 项目没有使用Kotlin，改成 annotationProcessor
    kapt "io.github.chiclaim:router-compiler:1.0.6"
    implementation "io.github.chiclaim:router:1.0.6"

}

//如果使用了组件，需要在用到 MRouter 的模块下添加如下配置，这样才能成功生成代码（模块化使用 APT 工具生成代码都需要如此）

annotationProcessor "io.github.chiclaim:router-compiler:1.0.6"

```


### 2. API 使用

- **框架的初始化操作**

    ```
    public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            MRouter.getInstance().init(this);//初始化操作
        }
    }
    ```


- **使用 `@Route` 注解告知框架哪些 Activity 交给框架管理**

    ```
    @Route(path = "/app/main")//path就是路由的路径
    public class MainActivity extends AppCompatActivity {
        //...
    }
    ```

- **界面跳转和参数传递**

    ```
    MRouter.getInstance()
                    .build("xxx/main")//build方法参数就是目标界面的路由路径
                    .putSerializable("user", user)//传递参数
                    .putParcelable("address", address)//传递参数
                    .navigation(this);
    ```

- **支持的参数类型和 Intent 参数传递的类型一致**
    
    - putBoolean(name, boolean)
    - putBooleanArray(name , boolean[])
    - putByte(name, byte)
    - putByteArray(name, byte[])
    - putChar(name, char)
    - putCharArray(name, char[])
    - putShort(name, short)
    - putShortArray(name, short[])
    - putFloat(name, float)
    - putFloatArray(name, float[])
    - putInt(name, int)
    - putIntArray(name, int[])
    - putIntList(name, ArrayList<Integer>)
    - putDouble(name, double)
    - putDoubleArray(name, double[])
    - putLong(name, long)
    - putLongArray(name, long[])
    - putString(name, String)
    - putStringArray(name, String[])
    - putStringList(name, ArrayList<String>)
    - putParcelable(name, Parcelable)
    - putParcelableArray(name, Parcelable[])
    - putParcelableList(name, ArrayList<Parcelable>)
    - putSerializable(name, Serialization)
    - putExtras(Bundle extras)

- **使用 @Autowired 注解完成在 Activity 和 Fragment 中自动注入参数**

    `第一步`：上一个界面传过来的参数在当前类中声明相应的属性，在该属性上加上 `@Autowired` 注解，name的值就是参数的key，如：
    
    ```

    // 也可以不设置 name 属性，默认是字段的名称
    @Autowired
    String defaultValue = "default value"

    @Autowired(name = "user")
    User user; //serializable

    @Autowired(name = "address")
    Address address; //parcelable
    
    //支持注入其他模块的Fragment，
    //也可以通过MRouter.getInstance().build("router path").find()方法获取实例
    @Autowired(name = "/user/login")
    Fragment loginFragment;
    
    //省略其他类型，详情可以查看例子工程
    
    ```
    
    `第二步`：调用 inject 方法，执行注入参数，如下所示：
    
    ```
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MRouter.getInstance().inject(this);//注入操作
        setContentView(R.layout.activity_layout);
    }
    
    ```


- **提供不同模块之间的业务注入**

    使用方法和参数传递类似，不同之处在于目标类需要实现 `IProvider` 接口。 如：
    
    ```
    public interface IOrderSource extends IProvider {
        Order getOrderDetail(String orderId);
    }
    
    @Route(path = "/source/order")
    public class OrderSourceImpl implements IOrderSource {
        @Override
        public Order getOrderDetail(String orderId) {
            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderName("Android从入门到放弃");
            order.setOrderPrice(100.9);
            return order;
        }
    }
    
    public class UserActivity extends BaseActivity{
        @Autowired(name = "/source/order")
        IOrderSource orderSource;
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          MRouter.getInstance().inject(this);
          setContentView(R.layout.activity_user_order_list);
    
          loadOrderDetail();
    
        }
    
        public void loadOrderDetail(){
            //上面是通过 @Autowired 注解的方式注入的
            //也可以通过 MRouter.getInstance().build("/source/order").find(); 方式
    
            if(orderSource!=null){
                orderSource.getOrderDetail("010101")
            }
        }
    }
    ```
    
    据此，可以通过两种方式来注入其他模块的业务类，第一种是注解自动注入的方式；第二种是通过 find 方法来手动获取。
    
    两种方式的区别在于，使用注解的方式需要目标类实现 IProvider 接口。而使用 find 方式来获取则不需要，只需要在目标类加上 @Route 注解即可。



### Activity Stack 管理

有的时候我们需要根据产品的需求关闭相关 Activity，但是又不想通过改变 Activity 的 launchMode 来实现，因为产品的交互千变万化。

可以使用 MRouter 提供的路由栈管理，支持页面路由和页面 Class 对象来关闭页面。如果拿不到页面的 Class，则可以使用页面的路由常量开关闭。

- RouteStackManager.get().finishActivity();

  关闭当前界面的 Activity

- RouteStackManager.get().finishActivity(Activity activity);

  关闭特定的 Activity 界面

- RouteStackManager.get().finishActivity(Class clazz);

  关闭特定的 Activity 界面

- RouteStackManager.get().finishActivity(String routerPath);

  关闭特定的 Activity 界面（要关闭的界面可能在其他模块定义的，拿不到它的 class，可使用它的 routerPath）

- RouteStackManager.get().finishActivity(List list);

  关闭 List 里所有的 Activity 界面（list 里面的元素可以是：Activity 对象、Activity 的 Class、Activity 的 routerPath）

- RouteStackManager.get().finishAllActivityExcept(List excepts);

  关闭所有的 Activity 界面，保留 excepts 集合的界面（excepts 里面的元素可以是 Activity 对象、Activity 的 Class、Activity 的 routerPath）

- RouteStackManager.get().finishAllActivityExcept(String routerPath);

  关闭所有的 Activity 界面，保留 routerPath 对应的的 Activity

- RouteStackManager.get().finishAllActivityExcept(Class activityClass);

  关闭所有的 Activity 界面，保留 activityClass 对应的的 Activity

- RouteStackManager.get().finishAllByRange(Class begin, Class end)

  关闭区间所有界面，包含 begin 和 end。如栈中有 A、B、C、D、E、F，想关闭 C 到 F 之间的 Activity，begin 参数就是 C，end 参数就是 F

# TODOs
- [ ] Activity/Fragent重建，恢复 Fragment
- [ ] 拦截器
- [ ] 支持增量编译
- [ ] 支持跳转页面前进行网络请求，根据请求结果决定是否跳转到目标页面或者弹出 Dialog/Toast


## 更新日志

### 版本更新 05

1. Hotfix remove activity

```
io.github.chiclaim:router:1.0.7
io.github.chiclaim:router-compiler:1.0.6
io.github.chiclaim:router-annotation:1.0.5
io.github.chiclaim:router-gradle-plugin:1.0.5
```


### 版本更新 04

1. 支持 Intent Flag
2. 支持 Activity 动画
3. 支持 navigate 路由跳转回调
4. 路由 path 重复检测
5. 基本类型及 String 参数传递支持默认参数
6. 支持 Uri scheme 启动 Activity
7. 重构路由 Activity 栈管理。使用者不用在 BaseActivity 中调用 RouteStackManager.add 和 RouteStackManager.remove 方法了，直接使用 RouteStackManager 即可

```
io.github.chiclaim:router:1.0.6
io.github.chiclaim:router-compiler:1.0.6
io.github.chiclaim:router-annotation:1.0.5
io.github.chiclaim:router-gradle-plugin:1.0.5
```

### 版本更新 03

1. Androidx 替换 support
2. 升级 AGP 版本
3. 修复 default package 的 bug
4. jCenter 被废弃，改成 mavenCentral
5. 简化配置，不要配置 routeConfig
6. 自动添加混淆文件，无需手动配置

```
io.github.chiclaim:router:1.0.4
io.github.chiclaim:router-compiler:1.0.4
io.github.chiclaim:router-annotation:1.0.4
io.github.chiclaim:router-gradle-plugin:1.0.5
```



### 版本更新 02

1. find() 调用方不需要强制类型转换
2. find() 可以控制是否弹出找不到的组件的 toast 提示
3. 添加新模块的时候不需要在 Application 配置模块名称

```
io.github.chiclaim:router:1.0.0
io.github.chiclaim:router-compiler:1.0.0
io.github.chiclaim:router-annotation:1.0.0
io.github.chiclaim:router-gradle-plugin:1.0.0
```

### 版本更新 01

1. 支持 Fragment 的注入
2. 添加没有对应 router path 的提示
3. app 配置模块的时候不需要删除特殊字符

```
io.github.chiclaim:router:0.2.4
io.github.chiclaim:router-compiler:0.2.4
io.github.chiclaim:router-annotation:0.2.4
io.github.chiclaim:router-gradle-plugin:0.2.4
```





