# MRoute(Android Modularization Route)

## 如何使用

### 1. 添加依赖配置
```
在工程的根目录 build.gradle 添加如下配置：
allprojects {
    repositories {
        if (!useLocal.toBoolean()) {
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
    compile "com.chiclaim:router:${MROUTER_VERSION}"
    annotationProcessor "com.chiclaim:router-compiler:${MROUTER_COMPILER_VERSION}"
}

//如果使用了模块化，需要在用到MRouter的模块下添加，这样才能成功生成代码（模块化使用apt工具生成代码都需要如此）
annotationProcessor "com.chiclaim:router-compiler:${MROUTER_COMPILER_VERSION}"

```
### 2. API使用

如果你是多模块，请在Application类加上@Components注解（避免扫码Dex文件的方式来初始化，提高效率）：
```
@Components({"app", "sampleuser", "sampleorder"})
//app，sampleuser，sampleorder三个模块都使用了MRouter
//注意如果模块名包含了特殊字符，如sample.order，需要把'.'去掉
public class MyApplication extends Application {
    //...
}
```

初始化操作

```
@Components({"app", "sampleuser", "sampleorder"})
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化操作
        MRouter.getInstance().init(this);
    }
}
```

使用路由注解(Route)：
```
@Route(path = "xxx/main")
public class MainActivity extends AppCompatActivity {
    //...
}
```

使用自动注入参数注解(Autowired)
```
@Autowired(name = "user")
User user; //serializable

@Autowired(name = "address")
Address address; //parcelable

//需要在onCreate方法里执行注入操作
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MRouter.getInstance().inject(this);//注入操作
    setContentView(R.layout.activity_user_order_list);
}

```

跳转

```
MRouter.getInstance()
                .build("xxx/main")
                .putSerializable("user", user)
                .putParcelable("address", address)
                .navigation(this);
```



## 模块化的页面路由功能

### 1. 支持不同模块之间的页面跳转

> MRouter.getInstance().build("user/order/list").navigation(fragment/activity);

### 2. 完整的支持Intent参数的所有类型
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

例如：

```
MRouter.getInstance()
        .build("user/order/list")
        .putSerializable("user", user)
        .putParcelable("address", address)
        .putParcelableList("addressList", addressList)
        .putParcelableArray("addressArray", addressArray)
        .putString("param", "chiclaim")
        .putStringArray("stringArray", new String[]{"a", "b", "c"})
        .putStringList("stringArrayList", stringList)
        .putStringList("stringList", stringArrayList)
        .putByte("byte", (byte) 2)
        .putByteArray("byteArray", new byte[]{1, 2, 3, 4, 5})
        .putInt("age", 33)
        .putIntArray("intArray", new int[]{10, 11, 12, 13})
        .putIntList("intList", intList)
        .putIntList("intArrayList", intArrayList)
        .putChar("chara", 'c')
        .putCharArray("charArray", "chiclaim".toCharArray())
        .putShort("short", (short) 1000000)
        .putShortArray("shortArray", new short[]{(short) 10.9, (short) 11.9})
        .putDouble("double", 1200000)
        .putDoubleArray("doubleArray", new double[]{1232, 9999, 8789, 3.1415926})
        .putLong("long", 999999999)
        .putLongArray("longArray", new long[]{1000, 2000, 3000})
        .putFloat("float", 333)
        .putFloatArray("floatArray", new float[]{12.9f, 234.9f})
        .putBoolean("boolean", true)
        .putBooleanArray("booleanArray", new boolean[]{true, false, true})
        .putExtras(extras)
        .navigation(this);
```

### 3. 自动注入参数

```
@Autowire(name = "param")
String username;

@Autowire(name = "byte")
byte paramByte;

@Autowire(name = "byteArray")
byte[] byteArray;

@Autowire(name = "age")
int age;

@Autowire(name = "intArray")
int[] intArray;

@Autowire(name = "intList")
List<Integer> intList;

@Autowire(name = "intArrayList")
ArrayList<Integer> intArrayList;

@Autowire(name = "chara")
char paramChar;

@Autowire(name = "charArray")
char[] charArray;

@Autowire(name = "boolean")
boolean isAdult;

@Autowire(name = "booleanArray")
boolean[] booleanArray;

@Autowire(name = "short")
short height;

@Autowire(name = "shortArray")
short[] shortArray;

@Autowire(name = "float")
float salary;

@Autowire(name = "floatArray")
float[] floatArray;

@Autowire(name = "double")
double salary2;

@Autowire(name = "doubleArray")
double[] doubleArray;

@Autowire(name = "long")
long liveDays;

@Autowire(name = "longArray")
long[] longArray;

@Autowire(name = "extra")
String extra;

@Autowire(name = "stringArray")
String[] stringArray;

@Autowire(name = "stringList")
List<String> stringList;

@Autowire(name = "stringArrayList")
ArrayList<String> stringArrayList;

@Autowire(name = "user")
User user; //serializable

@Autowire(name = "address")
Address address; //parcelable

@Autowire(name = "addressList")
List<Address> addressList; //List<Parcelable>

@Autowire(name = "addressArray")
Address[] addressArray; //Parcelable[]
```

执行注入

```
MRouter.getInstance().inject(this);
```


当然也可以通过最普通的方式来获取参数(Intent)，例如：

```
getIntent().getStringExtra("name");
getIntent().getBooleanExtra("name");

getArguments().getString("name");
getArguments().getBoolean("name");

...

```

### 4. 提供不同模块之间的业务注入

使用方法和传输传递类似，不同之处在于目标类需要实现`IProvider`接口。 如：

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
        if(orderSource!=null){
            orderSource.getOrderDetail("010101")
        }
    }
}
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


## 友好的错误提示

    在开发者使用过程中可能有些不当的地方导致无法运行，更加友好的错误提示，帮助快速定位问题。

## TODOs

    重构生成的代码
    优化通过@Components的方式来注册所有使用了MRouter的模块