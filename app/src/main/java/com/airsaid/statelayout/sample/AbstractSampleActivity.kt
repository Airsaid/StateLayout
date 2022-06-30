package com.airsaid.statelayout.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.airsaid.statelayout.StateLayout
import com.airsaid.statelayout.sample.state.EmptyState
import com.airsaid.statelayout.sample.state.ErrorState
import com.airsaid.statelayout.sample.state.LoadingState
import kotlin.random.Random

/**
 * @author airsaid
 */
abstract class AbstractSampleActivity : AppCompatActivity() {

  abstract val layoutId: Int

  abstract fun initialize()

  protected val mStateLayout: StateLayout by lazy {
    findViewById(R.id.stateLayout)
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutId)
    initialize()
    mStateLayout.getState(ErrorState::class.java).setOnReloadListener {
      startLoadingData()
    }
  }

  private fun startLoadingData() {
    mStateLayout.showState(LoadingState::class.java)
    mStateLayout.postDelayed(Random.nextLong(0, 2000)) {
      if (Random.nextBoolean()) {
        mStateLayout.showContent()
      } else {
        if (Random.nextBoolean()) {
          mStateLayout.showState(EmptyState::class.java)
        } else {
          mStateLayout.showState(ErrorState::class.java)
        }
      }
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
        mStateLayout.showContent()
        return true
      }
      1 -> {
        mStateLayout.showState(LoadingState::class.java)
        return true
      }
      2 -> {
        mStateLayout.showState(EmptyState::class.java)
        return true
      }
      3 -> {
        mStateLayout.showState(ErrorState::class.java)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}