package com.airsaid.statelayout.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @author airsaid
 */
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun onBasicSample(v: View) {
    startActivity(Intent(this, BasicSampleActivity::class.java))
  }

  fun onTransitionAnimatorSample(v: View) {
    startActivity(Intent(this, AnimatorSampleActivity::class.java))
  }

  fun onCustomUISample(v: View) {
    startActivity(Intent(this, CustomUISampleActivity::class.java))
  }

  fun onStateTriggerSample(v: View) {
    startActivity(Intent(this, StateTriggerSampleActivity::class.java))
  }

  fun onCustomStateProvider(v: View) {
    startActivity(Intent(this, CustomStateProviderSampleActivity::class.java))
  }
}