package revolut.backend.datastore

class AccountStoreImpl : AccountStore {
    private var nextId = 0L
    private val accounts: MutableMap<AccountId, Account> = HashMap()

    override fun getAccountById(id: AccountId): Account? = accounts[id]

    override fun createNewAccount(name: String, initialBalance: Int): AccountId {
        val newId = nextId++
        accounts[newId] = Account(newId, name, initialBalance)
        return newId
    }
}