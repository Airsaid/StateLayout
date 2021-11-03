package com.airsaid.multistatelayout.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.airsaid.multistatelayout.MultiStateLayout
import com.airsaid.multistatelayout.sample.state.EmptyState
import com.airsaid.multistatelayout.sample.state.ErrorState
import com.airsaid.multistatelayout.sample.state.LoadingState
import com.cz.android.sample.api.Exclude

/**
 * @author airsaid
 */
@Exclude
abstract class BaseMultiStateLayoutFragment : BaseFragment() {

  protected val mMultiStateLayout: MultiStateLayout by lazy {
    (findViewById<MultiStateLayout>(R.id.multiStateLayout)).apply {
      init(CommonStateProvider())
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mMultiStateLayout.getState<ErrorState>(ErrorState.ID).setOnReloadListener {
      mMultiStateLayout.showState(LoadingState.ID)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    menu.add(0, 0, 0, "Show Content Layout")
    menu.add(1, 1, 1, "Show Loading Layout")
    menu.add(2, 2, 2, "Show Empty Layout")
    menu.add(3, 3, 3, "Show Error Layout")
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      0 -> {
        mMultiStateLayout.showContent()
        return true
      }
      1 -> {
        mMultiStateLayout.showState(LoadingState.ID)
        return true
      }
      2 -> {
        mMultiStateLayout.showState(EmptyState.ID)
        return true
      }
      3 -> {
        mMultiStateLayout.showState(ErrorState.ID)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}