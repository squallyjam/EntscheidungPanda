package com.example.entscheidungpanda

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import android.content.Intent
// import android.view.View // Wird gerade nicht gebraucht
// import androidx.appcompat.app.AppCompatActivity // ComponentActivity reicht hier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        val toolbar: Toolbar = findViewById(R.id.mytoolbar)

        // --- BUTTON 1: JA / NEIN ---
        val button = findViewById<Button>(R.id.myButton)
        button.setOnClickListener {
            // Hier nur die Funktion aufrufen, NICHT nochmal startActivity(intent) schreiben!
            openSecondActivity()
        }

        // --- BUTTON 2: EIGENE ENTSCHEIDUNG (NEU) ---
        // Stelle sicher, dass du im Layout einen Button mit der ID btnCustom hast!
        val buttonCustom = findViewById<Button>(R.id.btnCustom)
        buttonCustom.setOnClickListener {
            openCustomActivity()
        }

        val btnFinger = findViewById<Button>(R.id.btnFinger)
        btnFinger.setOnClickListener {
        val intent = Intent(this, FingerRouletteActivity::class.java)
        startActivity(intent)
}
    }

    // Funktion für Button 1 (Ja/Nein)
    fun openSecondActivity() {
        val yesOptions = listOf(
            "Ja", "Natürlich", "Auf jeden fall", "Jup", "Klaro", "Ganz sicher","Definitiv","absolute"
        )
        val noOptions = listOf(
            "Nein", "Eher nicht", "Auf keinen Fall", "Keineswegs", "Keine Chance", "Nö","Heute nicht","Nope"
        )

        val allAnswers = yesOptions + noOptions
        val answer = allAnswers.random()
        val isNegative = noOptions.contains(answer)
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("Text", answer)

            putExtra("IsNegative", isNegative)
        }
        startActivity(intent)
    }

    // Funktion für Button 2 (Eigene Liste)
    fun openCustomActivity() {
        // Hier rufen wir die CustomDecisionActivity auf, die wir vorhin erstellt haben
        val intent = Intent(this, CustomDecisionActivity::class.java)
        startActivity(intent)
    }
}