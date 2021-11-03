package com.airsaid.multistatelayout.sample

import com.airsaid.multistatelayout.State
import com.airsaid.multistatelayout.StateProvider
import com.airsaid.multistatelayout.sample.state.EmptyState
import com.airsaid.multistatelayout.sample.state.ErrorState
import com.airsaid.multistatelayout.sample.state.LoadingState

/**
 * @author airsaid
 */
class CommonStateProvider : StateProvider {
  override fun getStates(): MutableList<State> {
    return mutableListOf(EmptyState(), ErrorState(), LoadingState())
  }
}