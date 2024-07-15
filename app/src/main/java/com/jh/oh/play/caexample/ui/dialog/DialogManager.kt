package com.jh.oh.play.caexample.ui.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jh.oh.play.caexample.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class DialogManager @Inject constructor(){
    private lateinit var baseDialog: AlertDialog

    fun showBaseDialog(
        context: Context,
        title: String? = null,
        message: String,
        positiveTextId: Int = R.string.button_ok,
        positiveClickListener: DialogInterface.OnClickListener? = null,
        negativeTextId: Int = R.string.button_cancel,
        negativeClickListener: DialogInterface.OnClickListener? = null,
        neutralTextId: Int = R.string.button_close,
        neutralClickListener: DialogInterface.OnClickListener? = null,
    ): AlertDialog {
        val builder = MaterialAlertDialogBuilder(context)
        if(title.isNullOrEmpty().not()) builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveTextId, positiveClickListener)
        if(negativeClickListener != null)
            builder.setNegativeButton(negativeTextId, negativeClickListener)
        if(neutralClickListener != null)
            builder.setNeutralButton(neutralTextId, neutralClickListener)

        baseDialog = builder.show()
        return baseDialog
    }
    fun hideBaseDialog() {
        if(::baseDialog.isInitialized) baseDialog.dismiss()
    }
}