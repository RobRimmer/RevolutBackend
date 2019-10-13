package revolut.datastore

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton

val accountStoreModule = Kodein.Module {
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}