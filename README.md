# react-native-launch-optimum

优化react-native android 启动时间

##作者

QQ: 289459798
QQ群: 161263093

欢迎更多的喜欢开源的小伙伴加入

### 安装

```
npm install react-native-launch-optimum

```

修改setting.gradle，增加

```
include ':react-native-launch-optimum'
project(':react-native-launch-optimum').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-launch-optimum/android')
```

修改app目录下的build.gradle，增加

```
compile project(':react-native-launch-optimum')

```

### 使用

修改MainActivit继承的父类为ZReactActivity

```
public class MainActivity extends ZReactActivity {
...
}
```

在启动页增加如下代码

```java
RNCacheViewManager.init(this, component_name, ((ViewGroup) findViewById(android.R.id.content)));
```

component_name 为 MainActivity设置的component_name