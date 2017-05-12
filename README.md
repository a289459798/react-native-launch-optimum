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
    ...// 其他代码
 
     // 启动显示的页面
     @Override
     public int getLayout() {
         return R.layout.loading;
     }
 
     // 启动页显示时间
     @Override
     public int getWaitTime() {
         return 3;
     }
 
     // 引导页面
     @Override
     public Class<? extends Activity> getGuideActivity() {
 
         int versionCode = DeviceHelper.getVersionCode(this);
         int preVersionCode = this.getSharedPreferences("config", Activity.MODE_PRIVATE).getInt("versionCode", 0);
         if (preVersionCode < versionCode) {
 
             return GuideActivity.class;
         }
         return null;
     }

}
```
