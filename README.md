# Android 中的meta-data

[谷歌官方文档(需要科学上网)](developer.android.com/guide/topics/manifest/meta-data-element.html)

[谷歌官方文档(国内访问)](https://developer.android.google.cn/guide/topics/manifest/meta-data-element.html)

## 用法

第一步， 在`AndroidManifest.xml`文件的application标签中声明：

```
        <meta-data
            android:name="service_id"
            android:value="123456" />
```

第二步，在代码中获取：

```
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA
            );
            Bundle metaData = applicationInfo.metaData;
            int anInt = metaData.getInt("service_id");
            Log.d(TAG, "anInt: " + anInt);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
```

## 支持的数据类型

|类型|Bundle 对应的方法|
|:-:|:-:|
|字符串|getString()|
|int值|getInt()|
|布尔值|getBoolean()|
|Color对应的int的值|getInt()|
|Float值|getFloat()|



### **注意**

meta-data**不支持long**数据类型，如果配置的值为数字类型，则默认为int值，使用getLong()方法获取到的值为0。

如果需要配置long数据类型，则可以在该long值前边加一个字符，然后使用getString()获取到字符串，在使用subString()，最后使用`Long.parseLong()`获取到该long值。具体代码为：

```
        <meta-data
            android:name="long_value"
            android:value="L12345678901"/>
            
        String tmpStr = metaData.getString("long_value");
        long aLong = Long.parseLong(tmpStr.substring(1));
        // 结果为12345678901
```

申请百度鹰眼服务就需要一个long值：[参考](http://lbsyun.baidu.com/trace/admin/service)

## 配置不同的value值

我们可以在build.gradle文件中，使用`manifestPlaceholders`来对不同的产品，配置不同的`meta-data`值。一般是使用多渠道打包，或者区别debug和release版本。这里以debug和release版本为例，并且使用配置文件`config.properties`来存放这些数据。

```
// 加载配置文件
Properties props = new Properties()
FileInputStream fis = new FileInputStream(file("config.properties"))
BufferedReader bf = new BufferedReader(new InputStreamReader(fis, "UTF-8"))
props.load(bf)
...

    buildTypes {
        debug {
            ...
            manifestPlaceholders = [
                    SERVICE_ID: props['SERVICE_ID_DEBUG']
            ]
        }

        release {
            ...
            manifestPlaceholders = [
                    SERVICE_ID: props['SERVICE_ID_RELEASE']
            ]
        }
    }
```

配置文件具体为：

```
# this is config file

# debug
...

SERVICE_ID_DEBUG = L112233445566

# release
...

SERVICE_ID_RELEASE = L665544332211
```

这样，使用debug和release版本，获取的SERVICE_ID就分别为112233445566和665544332211了。(使用了上一步的填充long值得方法)

# [工程github地址](https://github.com/YoungBear/MetaData)

## 参考

[Access long key from meta-data from AndroidManifeast.xml](https://stackoverflow.com/questions/16535705/access-long-key-from-meta-data-from-androidmanifeast-xml)

