package services

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import revolut.backend.services.AccountService
import revolut.backend.services.accountServiceModule
import revolut.datastore.Account
import revolut.datastore.AccountStore
import revolut.datastore.TransactionStore

class TestAccountService {

    private fun createService(accountStore: AccountStore? = null, transactionStore: TransactionStore? = null) =
        Kodein {
            import(accountServiceModule)
            bind() from singleton { accountStore ?: mock() }
            bind() from singleton { transactionStore ?: mock() }
        }.direct.instance<AccountService>()

    @Test
    fun testCreateAccount() {
        // Arrange
        val accountId = 123L
        val name = "Name"
        val balance = 123
        val mockAccountStore = mock<AccountStore> {
            on { createNewAccount(any(), any()) } doReturn accountId
        }
        val service = createService(mockAccountStore)

        // Act
        val result = service.createNewAccount(name, balance)

        // Assert
        assertThat("account id", result, equalTo(accountId))
        verify(mockAccountStore).createNewAccount(name, balance)
    }

    @Test
    fun testGetAccount() {
        // Arrange
        val accountId = 123L
        val name = "Name"
        val balance = 123
        val mockAccountStore = mock<AccountStore> {
            on { getAccountById(accountId) } doReturn Account(accountId, name, balance)
        }
        val service = createService(mockAccountStore)

        // Act
        val result = service.getAccountById(accountId)

        // Assert
        assertThat("account", result, equalTo(Account(accountId, name, balance)))
    }
}



