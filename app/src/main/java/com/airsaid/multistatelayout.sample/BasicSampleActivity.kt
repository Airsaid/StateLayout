package com.airsaid.multistatelayout.sample

import android.os.Bundle

/**
 * @author airsaid
 */
class BasicSampleActivity(
    override val layoutId: Int = R.layout.fragment_multi_state_layout
) : AbstractSampleActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mMultiStateLayout.addStateChangedListener { state, isShow ->
      println("onStateChanged state: $state, isShow: $isShow")
    }
  }
}