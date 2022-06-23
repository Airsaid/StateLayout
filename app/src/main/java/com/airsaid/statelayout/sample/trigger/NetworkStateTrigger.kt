package com.airsaid.statelayout.sample.trigger

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Handler
import android.os.Looper
import com.airsaid.statelayout.StateLayout
import com.airsaid.statelayout.StateTrigger
import com.airsaid.statelayout.sample.state.ErrorState

/**
 * A status trigger sample that automatically observes the current network status and
 * automatically sets it to the network exception state when no network is found and
 * to the content state when a normal network connection is found.
 *
 * @property context The [Context] object.
 * @author airsaid
 */
class NetworkStateTrigger(private val context: Context) : StateTrigger<Boolean>() {

  private val mainHandler = Handler(Looper.getMainLooper())

  private val networkCallback by lazy {
    StateNetworkCallback(this)
  }

  override fun onTrigger(stateLayout: StateLayout, isAvailable: Boolean) {
    if (isAvailable) {
      stateLayout.showContent()
    } else {
      stateLayout.showState(ErrorState::class.java)
    }
  }

  override fun onAttachedToWindow() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val cm: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)
      cm.registerDefaultNetworkCallback(networkCallback, mainHandler)
    }
  }

  override fun onDetachedFromWindow() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val cm: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)
      cm.unregisterNetworkCallback(networkCallback)
    }
  }

  private class StateNetworkCallback(
      private val stateTrigger: StateTrigger<Boolean>
  ) : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
      super.onAvailable(network)
      stateTrigger.trigger(true)
    }

    override fun onLost(network: Network) {
      super.onLost(network)
      stateTrigger.trigger(false)
    }
  }
}