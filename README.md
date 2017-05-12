# react-native-launch-optimum

优化react-native android 启动时间

##作者

QQ: 289459798
QQ群: 161263093

欢迎更多的喜欢开源的小伙伴加入

![](./ios.gif)

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


## ios

ios 优化很简单，其实react-native 在ios上的表现比android 要好很多，如果需要优化，可以在 `AppDelegate.m`中加以下代码

```oc

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  
  ...
  self.window.rootViewController = rootViewController;
  [self lanuchOptimum];
  [self.window makeKeyAndVisible];
  return YES;
}

- (void) lanuchOptimum {
  
  CGSize viewSize = self.window.bounds.size;
  // 横屏修改这个值
  NSString *viewOrientation = @"Portrait";

  NSArray* imagesDict = [[[NSBundle mainBundle] infoDictionary] valueForKey:@"UILaunchImages"];
  for (NSDictionary* dict in imagesDict) {
    CGSize imageSize = CGSizeFromString(dict[@"UILaunchImageSize"]);
    if (CGSizeEqualToSize(imageSize, viewSize) && [viewOrientation isEqualToString:dict[@"UILaunchImageOrientation"]]) {
      
      NSString *lanuchImage = dict[@"UILaunchImageName"];
  
      UIImageView *launchView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:lanuchImage]];
      launchView.frame = self.window.bounds;
      launchView.contentMode = UIViewContentModeScaleAspectFill;
      [self.window.rootViewController.view addSubview:launchView];
      [self.window.rootViewController.view bringSubviewToFront:launchView];
      
      // 延时显示1秒钟
      [UIView animateWithDuration:1.0f delay:1.0f options:UIViewAnimationOptionBeginFromCurrentState animations:^{
        
        // 增加隐藏时候的动画
        launchView.alpha = 0.0f;
        launchView.layer.transform = CATransform3DScale(CATransform3DIdentity, 1.2, 1.2, 1);
        
      } completion:^(BOOL finished) {
        [launchView removeFromSuperview];
      }];
    
      break;
    }
  }
}
```