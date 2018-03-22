package com.androidbootcamp.dbexercises.fragments.listener;


import com.androidbootcamp.dbexercises.model.NoteEntity;
import com.androidbootcamp.dbexercises.storage.CRUDOperations;
import com.androidbootcamp.dbexercises.storage.NoteRepository;

/**
 * Created by emedinaa on 15/09/15.
 */
public interface OnNoteListener {

     CRUDOperations getCrudOperations();
     NoteRepository getNoteRepository();
     void deleteNote(NoteEntity noteEntity);
     void editNote(NoteEntity noteEntity);
}
