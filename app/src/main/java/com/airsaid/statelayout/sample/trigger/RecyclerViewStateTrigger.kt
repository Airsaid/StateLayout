package com.airsaid.statelayout.sample.trigger

import androidx.recyclerview.widget.RecyclerView
import com.airsaid.statelayout.StateLayout
import com.airsaid.statelayout.StateTrigger
import com.airsaid.statelayout.sample.state.EmptyState

/**
 * A state trigger sample that passes in the specified [adapter] object for observation
 * and automatically sets the empty data state when the data size is 0,
 * and the content state when there is data.
 *
 * @property adapter The [RecyclerView.Adapter] object being watched.
 * @author airsaid
 */
class RecyclerViewStateTrigger(
    private val adapter: RecyclerView.Adapter<*>,
) : StateTrigger<Int>() {

  private val adapterDataObserver by lazy {
    StateAdapterDataObserver(this, adapter)
  }

  override fun onTrigger(stateLayout: StateLayout, count: Int) {
    if (count != 0) {
      stateLayout.showContent()
    } else {
      stateLayout.showState(EmptyState::class.java)
    }
  }

  override fun onAttachedToWindow() {
    adapter.registerAdapterDataObserver(adapterDataObserver)
  }

  override fun onDetachedFromWindow() {
    adapter.unregisterAdapterDataObserver(adapterDataObserver)
  }

  private class StateAdapterDataObserver(
      private val stateTrigger: StateTrigger<Int>,
      private val adapter: RecyclerView.Adapter<*>
  ) : RecyclerView.AdapterDataObserver() {
    override fun onChanged() {
      super.onChanged()
      dataChanged()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
      super.onItemRangeRemoved(positionStart, itemCount)
      dataChanged()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
      super.onItemRangeInserted(positionStart, itemCount)
      dataChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
      super.onItemRangeChanged(positionStart, itemCount)
      dataChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
      super.onItemRangeChanged(positionStart, itemCount, payload)
      dataChanged()
    }

    private fun dataChanged() {
      stateTrigger.trigger(adapter.itemCount)
    }
  }
}