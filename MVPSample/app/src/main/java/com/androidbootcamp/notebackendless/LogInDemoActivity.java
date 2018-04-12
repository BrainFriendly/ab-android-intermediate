package com.androidbootcamp.notebackendless;

import com.androidbootcamp.notebackendless.presenter.LogInView;
import com.androidbootcamp.notebackendless.storage.network.entity.LogInBLResponse;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 4/11/18
 */

public class LogInDemoActivity implements LogInView {
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void gotoMain() {

    }

    @Override
    public void gotoUserRegister() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public boolean validateForm() {
        return false;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void saveSession(LogInBLResponse logInResponse) {

    }
}
