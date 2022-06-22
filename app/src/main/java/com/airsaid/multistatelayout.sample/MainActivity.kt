package com.airsaid.multistatelayout.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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
    startActivity(Intent(this, CustomSampleActivity::class.java))
  }

  fun onStateTriggerSample(v: View) {
    startActivity(Intent(this, StateTriggerSampleActivity::class.java))
  }
}