package com.airsaid.statelayout;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * The transition animation between state switching.
 *
 * @author airsaid
 */
public interface TransitionAnimator {

  /**
   * Whether to play the entry and exit animations together.
   *
   * @return {@code true}: play together. {@code false}: no.
   */
  boolean isPlayTogether();

  /**
   * This method is called when the state view exit.
   *
   * @param layout The {@link StateLayout} object.
   * @param stateView The state view to be exit.
   */
  void onExitAnimation(@NonNull StateLayout layout, @NonNull View stateView);

  /**
   * This method is called when the state view entry.
   *
   * @param layout The {@link StateLayout} object.
   * @param stateView The state view to be entry.
   */
  void onEntryAnimation(@NonNull StateLayout layout, @NonNull View stateView);

  /**
   * This method is called when the state view exit finish.
   * <p>
   * Restore the view for the next call.
   *
   * @param layout The {@link StateLayout} object.
   * @param stateView The state view to be exit finish.
   */
  void onReset(@NonNull StateLayout layout, @NonNull View stateView);
}
