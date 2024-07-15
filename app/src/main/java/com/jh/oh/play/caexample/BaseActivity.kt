package com.jh.oh.play.caexample

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jh.oh.play.caexample.ui.dialog.DialogManager
import com.jh.oh.play.caexample.ui.dialogfragment.DialogFragmentManager
import com.jh.oh.play.domain.entity.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity<DB: ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes val layoutResId: Int
): AppCompatActivity() {
    abstract val viewModel: VM

    private lateinit var _binding: DB
    protected val binding get() = _binding

    protected var savedInstanceState: Bundle? = null

    @Inject internal lateinit var dialogManager: DialogManager
    @Inject internal lateinit var dialogFragmentManager: DialogFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
        }else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }

        if(layoutResId > 0) {
            _binding = DataBindingUtil.setContentView<DB>(this, layoutResId).apply {
                lifecycleOwner = this@BaseActivity
                setVariable(BR.viewModel, viewModel)
            }
        }

        loadView()
        registerListener()
        registerFlow()
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, 0, 0)
        }else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }

    protected open fun loadView() {}

    protected open fun registerListener() {}

    protected open fun registerFlow(){
        // 기본 loading progress, popup, snackbar를 사용하려면 해당 child class에서 super를 호출
        // 다른 UI가 적용된 loading progress, popup, snackbar를 사용하려면 child Activity에서
        // super를 호출하지 않고 각각 설정해줌(Ex. SplashActivity)
        lifecycleScope.repeatLaunchWhenCreated {
            viewModel.loadingDialogStatus.collect {
                setLoadingStatus(it)
            }
        }
        lifecycleScope.repeatLaunchWhenCreated {
            viewModel.showApiErrorPopup.collect {
                showApiErrorPopup(it)
            }
        }
        lifecycleScope.repeatLaunchWhenCreated {
            viewModel.showApiErrorFinishPopup.collect {
                showApiErrorFinishPopup(it)
            }
        }
    }

    protected fun setLoadingStatus(loadingType: Int = UIState.LOADING_SHOW_DEFAULT){
        setLoadingStatus(UIState.Loading(loadingType))
    }

    private fun setLoadingStatus(uiState: UIState.Loading) {
        when (uiState.loadingType) {
            UIState.LOADING_SHOW_DEFAULT ->
                dialogFragmentManager.showLoadingDialog(supportFragmentManager)
            UIState.LOADING_GONE -> dialogFragmentManager.hideLoadingDialog()
        }
    }

    protected open fun showApiErrorPopup(message: String) {
        dialogManager.showBaseDialog(
            context = this@BaseActivity,
            message = message
        )
    }

    protected open fun showApiErrorFinishPopup(message: String) {
        dialogManager.showBaseDialog(
            context = this@BaseActivity,
            message = message
        ).setOnDismissListener {
            finishApp()
        }
    }

    protected fun finishApp() {
        finishAffinity()
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