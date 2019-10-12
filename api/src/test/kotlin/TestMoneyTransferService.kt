package revolut.backend.api

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import revolut.backend.datastore.AccountStore
import kotlin.random.Random

class TestMoneyTransferService {

    private fun createService(accountstore: AccountStore? = null) =
        Kodein {
            import(moneyTransferServiceModule)
            bind() from singleton { accountstore ?: mock<AccountStore>() }
        }.instance<MoneyTransferService>()

    @Test
    fun testSimpleTransfer() {
        // Arrange
        val from = 0L
        val to = 1L
        val amount = 50
        val start1 = 100
        val start2 = 100
        val mockAccountStore = mock<AccountStore>()
        val service = createService(mockAccountStore)

        // Act
        service.transferFunds(from, to, amount)

        // Assert
        verify(mockAccountStore).modifyAccountBalance(from, start1-amount)
        verify(mockAccountStore).modifyAccountBalance(to, start2+amount)
    }

//    @TestFactory
//    fun testSomething() = collection.map { item ->
//        DynamicTest.dynamicTest("Name: ${item}") {
//            // Arrange
//
//            // Act
//            println("result = $result")
//
//            // Assert
//            assertThat(result, equalTo(original))
//        }
//    }
}



