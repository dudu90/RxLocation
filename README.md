# RxLocation
[![Download](https://api.bintray.com/packages/dudu90/maven/rxlocation/images/download.svg)](https://bintray.com/dudu90/maven/rxlocation/_latestVersion)

[![Build Status](https://api.travis-ci.org/dudu90/rxlocation.svg?branch=master)](https://travis-ci.org/dudu90/RxLocation)

适用于Android的与Rxjava结合的，快速简单的定位库.

## 特性

1.和Rxjava结合，使你的代码更加简洁;

2.支持高德和百度定位，方便集成。

## 使用

### 在Gradle中添加依赖

```gradle
repositories {
    maven {
        url 'https://dl.bintray.com/dudu90/maven'
    }
}

compile 'RxLocation:rxlocation:1.0'
```

###  初始化

```java
RxLocation.initialize(context);
RxLocation.initialize(context,type);
RxLocation.initialize(context,type,option);
```

### 获取定位信息

```java
new LocationRequester<BDLocation>().lastLocation()
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BDLocation>() {
                    @Override
                    public void call(BDLocation location) {
                        //TODO
                    }
                });
```

## TODO

1.位置提醒

2.欢迎新的需求

## 鸣谢

+ 感谢我的同事和良师,[promeG](https://github.com/promeG/)。