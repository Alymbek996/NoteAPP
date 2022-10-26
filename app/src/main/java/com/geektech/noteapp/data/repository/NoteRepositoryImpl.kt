package com.example.noteapp.data.repository

import com.example.noteapp.data.localdb.NoteDao
import com.example.noteapp.data.mapper.noteEntityToNote
import com.example.noteapp.data.mapper.noteToNoteEntity
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.repository.NoteRepository
import com.geektech.noteapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {


    override fun createNote(note: Note): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val data = noteDao.createNote(note.noteToNoteEntity())
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage as String))
        }
    }

    override fun editNote(note: Note): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val data = noteDao.edit(note.noteToNoteEntity())
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage as String))
        }

    }


    override fun deleteNote(note: Note): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val data = noteDao.deleteNote(note.noteToNoteEntity())
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage as String))
        }

    }

    override fun getAll(): Flow<Resource<List<Note>>> = flow {
        emit(Resource.Loading())
        try {
            val data = noteDao.getAllNotes().map { it.noteEntityToNote() }

            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage as String))
        }
    }
}
