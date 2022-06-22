package com.airsaid.statelayout;

import androidx.annotation.NonNull;

/**
 * The listener that will be called when state change.
 *
 * @author airsaid
 */
public interface OnStateChangedListener {

  /**
   * The method is called when the state changes.
   *
   * @param state The changed state.
   * @param isShow The state is show or hide.
   */
  void onStateChanged(@NonNull State state, boolean isShow);
}