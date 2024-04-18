package com.mindsync.library.node

import android.graphics.Canvas
import android.graphics.Typeface

interface NodeDrawer {
    fun drawNode(canvas: Canvas)
    fun drawText(canvas: Canvas, lines: List<String>, fontType: Typeface)
}