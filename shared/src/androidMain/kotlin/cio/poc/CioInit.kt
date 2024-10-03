package cio.poc

import android.app.Application
import android.content.Context
import com.example.app.BuildKonfig
import com.segment.analytics.kotlin.core.utilities.JsonAnySerializer
import io.customer.messaginginapp.MessagingInAppModuleConfig
import io.customer.messaginginapp.ModuleMessagingInApp
import io.customer.messaginginapp.type.InAppEventListener
import io.customer.messaginginapp.type.InAppMessage
import io.customer.messagingpush.ModuleMessagingPushFCM
import io.customer.sdk.CustomerIO
import io.customer.sdk.CustomerIOBuilder
import io.customer.sdk.core.util.CioLogLevel
import io.customer.sdk.data.model.Region
import kotlinx.serialization.serializer

class CioInit {

    fun track(application: Context): String {
        val init = init(application)
        init.apply {
            track("name", mapOf("a" to "b"))
            identify(
                userId = "123",
                traits = mapOf("345" to 123),
            )
            registerDeviceToken("token")
        }

        //reflex
        // stdlib version

        return init.anonymousId + " " + BuildKonfig.cdpApiKey
    }

    private fun init(application: Context): CustomerIO {

        return CustomerIOBuilder(
            cdpApiKey = BuildKonfig.cdpApiKey,
            applicationContext = application as Application,
        ).apply {
            logLevel(CioLogLevel.DEBUG)
            migrationSiteId("169f43bccb7f982d2aa1")//BuildKonfig.CUSTOMER_IO_SITE_ID)
            addCustomerIOModule(
                ModuleMessagingInApp(
                    config = MessagingInAppModuleConfig.Builder(
                        siteId = "169f43bccb7f982d2aa1",//BuildKonfig.CUSTOMER_IO_SITE_ID,
                        region = Region.US,
                    )
                        .setEventListener(object : InAppEventListener {
                            override fun errorWithMessage(message: InAppMessage) {}

                            override fun messageActionTaken(
                                message: InAppMessage,
                                actionValue: String,
                                actionName: String,
                            ) {
                            }

                            override fun messageDismissed(message: InAppMessage) {}

                            override fun messageShown(message: InAppMessage) {}
                        }).build()

                )
            )
            addCustomerIOModule(
                ModuleMessagingPushFCM()
                /* moduleConfig = MessagingPushModuleConfig.Builder().apply {
                     //setNotificationCallback(this@CustomerIOInitializer)
                     //setRedirectDeepLinksToOtherApps(false)
                 }.build()*/
            )
            build()
        }
            /*.addCustomerIOModule(
                ModuleMessagingInApp(
                    // the in-app module now has its own configuration
                    config = MessagingInAppModuleConfig.Builder(
                        siteId = "169f43bccb7f982d2aa1",//BuildKonfig.CUSTOMER_IO_SITE_ID,
                        region = Region.US,
                    )*//*.setEventListener(InAppMessageEventListener())*//*.build()
        )
    )*/
            .build()
    }
}