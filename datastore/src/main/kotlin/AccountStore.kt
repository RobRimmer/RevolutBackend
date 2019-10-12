package revolut.backend.datastore

// Accounts store
interface AccountsStore {
    // Get account by ID
    fun getAccountById(id: AccountId) : Account?
    // Create an account
    fun createNewAccount(name: String, initialBalance: Int): AccountId
}

