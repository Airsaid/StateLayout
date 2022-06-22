package com.airsaid.statelayout.sample.state

import android.view.View
import com.airsaid.statelayout.State
import com.airsaid.statelayout.sample.R

/**
 * @author airsaid
 */
class LoadingState : State {

  override fun getLayoutId() = R.layout.state_loading

  override fun onFinishInflate(stateView: View) {}
}