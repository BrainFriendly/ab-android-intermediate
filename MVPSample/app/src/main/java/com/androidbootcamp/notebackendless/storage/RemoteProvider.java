package com.androidbootcamp.notebackendless.storage;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 4/11/18
 */

public interface RemoteProvider {

    public void logIn(String username, String password, final RemoteCallback callback);
}
