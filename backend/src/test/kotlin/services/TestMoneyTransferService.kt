package revolut.backend.services

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.kodein.di.direct
import org.kodein.di.generic.instance
import revolut.datastore.Account
import revolut.datastore.AccountStore
import revolut.datastore.TransactionStore
import kotlin.test.assertFails

class TestMoneyTransferService {

    private fun createService(accountStore: AccountStore? = null, transactionStore: TransactionStore? = null) =
        Kodein {
            import(moneyTransferServiceModule)
            bind() from singleton { accountStore ?: mock() }
            bind() from singleton { transactionStore ?: mock() }
        }.direct.instance<MoneyTransferService>()

    private val accountFrom = 0L
    private val accountTo = 1L

    @TestFactory
    fun testSuccessfulTransfers() = testCases.map { testCase ->
        DynamicTest.dynamicTest("${testCase.name}") {
            // Arrange
            val mockAccountStore = mock<AccountStore> {
                on { getAccountById(accountFrom) } doReturn Account(accountFrom, "name", testCase.initialFrom)
                on { getAccountById(accountTo) } doReturn Account(accountTo, "name", testCase.initialTo)
            }
            val mockTransactionStore = mock<TransactionStore>()
            val service = createService(mockAccountStore, mockTransactionStore)

            // Act
            service.transferFunds(accountFrom, accountTo, testCase.amount)

            // Assert
            verify(mockAccountStore).modifyAccountBalance(accountFrom, testCase.finalFrom)
            verify(mockAccountStore).modifyAccountBalance(accountTo, testCase.finalTo)
            verify(mockTransactionStore).createNewTransaction(
                argThat { containsAll(listOf(accountFrom, accountTo)) },
                argThat { contains("Transfer") })
        }
    }

    private data class TestCase(var name: String, val initialFrom: Int, val initialTo: Int, val amount: Int) {
        var finalFrom = initialFrom - amount
        var finalTo = initialTo + amount
    }

    private val testCases = listOf(
        TestCase("Simple", 100, 100, 50),
        TestCase("No funds, cause overdraft!", 0, 100, 150),
        TestCase("Source already overdrawn", -100, 100, 50),
        TestCase("Target already overdrawn", 100, -100, 50)
    )

    @Test
    fun testMissingAccountFrom() {
        // Arrange
        val mockAccountStore = mock<AccountStore> {
            on { getAccountById(accountFrom) } doReturn null
            on { getAccountById(accountTo) } doReturn Account(accountTo, "name", 0)
        }
        val service = createService(mockAccountStore)

        // Act
        val ex = assertFails { service.transferFunds(accountFrom, accountTo, 1) }
        println(ex)

        // Assert
        val expected = AccountNotFoundException(accountFrom).message
        assertThat("exception message", ex.message, equalTo(expected))
    }

    @Test
    fun testMissingAccountTo() {
        // Arrange
        val mockAccountStore = mock<AccountStore> {
            on { getAccountById(accountFrom) } doReturn Account(accountFrom, "name", 0)
            on { getAccountById(accountTo) } doReturn null
        }
        val service = createService(mockAccountStore)

        // Act
        val ex = assertFails { service.transferFunds(accountFrom, accountTo, 1) } as AccountNotFoundException
        println(ex)

        // Assert
        val expected = AccountNotFoundException(accountTo).message
        assertThat("exception message", ex.message, equalTo(expected))
    }

}



