package com.airsaid.statelayout.sample

/**
 * @author airsaid
 */
class BasicSampleActivity(
    override val layoutId: Int = R.layout.activity_basic_sample
) : AbstractSampleActivity() {

  override fun initialize() {
    mStateLayout.addStateChangedListener { state, isShow ->
      println("onStateChanged state: $state, isShow: $isShow")
    }
  }
}