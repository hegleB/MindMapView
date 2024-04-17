package com.mindsync.mindmapview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MindMapViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MindMapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MindMapViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
