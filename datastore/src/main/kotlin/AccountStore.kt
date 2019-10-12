package revolut.backend.datastore

// Account store
interface AccountStore {
    // Get account by ID
    fun getAccountById(id: AccountId) : Account?
    // Create an account
    fun createNewAccount(name: String, initialBalance: Int): AccountId
}

