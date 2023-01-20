package com.utility.utilitylibrary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SystemPermission
{
    private static final int REQUEST_PERMISSIONS = 1234;

    private static final String permissionExtStorage           = "permission_ext_storage";
    private static final String permissionCamera               = "permission_camera";
    private static final String permissionLocation             = "permission_location";

    public static final String locationPermission             = "Location Permission";
    public static final String cameraPermission               = "Camera Permission";
    public static final String storagePermission              = "Storage Permission";
    public static final String cameraPermissionDescription    = "Allows apps to use your camera to take photos and record videos. Apps need this permission so you can take pictures";
    public static final String storagePermissionDescription   = "Allows apps to read and write to your internal or external storage. Apps need this permission so you can pick image or file form your device and you can upload it to server";
    public static final String locationPermissionDescription  = "Allows apps to access your approximate Location";

    private static final String permissionStorageTitle         = "Storage Permission";
    private static final String permissionStorageDescription   = "Grant storage permission to enable load or store image from/to storage";
    private static final String permissionCameraTitle          = "Camera Permission";
    private static final String permissionCameraDescription    = "Grant Camera permission to enable Camera capture feature";
    private static final String permissionFineLocationTitle    = "Location";
    private static final String permissionLocationDescription  = "Grant Location permission to enable location";

    private static final String PREF_KEY = "SystemPermission";

//------------------------------------------------------- isExternalStorage ----------------------------------------------------------------------------------------------------------------

    public static boolean isExternalStorage(Activity mActivity) {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                Utility.setPermissionDenied(mActivity, permissionExtStorage, true,PREF_KEY);
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle(permissionStorageTitle);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage(permissionStorageDescription);
                builder.setOnDismissListener(dialog -> mActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS));
                builder.show();
            }
            else
            {
                if((Utility.isPermissionDenied(mActivity, permissionExtStorage,PREF_KEY)))
                {
                    // redirect to settings
                    Utility.showToast(mActivity, permissionStorageDescription);
                    redirectToPermissionSettings(mActivity);
                }
                else {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
                }
            }
        }
        return false;
    }

//------------------------------------------------------- isCamera ----------------------------------------------------------------------------------------------------------------


    public static boolean isCamera(Activity mActivity) {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.CAMERA))
            {
                Utility.setPermissionDenied(mActivity, permissionCamera, true,PREF_KEY);
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle(permissionCameraTitle);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage(permissionCameraDescription);
                builder.setOnDismissListener(dialog -> mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS));
                builder.show();
            }
            else
            {   // became - shouldshowrequestRationale = true from here on permission denial.
                if((Utility.isPermissionDenied(mActivity, permissionCamera,PREF_KEY)))
                {
                    // redirect to settings
                    Utility.showToast(mActivity,permissionCameraDescription);
                    redirectToPermissionSettings(mActivity);
                }
                else {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
                }
            }
        }
        return false;
    }

//------------------------------------------------------- isLocation ----------------------------------------------------------------------------------------------------------------

    public static boolean isLocation(Activity mActivity){
        if( (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) )
        {
            return true;
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                Utility.setPermissionDenied(mActivity, permissionLocation, true,PREF_KEY);
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle(permissionFineLocationTitle);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage(permissionLocationDescription);

                builder.setOnDismissListener(dialog -> mActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS));
                builder.show();
            }
            else
            {   // became - shouldshowrequestRationale = true from here on permission denial.
                if((Utility.isPermissionDenied(mActivity, permissionLocation,PREF_KEY)))
                {
                    // redirect to settings
                    Utility.showToast(mActivity, permissionLocationDescription);
                    redirectToPermissionSettings(mActivity);
                }
                else {
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS);
                }
            }
        }
        return false;
    }

//------------------------------------------------------- isInternetConnected ----------------------------------------------------------------------------------------------------------------

    public static boolean isInternetConnected(Context context) {
        //boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network nw = connectivityManager.getActiveNetwork();
        if (nw == null) return false;
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
    }

//------------------------------------------------------- reDirect ----------------------------------------------------------------------------------------------------------------

    public static void redirectToPermissionSettings(Activity mContext) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        mContext.startActivity(intent);
    }

}
