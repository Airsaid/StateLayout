**English** | [简体中文](README_CN.md)

# StateLayout
[![version](https://img.shields.io/maven-central/v/com.airsaid/statelayout)](https://search.maven.org/artifact/com.airsaid/statelayout)
[![ci workflow](https://github.com/Airsaid/StateLayout/actions/workflows/android.yml/badge.svg)](https://github.com/Airsaid/StateLayout/actions)
[![license](https://img.shields.io/github/license/airsaid/statelayout)](LICENSE)

:cyclone: A customize multiple state layout for Android.

# Preview
<img width="300" height="630" src="preview/preview.gif"/>

Download the sample apk to see more: [Sample APK](sample.apk).

# Features
- Easy to use
- High scalability
- API is well designed
- Support states switching animation
- Support setting global states
- Support saving and restoring the current state
- Support automatic state switching according to network status, list data and other scenarios
- Support lazy loading, only when switching to the corresponding state to fill the layout

# Setup
Add the `statelayout` dependency to your library or app's `build.gradle` file:
```groovy
dependencies {
  implementation 'com.airsaid:statelayout:$version'
}
```

# Usage
1. Implement the `State` and `StateProvider` interfaces to provide states. Example:
```kotlin
import com.airsaid.statelayout.State

class LoadingState : State {

  override fun getLayoutId() = R.layout.state_loading
}
```
```kotlin
import com.airsaid.statelayout.StateProvider

class CommonStateProvider : StateProvider {
  override fun getStates(): MutableList<State> {
    return mutableListOf(EmptyState(), ErrorState(), LoadingState())
  }
}
```

2. Add `StateLayout` to the XML Layout. Example:
```xml
<com.airsaid.statelayout.StateLayout
    android:id="@+id/stateLayout"
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
</com.airsaid.statelayout.StateLayout>
```

3. Call `initStateProvider()` method to provide support for the states. Example:
```kotlin
stateLayout.initStateProvider(CommonStateProvider())
```
**Or, if you don't want to set it before each use, you can provide a global state by calling the `StateLayout.setGlobalStateProvider()` static method.** Example:
```kotlin
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    StateLayout.setGlobalStateProvider(CommonStateProvider())
  }
}
```

4. Call `showContent()` or `showState()` methods to switch between states. Example:
```kotlin
stateLayout.showContent()
stateLayout.showState(LoadingState::class.java)
stateLayout.showState(EmptyState::class.java)
```

# Transition Animator
You can set the transition animation when the state is switched via the `setTransitionAnimator()` method.

Currently existing transition animations are:
- AlphaTransitionAnimator (default)
- TranslationTransitionAnimator
- AlphaTranslationTransitionAnimator

# Listener
You can add listeners for state switching through the `addStateChangedListener()` method. Example:
```kotlin
stateLayout.addStateChangedListener { state, isShow ->
  Log.d(TAG, "onStateChanged state: $state, isShow: $isShow")
}
```

# Automatic state switching
`StateLayout` provides the `addStateTrigger()` method to automatically switch states when conditions are met. For example, the following `RecyclerViewStateTrigger` implements an automatic switch to an empty state layout when there is no data:

<details>
<summary>RecyclerViewStateTrigger</summary>

```kotlin
/**
 * A state trigger sample that passes in the specified [adapter] object for observation
 * and automatically sets the empty data state when the data size is 0,
 * and the content state when there is data.
 *
 * @property adapter The [RecyclerView.Adapter] object being watched.
 * @author airsaid
 */
class RecyclerViewStateTrigger(
    private val adapter: RecyclerView.Adapter<*>,
) : StateTrigger<Int>() {

  private val adapterDataObserver by lazy {
    StateAdapterDataObserver(this, adapter)
  }

  override fun onTrigger(stateLayout: StateLayout, count: Int) {
    if (count != 0) {
      stateLayout.showContent()
    } else {
      stateLayout.showState(EmptyState::class.java)
    }
  }

  override fun onAttachedToWindow() {
    adapter.registerAdapterDataObserver(adapterDataObserver)
  }

  override fun onDetachedFromWindow() {
    adapter.unregisterAdapterDataObserver(adapterDataObserver)
  }

  private class StateAdapterDataObserver(
      private val stateTrigger: StateTrigger<Int>,
      private val adapter: RecyclerView.Adapter<*>
  ) : RecyclerView.AdapterDataObserver() {
    override fun onChanged() {
      super.onChanged()
      dataChanged()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
      super.onItemRangeRemoved(positionStart, itemCount)
      dataChanged()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
      super.onItemRangeInserted(positionStart, itemCount)
      dataChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
      super.onItemRangeChanged(positionStart, itemCount)
      dataChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
      super.onItemRangeChanged(positionStart, itemCount, payload)
      dataChanged()
    }

    private fun dataChanged() {
      stateTrigger.trigger(adapter.itemCount)
    }
  }
}
```

In addition, `NetworkStateTrigger` is also provided in the example code. Or you can implement the `StateTrigger` interface to implement your own logic.

</details>

To use it, just call the `addStateTrigger()` method (multiple triggers can be added). Example:
```kotlin
stateLayout.addStateTrigger(RecyclerViewStateTrigger(sampleAdapter))
```

# Custom State
The `View` of the state layout can be retrieved in the `onFinishInflate()` callback, and then the specified UI or other actions can be set. For example:
```kotlin
class ErrorState : State {
  private lateinit var errorText: String

  override fun getLayoutId() = R.layout.state_error

  override fun onFinishInflate(stateLayout: StateLayout, stateView: View) {
    stateView.findViewById<TextView>(R.id.title).text = this.errorText
  }

  fun setErrorText(text: String) {
    this.errorText = text
  }
}
```
```kotlin
stateLayout.getState(ErrorState::class.java).setErrorText("Custom Error Text")
```

# Set click retry event
You can set the click retry event by calling the `setOnRetryClickListener()` method. For example:
```kotlin
stateLayout.setOnRetryClickListener {
  // Reload data
}
```
The exact time when a retry is triggered is specified by the specific state. For example, in the following example, the callback is triggered when the Retry button is clicked:
```kotlin
class ErrorState : State {
  override fun getLayoutId() = R.layout.state_error

  override fun onFinishInflate(stateLayout: StateLayout, stateView: View) {
    stateView.findViewById<Button>(R.id.reload).setOnClickListener {
      stateLayout.dispatchRetryClickListener(it)
    }
  }
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
