package com.mindsync.mindmap

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import boostcamp.and07.mindsync.ui.view.model.DrawInfo
import com.mindsync.mindmap.data.CircleNode
import com.mindsync.mindmap.data.Node
import com.mindsync.mindmap.data.RectangleNode
import com.mindsync.mindmap.util.toPx

class LineView @JvmOverloads constructor(
    private val mindMapManager: MindMapManager,
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    private val drawInfo = DrawInfo(context)
    private val path = Path()
    private val tree = mindMapManager.getTree()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (tree.getRootNode().children.isNotEmpty()) {
            traverseLine(canvas, tree.getRootNode(), 0)
        }
    }

    fun update() {
        invalidate()
    }

    fun traverseLine(
        canvas: Canvas,
        node: Node,
        depth: Int,
    ) {
        for (toNodeId in node.children) {
            val toNode = tree.getNode(toNodeId)
            drawLine(node, toNode, canvas)
            traverseLine(canvas, toNode, depth + 1)
        }
    }

    private fun drawLine(
        fromNode: Node,
        toNode: Node,
        canvas: Canvas,
    ) {
        val linePaint = getLinePaintForMode()

        val path = createPath(fromNode, toNode)
        drawPathConditionally(toNode, canvas, path, linePaint)
    }

    private fun createPath(
        fromNode: Node,
        toNode: Node,
    ): Path {
        val startX = getNodeEdgeX(fromNode, true)
        val startY = fromNode.path.centerY.toPx(context)
        val endX = getNodeEdgeX(toNode, false)
        val endY = toNode.path.centerY.toPx(context)
        val midX = (startX + endX) / 2
        return path.apply {
            reset()
            moveTo(startX, startY)
            cubicTo(midX, startY, midX, endY, endX, endY)
        }
    }

    private fun getLinePaintForMode(): Paint {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> drawInfo.darkModeLinePaint
            else -> drawInfo.linePaint
        }
    }

    private fun drawPathConditionally(
        toNode: Node,
        canvas: Canvas,
        path: Path,
        linePaint: Paint,
    ) {
        if (mindMapManager.getMovingState().not() || mindMapManager.getSelectedNode()?.id != toNode.id) {
            canvas.drawPath(path, linePaint)
        }
    }

    private fun getNodeEdgeX(
        node: Node,
        isStart: Boolean,
    ): Float {
        val nodeCenterX = node.path.centerX.toPx(context)
        val widthOffset =
            when (node) {
                is CircleNode -> node.path.radius.toPx(context)
                is RectangleNode -> node.path.width.toPx(context) / 2
            }
        return if (isStart) nodeCenterX + widthOffset else nodeCenterX - widthOffset
    }
}
