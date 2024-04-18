package com.mindsync.mindmapview.model

import com.mindsync.mindmapview.Dp

sealed class Node(
    open val id: String,
    open val parentId: String?,
    open val path: NodePath,
    open val description: String,
    open val children: List<String>,
) {
    abstract fun adjustPosition(
        horizontalSpacing: Dp,
        totalHeight: Dp,
    ): Node
}

data class CircleNode(
    override val id: String,
    override val parentId: String?,
    override val path: CirclePath = CirclePath(Dp(0f), Dp(0f), Dp(0f)),
    override val description: String,
    override val children: List<String>,
) : Node(id, parentId, path, description, children) {
    override fun adjustPosition(
        horizontalSpacing: Dp,
        totalHeight: Dp,
    ): Node {
        return this.copy(path = path.adjustPath(horizontalSpacing, totalHeight))
    }
}

data class RectangleNode(
    override val id: String,
    override val parentId: String,
    override val path: RectanglePath = RectanglePath(Dp(0f), Dp(0f), Dp(0f), Dp(0f)),
    override val description: String,
    override val children: List<String>,
) : Node(id, parentId, path, description, children) {
    override fun adjustPosition(
        horizontalSpacing: Dp,
        totalHeight: Dp,
    ): Node {
        return this.copy(path = path.adjustPath(horizontalSpacing, totalHeight))
    }
}
