# AnimationDemo
Activity切换动画演示


### 如何设置Activity的切换动画？

> 上一片文章我们讲述了[Navigation切换动画](https://juejin.cn/post/6998067266365423646)，其实在Activity也能轻松实现切换动画。只不过我们很少去给activity设置切换动画，一般都是系统默认的。


在Activity类中提供了`overridePendingTransition`方法:


![QQ截图20210830102703.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/18a35532bb8943b1b7834f81d6b0a54f~tplv-k3u1fbpfcp-watermark.image)

其参数意思是设置进入动画和退出动画，调用时机为：startActivity(intent)之后或者finish()方法中

**示例：** 从MainActivity跳转到MainActivity2


```
val intent = Intent(MainActivity::class.java, MainActivity2::class.java)
startActivity(intent)
//进入动画(for MainActivity2)和退出动画(for MainActivity)
overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
```

**R.anim.slide_in_left:**
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">

    <translate
        android:fromXDelta="-100%"
        android:toXDelta="0%"
        android:fromYDelta="0%"
        android:toYDelta="0%"
        android:duration="400"/>
</set>
```
**R.anim.slide_out_right:**
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">

    <translate android:fromXDelta="0%" android:toXDelta="100%"
               android:fromYDelta="0%" android:toYDelta="0%"
               android:duration="400"/>
</set>
```
**最终效果：**

![2.gif](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/9d68469280e34434ab180792d21506c4~tplv-k3u1fbpfcp-watermark.image)

> 从ActivityA跳转到ActivityB, A使用的是enter_anim,B使用的是exit_anim

返回的时候并没有执行动效，我们可以在finish中设置：
```
override fun finish() {
    super.finish()
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
}
```
> 从ActivtyB返回ActivityA，B执行exit_anim，A执行enter_anim

**现在我们得到一个完整的跳转动画：**

![2.gif](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/24730740c2be4ca7b25e79ebdfda5dc8~tplv-k3u1fbpfcp-watermark.image)

参考前面介绍的视图动画，我们可以创建一系列旋转缩放透明度动画，更多动效请在Demo中查看。


### View无缝衔接

我们经常在一些app中看到界面跳转的时候，一些控件好像可以无缝衔接，比如搜索框、九宫格图片等


现在来实现ImageView的无缝切换，在MainActivity界面中加入一张图片：
```
<ImageView
    android:id="@+id/imageview"
    android:layout_width="200dp"
    android:layout_height="wrap_content"
    android:layout_marginTop="32dp"
    android:adjustViewBounds="true"
    android:src="@drawable/test"
    android:transitionName="shareElement"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintHorizontal_bias="0.0"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/radioGroup" />
```
重点是`android:transitionName="shareElement"`用来设置`共享元素`名称，在ActivityB布局中加入同样的控件。

使用`ActivityOptionsCompat`进行获取共享元素，注意只在Andoid5.0以上有效：
```
val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this,binding.imageview,"shareElement").toBundle()
val intent = Intent(this,MainActivity3::class.java)
startActivity(intent,bundle)
```
**最终效果：**

![1.gif](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e473331cfab7430b9d7d58ec8aaa1a20~tplv-k3u1fbpfcp-watermark.image)

> 这里在加载Bitmap大图的时候有明显卡顿，所以只建议在一些原生控件中使用

**搜索框切换：**

![1.gif](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d9ef912c6e1647c69902b32123c28ad3~tplv-k3u1fbpfcp-watermark.image)


**设置多个共享元素：Pair(View,TAG)**
```
import androidx.core.util.Pair

val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(binding.imageview,"shareElement"),
    Pair(binding.searchEdit,"shareSearch")
).toBundle()
val intent = Intent(this,MainActivity3::class.java)
startActivity(intent, bundle)
```

### ActivityOptions

> 前面说到了`ActivityOptionsCompat`设置共享元素，其实`ActivityOptions`中还有许多其他方法


| Public Methods |  |
| --- | --- |
|makeCustomAnimation| 设置自定义进场动画 |
|makeScaleUpAnimation | 设置放大View |
| makeClipRevealAnimation  | 从View的某个区域开始放大 |
| makeThumbnailScaleUpAnimation | 放大一张图片 |

具体可以参考官方文档：https://developer.android.com/reference/android/app/ActivityOptions

个人觉得这些方法基本没太大用用处


---
文章代码略有缺失，完整请查看Demo,项目整体还在完善中，欢迎大佬们指点。*卑微Androider在线求个Star*😅
