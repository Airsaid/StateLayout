package com.airsaid.statelayout.sample.state

import android.view.View

/**
 * @author airsaid
 */
class NetErrorState : ErrorState() {
  override fun onFinishInflate(stateView: View) {
    setErrorText("Net Error")
    super.onFinishInflate(stateView)
  }
}