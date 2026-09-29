package com.example.entscheidungpanda

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secoundlayout)

        // 1. Views aus dem Layout holen
        val textView = findViewById<TextView>(R.id.textViewContent)
        val imageView = findViewById<ImageView>(R.id.imageView)

        // 2. Den Text und die "Ist-Negativ"-Information aus der MainActivity holen
        val receivedText = intent.getStringExtra("Text")
        val isNegative = intent.getBooleanExtra("IsNegative", false) // Standardwert ist false

        // 3. Den empfangenen Text anzeigen
        textView.text = receivedText

        // 4. Das passende Bild basierend auf der "IsNegative"-Information setzen
        if (isNegative) {
            // Es war eine negative Antwort -> Zeige das "nooption"-Bild
            imageView.setImageResource(R.drawable.nooption)
        } else {
            // Es war eine positive Antwort -> Zeige das "yesoption"-Bild
            imageView.setImageResource(R.drawable.yesoption)
        }
    }
}