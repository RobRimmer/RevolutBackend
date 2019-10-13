package revolut.backend.api

import api.ControllerTestHelper
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import revolut.datastore.Account

class TestAccountController {
    private val baseUrl = "http://localhost:8123/${RestServer.route}/${AccountController.route}"

    private data class TestCase(var name: String, val initialBalance: Int)

    private val testCases = listOf(
        TestCase("Alice", -100),
        TestCase("Bob", 0),
        TestCase("Charlie", 100)
    )

    @TestFactory
    fun testAccountCreation() = testCases.map { testCase ->
        DynamicTest.dynamicTest(testCase.name) {
            // Arrange
            ControllerTestHelper.startServer()
            val url = "$baseUrl/create/${testCase.name}/${testCase.initialBalance}"

            // Act
            val response = khttp.put(url)

            // Assert
            assertThat("Success", response.statusCode, equalTo(200))
            verify(ControllerTestHelper.accountStore).createNewAccount(testCase.name, testCase.initialBalance)
        }
    }

    @Test
    fun testGetAccount() {
        // Arrange
        ControllerTestHelper.startServer()
        val url = "$baseUrl/details"
        var accounts = listOf(
            Account(0L, "Name1", -200),
            Account(1L, "Name2", 0),
            Account(2L, "Name3", 200)
        )
        ControllerTestHelper.accountStore.stub {
            on { getAccountById(0L) } doReturn accounts[0]
            on { getAccountById(1L) } doReturn accounts[1]
            on { getAccountById(2L) } doReturn accounts[2]
        }
        // Act
        val responses = (0..2).map {
            khttp.get("$url/$it")
        }
        // Assert
        (0..2).forEach {
            assertThat("Success $it", responses[it].statusCode, equalTo(200))
            val content = responses[it].jsonObject
            assertThat("ID $it", content["id"].toString(), equalTo(accounts[it].id.toString()))
            assertThat("Name $it", content["name"].toString(), equalTo(accounts[it].name))
            assertThat("Balance $it", content["balance"].toString(), equalTo(accounts[it].balance.toString()))
        }
    }
}





