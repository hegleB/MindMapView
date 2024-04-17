package com.mindsync.library

import com.mindsync.library.data.NodeData

interface NodeClickListener {
    fun onClickListener(node: NodeData<*>?)
}