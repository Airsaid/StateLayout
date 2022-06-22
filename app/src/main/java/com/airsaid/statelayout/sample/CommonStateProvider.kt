package com.airsaid.statelayout.sample

import com.airsaid.statelayout.State
import com.airsaid.statelayout.StateProvider
import com.airsaid.statelayout.sample.state.EmptyState
import com.airsaid.statelayout.sample.state.ErrorState
import com.airsaid.statelayout.sample.state.LoadingState

/**
 * @author airsaid
 */
class CommonStateProvider : StateProvider {
  override fun getStates(): MutableList<State> {
    return mutableListOf(EmptyState(), ErrorState(), LoadingState())
  }
}