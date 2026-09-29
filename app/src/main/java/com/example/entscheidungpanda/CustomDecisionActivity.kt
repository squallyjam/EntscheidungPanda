package com.example.entscheidungpanda

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CustomDecisionActivity : AppCompatActivity() {

    // Hier erstellen wir eine leere Liste für Texte (Strings)
    private val optionsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_decision)

        val inputOption: EditText = findViewById(R.id.inputOption)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val textOptionList: TextView = findViewById(R.id.textOptionList)
        val btnDecide: Button = findViewById(R.id.btnDecide)
        val textResult: TextView = findViewById(R.id.textResult)

        // 1. Logik für "Hinzufügen"
        btnAdd.setOnClickListener {
            val text = inputOption.text.toString()

            if (text.isNotEmpty()) {
                // Zur internen Liste hinzufügen
                optionsList.add(text)

                // Die Anzeige auf dem Bildschirm aktualisieren
                // Wir nehmen den aktuellen Text der TextView und hängen den neuen dran
                textOptionList.append("• $text\n")

                // Eingabefeld wieder leeren für die nächste Eingabe
                inputOption.text.clear()
            } else {
                Toast.makeText(this, "Bitte schreib etwas rein!", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Logik für "Entscheiden"
        btnDecide.setOnClickListener {
            if (optionsList.isNotEmpty()) {
                // Zufälliges Element aus der Liste wählen
                val randomOption = optionsList.random()

                // Ergebnis anzeigen
                textResult.text = randomOption
            } else {
                Toast.makeText(this, "Die Liste ist leer! Füge erst Optionen hinzu.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}