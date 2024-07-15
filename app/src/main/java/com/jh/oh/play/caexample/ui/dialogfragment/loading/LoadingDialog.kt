package com.jh.oh.play.caexample.ui.dialogfragment.loading

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jh.oh.play.caexample.BaseDialogFragment
import com.jh.oh.play.caexample.MainViewModel
import com.jh.oh.play.caexample.R
import com.jh.oh.play.caexample.databinding.DialogLoadingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingDialog: BaseDialogFragment<DialogLoadingBinding, LoadingViewModel>(
    R.layout.dialog_loading,
) {
    override val viewModel: LoadingViewModel by viewModels()
    override val parentViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        return inflater.inflate(R.layout.dialog_loading, container, false)
    }
}