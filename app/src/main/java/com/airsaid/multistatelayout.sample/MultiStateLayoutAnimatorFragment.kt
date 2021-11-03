package com.airsaid.multistatelayout.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airsaid.multistatelayout.anim.AlphaTransitionAnimator
import com.airsaid.multistatelayout.anim.AlphaTranslationTransitionAnimator
import com.airsaid.multistatelayout.anim.TranslationTransitionAnimator
import com.cz.android.sample.api.Register
import com.cz.android.sample.library.component.code.SampleSourceCode
import com.cz.android.sample.library.view.RadioLayout

/**
 * @author airsaid
 */
@Register(title = "TransitionAnimator", priority = 2)
@SampleSourceCode
class MultiStateLayoutAnimatorFragment : BaseMultiStateLayoutFragment() {

  private val mAnimators = linkedMapOf(
      "AlphaTransitionAnimator" to AlphaTransitionAnimator(),
      "TranslationTransitionAnimator" to TranslationTransitionAnimator(),
      "AlphaTranslationTransitionAnimator" to AlphaTranslationTransitionAnimator(),
  )

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_multi_state_layout_animator, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val directionRadioLayout = (findViewById<RadioLayout>(R.id.direction)).apply {
      val values = TranslationTransitionAnimator.Direction.values();
      setRadioArray(values.map { it.name }.toTypedArray())
      check(0)

      setOnCheckedChangeListener { _, index, isChecked ->
        if (isChecked) {
          val animator = (mMultiStateLayout.transitionAnimator as TranslationTransitionAnimator)
          animator.translationDirection = values[index]
        }
      }
    }

    (findViewById<RadioLayout>(R.id.animator)).apply {
      val items = mAnimators.map { it.key }.toTypedArray()
      setRadioArray(items)
      setOnCheckedChangeListener { _, index, isChecked ->
        if (isChecked) {
          val animator = mAnimators[items[index]]
          mMultiStateLayout.transitionAnimator = animator
          directionRadioLayout.visibility = if (animator is TranslationTransitionAnimator)
            View.VISIBLE else View.GONE
        }
      }
      check(0)
    }
  }
}