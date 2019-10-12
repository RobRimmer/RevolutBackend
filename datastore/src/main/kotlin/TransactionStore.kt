package revolut.backend.datastore

// Transaction store
// A very simple ledger of transactions
interface TransactionStore {
    // Get transaction by ID
    fun getTransactionById(accountId: AccountId): Transaction?
    // Read transactions for a given account
    fun getTransactions(accountId: AccountId): Sequence<Transaction>
    // Create transaction
    fun createNewTransaction(accountIds: List<AccountId>, details: String): TransactionId
}

