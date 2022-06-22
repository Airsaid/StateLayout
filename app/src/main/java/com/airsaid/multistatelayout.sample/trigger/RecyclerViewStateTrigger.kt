package com.airsaid.multistatelayout.sample.trigger

import androidx.recyclerview.widget.RecyclerView
import com.airsaid.multistatelayout.MultiStateLayout
import com.airsaid.multistatelayout.StateTrigger
import com.airsaid.multistatelayout.sample.state.EmptyState

/**
 * @author airsaid
 */
class RecyclerViewStateTrigger(private val adapter: RecyclerView.Adapter<*>) : StateTrigger<Int>() {

  private val adapterDataObserver = InnerAdapterDataObserver(this, adapter)

  init {
    adapter.registerAdapterDataObserver(adapterDataObserver)
  }

  override fun onTrigger(multiStateLayout: MultiStateLayout, count: Int) {
    if (count != 0) {
      multiStateLayout.showContent()
    } else {
      multiStateLayout.showState(EmptyState::class.java)
    }
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    adapter.unregisterAdapterDataObserver(adapterDataObserver)
  }

  class InnerAdapterDataObserver(private val stateTrigger: StateTrigger<Int>,
                                 private val adapter: RecyclerView.Adapter<*>) : RecyclerView.AdapterDataObserver() {
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