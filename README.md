## MRouter(Android Modularization Router)

[![license](https://img.shields.io/badge/license-apache-brightgreen.svg?style=flat)](https://github.com/chiclaim/MRouter/blob/master/LICENSE)
[![Release Version](https://img.shields.io/badge/release-0.2.4-red.svg)](https://bintray.com/ggz-org/maven)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/chiclaim/MRouter/pulls)


## 主要功能

#### 1.支持不同模块之间的页面跳转

#### 2.支持自动注入传递过来的参数

#### 3.支持简单的依赖注入(用作注入其他module的业务类或者Fragment)

#### 4.支持对Activity的管理

#### 5.支持InstantRun、MultiDex

#### 6.使用过程中更友好的错误提示，提升开发效率

## 模块化架构实践（已在实际项目中使用）

[1. 二维火Android云收银模块化架构实践](http://blog.csdn.net/johnny901114/article/details/78346125)

## 如何使用

### 1. 添加依赖配置
```
在工程的根目录 build.gradle 添加如下配置：
allprojects {
    repositories {
        if (!USE_LOCAL.toBoolean()) {
            maven {
                url "https://dl.bintray.com/ggz-org/maven/"
            }
        }
        jcenter()
    }
}

//在每个使用了MRouter的字工程里的 build.gradle 添加如下配置：
android {
    javaCompileOptions {
        annotationProcessorOptions {
            arguments = [moduleName: project.getName()]
        }
    }
}

dependencies {
    compile "com.chiclaim:router:0.2.4"
    annotationProcessor "com.chiclaim:router-compiler:0.2.4"
}

//如果使用了模块化，需要在用到MRouter的模块下添加如下配置，这样才能成功生成代码（模块化使用apt工具生成代码都需要如此）

annotationProcessor "com.chiclaim:router-compiler:0.2.4"

```
### 2. API使用

#### 1. 告诉框架哪些模块用到了MRouter，

例如，有4个module，只有app、sample.user、sample.order三个module使用到了MRouter

需要在Application类加上@Components注解，如下所示：

```
//app、sample.user、sample.order 三个模块都使用了MRouter

@Components({"app", "sample.user", "sample.order"})
public class MyApplication extends Application {
    //...
}
```

#### 2. 框架的初始化操作

```
@Components({"app", "sampleuser", "sampleorder"})
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MRouter.getInstance().init(this);//初始化操作
    }
}
```


#### 3. 使用`@Route`注解告知框架哪些Activity交给框架管理

```
@Route(path = "xxx/main")//path就是路由的路径
public class MainActivity extends AppCompatActivity {
    //...
}
```

#### 4. 界面跳转和参数传递

```
MRouter.getInstance()
                .build("xxx/main")//build方法参数就是目标界面的路由路径
                .putSerializable("user", user)//传递参数
                .putParcelable("address", address)//传递参数
                .navigation(this);
```

**支持的参数类型和Intent参数传递的类型一致 ，完整的类型如下所示：**

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

#### 5. 使用@Autowired注解完成在Activity和Fragment中自动注入参数

`第一步`：上一个界面传过来的参数在当前类中声明相应的属性，在该属性上加上 `@Autowired` 注解，name的值就是参数的key，如：

```
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

`第二步`：调用inject方法，执行注入参数，如下所示：

```
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MRouter.getInstance().inject(this);//注入操作
    setContentView(R.layout.activity_layout);
}

```


#### 6. 提供不同模块之间的业务注入

使用方法和参数传递类似，不同之处在于目标类需要实现`IProvider`接口。 如：

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
        //上面是通过注解的方式注入的
        //也可以通过MRouter.getInstance().build("/source/order").find();方式

        if(orderSource!=null){
            orderSource.getOrderDetail("010101")
        }
    }
}
```

据此，可以通过两种方式来注入其他模块的业务类，第一种是注解自动注入的方式，第二种是通过find方法来手动获取。

两种方式的区别在于，使用注解的方式需要目标类实现IProvider接口。而使用find方式来获取则不需要，只需要在目标类加上@Route注解即可。



#### 7. Activity的管理

- RouterActivityManager.get().finishActivity();
  ```
    关闭当前界面的Activity
  ```

- RouterActivityManager.get().finishActivity(Activity activity);
    ```
    关闭特定的Activity界面
    ```

- RouterActivityManager.get().finishActivity(Class clazz);
    ```
    关闭特定的Activity界面
    ```

- RouterActivityManager.get().finishActivity(String routerPath);
    ```
    关闭特定的Activity界面
    （要关闭的界面可能在其他模块定义的，拿不到它的class，可使用它的 routerPath）
    ```
- RouterActivityManager.get().finishActivity(List list);
    ```
    关闭List里所有的Activity界面
    （list 里面的元素可以是Activity对象、Activity的Class、Activity的routerPath）
    ```

- RouterActivityManager.get().finishAllActivityExcept(List excepts);
    ```
    关闭所有的Activity界面，保留excepts集合的界面
    （excepts 里面的元素可以是Activity对象、Activity的Class、Activity的routerPath）
    ```

- RouterActivityManager.get().finishAllActivityExcept(String routerPath);
    ```
    关闭所有的Activity界面，保留routerPath对应的的Activity
    ```

- RouterActivityManager.get().finishAllActivityExcept(Class activityClass);
    ```
    关闭所有的Activity界面，保留activityClass对应的的Activity
    ```

- RouterActivityManager.get().finishAllByRange(Class begin, Class end)
    ```
    关闭区间所有界面，包含begin和end。
    如栈中有A、B、C、D、E、F，想关闭C到F之间的Activity，begin参数就是C，end参数就是F
    ```


## 混淆(Proguard)

如果你使用了Proguard，需要添加如下配置

```

#MRouter
-keep public class com.chiclaim.modularization.router.**{*;}
-keepclasseswithmembernames class * { @com.chiclaim.modularization.router.annotation.* <methods>; }
-keepclasseswithmembernames class * { @com.chiclaim.modularization.router.annotation.* <fields>; }
-keep public class * implements com.chiclaim.modularization.router.IAutowired{ public <init>(**); }
```


## TODOs

1，现在的可以作为简单的注入框架（需要提供默认的构造方式），下一步考虑支持复杂的注入

2，现在的提供的功能可能有限，但是在我们开发的过程中已经够用，如果有什么需要的增强的地方欢迎[ISSUES](https://github.com/chiclaim/MRouter/issues)
