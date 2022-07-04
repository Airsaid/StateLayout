package com.airsaid.statelayout.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.airsaid.statelayout.StateLayout
import com.airsaid.statelayout.sample.ViewPagerSampleActivity.ContentSampleFragment.Companion.KEY_POS
import com.airsaid.statelayout.sample.state.EmptyState
import com.airsaid.statelayout.sample.state.ErrorState
import com.airsaid.statelayout.sample.state.LoadingState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.random.Random

/**
 * @author airsaid
 */
class ViewPagerSampleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_view_pager_sample)
    val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
    val viewPager = findViewById<ViewPager2>(R.id.viewPager)

    viewPager.adapter = PagerSampleAdapter(this)
    TabLayoutMediator(tabLayout, viewPager, true, false) { tab, position ->
      tab.text = "Tab $position"
    }.attach()
  }

  private class PagerSampleAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount() = 6

    override fun createFragment(position: Int) = ContentSampleFragment().apply {
      arguments = Bundle().apply {
        putInt(KEY_POS, position)
      }
    }
  }

  class ContentSampleFragment : Fragment() {
    companion object {
      const val KEY_POS = "position"
    }

    private lateinit var mStateLayout: StateLayout

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setHasOptionsMenu(true)
    }

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View {
      return inflater.inflate(R.layout.fragment_content_sample, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      val position = arguments!!.getInt(KEY_POS)
      view.findViewById<TextView>(R.id.content).text = "Tab $position"

      mStateLayout = view.findViewById(R.id.stateLayout)
      // show loading state by default
      mStateLayout.showState(LoadingState::class.java, false)
      mStateLayout.getState(ErrorState::class.java).setOnReloadListener {
        mStateLayout.showState(LoadingState::class.java)
        requestData()
      }
      requestData()
    }

    private fun requestData() {
      mStateLayout.postDelayed(Random.nextLong(0, 2000)) {
        if (Random.nextBoolean()) {
          mStateLayout.showContent()
        } else {
          if (Random.nextBoolean()) {
            mStateLayout.showState(EmptyState::class.java)
          } else {
            mStateLayout.showState(ErrorState::class.java)
          }
        }
      }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      super.onCreateOptionsMenu(menu, inflater)
      menu.add(0, 0, 0, "Show Content Layout")
      menu.add(1, 1, 1, "Show Loading Layout")
      menu.add(2, 2, 2, "Show Empty Layout")
      menu.add(3, 3, 3, "Show Error Layout")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      when (item.itemId) {
        0 -> {
          mStateLayout.showContent()
          return true
        }
        1 -> {
          mStateLayout.showState(LoadingState::class.java)
          return true
        }
        2 -> {
          mStateLayout.showState(EmptyState::class.java)
          return true
        }
        3 -> {
          mStateLayout.showState(ErrorState::class.java)
          return true
        }
      }
      return super.onOptionsItemSelected(item)
    }
  }
}