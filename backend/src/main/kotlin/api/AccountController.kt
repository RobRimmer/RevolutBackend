package revolut.backend.api

import io.vertx.ext.web.RoutingContext
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import revolut.backend.services.AccountService
import revolut.backend.services.MoneyTransferService
import revolut.datastore.AccountId
import revolut.datastore.AccountStore
import uy.kohesive.kovert.core.HttpErrorNotFound

val accountControllerModule = Kodein.Module("AccountControllerModule") {
    bind() from singleton { AccountController(instance(),instance()) }
}

class AccountController(
    private val accountService: AccountService,
    private val moneyTransferService: MoneyTransferService
) {
    companion object {
        const val route = "accounts"
    }

    // Create a new account
    fun RoutingContext.putCreateByNameByInitialbalance(name: String, initialBalance: Int): AccountId {
        return accountService.createNewAccount(name, initialBalance)
    }

    // Get an account by ID
    fun RoutingContext.getDetailsById(id: AccountId) =
        accountService.getAccountById(id) ?: throw HttpErrorNotFound()

    // Transfer funds between accounts
    fun RoutingContext.putTransferByFromByToByAmount(from: AccountId, to: AccountId, amount: Int) {
        moneyTransferService.transferFunds(from, to, amount)
    }
}