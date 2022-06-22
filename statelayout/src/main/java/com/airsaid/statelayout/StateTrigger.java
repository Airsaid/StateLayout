package com.airsaid.statelayout;

import androidx.annotation.NonNull;

/**
 * The state trigger abstract class is used to associate data
 * with state and actively trigger state changes when data changes.
 *
 * @param <T> The data type.
 * @author airsaid
 */
public abstract class StateTrigger<T> {

  private StateLayout stateLayout;

  /**
   * Attach to {@link StateLayout}.
   *
   * @param stateLayout The {@link StateLayout} object to be attach.
   */
  public void attach(@NonNull StateLayout stateLayout) {
    this.stateLayout = stateLayout;
  }

  /**
   * Trigger state changes.
   *
   * @param value The data value.
   */
  public void trigger(@NonNull T value) {
    onTrigger(stateLayout, value);
  }

  /**
   * This method is called when the {@link StateLayout} attached to window.
   */
  public void onAttachedToWindow() {
    // subclasses are implemented on demand
  }

  /**
   * This method is called when the {@link StateLayout} detached from window.
   */
  public void onDetachedFromWindow() {
    // subclasses are implemented on demand
  }

  /**
   * The method is called when the data value changed. Triggers the corresponding state changes.
   *
   * @param stateLayout The {@link StateLayout} object.
   * @param value The data value.
   */
  public abstract void onTrigger(@NonNull StateLayout stateLayout, @NonNull T value);
}