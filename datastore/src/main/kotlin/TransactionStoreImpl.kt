package revolut.backend.datastore

import revolut.backend.datastore.AccountId
import revolut.backend.datastore.Transaction
import revolut.backend.datastore.TransactionId
import revolut.backend.datastore.TransactionStore

class TransactionStoreImpl : TransactionStore {
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