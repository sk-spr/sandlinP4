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
        frameRate(500f)
        background(emptyPixel)
        noStroke()
        textSize(100f)
        text("Click and drag!", 100f, 100f)
    }
    private var wasPressed = false;
    override fun draw() {
        if(mousePressed && !wasPressed){
            fill(random(150f, 255f), random(150f, 255f), random(150f, 255f))
            wasPressed = true
        }
        if(mousePressed) circle(mouseX.toFloat(), mouseY.toFloat(), 50f)
        else wasPressed = false
        val pixels = get().pixels
        var y = height-1;
        while(--y > 0){ // traverse pixel rows down stopping 1 short
            var x = -1;
            while(++x < width-1){
                pixels[x + when{ // choose flow direction
                    pixels[x + (y + 1) * width] == emptyPixel -> 0
                    pixels[x + 1 + (y + 1) * width] == emptyPixel && pixels[x + 1 + y * width] == emptyPixel -> 1
                    pixels[x - 1 + (y + 1) * width] == emptyPixel && pixels[x - 1 + y * width] == emptyPixel -> -1
                    else -> continue
                } + (y+1) * width] = pixels[x + y * width]
                pixels[x + y * width] = emptyPixel;
            }
        }
        val next = PImage(width,height)
        next.pixels = pixels
        image(next, 0f, 0f)
        kotlin.io.println("fps $frameRate.")
    }

}