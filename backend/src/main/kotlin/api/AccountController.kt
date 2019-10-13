package revolut.backend.api

import io.vertx.ext.web.RoutingContext
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import revolut.datastore.AccountId
import revolut.datastore.AccountStore
import uy.kohesive.kovert.core.HttpErrorNotFound

val createAccountControllerModule = Kodein.Module("CreateAccountControllerModule") {
    bind() from singleton { AccountController(instance()) }
}

class AccountController(private val accountStore: AccountStore) {
    companion object {
        const val route = "accounts"
    }

    fun RoutingContext.putCreateByNameByInitialbalance(name: String, initialBalance: Int): AccountId {
        return accountStore.createNewAccount(name, initialBalance)
    }

    fun RoutingContext.getDetailsById(id: AccountId) =
        accountStore.getAccountById(id) ?: throw HttpErrorNotFound()

}