package com.strawhat.looker.chat.ui.discussion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strawhat.looker.chat.SocketHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DiscussionViewModel @Inject constructor(

): ViewModel() {

    private val socket get() = SocketHandler.getSocket()

    init {
        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        viewModelScope.launch {
            socket?.emit("joinRoom", JoinRoom("6364e6739447f4058611e57c"))

            socket?.on("message") { args ->
                if (args[0] != null) {
                    val counter = args[0] as Int
                    Timber.i("counter: " + counter.toString())
                    Timber.d("counter: ${args}")
                }
            }

            repeat(100) {
                delay(1000)
                socket?.emit("message", JoinRoom("6364e6739447f4058611e57c"))
                Timber.d("isSockedConnected: ${socket?.connected()}")
            }
        }
    }


}