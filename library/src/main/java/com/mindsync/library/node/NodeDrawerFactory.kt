package com.mindsync.mindmap.node

import android.content.Context
import boostcamp.and07.mindsync.ui.view.model.DrawInfo
import com.mindsync.library.R
import com.mindsync.mindmap.data.CircleNode
import com.mindsync.mindmap.data.Node
import com.mindsync.mindmap.data.RectangleNode

class NodeDrawerFactory(
    private val node: Node,
    private val context: Context,
    private val depth: Int = 0,
) {
    private val nodeColors = listOf(
        context.getColor(R.color.main3),
        context.getColor(R.color.mindmap2),
        context.getColor(R.color.mindmap3),
        context.getColor(R.color.mindmap4),
        context.getColor(R.color.mindmap5),
    )
    private val drawInfo = DrawInfo(context)

    fun createStrokeNode(): NodeDrawer {
        return when(node) {
            is RectangleNode -> {
                RectangleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    drawInfo.strokePaint,
                )
            }
            is CircleNode -> {
                CircleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    drawInfo.strokePaint,
                )
            }
        }
    }

    fun createNodeDrawer(): NodeDrawer {
        return when (node) {
            is RectangleNode -> {
                val paint = drawInfo.rectanglePaint.apply {
                    color = nodeColors[(depth - 1) % nodeColors.size]
                }
                RectangleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    paint,
                )
            }

            is CircleNode -> {
                CircleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    drawInfo.circlePaint,
                )
            }
        }
    }
}