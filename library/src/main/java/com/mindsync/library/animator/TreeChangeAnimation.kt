package com.mindsync.mindmap.animator

import com.mindsync.mindmap.MindMapManager
import com.mindsync.mindmap.animator.AnimationUtils.END_PROCESS
import com.mindsync.mindmap.animator.AnimationUtils.START_PROCESS

class TreeChangeAnimation(
    private val mindMapManager: MindMapManager,
    private val treeInvalidate: () -> Unit,
) : AnimationStrategy {
    override fun animate() {
        val start = START_PROCESS
        val end = END_PROCESS
        val preTree = mindMapManager.deepCopyTree()
        mindMapManager.arrangeTree()
        val postTree = mindMapManager.deepCopyTree()

        AnimationUtils.createObjectAnimator(start, end) { progress ->
            preTree.entries.forEach { (nodeId, from) ->
                val to = postTree[nodeId] ?: return@forEach
                val fromNode = from
                val toNode = to
                val currentX =
                    fromNode.path.centerX + (toNode.path.centerX - fromNode.path.centerX) * progress
                val currentY =
                    fromNode.path.centerY + (toNode.path.centerY - fromNode.path.centerY) * progress

                mindMapManager.getTree()
                    .updateNode(nodeId, toNode.description, toNode.children, currentX, currentY)
            }
            treeInvalidate()
        }.start()
    }
}