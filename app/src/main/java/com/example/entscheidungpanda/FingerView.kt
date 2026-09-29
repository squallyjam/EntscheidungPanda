package com.example.entscheidungpanda

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class FingerView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // Speichert die Positionen der Finger (ID -> x,y Koordinaten)
    private val activePointers = mutableMapOf<Int, PointF>()

    // Farben für die Kreise
    private val paintNormal = Paint().apply {
        color = Color.parseColor("#2196F3") // Blau
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val paintWinner = Paint().apply {
        color = Color.parseColor("#4CAF50") // Grün
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private var winnerId: Int? = null
    private var isTimerRunning = false

    // Timer: Nach 2 Sekunden (2000ms) wird entschieden
    private val timer = object : CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            // Könnte man für Animationen nutzen
        }

        override fun onFinish() {
            if (activePointers.isNotEmpty()) {
                // Zufälligen Gewinner auswählen aus den aktuellen Fingern
                val keys = activePointers.keys.toList()
                winnerId = keys.random()
                invalidate() // Neu zeichnen, um Gewinner grün zu machen
            }
            isTimerRunning = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Index und ID des aktuellen Fingers holen
        val actionIndex = event.actionIndex
        val pointerId = event.getPointerId(actionIndex)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                // Neuer Finger berührt den Bildschirm
                val x = event.getX(actionIndex)
                val y = event.getY(actionIndex)
                activePointers[pointerId] = PointF(x, y)

                resetGame() // Wenn ein neuer Finger dazu kommt, Timer neu starten
            }

            MotionEvent.ACTION_MOVE -> {
                // Finger bewegen sich -> Koordinaten updaten
                for (i in 0 until event.pointerCount) {
                    val id = event.getPointerId(i)
                    if (activePointers.containsKey(id)) {
                        activePointers[id]?.x = event.getX(i)
                        activePointers[id]?.y = event.getY(i)
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                // Finger wird weggenommen
                val id = event.getPointerId(actionIndex)
                activePointers.remove(id)

                if (activePointers.isEmpty()) {
                    winnerId = null // Alles zurücksetzen
                }
                resetGame() // Timer neu starten, da sich Fingeranzahl geändert hat
            }
        }

        // Bildschirm neu malen lassen
        invalidate()
        return true
    }

    private fun resetGame() {
        winnerId = null
        timer.cancel()
        isTimerRunning = false

        // Timer nur starten, wenn mindestens 2 Finger drauf sind (oder 1, wenn du das willst)
        if (activePointers.size >= 2) {
            isTimerRunning = true
            timer.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Für jeden Finger einen Kreis malen
        for ((id, point) in activePointers) {
            val radius = 150f

            // Farbe wählen: Wenn es einen Gewinner gibt und DIESER Finger es ist -> Grün
            // Sonst -> Blau. Wenn das Spiel vorbei ist und dieser Finger verlor -> nicht malen?
            // Wir machen es einfach: Gewinner Grün, alle anderen Blau.

            if (winnerId != null) {
                if (id == winnerId) {
                    canvas.drawCircle(point.x, point.y, radius + 20f, paintWinner) // Gewinner etwas größer
                }
                // Verlierer werden ausgeblendet, indem wir sie einfach nicht malen,
                // oder wir malen sie grau. Hier: Nur Gewinner wird angezeigt.
            } else {
                // Spiel läuft noch -> alle Blau
                canvas.drawCircle(point.x, point.y, radius, paintNormal)
            }
        }
    }
}