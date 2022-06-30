package com.airsaid.statelayout;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airsaid.statelayout.anim.AlphaTransitionAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This layout dynamically manages multiple layouts in different states
 * and can be dynamically switched among them.
 * <p>
 * The child views of this layout is the content view, call to
 * {@link #showContent()} method to display it.
 * <p>
 * How do use {@link StateLayout}?
 * <p>
 * First, you need to call the {@link #initStateProvider(StateProvider)}
 * or {@link StateLayout#setGlobalStateProvider(StateProvider)} methods
 * to provide the state to be managed.
 * <p>
 * Then, you can call the {@link #showState(Class)} or {@link #showState(State)} methods
 * to switch the layout of different state.
 * <p>
 *
 * @author airsaid
 */
public class StateLayout extends FrameLayout {

  private static StateProvider sGlobalStateProvider;

  private final Queue<State> mPendingStates = new LinkedList<>();

  private TransitionAnimator mTransitionAnimator = new AlphaTransitionAnimator();

  private StateProvider mStateProvider;
  private Map<Class<? extends State>, State> mStates;
  private Class<? extends State> mCurrentStateClass;

  private List<OnStateChangedListener> mOnStateChangedListeners;
  private List<StateTrigger<?>> mStateTriggers;

  private View mContentView;

  public StateLayout(@NonNull Context context) {
    super(context);
  }

  public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (getChildCount() != 1) {
      mContentView = warpContentView();
    } else {
      mContentView = getChildAt(0);
    }
    mCurrentStateClass = ContentState.class;
  }

  /**
   * Initialize the layout. provide the state to be managed.
   *
   * @param stateProvider The {@link StateProvider} instance object.
   * @throws IllegalStateException If you call the method multiple times.
   * @throws IllegalArgumentException If state does not provide.
   */
  public void initStateProvider(@NonNull StateProvider stateProvider) {
    if (isInitStates()) {
      throw new IllegalStateException("Only need to initialize once.");
    }
    List<State> states = stateProvider.getStates();
    if (states.isEmpty()) {
      throw new IllegalArgumentException("States is empty.");
    }
    mStateProvider = stateProvider;
  }

  /**
   * Returns whether the StateLayout is initialized.
   *
   * @return true: initialized, false: uninitialized.
   */
  public boolean isInitStates() {
    return mStates != null && mStates.size() > 0;
  }

  /**
   * Show the content layout, which is the first child view.
   */
  public void showContent() {
    showState(ContentState.class);
  }

  /**
   * Show the layout of the specified {@code stateClass}.
   *
   * @param stateClass The state class to be showed.
   */
  public void showState(Class<? extends State> stateClass) {
    showState(getState(stateClass));
  }

  /**
   * Show the layout of the specified {@code state}.
   *
   * @param state The state to be showed.
   */
  public void showState(@NonNull State state) {
    boolean hasPendingStates = !mPendingStates.isEmpty();
    mPendingStates.add(state);
    if (!hasPendingStates) {
      setCurrentState(state);
    }
  }

  /**
   * The {@link State} object of the specified {@code stateClass}.
   *
   * @param stateClass The class of the {@link State} object.
   * @param <T> The state instance type.
   * @return The {@link State} object.
   * @throws IllegalArgumentException If not found {@link State} object for the {@code stateClass}.
   */
  @SuppressWarnings("unchecked")
  public <T extends State> T getState(Class<T> stateClass) {
    State state = getStates().get(stateClass);
    if (state == null) {
      throw new IllegalArgumentException("Not found state for " + stateClass + " state class.");
    }
    return (T) state;
  }

  /**
   * The state view of the specified {@code stateClass}.
   *
   * @param stateClass The class of the {@link State} object.
   * @return The view of the state.
   */
  @NonNull
  public View getStateView(Class<? extends State> stateClass) {
    return getStateView(getState(stateClass));
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
    View stateView = findViewWithTag(state);
    if (stateView == null) {
      stateView = LayoutInflater.from(getContext()).inflate(state.getLayoutId(), this, false);
      stateView.setVisibility(View.INVISIBLE);
      stateView.setTag(state);
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

  /**
   * Remove all the state triggers.
   */
  public void clearStateTriggers() {
    if (mStateTriggers == null) return;

    mStateTriggers.clear();
  }

  /**
   * Sets the global {@link StateProvider} object to avoid the need to call
   * the {@link #initStateProvider} method to provide states before each use.
   * <p>
   * When the {@link #initStateProvider} method is called, the {@link StateProvider}
   * specified by the method is used to operation the state.
   * <p>
   *
   * @param globalStateProvider The {@link StateProvider} object.
   */
  public static void setGlobalStateProvider(StateProvider globalStateProvider) {
    sGlobalStateProvider = globalStateProvider;
  }

  private View warpContentView() {
    FrameLayout wrapperLayout = new FrameLayout(getContext());
    int childCount = getChildCount();
    if (childCount > 0) {
      List<View> childViews = new ArrayList<>(childCount);
      for (int i = 0; i < childCount; i++) {
        View childView = getChildAt(i);
        childViews.add(childView);
      }
      removeAllViewsInLayout();
      for (View childView : childViews) {
        wrapperLayout.addView(childView);
      }
    }
    addView(wrapperLayout);
    return wrapperLayout;
  }

  private Map<Class<? extends State>, State> getStates() {
    if (isInitStates()) return mStates;

    mStates = new HashMap<>();
    mStates.put(ContentState.class, new ContentState());
    if (mStateProvider == null && sGlobalStateProvider == null) {
      throw new IllegalArgumentException("StateProvider not set. " +
          "Please call stateLayout.initStateProvider() or " +
          "StateLayout.setGlobalStateProvider() to set.");
    }
    StateProvider stateProvider = mStateProvider != null ? mStateProvider : sGlobalStateProvider;
    List<State> states = stateProvider.getStates();
    for (State state : states) {
      mStates.put(state.getClass(), state);
    }
    return mStates;
  }

  private void dispatchStateChangedListener(@NonNull State state, boolean isShow) {
    if (isShow) onStateChanged();

    if (mOnStateChangedListeners == null) return;

    int listenerCount = mOnStateChangedListeners.size();
    for (int i = listenerCount - 1; i >= 0; i--) {
      mOnStateChangedListeners.get(i).onStateChanged(state, isShow);
    }
  }

  private void onStateChanged() {
    // The current state is processed and removed
    if (!mPendingStates.isEmpty()) {
      mPendingStates.remove();
    }
    // Process the latest state
    while (!mPendingStates.isEmpty()) {
      State latestState = mPendingStates.remove();
      if (mPendingStates.peek() == null) {
        mPendingStates.add(latestState);
        setCurrentState(latestState);
        break;
      }
    }
  }

  private void setCurrentState(@NonNull State newState) {
    if (mCurrentStateClass == newState.getClass()) {
      onStateChanged();
      return;
    }

    State currentState = getState(mCurrentStateClass);
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
    mCurrentStateClass = newState.getClass();
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
    savedState.currentStateClass = mCurrentStateClass;
    return savedState;
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    if (state instanceof StateSavedState) {
      StateSavedState savedState = (StateSavedState) state;
      super.onRestoreInstanceState(savedState.getSuperState());
      showState(savedState.currentStateClass);
    } else {
      super.onRestoreInstanceState(state);
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    attachedStateTriggers();
  }

  private void attachedStateTriggers() {
    if (mStateTriggers == null) return;

    for (StateTrigger<?> stateTrigger : mStateTriggers) {
      stateTrigger.onAttachedToWindow();
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mPendingStates.clear();
    detachedStateTriggers();
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