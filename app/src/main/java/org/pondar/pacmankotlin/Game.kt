package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.lang.Math.sqrt
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context, view: TextView) {


    private var pointsView: TextView = view
     var points: Int = 0

    //bitmap of the pacman
    var pacBitmap: Bitmap
    var pacx: Int = 0
    var pacy: Int = 0
    var counter: Int = 0
    var timer: Int = 30
    var direction: Int = 0
    var running: Boolean = false

    // values of enemy
    var ghostx: Int = 0
    var ghosty: Int = 0
    var isAlive: Boolean = true
    var ghostdirection: Int = 1

    //Bitmap of the gold coin
    var goldBitmap: Bitmap

    var ghostBitmap: Bitmap

    //did we initialize the coins?
    var coinsInitialized = false

    //the list of goldcoins - initially empty
    var coins = ArrayList<GoldCoin>()

    var enemiesInitialized = false

    //the list of enemies
    var enemies = ArrayList<Enemy>()

    //a reference to the gameview
    private var gameView: GameView? = null
    private var h: Int = 0
    private var w: Int = 0 //height and width of screen


    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)

    }

    init {
        goldBitmap = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.money
        )

    }

    init {
        ghostBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost)
    }


    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        //DO Stuff to initialize the array list with coins.
        coins.add(GoldCoin(false, 110, 755))
        coins.add(GoldCoin(false, 580, 1300))
        coins.add(GoldCoin(false, 880, 310))
        coins.add(GoldCoin(false, 350, 367))
        coins.add(GoldCoin(false, 500, 800))
        coins.add(GoldCoin(false, 223, 1204))
        coins.add(GoldCoin(false, 657, 221))
        coins.add(GoldCoin(false, 587, 1130))
        coins.add(GoldCoin(false, 200, 950))
        coins.add(GoldCoin(false, 800, 945))
        coinsInitialized = true
    }

    // array liste af enemies
    fun initializeEnemies() {
        enemies.add(Enemy(true, false, 900, 500))
        enemiesInitialized = true
    }

    fun newGame() {
        pacx = 50
        pacy = 400 //just some starting coordinates - you can change this.
        timer = 30
        counter = 0
        running = false
        ghosty = 650
        ghostx = 450
        //reset the points
        coinsInitialized = false
        pointsView.text = "${context.resources.getString(R.string.points)} $points"

        gameView?.invalidate() //redraw screen
    }


    fun gameOver() {
        if (timer == 1) {
            timer = 0
            running = false
            counter = 0
            Toast.makeText(context, "You loose", Toast.LENGTH_SHORT).show()

        }

    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    fun movePacmanRight(pixels: Int) {
        //still within our boundaries?
        if (pacx + pixels + pacBitmap.width < w) {
            pacx = pacx + pixels
            doCollisionCheck()
            direction = 4
            gameView!!.invalidate()

        }
    }

    fun movePacmanLeft(pixels: Int) {
        if (pacx - pixels > 0) {
            pacx = pacx - pixels
            doCollisionCheck()
            direction = 3
            gameView!!.invalidate()

        }
    }

    fun movePacmanUp(pixels: Int) {
        if (pacy - pixels > 0) {
            pacy = pacy - pixels
            doCollisionCheck()
            direction = 1
            gameView!!.invalidate()
        }
    }

    fun movePacmanDown(pixels: Int) {
        if (pacy + pixels + pacBitmap.height < h) {
            pacy = pacy + pixels
            doCollisionCheck()
            direction = 2
            gameView!!.invalidate()

        }
    }

    //Move enemy
    fun moveEnemy(pixels: Int) {
        //still within our boundaries?
        if (ghostdirection == 2) { //direction is down
            if (ghosty + pixels + ghostBitmap.height < h) {
                ghosty = ghosty + pixels
                ghostdirection = 2
            } else {
                ghostdirection = 1
            }
        } else { // direction is up
            if (ghosty - pixels > 0) {
                ghosty = ghosty - pixels
                ghostdirection = 1
            } else {
                ghostdirection = 2
            }
        }
    }


    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun distance(pacx: Int, pacy: Int, golx: Int, goly: Int): Double {
        var coordination = (sqrt(((pacx - golx) * (pacx - golx) + (pacy - goly) * (pacy - goly)).toDouble()))
        return coordination
    }

    fun distanceEnemy(pacx: Int, pacy: Int, ghostx: Int, ghosty: Int): Double {
        var coordination = (sqrt(((pacx - ghostx) * (pacx - ghostx) + (pacy - ghosty) * (pacy - ghosty)).toDouble()))
        return coordination
    }

    fun doCollisionCheck() {
        enemies.forEach() {
            if (distanceEnemy(pacx, pacy, ghostx, ghosty) < 150) {
                running = false
                Toast.makeText(context, "You died!", Toast.LENGTH_SHORT).show()

            }
        }


        coins.forEach {
            if (distance(pacx, pacy, it.golx, it.goly) < 200) {
                if (it.taken == false) {
                    points++
                    it.taken = true
                    pointsView.text = "${context.resources.getString(R.string.points)} $points"
                    // checking if all 10 coins are taken
                    if (points === 10) {
                        running = false
                        Toast.makeText(context, "Congratulation! You won!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


