package com.androidbootcamp.dbroomcomplete.fragments.listener;


import com.androidbootcamp.dbroomcomplete.model.NoteEntity;
import com.androidbootcamp.dbroomcomplete.storage.CRUDOperations;
import com.androidbootcamp.dbroomcomplete.storage.NoteRepository;

/**
 * Created by emedinaa on 15/09/15.
 */
public interface OnNoteListener {

     CRUDOperations getCrudOperations();
     NoteRepository getNoteRepository();
     void deleteNote(NoteEntity noteEntity);
     void editNote(NoteEntity noteEntity);
}
