package com.farms.adhanapp.permission.helper;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {

    private static final int PERMISSION_REQUEST_CODE = 1;

    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean requestPermission(@NonNull AppCompatActivity activity, String permission) {
        if (checkPermission(activity, permission)) {
            // Permission already granted
            return true;
        } else {
            // Request the permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    PERMISSION_REQUEST_CODE);
            return false;
        }
    }

    // Handle the result of the permission request
    public static boolean onRequestPermissionsResult(int requestCode,
                                                     @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Check if the permission was granted
            return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
}
