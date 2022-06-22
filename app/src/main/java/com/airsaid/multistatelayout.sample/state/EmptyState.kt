package com.airsaid.multistatelayout.sample.state

import android.view.View
import com.airsaid.multistatelayout.State
import com.airsaid.multistatelayout.sample.R

/**
 * @author airsaid
 */
class EmptyState : State {

  override fun getLayoutId() = R.layout.multi_state_empty

  override fun onFinishInflate(stateView: View) {}
}