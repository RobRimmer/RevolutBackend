package revolut.backend

import com.github.salomonbrys.kodein.*
import revolut.backend.services.moneyTransferServiceModule

val backendModule = Kodein.Module {
     import(moneyTransferServiceModule)
}
