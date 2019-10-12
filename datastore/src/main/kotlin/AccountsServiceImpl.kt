package revolut.backend.datastore

class AccountsServiceImpl : AccountsService {
    var nextId = 0L

    override fun createNewAccount(name: String, initialBalance: Int): Account {
        return Account(nextId++, name, initialBalance)
    }
}