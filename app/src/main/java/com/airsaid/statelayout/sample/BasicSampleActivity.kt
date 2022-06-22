package com.airsaid.statelayout.sample

import android.os.Bundle

/**
 * @author airsaid
 */
class BasicSampleActivity(
    override val layoutId: Int = R.layout.activity_basic_sample
) : AbstractSampleActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mStateLayout.addStateChangedListener { state, isShow ->
      println("onStateChanged state: $state, isShow: $isShow")
    }
  }
}