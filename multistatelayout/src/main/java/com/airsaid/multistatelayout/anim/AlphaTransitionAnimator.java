package com.airsaid.multistatelayout.anim;

import android.view.View;

import androidx.annotation.NonNull;

import com.airsaid.multistatelayout.MultiStateLayout;
import com.airsaid.multistatelayout.TransitionAnimator;

/**
 * The alpha transition animator.
 *
 * @author airsaid
 */
public class AlphaTransitionAnimator implements TransitionAnimator {
  @Override
  public boolean isPlayTogether() {
    return false;
  }

  @Override
  public void onExitAnimation(@NonNull MultiStateLayout layout, @NonNull View stateView) {
    stateView.animate().alpha(0f);
  }

  @Override
  public void onEntryAnimation(@NonNull MultiStateLayout layout, @NonNull View stateView) {
    stateView.setAlpha(0f);
    stateView.animate().alpha(1f);
  }

  @Override
  public void onReset(@NonNull MultiStateLayout layout, @NonNull View stateView) {
    stateView.setAlpha(1f);
  }
}