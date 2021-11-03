package com.airsaid.multistatelayout.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airsaid.multistatelayout.sample.state.ErrorState
import com.cz.android.sample.api.Register
import com.cz.android.sample.library.component.code.SampleSourceCode

/**
 * @author airsaid
 */
@Register(title = "Custom UI", priority = 3)
@SampleSourceCode
class MultiStateLayoutCustomFragment : BaseMultiStateLayoutFragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_multi_state_layout, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mMultiStateLayout.getState<ErrorState>(ErrorState.ID).setErrorText("Custom text")
  }
}