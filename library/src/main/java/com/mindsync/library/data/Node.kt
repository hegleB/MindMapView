package com.mindsync.library.data

import com.mindsync.library.util.Dp


sealed class Node(
    open val id: String,
    open val parentId: String?,
    open val path: NodePath,
    open val description: String,
    open val children: List<String>,
    open var alpha: Float,
    open var isAnimating: Boolean,
    open var isDrawingLine: Boolean,
    open var strokeWidth: Float,
) : java.io.Serializable {
    abstract fun adjustPosition(horizontalSpacing: Dp, totalHeight: Dp): Node
}

data class CircleNode(
    override val id: String,
    override val parentId: String?,
    override val path: CirclePath = CirclePath(Dp(0f), Dp(0f), Dp(0f)),
    override val description: String,
    override val children: List<String>,
    override var alpha: Float = 0f,
    override var isAnimating: Boolean = false,
    override var isDrawingLine: Boolean = false,
    override var strokeWidth: Float = 1f,
) : Node(id, parentId, path, description, children, alpha, isAnimating, isDrawingLine, strokeWidth) {
    override fun adjustPosition(horizontalSpacing: Dp, totalHeight: Dp): Node {
        return this.copy(path = path.adjustPath(horizontalSpacing, totalHeight))
    }
}

data class RectangleNode(
    override val id: String,
    override val parentId: String,
    override val path: RectanglePath = RectanglePath(Dp(0f), Dp(0f), Dp(0f), Dp(0f)),
    override val description: String,
    override val children: List<String>,
    override var alpha: Float = 0f,
    override var isAnimating: Boolean = false,
    override var isDrawingLine: Boolean = false,
    override var strokeWidth: Float = 1f,
) : Node(id, parentId, path, description, children, alpha, isAnimating, isDrawingLine, strokeWidth) {
    override fun adjustPosition(horizontalSpacing: Dp, totalHeight: Dp): Node {
        return this.copy(path = path.adjustPath(horizontalSpacing, totalHeight))
    }
}
