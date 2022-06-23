package com.airsaid.statelayout.sample.stateprovider

import com.airsaid.statelayout.StateProvider
import com.airsaid.statelayout.sample.state.EmptyState
import com.airsaid.statelayout.sample.state.ErrorState
import com.airsaid.statelayout.sample.state.LoadingState

/**
 * @author airsaid
 */
open class GlobalStateProvider : StateProvider {
  override fun getStates() = mutableListOf(
      EmptyState(),
      ErrorState(),
      LoadingState()
  )
}