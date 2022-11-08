package com.strawhat.looker.chat

import io.socket.client.IO
import io.socket.client.Socket
import timber.log.Timber
import java.net.URISyntaxException

object SocketHandler {

    private var socket: Socket? = null

    @Synchronized
    fun setSocket() {
        try {
// "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
// "http://localhost:3000/" will not work
// If you want to use your physical phone you could use your ip address plus :3000
// This will allow your Android Emulator and physical device at your home to connect to the server
            socket = IO.socket("https://looker-api-3y18.onrender.com")
            Timber.d("Set Socked Done")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun getSocket(): Socket? {
        return socket
    }

    @Synchronized
    fun establishConnection() {
        socket?.connect()
        Timber.d("Establish Connection Done")
    }

    @Synchronized
    fun closeConnection() {
        socket?.disconnect()
    }
}