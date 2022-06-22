package com.airsaid.statelayout.anim;

import android.view.View;

import androidx.annotation.NonNull;

import com.airsaid.statelayout.StateLayout;
import com.airsaid.statelayout.TransitionAnimator;

/**
 * The translation transition animator. default direction: right to left.
 *
 * @author airsaid
 */
public class TranslationTransitionAnimator implements TransitionAnimator {

  private Direction mTranslationDirection = Direction.RIGHT_TO_LEFT;

  public enum Direction {
    RIGHT_TO_LEFT,
    LEFT_TO_RIGHT,
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP
  }

  public void setTranslationDirection(@NonNull Direction direction) {
    mTranslationDirection = direction;
  }

  public Direction getTranslationDirection() {
    return mTranslationDirection;
  }

  @Override
  public boolean isPlayTogether() {
    return true;
  }

  @Override
  public void onExitAnimation(@NonNull StateLayout layout, @NonNull View stateView) {
    if (mTranslationDirection == Direction.RIGHT_TO_LEFT) {
      stateView.animate().translationX(-stateView.getWidth());
    } else if (mTranslationDirection == Direction.LEFT_TO_RIGHT) {
      stateView.animate().translationX(stateView.getWidth());
    } else if (mTranslationDirection == Direction.TOP_TO_BOTTOM) {
      stateView.animate().translationY(stateView.getHeight());
    } else if (mTranslationDirection == Direction.BOTTOM_TO_TOP) {
      stateView.animate().translationY(-stateView.getHeight());
    }
  }

  @Override
  public void onEntryAnimation(@NonNull StateLayout layout, @NonNull View stateView) {
    if (mTranslationDirection == Direction.RIGHT_TO_LEFT) {
      stateView.setTranslationX(stateView.getWidth());
      stateView.animate().translationX(0f);
    } else if (mTranslationDirection == Direction.LEFT_TO_RIGHT) {
      stateView.setTranslationX(-stateView.getWidth());
      stateView.animate().translationX(0f);
    } else if (mTranslationDirection == Direction.TOP_TO_BOTTOM) {
      stateView.setTranslationY(-stateView.getHeight());
      stateView.animate().translationY(0f);
    } else if (mTranslationDirection == Direction.BOTTOM_TO_TOP) {
      stateView.setTranslationY(stateView.getHeight());
      stateView.animate().translationY(0f);
    }
  }

  @Override
  public void onReset(@NonNull StateLayout layout, @NonNull View stateView) {
    stateView.setTranslationX(0f);
    stateView.setTranslationY(0f);
  }
}
