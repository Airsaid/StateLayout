package com.airsaid.multistatelayout.sample.trigger

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Handler
import android.os.Looper
import com.airsaid.multistatelayout.MultiStateLayout
import com.airsaid.multistatelayout.StateTrigger
import com.airsaid.multistatelayout.sample.state.ErrorState

/**
 * @author airsaid
 */
class NetworkStateTrigger(private val context: Context) : StateTrigger<Boolean>() {

  private val networkCallback = StateNetworkCallback(this)

  init {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val cm: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)
      cm.registerDefaultNetworkCallback(networkCallback, Handler(Looper.getMainLooper()))
    }
  }

  override fun onTrigger(multiStateLayout: MultiStateLayout, isAvailable: Boolean) {
    if (isAvailable) {
      multiStateLayout.showContent()
    } else {
      multiStateLayout.showState(ErrorState::class.java)
    }
  }

  class StateNetworkCallback(private val stateTrigger: StateTrigger<Boolean>) : ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
      super.onAvailable(network)
      stateTrigger.trigger(true)
    }

    override fun onLost(network: Network) {
      super.onLost(network)
      stateTrigger.trigger(false)
    }
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val cm: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)
      cm.unregisterNetworkCallback(networkCallback)
    }
  }
}