package com.example.gitmvpapp.manager;

import android.app.Activity;
import android.app.AlertDialog;

import com.example.gitmvpapp.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import timber.log.Timber;

public class PermissionManager {

    public static final int APP_PERMISSION_IS_ON = 0;
    public static final int APP_PERMISSION_IS_OFF = -1;

    public static <T extends Activity> void checkPermission(T activity,
                                                            String permission,
                                                            String explanation,
                                                            OnPermissionGrantedCallback grantedCallback,
                                                            OnPermissionDeniedCallback deniedCallback) {

        Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(getPermissionListener(activity, explanation, grantedCallback, deniedCallback))
                .check();
    }

    public static <T extends Activity> void checkPermissions(T activity,
                                                             List<String> permissions,
                                                             String explanation,
                                                             OnPermissionsGrantedCallback grantedCallback,
                                                             OnPermissionsDeniedCallback deniedCallback) {

        Dexter.withActivity(activity)
                .withPermissions(permissions)
                .withListener(getPermissionsListener(activity, explanation, grantedCallback, deniedCallback))
                .check();
    }


    private static <T extends Activity> PermissionListener getPermissionListener(T activity,
                                                                                 String explanation,
                                                                                 OnPermissionGrantedCallback grantedCallback,
                                                                                 OnPermissionDeniedCallback deniedCallback) {

        return new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                grantedCallback.onPermissionGranted(response);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                if (response.isPermanentlyDenied()) {
                    Timber.d(activity.getString(R.string.permissions_permanently_denied));
                } else {
                    Timber.d(activity.getString(R.string.permissions_temporary_denied));
                }
                deniedCallback.onPermissionDenied(response);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                           PermissionToken token) {
                new AlertDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.permissions_request))
                        .setMessage(explanation)
                        .setNegativeButton(activity.getString(R.string.cancel), (dialog, which) -> {
                            dialog.dismiss();
                            token.cancelPermissionRequest();
                        })
                        .setPositiveButton(activity.getString(R.string.ok), (dialog, which) -> {
                            dialog.dismiss();
                            token.continuePermissionRequest();
                        })
                        .setOnDismissListener(dialog -> token.cancelPermissionRequest())
                        .show();
            }
        };
    }

    private static <T extends Activity> MultiplePermissionsListener getPermissionsListener(T activity,
                                                                                           String explanation,
                                                                                           OnPermissionsGrantedCallback grantedCallback,
                                                                                           OnPermissionsDeniedCallback deniedCallback) {

        return new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    grantedCallback.onPermissionsGranted(report);
                }

                if (report.isAnyPermissionPermanentlyDenied()) {
                    deniedCallback.onPermissionsDenied(report);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                           PermissionToken token) {
                new AlertDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.permissions_request))
                        .setMessage(explanation)
                        .setNegativeButton(activity.getString(R.string.cancel), (dialog, which) -> {
                            dialog.dismiss();
                            token.cancelPermissionRequest();
                        })
                        .setPositiveButton(activity.getString(R.string.ok), (dialog, which) -> {
                            dialog.dismiss();
                            token.continuePermissionRequest();
                        })
                        .setOnDismissListener(dialog -> token.cancelPermissionRequest())
                        .show();
            }
        };
    }

    public interface OnPermissionGrantedCallback {
        void onPermissionGranted(PermissionGrantedResponse response);
    }

    public interface OnPermissionDeniedCallback {
        void onPermissionDenied(PermissionDeniedResponse response);
    }

    public interface OnPermissionsGrantedCallback {
        void onPermissionsGranted(MultiplePermissionsReport report);
    }

    public interface OnPermissionsDeniedCallback {
        void onPermissionsDenied(MultiplePermissionsReport report);
    }
}

