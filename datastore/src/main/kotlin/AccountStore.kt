package revolut.datastore

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val accountStoreModule = Kodein.Module("AccountStoreModule") {
    bind<AccountStore>() with singleton { AccountStoreImpl() }
}

// Account store
interface AccountStore {
    // Get account by ID
    fun getAccountById(id: AccountId): Account?

    // Create an account
    fun createNewAccount(name: String, initialBalance: Int): AccountId

    // Modify an account
    fun modifyAccountBalance(id: AccountId, newBalance: Int)
}

private class AccountStoreImpl : AccountStore {
    private var nextId = 0L
    private val accounts: MutableMap<AccountId, Account> = HashMap()

    override fun getAccountById(id: AccountId): Account? = accounts[id]

    override fun createNewAccount(name: String, initialBalance: Int): AccountId {
        val newId = nextId++
        accounts[newId] = Account(newId, name, initialBalance)
        return newId
    }

    override fun modifyAccountBalance(id: AccountId, newBalance: Int) {
       if (accounts.containsKey(id)){
           accounts[id] = accounts[id]!!.changeBalance(newBalance)
       }
    }

}