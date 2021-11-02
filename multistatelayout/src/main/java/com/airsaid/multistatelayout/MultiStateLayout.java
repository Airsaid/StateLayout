package com.airsaid.multistatelayout;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airsaid.multistatelayout.anim.AlphaTransitionAnimator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This layout dynamically manages multiple layouts in different states
 * and can be dynamically switched among them.
 * <p>
 * The layout only allows one child view, as the content view, call to
 * {@link #showContent()} method to display it.
 * <p>
 * First, you need to call the {@link #init(StateProvider)} method to
 * provide the state to be managed.
 * <p>
 * Then, you can call the {@link #showState(int)} method to switch the
 * layout of different state.
 * <p>
 *
 * @author airsaid
 */
public class MultiStateLayout extends FrameLayout {

  private final SparseArray<State> mStates = new SparseArray<>();
  private TransitionAnimator mTransitionAnimator = new AlphaTransitionAnimator();

  private View mContentView;
  private int mCurrentStateId;
  private List<OnStateChangedListener> mOnStateChangedListeners;
  private List<StateTrigger<?>> mStateTriggers;

  public MultiStateLayout(@NonNull Context context) {
    super(context);
  }

  public MultiStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public MultiStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (getChildCount() != 1) {
      throw new IllegalStateException("MultiStateLayout can host only one direct child");
    }
    mContentView = getChildAt(0);
    mCurrentStateId = ContentState.ID;
  }

  /**
   * Initialize the layout. provide the state to be managed.
   *
   * @param stateProvider The {@link StateProvider} instance object.
   * @throws IllegalStateException If you call the method multiple times.
   * @throws IllegalArgumentException If state does not provide.
   */
  public void init(@NonNull StateProvider stateProvider) {
    if (mStates.size() > 0) {
      throw new IllegalStateException("Only need to initialize once.");
    }
    List<State> states = stateProvider.getStates();
    if (states.isEmpty()) {
      throw new IllegalArgumentException("States is empty.");
    }
    mStates.put(ContentState.ID, new ContentState());
    for (State state : states) {
      mStates.append(state.getId(), state);
    }
  }

  /**
   * Show the content layout, which is the first child view.
   */
  public void showContent() {
    showState(ContentState.ID);
  }

  /**
   * Show the layout of the specified {@code stateId}.
   *
   * @param stateId The state id to be showed.
   */
  public void showState(int stateId) {
    showState(getState(stateId));
  }

  /**
   * Show the layout of the specified {@code state}.
   *
   * @param state The state to be showed.
   */
  public void showState(@NonNull State state) {
    setCurrentState(state);
  }

  /**
   * The {@link State} object of the specified {@code stateId}.
   *
   * @param stateId The state id of the {@link State} object.
   * @param <T> The state instance type.
   * @return The {@link State} object.
   * @throws IllegalArgumentException If not found {@link State} object for the {@code stateId}.
   */
  @SuppressWarnings("unchecked")
  public <T extends State> T getState(int stateId) {
    State state = mStates.get(stateId);
    if (state == null) {
      throw new IllegalArgumentException("Not found state for " + stateId + " state id.");
    }
    return (T) state;
  }

  /**
   * The state view of the specified {@code stateId}.
   *
   * @param stateId The state id of the state view.
   * @return The view of the state.
   */
  @NonNull
  public View getStateView(int stateId) {
    return getStateView(getState(stateId));
  }

  /**
   * The state view of the specified {@code state}.
   *
   * @param state The {@link State} object.
   * @return The view of the state.
   */
  @NonNull
  public View getStateView(@NonNull State state) {
    if (state instanceof ContentState) {
      return mContentView;
    }
    View stateView = findViewById(state.getId());
    if (stateView == null) {
      stateView = LayoutInflater.from(getContext()).inflate(state.getLayoutId(), this, false);
      stateView.setVisibility(View.INVISIBLE);
      addView(stateView);
      state.onFinishInflate(stateView);
    }
    return stateView;
  }

  /**
   * Sets the transition animation between state switching.
   * default animation: {@link AlphaTransitionAnimator}.
   *
   * @param transitionAnimator The transition animation. if {@code null}, then no animation.
   */
  public void setTransitionAnimator(@Nullable TransitionAnimator transitionAnimator) {
    mTransitionAnimator = transitionAnimator;
  }

  /**
   * The transition animation between state switching.
   *
   * @return The {@link TransitionAnimator} instance object.
   */
  @Nullable
  public TransitionAnimator getTransitionAnimator() {
    return mTransitionAnimator;
  }

  /**
   * Add a listener that will be called when the state changes.
   *
   * @param listener The listener that will be called when state change.
   */
  public void addStateChangedListener(@NonNull OnStateChangedListener listener) {
    if (mOnStateChangedListeners == null) {
      mOnStateChangedListeners = new ArrayList<>();
    }
    mOnStateChangedListeners.add(listener);
  }

  /**
   * Remove a listener for state changes.
   *
   * @param listener The listener for state change.
   */
  public void removeStateChangedListener(@NonNull OnStateChangedListener listener) {
    if (mOnStateChangedListeners == null) return;

    mOnStateChangedListeners.remove(listener);
  }

  /**
   * Add the state trigger. It's automatically removed in onDetachedFromWindow.
   *
   * @param stateTrigger The {@link StateTrigger} instance object.
   */
  public void addStateTrigger(@NonNull StateTrigger<?> stateTrigger) {
    if (mStateTriggers == null) {
      mStateTriggers = new ArrayList<>();
    }
    stateTrigger.attach(this);
    mStateTriggers.add(stateTrigger);
  }

  /**
   * Remove the state trigger.
   *
   * @param stateTrigger The {@link StateTrigger} instance object.
   */
  public void removeStateTrigger(@NonNull StateTrigger<?> stateTrigger) {
    if (mStateTriggers == null) return;

    mStateTriggers.remove(stateTrigger);
  }

  private void dispatchStateChangedListener(@NonNull State state, boolean isShow) {
    if (mOnStateChangedListeners == null) return;

    int listenerCount = mOnStateChangedListeners.size();
    for (int i = listenerCount - 1; i >= 0; i--) {
      mOnStateChangedListeners.get(i).onStateChanged(state, isShow);
    }
  }

  private void setCurrentState(@NonNull State newState) {
    if (mCurrentStateId == newState.getId()) return;

    State currentState = getState(mCurrentStateId);
    View currentStateView = getStateView(currentState);
    View newStateView = getStateView(newState);
    if (mTransitionAnimator != null) {
      if (newStateView.isLaidOut()) {
        playTransitionAnimation(currentState, newState, currentStateView, newStateView);
      } else {
        newStateView.post(() -> playTransitionAnimation(currentState, newState, currentStateView, newStateView));
      }
    } else {
      currentStateView.setVisibility(View.INVISIBLE);
      dispatchStateChangedListener(currentState, false);
      newStateView.setVisibility(View.VISIBLE);
      dispatchStateChangedListener(newState, true);
    }
    mCurrentStateId = newState.getId();
  }

  private void playTransitionAnimation(@NonNull State currentState, @NonNull State newState, @NonNull View exitView, @NonNull View entryView) {
    if (mTransitionAnimator == null) return;

    if (mTransitionAnimator.isPlayTogether()) {
      ViewPropertyAnimator exitAnimate = exitView.animate();
      exitAnimate.withEndAction(() -> {
        exitView.setVisibility(View.INVISIBLE);
        mTransitionAnimator.onReset(this, exitView);
        dispatchStateChangedListener(currentState, false);
      });
      mTransitionAnimator.onExitAnimation(this, exitView);

      ViewPropertyAnimator entryAnimate = entryView.animate();
      entryAnimate.withStartAction(() -> entryView.setVisibility(View.VISIBLE));
      entryAnimate.withEndAction(() -> dispatchStateChangedListener(newState, true));
      mTransitionAnimator.onEntryAnimation(this, entryView);

      exitAnimate.start();
      entryAnimate.start();
    } else {
      ViewPropertyAnimator exitAnimate = exitView.animate();
      ViewPropertyAnimator entryAnimate = entryView.animate();
      exitAnimate.withEndAction(() -> {
        exitView.setVisibility(View.INVISIBLE);
        mTransitionAnimator.onReset(this, exitView);
        dispatchStateChangedListener(currentState, false);
        entryView.setVisibility(View.VISIBLE);
        mTransitionAnimator.onEntryAnimation(this, entryView);
        entryAnimate.start();
      });
      entryAnimate.withEndAction(() -> dispatchStateChangedListener(newState, true));
      mTransitionAnimator.onExitAnimation(this, exitView);
      exitAnimate.start();
    }
  }

  @Nullable
  @Override
  protected Parcelable onSaveInstanceState() {
    StateSavedState savedState = new StateSavedState(super.onSaveInstanceState());
    savedState.currentStateId = mCurrentStateId;
    return savedState;
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    if (state instanceof StateSavedState) {
      StateSavedState savedState = (StateSavedState) state;
      super.onRestoreInstanceState(savedState.getSuperState());
      setCurrentState(getState(savedState.currentStateId));
    } else {
      super.onRestoreInstanceState(state);
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    detachedStateTriggers();
    super.onDetachedFromWindow();
  }

  private void detachedStateTriggers() {
    if (mStateTriggers == null) return;

    Iterator<StateTrigger<?>> iterator = mStateTriggers.iterator();
    while (iterator.hasNext()) {
      iterator.next().onDetachedFromWindow();
      iterator.remove();
    }
  }
}