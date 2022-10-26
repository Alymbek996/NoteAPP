package com.geektech.noteapp.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.usecase.CreateNoteUseCase
import com.example.noteapp.domain.usecase.DeleteNoteUseCase
import com.example.noteapp.domain.usecase.EditNoteUseCase
import com.example.noteapp.domain.usecase.GetNoteAllUseCase
import com.geektech.noteapp.domain.utils.Resource
import com.geektech.noteapp.presentation.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNoteAllUseCase: GetNoteAllUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
) : ViewModel() {
    private val _getAllNoteState = MutableStateFlow<UIState<List<Note>>>(UIState.Loading())
    val getAllNoteState = _getAllNoteState.asStateFlow()

    private val _creteNoteState = MutableStateFlow<UIState<Unit >>(UIState.Loading())
    val crateNoteState = _creteNoteState.asStateFlow()


    fun getAllNotes() {
        viewModelScope.launch {
            getNoteAllUseCase.getAllNote().collect {
                when (it) {
                    is Resource.Loading -> {
                        _getAllNoteState.value = UIState.Loading()
                    }
                    is Resource.Error -> {
                        _getAllNoteState.value = UIState.Error(it.message ?: "Something went wrong")
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            _getAllNoteState.value = UIState.Success(it.data)
                        }
                    }
                }
            }
        }
    }

    fun createNote(note: Note) {
        viewModelScope.launch {
            createNoteUseCase.createNote(note).collect {
                when (it) {
                    is Resource.Loading -> {
                        _creteNoteState.value = UIState.Loading()
                    }
                    is Resource.Error -> {
                        _creteNoteState.value =
                            UIState.Error(it.message ?: "Something went wrong")
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            _creteNoteState.value = UIState.Success(it.data)
                        }
                    }

                }
            }
        }
    }
}