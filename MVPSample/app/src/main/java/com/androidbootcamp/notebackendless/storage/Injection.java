package com.androidbootcamp.notebackendless.storage;

import com.androidbootcamp.notebackendless.storage.network.RestProvider;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 4/11/18
 */

public class Injection {

    public static RemoteProvider getProvider(){
        return RestProvider.getInstance();
    }
}
