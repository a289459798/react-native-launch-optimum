# react-native-launch-optimum

优化react-native android 启动时间

### 安装

```
npm install react-native-launch-optimum
react-native link
```

### 使用

在启动页增加如下代码

```java
RNCacheViewManager.init(this, component_name, ((ViewGroup) findViewById(android.R.id.content)));
```

component_name 为 MainActivity设置的component_name