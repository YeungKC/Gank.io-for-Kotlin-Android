package com.yeungkc.gank.io.ui.chromium

import android.content.ComponentName
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsServiceConnection
import java.lang.ref.WeakReference

class ServiceConnection(connectionCallback: ServiceConnectionCallback) : CustomTabsServiceConnection() {
    // A weak reference to the ServiceConnectionCallback to avoid leaking it.
    private val mConnectionCallback: WeakReference<ServiceConnectionCallback>

    init {
        mConnectionCallback = WeakReference(connectionCallback)
    }

    override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
        val connectionCallback = mConnectionCallback.get()
        connectionCallback?.onServiceConnected(client)
    }

    override fun onServiceDisconnected(name: ComponentName) {
        val connectionCallback = mConnectionCallback.get()
        connectionCallback?.onServiceDisconnected()
    }
}