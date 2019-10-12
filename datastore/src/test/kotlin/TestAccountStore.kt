package revolut.backend.datastore

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import kotlin.random.Random

class TestAccountStore {

    private fun createStore() =
        Kodein {
            import(datastoreModule)
        }.instance<AccountStore>()

    @Test
    fun testGetMissingAccount() {
        // Arrange
        val store = createStore()

        // Act
        val account = store.getAccountById(0L)

        // Assert
        assertThat("no account", account, absent())
    }

    @Test
    fun testCreateAccount() {
        // Arrange
        val name = "SomeName"
        val balance = Random.nextInt(500,10000)
        val store = createStore()

        // Act
        val accountId = store.createNewAccount(name, balance)
        val account = store.getAccountById(accountId)

        // Assert
        assertThat("name", account?.name, equalTo(name))
        assertThat("balance", account?.balance, equalTo(balance))
    }

    @Test
    fun testCreateAccountHasUniqueIds() {
        // Arrange
        val store = createStore()

        // Act
        val accountIds = (1..100)
            .map {
            val name = "SomeName $it"
            val balance = Random.nextInt(500,10000)
            store.createNewAccount(name, balance)
        }

        // Assert
        assertThat("ids are unique", accountIds.distinct().size, equalTo(accountIds.size))
    }
}



