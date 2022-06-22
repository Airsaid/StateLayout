package com.airsaid.multistatelayout.sample

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.airsaid.multistatelayout.anim.AlphaTransitionAnimator
import com.airsaid.multistatelayout.anim.AlphaTranslationTransitionAnimator
import com.airsaid.multistatelayout.anim.TranslationTransitionAnimator

/**
 * @author airsaid
 */
class AnimatorSampleActivity(
    override val layoutId: Int = R.layout.fragment_multi_state_layout_animator
) : AbstractSampleActivity() {

  private val mAnimators = linkedMapOf(
      "AlphaTransitionAnimator" to AlphaTransitionAnimator(),
      "TranslationTransitionAnimator" to TranslationTransitionAnimator(),
      "AlphaTranslationTransitionAnimator" to AlphaTranslationTransitionAnimator(),
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val directionRadioLayout = (findViewById<RadioGroup>(R.id.direction)).apply {
      val values = TranslationTransitionAnimator.Direction.values()
      setRadioButtons(values.map { it.name })
      check(0)

      setOnCheckedChangeListener { _, checkedId ->
        val animator = (mMultiStateLayout.transitionAnimator as TranslationTransitionAnimator)
        animator.translationDirection = values[checkedId]
      }
    }

    (findViewById<RadioGroup>(R.id.animator)).apply {
      val items = mAnimators.map { it.key }
      setRadioButtons(items)

      setOnCheckedChangeListener { _, checkedId ->
        val animator = mAnimators[items[checkedId]]
        mMultiStateLayout.transitionAnimator = animator
        directionRadioLayout.visibility = if (animator is TranslationTransitionAnimator)
          View.VISIBLE else View.GONE
      }
      check(0)
    }
  }

  private fun RadioGroup.setRadioButtons(buttons: List<String>) {
    buttons.forEachIndexed { id, text ->
      addView(RadioButton(context).apply {
        this.id = id
        this.text = text
      })
    }
  }
}