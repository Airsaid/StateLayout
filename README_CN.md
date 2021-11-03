# MultiStateLayout
![version](https://img.shields.io/maven-central/v/com.airsaid/multistatelayout)
![ci workflow](https://github.com/Airsaid/MultiStateLayout/actions/workflows/android.yml/badge.svg)

:cyclone: 一个 Android 上的自定义多状态布局。

# 预览
![image](https://github.com/Airsaid/MultiStateLayout/blob/master/preview/preview.gif)

下载示例 APK 查看更多: [Sample APK](https://github.com/Airsaid/MultiStateLayout/raw/master/sample.apk).

# 接入
添加 ```multistatelayout``` 依赖到你的库或 APP 项目的 ```build.gradle``` 文件:
```groovy
dependencies {
  ......
  implementation 'com.airsaid:multistatelayout:$version'
}
```

# 使用
1. 实现 ```State``` 和 ```StateProvider``` 接口来提供各种状态:
```kotlin
import com.airsaid.multistatelayout.State

class EmptyState : State {

  companion object {
    const val ID = R.id.emptyLayout
  }

  override fun getId() = ID

  override fun getLayoutId() = R.layout.multi_state_empty

  override fun onFinishInflate(stateView: View) {}
}
......
```
```kotlin
import com.airsaid.multistatelayout.StateProvider

class CommonStateProvider : StateProvider {
  override fun getStates(): MutableList<State> {
    return mutableListOf(EmptyState(), ErrorState(), LoadingState())
  }
}
```
2. 添加 ```MultiStateLayout``` 到 XML 布局中:
```xml
<com.airsaid.multistatelayout.MultiStateLayout
    android:id="@+id/multiStateLayout"
    android:layout_width="match_parent" 
    android:layout_height="match_parent">

    <!-- 内容布局 -->
    <FrameLayout 
        android:layout_width="match_parent" 
        android:layout_height="match_parent">

        <TextView 
            android:layout_width="wrap_content" 
            android:layout_height="wrap_content"
            android:layout_gravity="center" 
            android:text="I'am content." />
    </FrameLayout>
</com.airsaid.multistatelayout.MultiStateLayout>
```
3. 在代码中调用 ```init()``` 方法来初始化 ```MultiStateLayout```:
```kotlin
multiStateLayout.init(CommonStateProvider())
```
4. 切换不同的状态：
```kotlin
multiStateLayout.showContent()
multiStateLayout.showState(LoadingState.ID)
multiStateLayout.showState(EmptyState.ID)
```

# 过渡动画
你可以通过调用 ```setTransitionAnimator(@Nullable TransitionAnimator transitionAnimator)``` 方法来设置状态间切换的过渡动画。

目前已有的过渡动画有：
- AlphaTransitionAnimator (默认的)
- TranslationTransitionAnimator
- AlphaTranslationTransitionAnimator

# 监听
你可以通过调用 ```addStateChangedListener(@NonNull OnStateChangedListener listener)``` 方法来添加状态改变的监听：
```kotlin
multiStateLayout.addStateChangedListener { state, isShow ->
  Log.d(TAG, "onStateChanged state: $state, isShow: $isShow")
}
```

# 更多
- 设置特定状态的点击事件：
```kotlin
class ErrorState : State {
  companion object {
    const val ID = R.id.errorLayout
  }

  private lateinit var callback: () -> Unit?

  override fun getId() = ID

  override fun getLayoutId() = R.layout.multi_state_error

  override fun onFinishInflate(stateView: View) {
    stateView.findViewById<Button>(R.id.reload).setOnClickListener {
      callback.invoke()
    }
  }

  fun setOnReloadListener(callback: () -> Unit) {
    this.callback = callback
  }
}
```
```kotlin
multiStateLayout.getState<ErrorState>(ErrorState.ID).setOnReloadListener {
  // do something...
}
```

# License
```
Copyright 2021 Airsaid. https://github.com/airsaid

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
