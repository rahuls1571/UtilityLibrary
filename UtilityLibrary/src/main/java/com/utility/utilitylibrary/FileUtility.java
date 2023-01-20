package com.utility.utilitylibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FileUtility {


    // Pick And Request
    public static final int REQUEST_TAKE_PHOTO = 1002;
    public static final int PICK_IMAGE_REQUEST = 1001;
    public static final int PICK_FILE_RESULT_CODE = 1004;
    public static final int PICK_AUDIO_FILE_RESULT_CODE = 1005;
    public static final int PICK_VIDEO_FILE_RESULT_CODE = 1006;

//------------------------------------------------------- Picker ----------------------------------------------------------------------------------------------------------------

    public static void openFilePicker(Activity context, int position, onPhotoCaptured onPhotoCaptured) {
        if (SystemPermission.isExternalStorage(context)) {
            Intent chooseFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("*/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            onPhotoCaptured.getPath(null, position);
            context.startActivityForResult(chooseFile, PICK_FILE_RESULT_CODE);
        }
    }

    public static void openFilePickerMultiple(Activity context, int position, onPhotoCaptured onPhotoCaptured) {
        if (SystemPermission.isExternalStorage(context)) {
            Intent chooseFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("*/*");
            chooseFile.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            onPhotoCaptured.getPath(null, position);
            context.startActivityForResult(chooseFile, PICK_FILE_RESULT_CODE);
        }
    }

    public static void openAudioFilePicker(Activity context, int position, onPhotoCaptured onPhotoCaptured) {
        if (SystemPermission.isExternalStorage(context)) {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("audio/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            onPhotoCaptured.getPath(null, position);
            context.startActivityForResult(chooseFile, PICK_AUDIO_FILE_RESULT_CODE);
        }
    }

    public static void openVideoFilePicker(Activity context, int position, onPhotoCaptured onPhotoCaptured) {
        if (SystemPermission.isExternalStorage(context)) {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("video/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            onPhotoCaptured.getPath(null, position);
            context.startActivityForResult(chooseFile, PICK_VIDEO_FILE_RESULT_CODE);
        }
    }

    public static void openImagePicker(Activity context, FileUtils fileUtils, int position, onPhotoCaptured onPhotoCaptured, String BuildConfig) {
        String[] items = getPhotoSelectionOptions();
        showItems(context, "Select", items, (dialogInterface, item) -> {
                    switch (items[item]) {
                        case PHOTO_SELECTION.TAKE_PHOTO:
                            if (SystemPermission.isExternalStorage(context)) {
                                if (SystemPermission.isCamera(context)) {
                                    takePhoto(context, fileUtils, position, onPhotoCaptured,BuildConfig);
                                }
                            }
                            break;
                        case PHOTO_SELECTION.CHOOSE_FROM_GAL:
                            if (SystemPermission.isExternalStorage(context)) {
                                pickFromGallery(context, position, onPhotoCaptured);
                            }
                            break;
                        case PHOTO_SELECTION.CANCEL:
                            dialogInterface.dismiss();
                            break;
                    }
                });
    }

    private static void pickFromGallery(Activity context, int position, onPhotoCaptured onPhotoCaptured) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        onPhotoCaptured.getPath(null, position);
        context.startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    private static void takePhoto(Activity context, FileUtils fileUtils, int position, onPhotoCaptured onPhotoCaptured, String BuildConfig) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File destFileTemp = fileUtils.getDestinationFile(fileUtils.getRootDirFile(context));
        Uri photoURI = FileProvider.getUriForFile(context, BuildConfig + ".provider", destFileTemp);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        onPhotoCaptured.getPath(destFileTemp.getAbsolutePath(), position);
        context.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }



//------------------------------------------------------- Calender ----------------------------------------------------------------------------------------------------------------

    public static Calendar getEveningStartTime() {
        Calendar calendar8pm = Calendar.getInstance(Locale.US);
        calendar8pm.set(Calendar.HOUR_OF_DAY, 18);
        calendar8pm.set(Calendar.MINUTE, 0);
        calendar8pm.set(Calendar.SECOND, 0);
        return calendar8pm;
    }

    public static Calendar getNoonStartTime() {
        Calendar calendar12pm = Calendar.getInstance(Locale.US);

        calendar12pm.set(Calendar.HOUR_OF_DAY, 12);
        calendar12pm.set(Calendar.MINUTE, 0);
        calendar12pm.set(Calendar.SECOND, 0);
        return calendar12pm;
    }

    public static String getRecordDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.format(c.getTime());
    }

    public static String getDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.format(c.getTime());
    }


    public static void showItems(Context context, String title, String[] items, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, onClickListener);
        builder.setCancelable(false);
        builder.create().show();
    }


//------------------------------------------------------- Interface -------------------------------------------------------------------------------------------------------------------------------------------------

    public interface PHOTO_SELECTION {
        String TAKE_PHOTO = "Take Photo";
        String CHOOSE_FROM_GAL = "Choose from Gallery";
        String CANCEL = "Cancel";
    }

    public static String[] getPhotoSelectionOptions() {
        return new String[]{PHOTO_SELECTION.TAKE_PHOTO,
                PHOTO_SELECTION.CHOOSE_FROM_GAL,
                PHOTO_SELECTION.CANCEL};
    }

    public interface onPhotoCaptured {
        void getPath(String path, int position);
    }

}
