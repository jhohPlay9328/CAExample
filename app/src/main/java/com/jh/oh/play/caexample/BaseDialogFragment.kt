package com.jh.oh.play.caexample

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


abstract class BaseDialogFragment<DB: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes private val layoutResId: Int,
): DialogFragment() {
    abstract val viewModel: VM
    abstract val parentViewModel: BaseViewModel
    protected val activityViewModel: MainViewModel by activityViewModels()

    private lateinit var _binding: DB
    protected val binding get() = _binding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // http://bts.popkontv.kr/issues/10251 이슈 대응
        // OS 13에서 앱 설정에 Push를 off하면 앱 process를 종료한다.
        // 이때 앱 History 목록에서 앱을 선택해서 실행하면 화면을 OnCreate부터 새로 그린다.
        // 이런 이유로 Main 화면만 남겨두고 모두 종료한다.
        // 레거시 앱에서 앱의 메모리 최적화 기능을 대응한 방법과 동일
        if(savedInstanceState != null) {
            dismiss()
        }

        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = DataBindingUtil.inflate(inflater, layoutResId, null, false)
        with(_binding){
            lifecycleOwner = this@BaseDialogFragment
            setVariable(BR.viewModel, viewModel)
        }

        loadView()
        registerListener()
        registerFlow()

        return super.onCreateDialog(savedInstanceState)
    }


    protected open fun loadView() {}

    protected open fun registerListener() {}

    protected open fun registerFlow(){
        lifecycleScope.repeatLaunchWhenCreated {
            viewModel.loadingDialogStatus.collect {
                activityViewModel.setLoadingDialogStatus(it)
            }
        }
        lifecycleScope.repeatLaunchWhenCreated {
            viewModel.showApiErrorPopup.collect {
                activityViewModel.showApiErrorPopup(it)
            }
        }
    }

    fun LifecycleCoroutineScope.repeatLaunchWhenCreated(
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                block()
            }
        }
    }

    val Int.dp: Float
        get() {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                resources.displayMetrics
            )
        }
}