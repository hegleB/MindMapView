package com.mindsync.library.command

import com.mindsync.library.MindMapManager
import com.mindsync.library.data.CircleNode
import com.mindsync.library.data.Node

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