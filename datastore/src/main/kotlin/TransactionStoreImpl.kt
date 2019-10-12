package revolut.backend.datastore

import revolut.backend.datastore.AccountId
import revolut.backend.datastore.Transaction
import revolut.backend.datastore.TransactionId
import revolut.backend.datastore.TransactionStore

class TransactionStoreImpl : TransactionStore {
    override fun createNewTransaction(accountIds: List<AccountId>, details: String): TransactionId {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactionById(accountId: AccountId): Transaction? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactions(accountId: AccountId): Sequence<Transaction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}