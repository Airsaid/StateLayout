package com.airsaid.multistatelayout;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Provides the interface to the state collection.
 *
 * @author airsaid
 */
public interface StateProvider {

  /**
   * The state collection.
   *
   * @return The state list.
   */
  @NonNull
  List<State> getStates();
}