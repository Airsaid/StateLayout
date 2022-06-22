package com.airsaid.multistatelayout.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.airsaid.multistatelayout.sample.trigger.RecyclerViewStateTrigger
import com.airsaid.multistatelayout.sample.trigger.NetworkStateTrigger
import kotlin.random.Random

/**
 * @author airsaid
 */
class StateTriggerSampleActivity(
    override val layoutId: Int = R.layout.fragment_multi_state_layout_recycler_view
) : AbstractSampleActivity() {

  private val mRecyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }

  @SuppressLint("NotifyDataSetChanged")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val mockData = getMockData()
    val sampleAdapter = SampleAdapter(mockData)
    mMultiStateLayout.addStateTrigger(RecyclerViewStateTrigger(sampleAdapter))
    mMultiStateLayout.addStateTrigger(NetworkStateTrigger(applicationContext))

    mRecyclerView.adapter = sampleAdapter

    findViewById<Button>(R.id.setEmptyData).setOnClickListener {
      mockData.clear()
      sampleAdapter.notifyDataSetChanged()
    }

    findViewById<Button>(R.id.setFullData).setOnClickListener {
      mockData.clear()
      mockData.addAll(getMockData())
      sampleAdapter.notifyDataSetChanged()
    }
  }

  private fun getMockData(): MutableList<String> {
    val result = mutableListOf<String>()
    for (i in 0..Random.nextInt(10, 100)) {
      result.add("Item$i")
    }
    return result
  }

  private class SampleAdapter(private val data: MutableList<String>) : RecyclerView.Adapter<SampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SampleViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
      holder.text.text = data[position]
    }

    override fun getItemCount() = data.size
  }

  private class SampleViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewById(android.R.id.text1)

    companion object {
      fun newInstance(parent: ViewGroup) = SampleViewHolder(
          LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false))
    }
  }
}