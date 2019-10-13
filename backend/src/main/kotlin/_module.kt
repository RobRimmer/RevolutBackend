package revolut.backend

import org.kodein.di.Kodein
import revolut.backend.api.moneyTransferControllerModule
import revolut.backend.api.restServerModule
import revolut.backend.services.moneyTransferServiceModule

val backendModule = Kodein.Module("BackendModule") {
     import(moneyTransferServiceModule)
     import(moneyTransferControllerModule)
     import(restServerModule)
}
