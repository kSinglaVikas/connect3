package com.vkumar.connect3

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import java.util.*

class MainActivity : AppCompatActivity() {
    //Active Player - Yellow(0) Red (1) Blank = 2
    private var activePlayer = 0
    private var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    private val winningPositions = arrayOf(intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), intArrayOf(0, 4, 8), intArrayOf(2, 4, 6))
    fun dropIn(view: View) {
        val gameStatus = findViewById<View>(R.id.tv_gameStatus) as TextView
        val counter = view as ImageView
        val tagged = counter.tag.toString().toInt() - 1
        if (gameState[tagged] == 2) {
            gameState[tagged] = activePlayer
            counter.translationY = -1000f
            activePlayer = if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow)
                gameStatus.text = "It's Red Turn"
                1
            } else {
                counter.setImageResource(R.drawable.red)
                gameStatus.text = "It's Yellow Turn now"
                0
            }
            counter.animate().translationYBy(1000f).duration = 300
            val winnerMessage = findViewById<View>(R.id.textView) as TextView
            for (winningPosition in winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    var winner = "Red"
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Yellow"
                    }
                    winnerMessage.text = String.format("Player %s has WON!!!", winner)
                    gameStatus.text = "Game is finished"
                    val layout = findViewById<View>(R.id.Layout) as LinearLayout
                    layout.visibility = View.VISIBLE
                }
            }
            // Check if still can be played
            if (2 !in gameState){
                winnerMessage.text = String.format("Game is finished in draw !!!")
                gameStatus.text = "Game is finished"
                val layout = findViewById<View>(R.id.Layout) as LinearLayout
                layout.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(this@MainActivity, "Block is Already Taken!!!", Toast.LENGTH_LONG).show()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun playAgain(view: View?) {
        val layout = findViewById<View>(R.id.Layout) as LinearLayout
        layout.visibility = View.INVISIBLE
        val gameStatus = findViewById<View>(R.id.tv_gameStatus) as TextView
        gameStatus.text = "Yellow to Start"
        activePlayer = 0
        Arrays.fill(gameState, 2)
        val gridLayout = findViewById<View>(R.id.gridLayout) as androidx.gridlayout.widget.GridLayout
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCenter.start(application, getString(R.string.appCentreSecret),
                Analytics::class.java, Crashes::class.java)
        setContentView(R.layout.activity_main)
    }
}