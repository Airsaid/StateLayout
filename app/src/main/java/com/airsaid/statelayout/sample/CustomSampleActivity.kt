package com.airsaid.statelayout.sample

import android.os.Bundle
import com.airsaid.statelayout.sample.state.ErrorState

/**
 * @author airsaid
 */
class CustomSampleActivity(
    override val layoutId: Int = R.layout.activity_basic_sample
) : AbstractSampleActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mStateLayout.getState<ErrorState>(ErrorState::class.java).setErrorText("Custom text")
  }
}