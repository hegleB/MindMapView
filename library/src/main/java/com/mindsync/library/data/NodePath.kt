package com.mindsync.mindmap.data

import com.mindsync.mindmap.util.Dp

sealed class NodePath(open var centerX: Dp, open var centerY: Dp) {
    abstract fun adjustPath(horizontalSpacing: Dp, totalHeight: Dp): NodePath
    protected fun calculateNewCenterY(horizontalSpacing: Dp, totalHeight: Dp): Dp {
        return centerY + totalHeight / 2 + horizontalSpacing
    }
}

data class RectanglePath(
    override var centerX: Dp,
    override var centerY: Dp,
    val width: Dp,
    val height: Dp,
) : NodePath(centerX, centerY) {
    fun leftX() = centerX - (width / (Dp(2f)))

    fun topY() = centerY - (height / (Dp(2f)))

    fun rightX() = centerX + (width / (Dp(2f)))

    fun bottomY() = centerY + (height / (Dp(2f)))

    override fun adjustPath(horizontalSpacing: Dp, totalHeight: Dp): RectanglePath {
        return this.copy(centerY = calculateNewCenterY(horizontalSpacing, totalHeight))
    }
}

data class CirclePath(
    override var centerX: Dp,
    override var centerY: Dp,
    val radius: Dp,
) : NodePath(centerX, centerY) {
    override fun adjustPath(horizontalSpacing: Dp, totalHeight: Dp): CirclePath {
        return this.copy(centerY = totalHeight / 2 + horizontalSpacing)
    }
}
