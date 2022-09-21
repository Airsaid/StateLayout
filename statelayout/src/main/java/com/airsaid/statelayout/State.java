package com.airsaid.statelayout;

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
   * @deprecated Use {@link #onFinishInflate(StateLayout, View)} instead.
   */
  @Deprecated
  default void onFinishInflate(@NonNull View stateView) {

  }

  /**
   * This method is called when the view is inflate for the first time.
   *
   * @param stateLayout The stateLayout that currently hosts the state.
   * @param stateView The inflated view of the state.
   */
  default void onFinishInflate(@NonNull StateLayout stateLayout, @NonNull View stateView) {
    onFinishInflate(stateView);
  }
}
