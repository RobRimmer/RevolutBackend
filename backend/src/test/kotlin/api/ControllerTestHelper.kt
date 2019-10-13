package api

import com.nhaarman.mockitokotlin2.mock
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import revolut.backend.api.RestServer
import revolut.backend.api.accountControllerModule
import revolut.backend.api.restServerModule
import revolut.backend.services.AccountService
import revolut.backend.services.MoneyTransferService

object ControllerTestHelper {
    private var serverStarted = false

    val accountService = mock<AccountService>()
    val moneyTransferService = mock<MoneyTransferService>()

    fun startServer() {
        synchronized(serverStarted) {
            if (!serverStarted) {
                val kodein = Kodein {
                    import(accountControllerModule)
                    import(restServerModule)
                    bind() from singleton { moneyTransferService }
                    bind() from singleton { accountService }
                }.direct

                println("Creating REST server")
                val server = kodein.instance<RestServer>()
                println("Starting REST server")
                val promise = server.start()
                println("REST server started: ${promise.get()}")
                serverStarted = true
            }
        }
    }
}
