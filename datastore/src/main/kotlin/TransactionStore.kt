package revolut.backend.datastore

import com.github.salomonbrys.kodein.*

val transactionStoreModule = Kodein.Module {
    bind<TransactionStore>() with singleton { TransactionStoreImpl() }
}

// Transaction store
// A very simple ledger of transactions
interface TransactionStore {
    // Get transaction by ID
    fun getTransactionById(transactionId: TransactionId): Transaction?
    // Read transactions (ids) for a given account
    // Read IDs (which are small) to allow actual transactions (which are bigger) to be read when/if needed
    // Consider adding summary here to allow further filtering before fetching full transaction (eg date)
    fun getTransactions(accountId: AccountId): Sequence<TransactionId>
    // Create transaction
    fun createNewTransaction(accountIds: List<AccountId>, details: String): TransactionId
}

private class TransactionStoreImpl : TransactionStore {
    private var nextId = 0L
    private val transactions: MutableMap<TransactionId, Transaction> = HashMap()
    private val byAccount: MutableMap<AccountId, List<TransactionId>> = HashMap()

    override fun getTransactionById(transactionId: TransactionId): Transaction? = transactions[transactionId]

    override fun createNewTransaction(accountIds: List<AccountId>, details: String): TransactionId {
        val newId = nextId++
        transactions[newId] = Transaction(newId, accountIds, details)
        accountIds.forEach { byAccount[it] =byAccount.getOrDefault(it,  emptyList()) + newId}
        return newId
    }

    override fun getTransactions(accountId: AccountId): Sequence<TransactionId> {
        return byAccount.getOrDefault(accountId,  emptyList()).asSequence()
    }
}