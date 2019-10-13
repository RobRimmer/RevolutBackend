package revolut.backend.services

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import revolut.datastore.Account
import revolut.datastore.AccountId
import revolut.datastore.AccountStore

val accountServiceModule = Kodein.Module("AccountServiceModule") {
    bind<AccountService>() with singleton { AccountServiceImpl(instance()) }
}

// Service for managing accounts
interface AccountService {
    // Get account by ID
    fun getAccountById(id: AccountId): Account?

    // Create an account
    fun createNewAccount(name: String, initialBalance: Int): AccountId
}

private class AccountServiceImpl(private val accountStore: AccountStore) : AccountService {
    override fun getAccountById(id: AccountId) = accountStore.getAccountById(id)

    override fun createNewAccount(name: String, initialBalance: Int) =
        accountStore.createNewAccount(name, initialBalance)
}


