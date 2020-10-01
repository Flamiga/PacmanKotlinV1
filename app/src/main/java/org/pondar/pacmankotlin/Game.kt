package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.TextView
import java.util.ArrayList


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context,view: TextView) {



    private var pointsView: TextView = view
        private var points : Int = 0
        //bitmap of the pacman
        var pacBitmap: Bitmap
        var pacx: Int = 0
        var pacy: Int = 0

        //Bitmap of the gold coin
        var goldBitmap: Bitmap

        //did we initialize the coins?
        var coinsInitialized = false

        //the list of goldcoins - initially empty
        var coins = ArrayList<GoldCoin>()

        //a reference to the gameview
        private var gameView: GameView? = null
        private var h: Int = 0
        private var w: Int = 0 //height and width of screen


    init {
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)

    }

    init{
        goldBitmap = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.money
        )

    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins()
    {
        //DO Stuff to initialize the array list with coins.
        coins.add(GoldCoin(false, 110, 755))
        coins.add(GoldCoin(false, 580, 1460))
        coins.add(GoldCoin(false, 880,310))
        coins.add(GoldCoin(false, 350, 367))
        coins.add(GoldCoin(false, 500,800))
        coins.add(GoldCoin(false, 223, 1204))
        coins.add(GoldCoin(false,  657, 221))
        coins.add(GoldCoin(false, 587, 1130))
        coins.add(GoldCoin(false, 200, 950))
        coins.add(GoldCoin(false, 800, 945))
        coinsInitialized = true
    }


    fun newGame() {
        pacx = 50
        pacy = 400 //just some starting coordinates - you can change this.
        //reset the points
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
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
            gameView!!.invalidate()
        }
    }
    fun movePacmanLeft(pixels: Int){
        if(pacx - pixels > 0){
            pacx = pacx - pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }
    fun movePacmanUp(pixels: Int){
        if(pacy - pixels > 0){
            pacy = pacy - pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }
    fun movePacmanDown(pixels: Int){
        if(pacy + pixels + pacBitmap.height < h){
            pacy = pacy + pixels
            doCollisionCheck()
            gameView!!.invalidate()
        }
    }

    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck() {

    }


}