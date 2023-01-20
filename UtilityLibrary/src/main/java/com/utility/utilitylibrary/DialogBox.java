package com.utility.utilitylibrary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import java.util.Calendar;

public class DialogBox {

//------------------------------------------------------- Dialog box ----------------------------------------------------------------------------------------------------------------

    public static void show(Context context, String title,String  msg, String firstButtonName, String secondButtonName, onDialogClickListener onDialogClickListener){
    AlertDialog alertDialog = new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(firstButtonName, (dialog, i) -> onDialogClickListener.onBtnFirstAction(dialog))
            .setNegativeButton(secondButtonName, (dialog, i) -> onDialogClickListener.onBtnSecondAction(dialog))
            .create();
    alertDialog.show();
}

//------------------------------------------------------- Picker Dialog box ----------------------------------------------------------------------------------------------------------------

    public static void showDatePicker(Context context, onDateSelection onDateSelection) {
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> onDateSelection.onDateSelected( validateDoubleDigit(year)+"-"+validateDoubleDigit((monthOfYear + 1)) + "-"+ validateDoubleDigit(dayOfMonth)), mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static void showTimePicker(Context context, onTimeSelection onTimeSelection,boolean is24hrView) {
        int mHour, mMinute, mSec;
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSec = c.get(Calendar.SECOND);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> onTimeSelection.onTimeSelected(validateDoubleDigit(hourOfDay) + ":" + validateDoubleDigit(minute) + ":" + validateDoubleDigit(mSec)), mHour, mMinute, is24hrView);
        timePickerDialog.show();
    }

    public static String validateDoubleDigit(int digit) {
        if (String.valueOf(digit).length() < 2) {
            return "0" + digit;
        } else {
            return "" + digit;
        }
    }

//------------------------------------------------------- Ok Cancel Dialog box ----------------------------------------------------------------------------------------------------------------

    public static void showOKCancel(Context context, String title,String  msg, OkClick okClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> okClick.okClick(dialog))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
        alertDialog.show();
    }

    public static void showOKCancel(Context context, String  msg, OkClick okClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> okClick.okClick(dialog))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
        alertDialog.show();
    }

    public static void showOKCancel(Context context, String title,String  msg, OkClick okClick, CancelClick cancelClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> okClick.okClick(dialog))
                .setNegativeButton("Cancel", (dialog, i) -> cancelClick.cancelClick(dialog))
                .create();
        alertDialog.show();
    }

    public static void showOKCancel(Context context, String  msg, OkClick okClick, CancelClick cancelClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> okClick.okClick(dialog))
                .setNegativeButton("Cancel", (dialog, i) -> cancelClick.cancelClick(dialog))
                .create();
        alertDialog.show();
    }

//------------------------------------------------------- Yes No Dialog box ----------------------------------------------------------------------------------------------------------------

    public static void showYesNo(Context context, String title,String  msg, YesClick dialogBoxYesClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, i) -> dialogBoxYesClick.yesClick(dialog) )
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
        alertDialog.show();
    }

    public static void showYesNo(Context context, String  msg, YesClick dialogBoxYesClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, i) -> dialogBoxYesClick.yesClick(dialog) )
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
        alertDialog.show();
    }

    public static void showYesNo(Context context,String title,String msg, YesClick dialogBoxYesClick, NoClick dialogBoxNoClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogOk, i) -> dialogBoxYesClick.yesClick(dialogOk))
                .setNegativeButton("No", (dialogCancel, i) -> dialogBoxNoClick.noClick(dialogCancel))
                .create();
        alertDialog.show();
    }

    public static void showYesNo(Context context, String msg, YesClick dialogBoxYesClick, NoClick dialogBoxNoClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogOk, i) -> dialogBoxYesClick.yesClick(dialogOk))
                .setNegativeButton("No", (dialogCancel, i) -> dialogBoxNoClick.noClick(dialogCancel))
                .create();
        alertDialog.show();
    }

//------------------------------------------------------- Yes No Dialog box ----------------------------------------------------------------------------------------------------------------

    public static void showOK(Context context, String title,String msg, OkClick dialogBoxOKClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> dialogBoxOKClick.okClick(dialog) )
                .create();
        alertDialog.show();
    }

    public static void showOK(Context context, String  msg, OkClick dialogBoxOKClick){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, i) -> dialogBoxOKClick.okClick(dialog) )
                .create();
        alertDialog.show();
    }

    public static void showOK(Context context,String title,String  msg, String btnName ,OkClick dialogBoxOKClick){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        if(!isEmptyString(title)){
            alertDialog.setTitle(title);
        }
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(btnName, (dialog, i) -> dialogBoxOKClick.okClick(dialog));
        alertDialog.create();
        alertDialog.show();
    }

//------------------------------------------------------- Selected Items Dialog box ----------------------------------------------------------------------------------------------------------------

    public static void showSelectedItems(Context context,String title, String[] itemsLists, ItemSelected itemSelected){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(itemsLists, (dialog, which) -> {
            itemSelected.onItemSelected(itemsLists[which]);
            dialog.dismiss();
        });
        builder.show();
    }

//------------------------------------------------------- DialogBox Interface -------------------------------------------------------------------------------------------------------------------------------------------------

    public interface OkClick{ void okClick(DialogInterface okDialogBox);}

    public interface CancelClick{ void cancelClick(DialogInterface cancelDialogBox);}

    public interface NoClick{ void noClick(DialogInterface noDialogBox);}

    public interface YesClick{ void yesClick(DialogInterface yesDialogBox);}

    public interface ItemSelected{ void onItemSelected(String itemSelect);}

    public interface onDialogClickListener {
        void onBtnFirstAction(DialogInterface dialogInterface);
        void onBtnSecondAction(DialogInterface dialogInterface);
    }

    public interface onDateSelection {
        void onDateSelected(String date);
    }

    public interface onTimeSelection {
        void onTimeSelected(String time);
    }

//------------------------------------------------------- isEmpty String -------------------------------------------------------------------------------------------------------------------------------------------------
    public static boolean isEmptyString(String str) {
        if (str == null) {
            return true;
        } else if (str.isEmpty()) {
            return true;
        } else return str.equalsIgnoreCase("null");
    }
}
