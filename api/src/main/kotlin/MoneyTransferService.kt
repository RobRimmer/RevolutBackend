package revolut.backend.api

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import revolut.backend.datastore.AccountId
import revolut.backend.datastore.AccountStore
import revolut.backend.datastore.TransactionStore

val moneyTransferServiceModule = Kodein.Module {
    bind<MoneyTransferService>() with singleton { MoneyTransferServiceImpl(instance(), instance()) }
}

// Service for transferring money between accounts
interface MoneyTransferService {
    // Transfer money
    fun transferFunds(from: AccountId, to: AccountId, amount: Int)
}

private class MoneyTransferServiceImpl(private val accountStore: AccountStore, private val transactionStore: TransactionStore) : MoneyTransferService {
    override fun transferFunds(from: AccountId, to: AccountId, amount: Int) {
        val accountFrom = accountStore.getAccountById(from) ?: throw AccountNotFoundException(from)
        val accountTo = accountStore.getAccountById(to) ?: throw AccountNotFoundException(to)
        if (accountFrom != null && accountTo != null) {
            accountStore.modifyAccountBalance(from, accountFrom.balance - amount)
            accountStore.modifyAccountBalance(to, accountTo.balance + amount)
        }
        transactionStore.createNewTransaction(listOf(from,to), "Transfer $amount from $from to $to")
    }
}

