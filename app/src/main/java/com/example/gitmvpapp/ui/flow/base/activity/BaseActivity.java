package com.example.gitmvpapp.ui.flow.base.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;

import com.arellomobile.mvp.MvpDelegate;
import com.example.gitmvpapp.R;
import com.example.gitmvpapp.app.MvpApplication;
import com.example.gitmvpapp.di.component.ApplicationComponent;
import com.example.gitmvpapp.manager.DialogManager;
import com.example.gitmvpapp.manager.PictureManager;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;

import static com.example.gitmvpapp.utils.InteractionUtils.openSettings;

@SuppressWarnings("unused")
public abstract class BaseActivity extends AppCompatActivity {

    private DialogManager dialogManager;
    private PictureManager pictureManager;
    private MvpDelegate<? extends BaseActivity> mvpDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
        initDependencies(((MvpApplication) getApplication()).getApplicationComponent());

        getLifecycle().addObserver(getLifecycleObserver());
        overridePendingTransition(R.anim.animation_activity_fade_in,
                R.anim.animation_activity_fade_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvpDelegate().onAttach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onAttach();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(getLifecycleObserver());
        closeDialogs();

        getMvpDelegate().onDestroyView();
        if (isFinishing()) getMvpDelegate().onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.animation_activity_fade_in,
                R.anim.animation_activity_fade_out);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPictureManager().onActivityResult(requestCode, resultCode, data);
    }

    protected abstract LifecycleObserver getLifecycleObserver();

    protected abstract void initDependencies(ApplicationComponent applicationComponent);

    public DialogManager showDialog() {
        return getDialogManager();
    }

    public void startCropImage(PictureManager.OnCropImageCallback cropImageCallback) {
        getPictureManager().getCroppedImage(cropImageCallback, this::openSettingsDialog);
    }

    protected FragmentTransaction startTransaction() {
        return getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.animation_fragment_fade_open, R.anim.animation_ffragment_ade_close,
                R.anim.animation_fragment_fade_open, R.anim.animation_ffragment_ade_close);
    }

    private void openSettingsDialog() {
        getDialogManager().confirm("", getString(R.string.settings_permission_explanation),
                isPositive -> {
                    if (isPositive) startActivity(openSettings(this));
                });
    }

    private void closeDialogs() {
        getDialogManager().closeDialogs();
    }

    private DialogManager getDialogManager() {
        if (dialogManager == null) dialogManager = new DialogManager(this);
        return dialogManager;
    }

    private PictureManager getPictureManager() {
        if (pictureManager == null) pictureManager = new PictureManager(this);
        return pictureManager;
    }

    private MvpDelegate<? extends BaseActivity> getMvpDelegate() {
        if (mvpDelegate == null) mvpDelegate = new MvpDelegate<>(this);
        return mvpDelegate;
    }
}
