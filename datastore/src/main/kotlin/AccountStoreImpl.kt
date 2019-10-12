package revolut.backend.datastore

class AccountsStoreImpl : AccountsStore {
    var nextId = 0L
    val accounts: MutableMap<AccountId, Account> = HashMap()

    override fun getAccountById(id: AccountId): Account? = accounts.get(id)


    override fun createNewAccount(name: String, initialBalance: Int): AccountId {
        val newId = nextId++
        accounts.put(newId, Account(newId, name, initialBalance))
        return newId
    }
}