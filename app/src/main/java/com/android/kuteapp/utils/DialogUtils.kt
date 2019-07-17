package com.kuteapp.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.android.kuteapp.R
import java.util.*


/**
 * Created by Anirudh_Sharma on 27-Jun-18.
 */
class DialogUtils(private val context: Context) {

    /**
     * Return an alert dialog
     *
     * @param message  message for the alert dialog
     * @param listener listener to trigger selection methods
     */
    fun openAlertDialog(message: String, positiveBtnText: String, negativeBtnText: String,
                        listener: OnDialogButtonClickListener) {

        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton(positiveBtnText, { dialog, _ ->
            dialog.dismiss()
            listener.onPositiveButtonClicked()

        })

        builder.setNegativeButton(negativeBtnText, { dialog, _ ->
            dialog.dismiss()
            listener.onNegativeButtonClicked()

        })
        builder.setTitle(context.resources.getString(R.string.app_name))
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setCancelable(false)
        builder.create().show()
    }

    /**
     * return a dialog object
     *
     * @return a dialog with given content
     */
    fun openDialog(@LayoutRes layoutId: Int): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(layoutId)
        dialog.window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    /**
     * return a date picker dialog
     */
    fun getDatePicker(listener: DatePickerDialog.OnDateSetListener): DatePickerDialog {

        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val picker = DatePickerDialog(context, listener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        picker.datePicker.maxDate = calendar.timeInMillis
        return picker
    }

/*    */
    /**
     * return a List popup window
     *
     * @return popup window
     *//*
    fun popupWindowSort(results: List<String>, anchor: EditText, clickListener: AdapterView.OnItemClickListener): PopupWindow {
        val window = PopupWindow(mContext)

        val adapter = ArrayAdapter(mContext, R.layout.item_user_suggestion, results)
        // the drop down list is a list view
        val listViewSort = CustomListView(mContext)
        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter)
        //set list color
        listViewSort.setBackgroundColor(Color.WHITE)
        // set on item selected
        listViewSort.setOnItemClickListener(clickListener)

        listViewSort.setOnKeyListener({ v, keyCode, event ->
            anchor.dispatchKeyEvent(event)
            false
        })

        // some other visual settings for popup window
        window.setWidth(anchor.width)
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        window.height = WindowManager.LayoutParams.WRAP_CONTENT
        // set the list view as popup content
        window.isOutsideTouchable = true
        window.isFocusable = true
        window.isTouchable = true
        window.contentView = listViewSort
        return window
    }

    fun getProgressDialog(): ProgressDialog {
        val pd = ProgressDialog(mContext, R.style.MyTheme)
        pd.setCancelable(false)
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small)
        return pd
    }*/

    interface OnDialogButtonClickListener {
        fun onPositiveButtonClicked()

        fun onNegativeButtonClicked()
    }
}