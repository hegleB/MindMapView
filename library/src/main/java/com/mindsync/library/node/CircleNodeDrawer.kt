package com.mindsync.library.node

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import com.mindsync.library.data.CircleNodeData
import com.mindsync.library.model.DrawInfo
import com.mindsync.library.util.toPx

class CircleNodeDrawer(
    private val node: CircleNodeData,
    private val drawInfo: DrawInfo,
    private val context: Context,
    private val paint: Paint,
) : NodeDrawer {
    override fun drawNode(canvas: Canvas) {
        canvas.drawCircle(
            node.path.centerX.toPx(context),
            node.path.centerY.toPx(context),
            node.path.radius.toPx(context),
            paint
        )
    }

    override fun drawText(canvas: Canvas, lines: List<String>, fontType: Typeface) {
        drawInfo.textPaint.apply {
            color = Color.WHITE
            typeface = fontType
        }
        val bounds = Rect()
        if (lines.size > 1) {
            var y =
                node.path.centerY.toPx(context) - node.path.radius.toPx(context) + drawInfo.padding.toPx(
                    context
                )
            for (line in lines) {
                drawInfo.textPaint.getTextBounds(line, 0, line.length, bounds)
                canvas.drawText(
                    line,
                    node.path.centerX.toPx(context),
                    y + bounds.height(),
                    drawInfo.textPaint,
                )
                y += bounds.height() + drawInfo.lineHeight.dpVal
            }
        } else {
            canvas.drawText(
                node.description,
                node.path.centerX.toPx(context),
                node.path.centerY.toPx(context) + drawInfo.lineHeight.dpVal / 2,
                drawInfo.textPaint,
            )
        }
    }
}