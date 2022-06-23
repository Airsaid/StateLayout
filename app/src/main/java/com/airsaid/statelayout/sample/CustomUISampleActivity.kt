package com.airsaid.statelayout.sample

import com.airsaid.statelayout.sample.state.ErrorState

/**
 * @author airsaid
 */
class CustomUISampleActivity(
    override val layoutId: Int = R.layout.activity_basic_sample
) : AbstractSampleActivity() {

  override fun initialize() {
    mStateLayout.getState<ErrorState>(ErrorState::class.java).setErrorText("Custom text")
  }
}