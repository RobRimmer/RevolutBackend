package revolut.backend.datastore

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

