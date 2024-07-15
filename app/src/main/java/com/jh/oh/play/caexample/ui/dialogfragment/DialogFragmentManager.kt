package com.jh.oh.play.caexample.ui.dialogfragment

import androidx.fragment.app.FragmentManager
import com.jh.oh.play.caexample.ui.dialogfragment.loading.LoadingDialog
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class DialogFragmentManager @Inject constructor(){
    private lateinit var loadingDialog: LoadingDialog

    fun showLoadingDialog(
        fragmentManager: FragmentManager
    ) {
        hideLoadingDialog()

        loadingDialog = LoadingDialog().apply {
            show(fragmentManager, "LoadingDialog")
        }
    }
    fun hideLoadingDialog() {
        if(::loadingDialog.isInitialized) loadingDialog.dismiss()
    }
}