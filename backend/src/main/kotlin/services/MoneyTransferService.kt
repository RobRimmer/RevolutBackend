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
) : MoneyTransferService {

    // Transfer is synchronised to prevent threading issues
    // ie if one thread gets the accounts, then is interrupted by another which gets the accounts again
    // Both threads would then be modifying the original values and the first modification to finish would be overwritten by the second
    // In this simple scenario (code test), @Synchronised is enough - there is a single endpoint that can modify the accounts
    // In an enterprise system, a more complex locking system would be necessary to lock across multiple endpoints
    @Synchronized
    override fun transferFunds(from: AccountId, to: AccountId, amount: Int) {
        val accountFrom = accountStore.getAccountById(from) ?: throw AccountNotFoundException(from)
        val accountTo = accountStore.getAccountById(to) ?: throw AccountNotFoundException(to)
        if (accountFrom != null && accountTo != null) {
            accountStore.modifyAccountBalance(from, accountFrom.balance - amount)
            accountStore.modifyAccountBalance(to, accountTo.balance + amount)
        }
        transactionStore.createNewTransaction(listOf(from, to), "Transfer $amount from $from to $to")
        // This transfer also needs a transaction solution
        // ie to prevent part transactions being caused by errors in the datastore
        // This would be performed by the underlying DB technology and is therefore not "hand rolled" here
    }
}

