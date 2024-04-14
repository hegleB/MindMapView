package com.mindsync.library.node

import android.graphics.Canvas

interface NodeDrawer {
    fun drawNode(canvas: Canvas)
    fun drawText(canvas: Canvas, lines: List<String>)
}