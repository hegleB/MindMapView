package com.mindsync.mindmap

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.mindsync.mindmap.data.CircleNode
import com.mindsync.mindmap.data.Node
import com.mindsync.mindmap.data.RectangleNode
import com.mindsync.mindmap.data.RectanglePath
import com.mindsync.mindmap.data.Tree
import com.mindsync.mindmap.layout.MeasureTextSize
import com.mindsync.mindmap.layout.MindMapRightLayoutManager
import com.mindsync.mindmap.util.Dp

class MindMapManager(context: Context) {
    private var tree: Tree = Tree(context)
    private var selectNode: Node? = null
    private val rightLayoutManager = MindMapRightLayoutManager()
    private val measureTextSize = MeasureTextSize(context)
    private var isMoving = false

    init {
        measureTextSize.traverseTextHead(tree)
    }

    fun update(tree: Tree) {
        this.tree = tree
        if (isMoving.not()) {
            measureTextSize.traverseTextHead(this.tree)
        }
    }

    fun update(target: Node) {
        tree.updateNode(
            target.id,
            target.description,
            target.children,
            target.path.centerX,
            target.path.centerY
        )
        measureTextSize.traverseTextHead(tree)
    }

    fun getTree(): Tree {
        return this.tree
    }

    fun addNode(node: Node, description: String) {
        this.tree.addNode(node.id, node.parentId, description)
        update(node)
    }

    fun removeNode(node: Node) {
        this.tree.removeNode(node.id)
    }

    fun setSelectedNode(node: Node?) {
        this.selectNode = node
    }

    fun setMoving() {
        this.isMoving = true
    }

    fun setNotMoving() {
        this.isMoving = false
    }

    fun getMovingState(): Boolean {
        return this.isMoving
    }

    fun getSelectedNode(): Node? {
        return this.selectNode
    }

    fun arrangeTree() {
        rightLayoutManager.arrangeNode(this.tree)
    }

    fun measureHeight(node: Node): Dp {
        return rightLayoutManager.measureChildHeight(node, tree)
    }

    fun measureWidth(node: Node): Dp {
        return rightLayoutManager.measureChildWidth(node, tree)
    }

    fun deepCopyTree(): Map<String, Node> {
        return tree.nodes.entries.associate { node ->
            node.key to node.value
        }
    }
}