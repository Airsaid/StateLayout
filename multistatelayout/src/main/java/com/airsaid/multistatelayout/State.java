package com.airsaid.multistatelayout;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * The state interface.
 *
 * @author airsaid
 */
public interface State {

  /**
   * The id of the state. it has to be unique.
   *
   * @return The id of the state.
   */
  int getId();

  /**
   * The layout id of the state.
   *
   * @return The layout id of the state.
   */
  @LayoutRes
  int getLayoutId();

  /**
   * This method is called when the view is inflate for the first time.
   *
   * @param stateView The inflated view of the state.
   */
  void onFinishInflate(@NonNull View stateView);
}