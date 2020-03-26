package io.homeassistant.companion.android.launch

import android.os.Build
import android.util.Log
import io.homeassistant.companion.android.BuildConfig
import io.homeassistant.companion.android.domain.authentication.AuthenticationUseCase
import io.homeassistant.companion.android.domain.authentication.SessionState
import io.homeassistant.companion.android.domain.integration.IntegrationUseCase
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LaunchPresenterImpl @Inject constructor(
    private val view: LaunchView,
    private val authenticationUseCase: AuthenticationUseCase,
    private val integrationUseCase: IntegrationUseCase
) : LaunchPresenter {

    companion object {
        const val TAG = "LaunchPresenter"
    }

    private val mainScope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onViewReady() {
        mainScope.launch {
            val sessionValid = authenticationUseCase.getSessionState() == SessionState.CONNECTED
            if (sessionValid && integrationUseCase.isRegistered()) {
                if (authenticationUseCase.isLockEnabled())
                    view.displayLockView()
                else
                    view.displayWebview()
            } else {
                view.displayOnBoarding(sessionValid)
            }
        }
    }

    override fun onFinish() {
        mainScope.cancel()
    }

}
