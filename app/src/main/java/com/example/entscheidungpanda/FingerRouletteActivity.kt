package com.example.entscheidungpanda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FingerRouletteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Lade das Layout mit der FingerView
        setContentView(R.layout.activity_finger_roulette)
    }
}