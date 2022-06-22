package com.airsaid.statelayout.anim;

import android.view.View;

import androidx.annotation.NonNull;

import com.airsaid.statelayout.StateLayout;

/**
 * The alpha and translation transition animator.
 *
 * @author airsaid
 */
public class AlphaTranslationTransitionAnimator extends TranslationTransitionAnimator {

  private final AlphaTransitionAnimator mAlphaTransitionAnimator = new AlphaTransitionAnimator();

  @Override
  public boolean isPlayTogether() {
    return true;
  }

  @Override
  public void onExitAnimation(@NonNull StateLayout layout, @NonNull View stateView) {
    super.onExitAnimation(layout, stateView);
    mAlphaTransitionAnimator.onExitAnimation(layout, stateView);
  }

  @Override
  public void onEntryAnimation(@NonNull StateLayout layout, @NonNull View stateView) {
    super.onEntryAnimation(layout, stateView);
    mAlphaTransitionAnimator.onEntryAnimation(layout, stateView);
  }

  @Override
  public void onReset(@NonNull StateLayout layout, @NonNull View stateView) {
    super.onReset(layout, stateView);
    mAlphaTransitionAnimator.onReset(layout, stateView);
  }
}