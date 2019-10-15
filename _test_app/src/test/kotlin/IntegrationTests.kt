import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance
import revolut.backend.api.AccountController
import revolut.backend.api.RestServer
import revolut.backend.backendModule
import revolut.datastore.Account
import revolut.datastore.datastoreModule

class IntegrationTests {
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

        val jsonMapper = jacksonObjectMapper()
    }

    private val baseUrl = "http://localhost:8123/${RestServer.route}/${AccountController.route}"

    data class AccountDetail(val name: String, val balance: Int)

    private var accountDetails = listOf(
        AccountDetail("Name1", -200),
        AccountDetail("Name2", 0),
        AccountDetail("Name3", 200)
    )

    @Test
    fun testEndToEnd() {
        // Start the REST server (if not started)
        startService()
        // Create some accounts
        val accounts = accountDetails.map {
            val response = khttp.put("$baseUrl/create/${it.name}/${it.balance}")
            assertThat("Create success: $it", response.statusCode, equalTo(200))
            Account(String(response.content).toLong(), it.name, it.balance)
        }
        // Get the accounts
        accounts.forEach {
            val response = khttp.get("$baseUrl/details/${it.id}")
            assertThat("Get success: $it.id", response.statusCode, equalTo(200))
            val account = jsonMapper.readValue<Account>(response.content)
            assertThat("Correct details: $it.id", account, equalTo(it))
        }
        // Transfer some money
        // TODO - check transaction, id maps to correct tx
        val amount01 = 10
        val response01 = khttp.put("$baseUrl/transfer/${accounts[0].id}/${accounts[1].id}/$amount01")
        assertThat("Transfer success: 0->1", response01.statusCode, equalTo(200))
        val amount12 = 20
        val response12 = khttp.put("$baseUrl/transfer/${accounts[1].id}/${accounts[2].id}/$amount12")
        assertThat("Transfer success: 1->2", response12.statusCode, equalTo(200))
        // Get accounts and check new balances
        val newAccounts = accounts.map {
            jsonMapper.readValue<Account>(khttp.get("$baseUrl/details/${it.id}").content)
        }
        assertThat("New balance 0", newAccounts[0].balance, equalTo(accounts[0].balance - amount01))
        assertThat("New balance 1", newAccounts[1].balance, equalTo(accounts[1].balance + amount01 - amount12))
        assertThat("New balance 2", newAccounts[2].balance, equalTo(accounts[2].balance + amount12))
    }
}



