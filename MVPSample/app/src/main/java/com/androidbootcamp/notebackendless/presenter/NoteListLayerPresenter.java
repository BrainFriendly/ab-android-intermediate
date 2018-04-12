package com.androidbootcamp.notebackendless.presenter;


import com.androidbootcamp.notebackendless.storage.Injection;
import com.androidbootcamp.notebackendless.storage.RemoteCallback;
import com.androidbootcamp.notebackendless.storage.RemoteProvider;
import com.androidbootcamp.notebackendless.storage.network.ApiClient;
import com.androidbootcamp.notebackendless.storage.network.StorageConstant;
import com.androidbootcamp.notebackendless.storage.network.entity.NotesBLResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eduardomedina on 13/03/18.
 */

public class NoteListLayerPresenter {

    private NoteListView view;
    private RemoteProvider provider;

    public NoteListLayerPresenter() {
        provider= Injection.getProvider();
    }

    public void loadDataBackendless(String token){
        view.showLoading();

        provider.notes(token, new RemoteCallback() {
            @Override
            public void onSuccess(Object result) {
                view.hideLoading();

                if(result instanceof NotesBLResponse){
                    NotesBLResponse response= (NotesBLResponse)result;
                    if(response.isEmpty()){
                        view.emptyNotes();
                    }else{
                        view.renderNotesBL(response);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                view.hideLoading();
                view.showMessage(e.getMessage());
            }
        });
    }

    public void loadDataBackendless_(String token){
        view.showLoading();

        //String token= PreferencesHelper.getTokenSession(this);

        Map<String, String> map = new HashMap<>();
        map.put("user-token",token);
        Call<NotesBLResponse> call= ApiClient.getMyApiClient().notesbl(
                StorageConstant.APPLICATIONID, StorageConstant.RESTAPIKEY,map);

        call.enqueue(new Callback<NotesBLResponse>() {
            @Override
            public void onResponse(Call<NotesBLResponse> call, Response<NotesBLResponse> response) {
                view.hideLoading();
                if(response!=null){
                    NotesBLResponse notesResponse=null;
                    if(response.isSuccessful()){
                        notesResponse= response.body();
                        if(notesResponse.isEmpty()){
                            view.emptyNotes();
                        }else{
                            view.renderNotesBL(notesResponse);
                        }
                    }else{
                    }
                }
            }

            @Override
            public void onFailure(Call<NotesBLResponse> call, Throwable t) {
                view.hideLoading();
                view.showMessage(t.getMessage());
            }
        });
    }

    public void logout(){
        view.logOut();
    }
    public void attachView(NoteListView view){
        this.view=view;
    }
    public void detachView(){
        this.view=null;
    }
}
