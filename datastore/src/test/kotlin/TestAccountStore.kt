package revolut.backend.datastore

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import kotlin.random.Random

class TestAccountsStore {

    @Test
    fun testGetMissingAccount() {
        // Arrange
        val store: AccountsStore = AccountsStoreImpl()

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
        val store: AccountsStore = AccountsStoreImpl()

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
        val store: AccountsStore = AccountsStoreImpl()

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



