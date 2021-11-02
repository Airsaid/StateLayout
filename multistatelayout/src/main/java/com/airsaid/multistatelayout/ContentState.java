package com.airsaid.multistatelayout;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * The content state. this is a marker state to mark whether it is a content layout.
 *
 * @author airsaid
 */
public class ContentState implements State {
  public static final int ID = 0;

  @Override
  public int getId() {
    return ID;
  }

  @Override
  public int getLayoutId() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void onFinishInflate(@NonNull View stateView) {
    // do nothing...
  }
}