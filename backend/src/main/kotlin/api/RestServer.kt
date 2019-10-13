package revolut.backend.api

import io.vertx.ext.web.Router
import nl.komponents.kovenant.functional.bind
import org.kodein.di.Kodein
import org.kodein.di.conf.global
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uy.klutter.config.typesafe.ClassResourceConfig
import uy.klutter.config.typesafe.ReferenceConfig
import uy.klutter.config.typesafe.kodein.importConfig
import uy.klutter.config.typesafe.loadConfig
import uy.klutter.vertx.kodein.KodeinVertx
import uy.kohesive.kovert.vertx.bindController
import uy.kohesive.kovert.vertx.boot.KodeinKovertVertx
import uy.kohesive.kovert.vertx.boot.KovertVerticle
import uy.kohesive.kovert.vertx.boot.KovertVerticleModule
import uy.kohesive.kovert.vertx.boot.KovertVertx

val restServerModule = Kodein.Module("RestServerModule") {
    bind() from singleton { RestServer(instance(), instance()) }
    import(kovertModule)
}
private val kovertModule = Kodein.Module("KovertModule") {
    val config = ClassResourceConfig("/kovert.conf", RestServer::class.java)
    importConfig(loadConfig(config, ReferenceConfig())) {
        import("kovert.vertx", KodeinKovertVertx.configModule)
        import("kovert.server", KovertVerticleModule.configModule)
    }
    import(KodeinVertx.moduleWithLoggingToSlf4j)
    import(KodeinKovertVertx.module)
    import(KovertVerticleModule.module)
}

class RestServer(
    accountController: AccountController,
    moneyTransferController: MoneyTransferController
) {
    companion object {
        const val route = "api"
        private val LOG: Logger = LoggerFactory.getLogger(RestServer::class.java)
    }

    private val initControllers = fun Router.() {
        bindController(accountController, "$route/${AccountController.route}")
        bindController(moneyTransferController, "$route/${MoneyTransferController.route}")
    }

    init {
        Kodein.global.addImport(kovertModule)
    }

    fun start() = KovertVertx.start() bind { vertx ->
        KovertVerticle.deploy(vertx, routerInit = initControllers)
    } success { deploymentId ->
        LOG.info("Deployment complete, ID = $deploymentId")
    } fail { error ->
        LOG.error("Deployment failed!", error)
    }
}
