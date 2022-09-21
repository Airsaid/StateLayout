package com.airsaid.statelayout.sample

import android.view.Menu
import android.view.MenuItem
import com.airsaid.statelayout.sample.state.NetErrorState
import com.airsaid.statelayout.sample.stateprovider.CustomStateProvider

/**
 * @author airsaid
 */
class CustomStateProviderSampleActivity(
    override val layoutId: Int = R.layout.activity_basic_sample
) : AbstractSampleActivity() {

  override fun initialize() {
    mStateLayout.initStateProvider(CustomStateProvider())
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    super.onCreateOptionsMenu(menu)
    menu.add(4, 4, 4, "Show NetError Layout")
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == 4) {
      mStateLayout.showState(NetErrorState::class.java)
      return true
    }
    return super.onOptionsItemSelected(item)
  }
}