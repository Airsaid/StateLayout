package com.airsaid.statelayout.sample

import android.app.Application
import com.airsaid.statelayout.StateLayout
import com.airsaid.statelayout.sample.stateprovider.GlobalStateProvider

/**
 * @author airsaid
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    StateLayout.setGlobalStateProvider(GlobalStateProvider())
  }
}