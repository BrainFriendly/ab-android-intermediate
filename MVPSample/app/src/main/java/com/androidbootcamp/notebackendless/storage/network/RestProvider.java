package com.androidbootcamp.notebackendless.storage.network;

import android.util.Log;

import com.androidbootcamp.notebackendless.storage.RemoteCallback;
import com.androidbootcamp.notebackendless.storage.RemoteProvider;
import com.androidbootcamp.notebackendless.storage.network.entity.LogInBLRaw;
import com.androidbootcamp.notebackendless.storage.network.entity.LogInBLResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 4/11/18
 */

public class RestProvider implements RemoteProvider {

    private static RestProvider instance = null;
    protected RestProvider() {}

    public static RestProvider getInstance() {
        if(instance == null) {
            instance = new RestProvider();
        }
        return instance;
    }

    @Override
    public void logIn(String username, String password, final RemoteCallback callback) {

        final LogInBLRaw logInRaw= new LogInBLRaw();
        logInRaw.setLogin(username);
        logInRaw.setPassword(password);

        Call<LogInBLResponse> call= ApiClient.getMyApiClient().logInBL(
                StorageConstant.APPLICATIONID,
                StorageConstant.RESTAPIKEY,logInRaw);

        call.enqueue(new Callback<LogInBLResponse>() {
            @Override
            public void onResponse(Call<LogInBLResponse> call, Response<LogInBLResponse> response) {
                if(response!=null){
                    LogInBLResponse logInResponse=null;

                    if(response.isSuccessful()){
                        logInResponse=response.body();
                        if(logInResponse!=null){
                            callback.onSuccess(logInResponse);
                        }
                    }else{
                        Log.v("CONSOLE", "error "+response.errorBody());

                        JSONObject jsonObject = null;
                        try {
                            jsonObject=new JSONObject(response.errorBody().string());
                        }catch (Exception e){
                            jsonObject= new JSONObject();
                        }
                        logInResponse= GsonHelper.jsonToLogInBLResponse(jsonObject.toString());
                        callback.onFailure(new Exception(logInResponse.getMessage()));

                    }
                }else{
                    callback.onFailure(new Exception("Ocurrió un error"));
                }
            }

            @Override
            public void onFailure(Call<LogInBLResponse> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }
}
