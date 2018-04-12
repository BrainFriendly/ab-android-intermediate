package com.androidbootcamp.notebackendless.storage;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 4/11/18
 */

public interface RemoteCallback {

   void onSuccess(Object result);
   void onFailure(Exception e);
}
