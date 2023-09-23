package at.cgaisl.statecomposearticle.android//package at.cgaisl.statecomposearticle.android

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class PhoneDialerScreenViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun phoneDialerScreenViewModelTest() = runTest {
        val viewModel = PhoneDialerScreenViewModel()

        // Capture side effects
        viewModel.sideEffects.test {

            // Trigger action
            viewModel.loadUsername()

            // Assert state
            assertEquals("Christian", viewModel.state.value.username)

            // Trigger action
            viewModel.inputPhoneNumber("1234567890")

            // Assert state
            assertEquals("1234567890", viewModel.state.value.phoneNumber)

            // Trigger action
            viewModel.onDialButtonPress()

            // Assert side effects
            assertEquals(PhoneDialerScreenSideEffect.Dial("1234567890"), awaitItem())
        }
    }
}