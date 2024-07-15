package com.jh.oh.play.caexample

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.jh.oh.play.caexample.databinding.ActivityMainBinding
import com.jh.oh.play.caexample.thirdparty.ThirdPartyListener
import com.jh.oh.play.caexample.thirdparty.ThirdPartyManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main
), View.OnClickListener {
    override val viewModel: MainViewModel by viewModels()

    private lateinit var backPressedCallback: OnBackPressedCallback
    private var backKeyInterval = false

    private val thirdPartyListener = object: ThirdPartyListener() {
        // 네이버 로그인 인증 성공
        override fun onAuthSuccessNaver(accessToken: String) {
            super.onAuthSuccessNaver(accessToken)

            // 네이버는 인증 완료 후에 사용자 정보를 따로 조회해야 한다.
            requestNaverUserData(
                "Bearer $accessToken"
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }

    override fun loadView() {
        super.loadView()

    }

    override fun registerListener() {
        super.registerListener()

        if(::backPressedCallback.isInitialized.not()) {
            backPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    doubleTapFinishApp()
                }
            }
            onBackPressedDispatcher.addCallback(this, backPressedCallback)
        }

        binding.btnLoginNaver.setOnClickListener(this)
    }

    override fun registerFlow() {
        super.registerFlow()

        lifecycleScope.repeatLaunchWhenCreated {
            // 네이버 유저 정보를 가져오는 네이버 API
            // 네이버는 로그인 유효성 검사 체크가 완료된 후에 유저 정보를 가져오는 API를 별도로 호출해야 한단.
            // 카카오톡은 로그인 유효성 검사 완료 API에서 유저 정보를 내려준다.
            viewModel.getNaverUserData.collect {

            }
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnLoginNaver -> {
                ThirdPartyManager.loginNaver(this, thirdPartyListener)
            }
            else -> {}
        }
    }

    private fun requestNaverUserData(accessToken: String) {
        viewModel.requestNaverUserData(
            getString(R.string.naver_client_id),
            getString(R.string.naver_client_secret),
            accessToken
        )
    }

    private fun doubleTapFinishApp() {
        if(!backKeyInterval) {
            backKeyInterval = true

            Snackbar.make(
                binding.root,
                getString(R.string.snackbar_finish_app),
                Snackbar.LENGTH_SHORT
            ).addCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {
                    super.onShown(sb)
                    // snackbar가 완전히 보여진 뒤에 호출되는 이벤트로 빠르게 두번 클릭하면 종료되지 않는
                    // 이슈가 발생하여 제거
//                    backKeyInterval = true
                }

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    backKeyInterval = false
                }
            }).show()
        } else {
            finishApp()
        }
    }
}