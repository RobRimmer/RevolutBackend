package revolut.backend.datastore

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import kotlin.random.Random

class TestTransactionStore {

    @Test
    fun testGetMissingTransaction() {
        // Arrange
        val store: TransactionStore = TransactionStoreImpl()

        // Act
        val transaction = store.getTransactionById(0L)

        // Assert
        assertThat("no transaction", transaction, absent())
    }

    @Test
    fun testCreateTransaction() {
        // Arrange
        val details = "SomeTransaction"
        val accountIds = listOf(1L,2L)
        val store: TransactionStore = TransactionStoreImpl()

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
        val store: TransactionStore = TransactionStoreImpl()

        // Act
        val transactionIds = (1..100)
            .map {
                val details = "SomeTransaction $it"
                val accountIds = listOf(1L,2L)
                store.createNewTransaction(accountIds, details)
        }

        // Assert
        assertThat("ids are unique", transactionIds.distinct().size, equalTo(transactionIds.size))
    }
}



