package com.mindsync.mindmap.layout

import android.util.Log
import com.mindsync.mindmap.data.CircleNode
import com.mindsync.mindmap.data.Node
import com.mindsync.mindmap.data.RectangleNode
import com.mindsync.mindmap.data.Tree
import com.mindsync.mindmap.util.Dp
import com.mindsync.mindmap.util.toPx

class MindMapRightLayoutManager {
    private val horizontalSpacing = Dp(50f)
    private val verticalSpacing = Dp(50f)

    fun arrangeNode(tree: Tree, rootNode: Node? = null) {
        val root = rootNode ?: tree.getRootNode()
        val totalHeight = measureChildHeight(root, tree)
        val newHead =
            if (root.path.centerX.dpVal <= (totalHeight / 2).dpVal) {
                root.adjustPosition(horizontalSpacing, totalHeight)
            } else {
                root
            }
        if (rootNode == null) {
            tree.setRootNode(newHead as CircleNode)
        }
        recurArrangeNode(newHead, tree)
    }

    private fun recurArrangeNode(
        currentNode: Node,
        tree: Tree,
    ) {
        val childHeightSum = measureChildHeight(currentNode, tree)

        val nodeWidth =
            when (currentNode) {
                is RectangleNode -> currentNode.path.width
                is CircleNode -> currentNode.path.radius
            }

        val criteriaX = currentNode.path.centerX + nodeWidth / 2 + horizontalSpacing
        var startX: Dp
        var startY = currentNode.path.centerY - (childHeightSum / 2)

        currentNode.children.forEach { childId ->
            val child = tree.getNode(childId)
            val childHeight = measureChildHeight(child, tree)
            val newY = startY + (childHeight / 2)

            startX =
                when (child) {
                    is CircleNode -> criteriaX + (child.path.radius / 2)
                    is RectangleNode -> criteriaX + (child.path.width / 2)
                }
            val newChild =
                when (child) {
                    is CircleNode -> {
                        val newPath = child.path.copy(centerX = startX, centerY = newY)
                        child.copy(path = newPath)
                    }

                    is RectangleNode -> {
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
        node: Node,
        tree: Tree,
    ): Dp {
        var heightSum = Dp(0f)

        if (node.children.isNotEmpty()) {
            node.children.forEach { childId ->
                val childNode = tree.getNode(childId)
                heightSum += measureChildHeight(childNode, tree)
            }
            heightSum += verticalSpacing * (node.children.size - 1)
        } else {
            heightSum =
                when (node) {
                    is CircleNode -> node.path.radius
                    is RectangleNode -> node.path.height
                }
        }
        return heightSum
    }

    private var rightMost = Float.MIN_VALUE

    fun measureChildWidth(node: Node, tree: Tree): Dp {
        calculateWidth(node, tree)
        return Dp(rightMost)
    }

    private fun calculateWidth(node: Node, tree: Tree) {
        when (node) {
            is RectangleNode -> {
                val right = node.path.centerX.dpVal + node.path.width.dpVal / 2
                rightMost = maxOf(rightMost, right)
            }
            is CircleNode -> {
                val right = node.path.centerX.dpVal + node.path.radius.dpVal
                rightMost = maxOf(rightMost, right)
            }
        }
        node.children.forEach { calculateWidth(tree.getNode(it), tree) }
    }
}
