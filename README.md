# android-modularization
android modularization

## 模块化的页面路由功能

### 支持不同模块之间的页面跳转

> MRouter.getInstance().build("user/order/list").navigation(fragment/activity);

### 支持Intent一样多的数据传递
- putBoolean
- putBooleanArray
- putByte
- putByteArray
- putChar
- putCharArray
- putShort
- putShortArray
- putFloat
- putFloatArray
- putInt
- putIntArray
- putIntList
- putDouble
- putDoubleArray
- putLong
- putLongArray
- putString
- putStringArray
- putStringList
- putParcelable
- putParcelableArray
- putParcelableList
- putSerializable

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

