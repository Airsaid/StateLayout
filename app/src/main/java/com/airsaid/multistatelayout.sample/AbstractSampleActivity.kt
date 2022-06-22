package com.airsaid.multistatelayout.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.airsaid.multistatelayout.MultiStateLayout
import com.airsaid.multistatelayout.sample.state.EmptyState
import com.airsaid.multistatelayout.sample.state.ErrorState
import com.airsaid.multistatelayout.sample.state.LoadingState

/**
 * @author airsaid
 */
abstract class AbstractSampleActivity : AppCompatActivity() {

  abstract val layoutId: Int

  protected val mMultiStateLayout: MultiStateLayout by lazy {
    (findViewById<MultiStateLayout>(R.id.multiStateLayout)).apply {
      init(CommonStateProvider())
    }
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutId)
    mMultiStateLayout.getState<ErrorState>(ErrorState::class.java).setOnReloadListener {
      mMultiStateLayout.showState(LoadingState::class.java)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menu.add(0, 0, 0, "Show Content Layout")
    menu.add(1, 1, 1, "Show Loading Layout")
    menu.add(2, 2, 2, "Show Empty Layout")
    menu.add(3, 3, 3, "Show Error Layout")
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      0 -> {
        mMultiStateLayout.showContent()
        return true
      }
      1 -> {
        mMultiStateLayout.showState(LoadingState::class.java)
        return true
      }
      2 -> {
        mMultiStateLayout.showState(EmptyState::class.java)
        return true
      }
      3 -> {
        mMultiStateLayout.showState(ErrorState::class.java)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}