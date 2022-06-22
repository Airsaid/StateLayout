package com.airsaid.multistatelayout;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Provides the interface to the state collection.
 *
 * @author airsaid
 */
@FunctionalInterface
public interface StateProvider {

  /**
   * The state collection.
   *
   * @return The state list.
   */
  @NonNull
  List<State> getStates();
}