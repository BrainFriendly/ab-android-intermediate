package com.androidbootcamp.dbcomplete.fragments.listener;


import com.androidbootcamp.dbcomplete.model.NoteEntity;
import com.androidbootcamp.dbcomplete.storage.CRUDOperations;

/**
 * Created by emedinaa on 15/09/15.
 */
public interface OnNoteListener {

     CRUDOperations getCrudOperations();
     void deleteNote(NoteEntity noteEntity);
     void editNote(NoteEntity noteEntity);
}
