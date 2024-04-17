package com.mindsync.library.command

import com.mindsync.library.MindMapManager
import com.mindsync.library.data.CircleNodeData
import com.mindsync.library.data.NodeData

class RemoveNodeCommand(
    private val mindMapManager: MindMapManager,
    private val node: NodeData<*>,
) : MindMapCommand {
    override fun execute() {
        if (node is CircleNodeData) return
        mindMapManager.removeNode(node)
        mindMapManager.setSelectedNode(null)
    }
}