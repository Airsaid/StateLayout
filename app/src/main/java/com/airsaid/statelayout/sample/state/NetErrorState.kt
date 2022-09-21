package com.airsaid.statelayout.sample.state

import android.view.View
import com.airsaid.statelayout.StateLayout

/**
 * @author airsaid
 */
class NetErrorState : ErrorState() {
  override fun onFinishInflate(stateLayout: StateLayout, stateView: View) {
    setErrorText("Net Error")
    super.onFinishInflate(stateLayout, stateView)
  }
}
