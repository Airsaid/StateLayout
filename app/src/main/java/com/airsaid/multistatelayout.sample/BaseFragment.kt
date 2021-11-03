package com.airsaid.multistatelayout.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cz.android.sample.api.Exclude

/**
 * @author airsaid
 */
@Exclude
abstract class BaseFragment : Fragment() {

  private lateinit var mContentView: View

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mContentView = view
  }

  fun <T : View> findViewById(id: Int): T {
    return mContentView.findViewById(id)
  }
}