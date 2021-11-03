# MultiStateLayout
![version](https://img.shields.io/maven-central/v/com.airsaid/multistatelayout)
![ci workflow](https://github.com/Airsaid/MultiStateLayout/actions/workflows/android.yml/badge.svg)

:cyclone: A customize multiple state layout for Android. ([中文文档](https://github.com/Airsaid/MultiStateLayout/blob/master/README_CN.md))

# Preview
![image](https://github.com/Airsaid/MultiStateLayout/blob/master/preview/preview.gif)

Download the sample apk to see more: [Sample APK](https://github.com/Airsaid/MultiStateLayout/raw/master/sample.apk).

# Setup
Add the ```multistatelayout``` dependency to your library or app's ```build.gradle``` file:
```groovy
dependencies {
  ......
  implementation 'com.airsaid:multistatelayout:$version'
}
```

# Usage
1. Implement the ```State``` and ```StateProvider``` interfaces to provide states:
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
2. Add ```MultiStateLayout``` to the XML layout:
```xml
<com.airsaid.multistatelayout.MultiStateLayout
    android:id="@+id/multiStateLayout"
    android:layout_width="match_parent" 
    android:layout_height="match_parent">

    <!-- content layout -->
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
3. Call the ```init()``` method in the code to initialize ```MultiStateLayout```:
```kotlin
multiStateLayout.init(CommonStateProvider())
```
4. Switching between states:
```kotlin
multiStateLayout.showContent()
multiStateLayout.showState(LoadingState.ID)
multiStateLayout.showState(EmptyState.ID)
```

# TransitionAnimation
You can set the transition animation when the state is switched via the ```setTransitionAnimator(@Nullable TransitionAnimator transitionAnimator)``` method.

Currently existing transition animations are:
- AlphaTransitionAnimator (default)
- TranslationTransitionAnimator
- AlphaTranslationTransitionAnimator

# Listener
You can add listeners for state switching through the ```addStateChangedListener(@NonNull OnStateChangedListener listener)``` method:
```kotlin
multiStateLayout.addStateChangedListener { state, isShow ->
  Log.d(TAG, "onStateChanged state: $state, isShow: $isShow")
}
```

# More
- Set the click event of a specific state
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
