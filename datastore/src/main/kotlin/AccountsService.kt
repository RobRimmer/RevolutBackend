package revolut.backend.datastore

// Accounts service
// Accounts are very simplistic
interface AccountsService {
    // Create an account
    fun createNewAccount(name: String, initialBalance: Int): Account
}

