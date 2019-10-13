package revolut.backend.api

import io.vertx.ext.web.RoutingContext
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val moneyTransferControllerModule = Kodein.Module("MoneyTransferControllerModule") {
    bind() from singleton { MoneyTransferController() }
}

class MoneyTransferController {
    fun RoutingContext.getStringById(id: String) = id + 100
}