package com.airsaid.multistatelayout.sample

import android.os.Bundle
import com.airsaid.multistatelayout.sample.state.ErrorState

/**
 * @author airsaid
 */
class CustomSampleActivity(
    override val layoutId: Int = R.layout.fragment_multi_state_layout
) : AbstractSampleActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mMultiStateLayout.getState<ErrorState>(ErrorState::class.java).setErrorText("Custom text")
  }
}