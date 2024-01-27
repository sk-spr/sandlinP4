package com.skyesprung

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage

class Processing : PApplet() {

    override fun settings() {
        size(1920/2, 1080/2, PConstants.JAVA2D)
    }
    private val emptyPixel: Int = color(50f)
    override fun setup() {
        background(emptyPixel)
        noStroke()
        textSize(50f)
        text("Click and drag!", 100f, 100f)
        imageMode(CORNER)
    }
    private var wasPressed : Boolean = false;
    override fun draw() {
        if(mousePressed && !wasPressed){
            fill(random(150f, 255f), random(150f, 255f), random(150f, 255f))
            wasPressed = true;
        }
        if(mousePressed)
            circle(mouseX.toFloat(), mouseY.toFloat(), 50f)
        else
            wasPressed = false;

        var leftCount = 0;
        var rightCount = 0;
        val currentImg = get() // double buffering
        val nextImg : PImage = currentImg.clone() as PImage
        nextImg.pixels = currentImg.pixels.clone()
        for (y in (0 until currentImg.height-1).reversed()){ // traverse pixel rows down stopping 1 short
            //experiment
            var xrange = 1 until currentImg.width-1;
            for(x in (if( random(1f)>0.5f )xrange else xrange.reversed())){ // extremely horrible hack
                val direction = when{ // choose flow direction
                    nextImg.get(x,y+1) == emptyPixel -> 0
                    nextImg.get(x+1,y+1) == emptyPixel -> 1
                    nextImg.get(x-1,y+1) == emptyPixel -> -1
                    else -> continue
                }
                if(direction < 0) leftCount++;
                if(direction > 0) rightCount++;
                //progress grain
                nextImg.set(x,y, emptyPixel);
                nextImg.set(x + direction, y+1, currentImg.get(x,y))
            }
        }
        clear()
        image(nextImg, 0f, 0f)
        kotlin.io.println("fps $frameRate.")
        println("l = $leftCount; r = $rightCount")
    }

}