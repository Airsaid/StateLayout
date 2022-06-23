package com.airsaid.statelayout.sample.stateprovider

import com.airsaid.statelayout.sample.state.NetErrorState

/**
 * @author airsaid
 */
class CustomStateProvider : GlobalStateProvider() {
  override fun getStates() = super.getStates().apply {
    add(NetErrorState())
  }
}