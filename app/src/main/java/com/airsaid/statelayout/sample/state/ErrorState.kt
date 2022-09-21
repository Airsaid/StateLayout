package com.airsaid.statelayout.sample.state

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airsaid.statelayout.State
import com.airsaid.statelayout.StateLayout
import com.airsaid.statelayout.sample.R

/**
 * @author airsaid
 */
open class ErrorState : State {
  private var text: String? = null

  override fun getLayoutId() = R.layout.state_error

  override fun onFinishInflate(stateLayout: StateLayout, stateView: View) {
    text?.let {
      stateView.findViewById<TextView>(R.id.errorText).text = text
    }

    stateView.findViewById<Button>(R.id.reload).setOnClickListener {
      stateLayout.dispatchRetryClickListener(it)
    }
  }

  fun setErrorText(text: String?) {
    this.text = text
  }
}
