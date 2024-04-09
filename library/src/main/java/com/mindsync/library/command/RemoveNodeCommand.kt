package com.mindsync.mindmap.command

import com.mindsync.mindmap.MindMapManager
import com.mindsync.mindmap.data.CircleNode
import com.mindsync.mindmap.data.Node

class RemoveNodeCommand(
    private val mindMapManager: MindMapManager,
    private val node: Node,
) : MindMapCommand {
    override fun execute() {
        if (node is CircleNode) return
        mindMapManager.removeNode(node)
        mindMapManager.setSelectedNode(null)
    }
}