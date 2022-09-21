package com.airsaid.statelayout;

import android.view.View;

/**
 * Event listener interface after click retry.
 *
 * @author airsaid
 */
@FunctionalInterface
public interface OnRetryClickListener {

  /**
   * Called when a retry view has been clicked.
   *
   * @param view The view that was clicked.
   */
  void onClickRetry(View view);

}
