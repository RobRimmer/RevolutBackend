package revolut.datastore

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class TestTransactionStore {

    private fun createStore() =
        Kodein {
            import(datastoreModule)
        }.instance<TransactionStore>()

    @Test
    fun testGetMissingTransaction() {
        // Arrange
        val store = createStore()

        // Act
        val transaction = store.getTransactionById(0L)

        // Assert
        assertThat("no transaction", transaction, absent())
    }

    @Test
    fun testCreateTransaction() {
        // Arrange
        val details = "SomeTransaction"
        val accountIds = listOf(1L, 2L)
        val store = createStore()

        // Act
        val transactionId = store.createNewTransaction(accountIds, details)
        val transaction = store.getTransactionById(transactionId)

        // Assert
        assertThat("accounts", transaction?.accounts, equalTo(accountIds))
        assertThat("details", transaction?.details, equalTo(details))
    }

    @Test
    fun testCreateTransactionHasUniqueIds() {
        // Arrange
        val store = createStore()

        // Act
        val transactionIds = (1..100)
            .map {
                val details = "SomeTransaction $it"
                val accountIds = listOf(1L, 2L)
                store.createNewTransaction(accountIds, details)
            }

        // Assert
        assertThat("ids are unique", transactionIds.distinct().size, equalTo(transactionIds.size))
    }

    @Test
    fun testGetTransactionByAccount() {
        // Arrange
        val store = createStore()
        val accounts12 = store.createNewTransaction(listOf(1L, 2L), "acc: 1 & 2")
        val accounts23 = store.createNewTransaction(listOf(2L, 3L), "acc: 2 & 3")
        val accounts13 = store.createNewTransaction(listOf(1L, 3L), "acc: 1 & 3")

        // Act
        val result1 = store.getTransactions(1L)
        val result2 = store.getTransactions(2L)
        val result3 = store.getTransactions(3L)
        val result4 = store.getTransactions(4L)

        // Assert
        assertThat("account 1", result1.toList(), List<TransactionId>::containsAll, listOf(accounts12, accounts13))
        assertThat("account 2", result2.toList(), List<TransactionId>::containsAll, listOf(accounts12, accounts23))
        assertThat("account 3", result3.toList(), List<TransactionId>::containsAll, listOf(accounts13, accounts23))
        assertThat("account 4", result4.toList(), equalTo(emptyList()))
    }
}



