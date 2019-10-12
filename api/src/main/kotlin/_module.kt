package revolut.backend.api

import com.github.salomonbrys.kodein.*

val apiModule = Kodein.Module {
     import(moneyTransferServiceModule)
}
