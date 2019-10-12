package revolut.backend.api

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import revolut.backend.datastore.AccountId
import revolut.backend.datastore.AccountStore

val moneyTransferServiceModule = Kodein.Module {
    bind<MoneyTransferService>() with singleton { MoneyTransferServiceImpl(instance()) }
}

// Service for transferring money between accounts
interface MoneyTransferService {
    // Transfer money
    fun transferFunds(from: AccountId, to: AccountId, amount: Int)
}

private class MoneyTransferServiceImpl(private val accountStore: AccountStore) : MoneyTransferService {
    override fun transferFunds(from: AccountId, to: AccountId, amount: Int) {
        // TODO - check for various errors
        // Remove funds
        val accountFrom = accountStore.getAccountById(from)
        val accountTo = accountStore.getAccountById(to)
        if (accountFrom != null && accountTo != null) {
            accountStore.modifyAccountBalance(from, accountFrom.balance - amount)
            accountStore.modifyAccountBalance(to, accountFrom.balance + amount)
        }
    }
}

