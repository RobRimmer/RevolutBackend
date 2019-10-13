package revolut.backend.services

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import revolut.datastore.AccountId
import revolut.datastore.AccountStore
import revolut.datastore.TransactionStore

val moneyTransferServiceModule = Kodein.Module("MoneyTransferServiceModule") {
    bind<MoneyTransferService>() with singleton { MoneyTransferServiceImpl(instance(), instance()) }
}

// Service for transferring money between accounts
interface MoneyTransferService {
    // Transfer money
    fun transferFunds(from: AccountId, to: AccountId, amount: Int)
}

private class MoneyTransferServiceImpl(
    private val accountStore: AccountStore,
    private val transactionStore: TransactionStore
) :
    MoneyTransferService {
    override fun transferFunds(from: AccountId, to: AccountId, amount: Int) {
        val accountFrom = accountStore.getAccountById(from) ?: throw AccountNotFoundException(from)
        val accountTo = accountStore.getAccountById(to) ?: throw AccountNotFoundException(to)
        if (accountFrom != null && accountTo != null) {
            accountStore.modifyAccountBalance(from, accountFrom.balance - amount)
            accountStore.modifyAccountBalance(to, accountTo.balance + amount)
        }
        transactionStore.createNewTransaction(listOf(from, to), "Transfer $amount from $from to $to")
    }
}

