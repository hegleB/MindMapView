package com.mindsync.library

import com.mindsync.library.data.NodeData
import com.mindsync.library.data.Tree

interface NodeMoveListener {
    fun onMoveListener(
        tree: Tree<*>,
        target: NodeData<*>,
        parent: NodeData<*>,
    )
}