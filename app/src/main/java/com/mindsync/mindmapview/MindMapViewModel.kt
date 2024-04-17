package com.mindsync.mindmapview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindsync.library.data.Tree
import com.mindsync.mindmapview.crdt.CrdtTree
import com.mindsync.mindmapview.crdt.Operation
import com.mindsync.mindmapview.crdt.OperationAdd
import com.mindsync.mindmapview.crdt.OperationDelete
import com.mindsync.mindmapview.crdt.OperationMove
import com.mindsync.mindmapview.crdt.OperationType
import com.mindsync.mindmapview.crdt.OperationUpdate
import com.mindsync.mindmapview.mindmap.SerializedCrdtTree
import com.mindsync.mindmapview.mindmap.SerializedOperation
import com.mindsync.mindmapview.model.Node
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class MindMapViewModel: ViewModel() {
    private var _selectedNode = MutableStateFlow<Node?>(null)
    val selectedNode: StateFlow<Node?> = _selectedNode

    fun setSelectedNode(selectNode: Node?) {
        _selectedNode.value = selectNode
    }
}
