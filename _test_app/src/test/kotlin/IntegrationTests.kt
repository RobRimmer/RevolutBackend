package revolut.backend.services

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance
import revolut.backend.api.RestServer
import revolut.backend.backendModule
import revolut.datastore.datastoreModule

/*class IntegrationTests {
    companion object {
        private var serverStarted = false

        private fun startService() {
            synchronized(serverStarted) {
                if (!serverStarted) {
                    val kodein = Kodein {
                        import(backendModule)
                        import(datastoreModule)
                    }.direct

                    println("Creating REST server")
                    val server = kodein.instance<RestServer>()
                    println("Starting REST server")
                    val promise = server.start()
                    println("REST server started: ${promise.get()}")
                    serverStarted = true
                }
            }
        }
    }

    private data class AccountCreationTestCase(var name: String, val initialBalance: Int)
    private val accountCreationTestCases = listOf(
        AccountCreationTestCase("Alice", -100),
        AccountCreationTestCase("Bob", 0),
        AccountCreationTestCase("Charlie", 100)
    )

    @TestFactory
    fun testAccountCreation() = accountCreationTestCases.map { testCase ->
        DynamicTest.dynamicTest(testCase.name) {
            startService()
            // Arrange

            // Act
            val response = khttp.post("http://localhost:8123/api/createaccount")

            // Assert
            assertThat("Success", response.statusCode, equalTo(200))
            assertThat("Content", String(response.content), equalTo(testCase.expected))
        }
    }


}

*/

