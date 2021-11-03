package com.airsaid.multistatelayout.sample.state

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airsaid.multistatelayout.State
import com.airsaid.multistatelayout.sample.R

/**
 * @author airsaid
 */
class ErrorState : State {
  companion object {
    const val ID = R.id.errorLayout
  }

  private lateinit var callback: () -> Unit?
  private var text: String? = null

  override fun getId() = ID

  override fun getLayoutId() = R.layout.multi_state_error

  override fun onFinishInflate(stateView: View) {
    text?.let {
      stateView.findViewById<TextView>(R.id.errorText).text = text
    }

    stateView.findViewById<Button>(R.id.reload).setOnClickListener {
      callback.invoke()
    }
  }

  fun setOnReloadListener(callback: () -> Unit) {
    this.callback = callback
  }

  fun setErrorText(text: String?) {
    this.text = text
  }
}