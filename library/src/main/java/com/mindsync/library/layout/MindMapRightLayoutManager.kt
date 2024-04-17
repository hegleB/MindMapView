package com.mindsync.library.layout

import com.mindsync.library.data.CircleNodeData
import com.mindsync.library.data.NodeData
import com.mindsync.library.data.RectangleNodeData
import com.mindsync.library.data.Tree
import com.mindsync.library.util.Dp


class MindMapRightLayoutManager {
    private val horizontalSpacing = Dp(50f)
    private val verticalSpacing = Dp(50f)

    fun arrangeNode(tree: Tree<*>, rootNodeData: NodeData<*>? = null) {
        val root = rootNodeData ?: tree.getRootNode()
        val totalHeight = measureChildHeight(root, tree)
        val newHead =
            if (root.path.centerX.dpVal <= (totalHeight / 2).dpVal) {
                root.adjustPosition(horizontalSpacing, totalHeight)
            } else {
                root
            } as NodeData<*>
        if (rootNodeData == null) {
            tree.setRootNode(newHead)
        }
        recurArrangeNode(newHead, tree)
    }

    private fun recurArrangeNode(
        currentNodeData: NodeData<*>,
        tree: Tree<*>,
    ) {
        val childHeightSum = measureChildHeight(currentNodeData, tree)

        val nodeWidth =
            when (currentNodeData) {
                is RectangleNodeData -> currentNodeData.path.width
                is CircleNodeData -> currentNodeData.path.radius
            }

        val criteriaX = currentNodeData.path.centerX + nodeWidth / 2 + horizontalSpacing
        var startX: Dp
        var startY = currentNodeData.path.centerY - (childHeightSum / 2)

        currentNodeData.children.forEach { childId ->
            val child = tree.getNode(childId)
            val childHeight = measureChildHeight(child, tree)
            val newY = startY + (childHeight / 2)

            startX =
                when (child) {
                    is CircleNodeData -> criteriaX + (child.path.radius / 2)
                    is RectangleNodeData -> criteriaX + (child.path.width / 2)
                }
            val newChild =
                when (child) {
                    is CircleNodeData -> {
                        val newPath = child.path.copy(centerX = startX, centerY = newY)
                        child.copy(path = newPath)
                    }

                    is RectangleNodeData -> {
                        val newPath = child.path.copy(centerX = startX, centerY = newY)
                        child.copy(path = newPath)
                    }
                }

            tree.setNode(childId, newChild)
            recurArrangeNode(newChild, tree)
            startY += childHeight + verticalSpacing
        }
    }

    fun measureChildHeight(
        nodeData: NodeData<*>,
        tree: Tree<*>,
    ): Dp {
        var heightSum = Dp(0f)

        if (nodeData.children.isNotEmpty()) {
            nodeData.children.forEach { childId ->
                val childNode = tree.getNode(childId)
                heightSum += measureChildHeight(childNode, tree)
            }
            heightSum += verticalSpacing * (nodeData.children.size - 1)
        } else {
            heightSum =
                when (nodeData) {
                    is CircleNodeData -> nodeData.path.radius
                    is RectangleNodeData -> nodeData.path.height
                }
        }
        return heightSum
    }

    private var rightMost = Float.MIN_VALUE

    fun measureChildWidth(nodeData: NodeData<*>, tree: Tree<*>): Dp {
        calculateWidth(nodeData, tree)
        return Dp(rightMost)
    }

    private fun calculateWidth(nodeData: NodeData<*>, tree: Tree<*>) {
        when (nodeData) {
            is RectangleNodeData -> {
                val right = nodeData.path.centerX.dpVal + nodeData.path.width.dpVal / 2
                rightMost = maxOf(rightMost, right)
            }
            is CircleNodeData -> {
                val right = nodeData.path.centerX.dpVal + nodeData.path.radius.dpVal
                rightMost = maxOf(rightMost, right)
            }
        }
        nodeData.children.forEach { calculateWidth(tree.getNode(it), tree) }
    }
}
