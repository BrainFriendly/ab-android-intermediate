package com.androidbootcamp.notebackendless.presenter;


import com.androidbootcamp.notebackendless.storage.network.entity.LogInBLResponse;

/**
 * Created by emedinaa on 27/10/17.
 */

public interface LogInContract {

    interface View{
        void showLoading();
        void hideLoading();
        void gotoMain();
        void gotoUserRegister();
        void showMessage(String message);

        boolean validateForm();

        String getUsername();
        String getPassword();

        void saveSession(LogInBLResponse logInResponse);
    }

    interface Presenter{
        void logIn();
        void authentication();
        void goToUserRegister();

        void cancelRequest();
        void attachView(View view);
        void detachView();
    }
}
