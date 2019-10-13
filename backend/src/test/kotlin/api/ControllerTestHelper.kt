package api

import com.nhaarman.mockitokotlin2.mock
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import revolut.backend.api.RestServer
import revolut.backend.backendModule
import revolut.datastore.AccountStore
import revolut.datastore.TransactionStore

object ControllerTestHelper {
    private var serverStarted = false

    val accountStore = mock<AccountStore>()
    val transactionStore = mock<TransactionStore>()

    fun startServer() {
        synchronized(serverStarted) {
            if (!serverStarted) {
                val kodein = Kodein {
                    import(backendModule)
                    bind() from singleton { accountStore }
                    bind() from singleton { transactionStore }
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
