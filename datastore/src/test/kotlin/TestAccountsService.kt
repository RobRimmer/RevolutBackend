package revolut.backend.datastore

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.random.Random

class TestAccountsService {

    @Test
    fun testCreateAccount() {
        // Arrange
        val name = "SomeName"
        val balance = Random.nextInt(500,10000)
        val service: AccountsService = AccountsServiceImpl()

        // Act
        val account = service.createNewAccount(name, balance)

        // Assert
        assertThat("name", account.name, equalTo(name))
        assertThat("balance", account.balance, equalTo(balance))
    }

    @Test
    fun testCreateAccountHasUniqueIds() {
        // Arrange
        val service: AccountsService = AccountsServiceImpl()

        // Act
        val accountIds = (1..100)
            .map {
            val name = "SomeName $it"
            val balance = Random.nextInt(500,10000)
            service.createNewAccount(name, balance)
        }.map { it.id }

        // Assert
        assertThat("ids are unique", accountIds.distinct().size, equalTo(accountIds.size))
    }
}



