package com.example.gitmvpapp.ui.flow.base.contract;

import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {

    void updateLoading(boolean show);

    void showError(String title, String description);
}

