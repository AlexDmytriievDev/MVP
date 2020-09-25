package com.example.gitmvpapp.manager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.example.gitmvpapp.R;
import com.theartofdev.edmodo.cropper.CropImage;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

public class PictureManager {

    private int imageWidth;
    private int imageHeight;
    private boolean imageZoomOn;

    private final Activity activity;
    private OnCropImageCallback cropImageCallback;

    public PictureManager(Activity activity) {
        this.activity = activity;
    }

    public void getCroppedImage(OnCropImageCallback cropImageCallback,
                                OnCropImagePermissionCallback permissionCallback) {
        getCroppedImage(0, 0, false,
                cropImageCallback, permissionCallback);
    }

    public void getCroppedImage(int imageWidth, int imageHeight, boolean imageZoomOn,
                                OnCropImageCallback cropImageCallback,
                                OnCropImagePermissionCallback permissionCallback) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageZoomOn = imageZoomOn;
        this.cropImageCallback = cropImageCallback;
        setupPermissions(permissionCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                    setupCropImageResult(data);
                    break;
                }
                case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE: {
                    setupPickImageResult(data);
                    break;
                }
                case CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE: {
                    setupCropImageResult(data);
                    break;
                }
            }
        }
    }

    private void setupCropImageResult(Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (cropImageCallback != null && result != null && result.getUri() != null) {
            cropImageCallback.onCropImageResult(result.getUri());
        } else {
            Timber.d(activity.getString(R.string.cropped_image_data_empty));
        }
    }

    private void setupPickImageResult(Intent data) {
        Uri imageUri = CropImage.getPickImageResultUri(activity, data);

        if (CropImage.isReadExternalStoragePermissionsRequired(activity, imageUri)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            }
        } else {
            startCropImageActivity(imageUri);
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.ActivityBuilder cropImageActivityBuilder = CropImage.activity(imageUri);
        if (imageWidth > 0 && imageHeight > 0) {
            cropImageActivityBuilder.setAspectRatio(imageWidth, imageHeight)
                    .setRequestedSize(imageWidth, imageHeight);
        }
        cropImageActivityBuilder.setAutoZoomEnabled(imageZoomOn);
        cropImageActivityBuilder.start(activity);
    }

    private void setupPermissions(OnCropImagePermissionCallback permissionCallback) {
        int cameraPermission = ContextCompat
                .checkSelfPermission(activity, Manifest.permission.CAMERA);
        int readPermission = ContextCompat
                .checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ContextCompat
                .checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (cameraPermission == PermissionManager.APP_PERMISSION_IS_ON
                && readPermission == PermissionManager.APP_PERMISSION_IS_ON
                && writePermission == PermissionManager.APP_PERMISSION_IS_ON) {
            CropImage.startPickImageActivity(activity);
        } else {
            permissionCallback.onPermissionDenied();
        }
    }

    public interface OnCropImageCallback {
        void onCropImageResult(Uri image);
    }

    public interface OnCropImagePermissionCallback {
        void onPermissionDenied();
    }
}
