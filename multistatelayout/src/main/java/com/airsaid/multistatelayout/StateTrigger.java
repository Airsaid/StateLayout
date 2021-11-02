package com.airsaid.multistatelayout;

import androidx.annotation.NonNull;

/**
 * The state trigger abstract class is used to associate data
 * with state and actively trigger state changes when data changes.
 *
 * @param <T> The data type.
 * @author airsaid
 */
public abstract class StateTrigger<T> {

  private MultiStateLayout multiStateLayout;

  /**
   * Attach to {@link MultiStateLayout}.
   *
   * @param multiStateLayout The {@link MultiStateLayout} object to be attach.
   */
  public void attach(@NonNull MultiStateLayout multiStateLayout) {
    this.multiStateLayout = multiStateLayout;
  }

  /**
   * Trigger state changes.
   *
   * @param value The data value.
   */
  public void trigger(@NonNull T value) {
    onTrigger(multiStateLayout, value);
  }

  /**
   * This method is called when the {@link MultiStateLayout} detached from window.
   */
  public void onDetachedFromWindow() {
    // subclasses are implemented on demand
  }

  /**
   * The method is called when the data value changed. Triggers the corresponding state changes.
   *
   * @param multiStateLayout The {@link MultiStateLayout} object.
   * @param value The data value.
   */
  public abstract void onTrigger(@NonNull MultiStateLayout multiStateLayout, @NonNull T value);
}
