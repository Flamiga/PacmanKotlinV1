package org.pondar.pacmankotlin

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private var myTimer: Timer = Timer()
    private var countDown: Timer = Timer()
    var paused = false
    private var running = false
    //reference to the game class.
    private var game: Game? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        game = Game(this, pointsView)

        game?.setGameView(gameView)
        gameView.setGame(game)
        game?.newGame()
        moveRight.setOnClickListener {
            game?.movePacmanRight(10)
        }
        moveLeft.setOnClickListener {
            game?.movePacmanLeft(10)
        }
        moveUp.setOnClickListener {
            game?.movePacmanUp(10)
        }

        moveDown.setOnClickListener {
            game?.movePacmanDown(10)
        }

        running = true

         fun timerMethod() {
            this.runOnUiThread(timerTick);
        }

        myTimer.schedule(
                object : TimerTask() {
                    override fun run() {
                        timerMethod()
                    }
                }, 0, 1000)

        //0 indicates we start now, 200
        //is the number of miliseconds between each call,
    }

    private val timerTickCountDown = Runnable {
        if(running) {
            game!!.timer--
        }
    }
    private val timerTick = Runnable {
        //This method runs in the same thread as the UI.
        // so we can draw
        if (running) {
            game!!.counter++
            //update the counter - notice this is NOT seconds in this example
            //you need TWO counters - one for the timer count down that will
            // run every second and one for the pacman which need to run
            //faster than every second
            timer.text = getString(R.string.timer, game!!.counter)

            if (game!!.direction == 1) {
                game?.movePacmanUp(20)
                if (game!!.timer === 1) {
                    game?.newGame()
                    Toast.makeText(this, "You died", Toast.LENGTH_LONG).show()
                }
            } else if (game!!.direction == 2) {
                game?.movePacmanDown(20)
                if (game!!.timer === 1) {
                    game?.newGame()
                    Toast.makeText(this, "you died", Toast.LENGTH_LONG).show()
                }
            } else if (game!!.direction == 3) {
                game?.movePacmanLeft(20)
                if (game!!.timer === 1) {
                    game?.newGame()
                    Toast.makeText(this, "you died", Toast.LENGTH_LONG).show()
                }
            } else if (game!!.direction == 4) {
                game?.movePacmanRight(20)
                if (game!!.timer === 1) {
                    game?.newGame()
                    Toast.makeText(this, "you died", Toast.LENGTH_LONG).show()
                }
            }
            //move the pacman - you
            //should call a method on your game class to move
            //the pacman instead of this
        }
    }


override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    val id = item.itemId
    if (id == R.id.action_settings) {
        Toast.makeText(this, "settings clicked", Toast.LENGTH_LONG).show()
        return true
    } else if (id == R.id.action_newGame) {
        Toast.makeText(this, "New Game clicked", Toast.LENGTH_LONG).show()
        game?.newGame()
        return true
    }
    return super.onOptionsItemSelected(item)
}
}
