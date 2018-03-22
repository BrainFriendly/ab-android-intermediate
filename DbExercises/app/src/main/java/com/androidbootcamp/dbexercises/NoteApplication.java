package com.androidbootcamp.dbexercises;

import android.app.Application;

import com.androidbootcamp.dbexercises.storage.NoteRepository;


/**
 * Created by eduardomedina on 27/02/18.
 */

public class NoteApplication extends Application {

    private NoteRepository noteRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        noteRepository= new NoteRepository(this);
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }
}
