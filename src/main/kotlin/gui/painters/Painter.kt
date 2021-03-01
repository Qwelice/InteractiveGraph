package gui.painters

import java.awt.Graphics

abstract class Painter {
    abstract fun draw(g: Graphics)
    abstract fun update(w: Int, h: Int)
}