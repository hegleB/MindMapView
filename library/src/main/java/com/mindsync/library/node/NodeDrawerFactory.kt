package com.mindsync.library.node

import android.content.Context
import com.mindsync.library.R
import com.mindsync.library.data.CircleNodeData
import com.mindsync.library.data.NodeData
import com.mindsync.library.data.RectangleNodeData
import com.mindsync.library.model.DrawInfo

class NodeDrawerFactory(
    private val node: NodeData<*>,
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
            is RectangleNodeData -> {
                RectangleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    drawInfo.strokePaint,
                )
            }
            is CircleNodeData -> {
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
            is RectangleNodeData -> {
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

            is CircleNodeData -> {
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