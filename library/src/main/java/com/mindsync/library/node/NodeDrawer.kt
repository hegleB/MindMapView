package com.mindsync.mindmap.node

import android.graphics.Canvas

interface NodeDrawer {
    fun drawNode(canvas: Canvas)
    fun drawText(canvas: Canvas, lines: List<String>)
}