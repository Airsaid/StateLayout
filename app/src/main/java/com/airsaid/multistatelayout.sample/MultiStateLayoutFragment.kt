package com.airsaid.multistatelayout.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cz.android.sample.api.Register
import com.cz.android.sample.library.component.code.SampleSourceCode
import com.cz.android.sample.library.component.message.SampleMessage

/**
 * @author airsaid
 */
@Register(title = "Basic", priority = 1)
@SampleSourceCode
@SampleMessage
class MultiStateLayoutFragment : BaseMultiStateLayoutFragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_multi_state_layout, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mMultiStateLayout.addStateChangedListener { state, isShow ->
      println("onStateChanged state: $state, isShow: $isShow")
    }
  }
}