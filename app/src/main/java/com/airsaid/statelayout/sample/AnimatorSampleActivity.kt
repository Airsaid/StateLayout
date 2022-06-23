package com.airsaid.statelayout.sample

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.airsaid.statelayout.anim.AlphaTransitionAnimator
import com.airsaid.statelayout.anim.AlphaTranslationTransitionAnimator
import com.airsaid.statelayout.anim.TranslationTransitionAnimator

/**
 * @author airsaid
 */
class AnimatorSampleActivity(
    override val layoutId: Int = R.layout.activity_animator_sample
) : AbstractSampleActivity() {

  private val mAnimators = linkedMapOf(
      "AlphaTransitionAnimator" to AlphaTransitionAnimator(),
      "TranslationTransitionAnimator" to TranslationTransitionAnimator(),
      "AlphaTranslationTransitionAnimator" to AlphaTranslationTransitionAnimator(),
  )

  override fun initialize() {
    val directionRadioLayout = (findViewById<RadioGroup>(R.id.direction)).apply {
      val values = TranslationTransitionAnimator.Direction.values()
      setRadioButtons(values.map { it.name })
      check(0)

      setOnCheckedChangeListener { _, checkedId ->
        val animator = (mStateLayout.transitionAnimator as TranslationTransitionAnimator)
        animator.translationDirection = values[checkedId]
      }
    }

    (findViewById<RadioGroup>(R.id.animator)).apply {
      val items = mAnimators.map { it.key }
      setRadioButtons(items)

      setOnCheckedChangeListener { _, checkedId ->
        val animator = mAnimators[items[checkedId]]
        mStateLayout.transitionAnimator = animator
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