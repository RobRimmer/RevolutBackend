package revolut.backend.api

import org.kodein.di.Kodein
import uy.klutter.config.typesafe.ClassResourceConfig
import uy.klutter.config.typesafe.ReferenceConfig
import uy.klutter.config.typesafe.kodein.importConfig
import uy.klutter.config.typesafe.loadConfig
import uy.klutter.vertx.kodein.KodeinVertx
import uy.kohesive.kovert.vertx.boot.KodeinKovertVertx
import uy.kohesive.kovert.vertx.boot.KovertVerticleModule

val moneyTransferControllerModule = Kodein.Module("MoneyTransferControllerModule") {
}

class MoneyTransferController {
}