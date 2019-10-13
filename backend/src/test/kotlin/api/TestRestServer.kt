package revolut.backend.api

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.present
import nl.komponents.kovenant.FailedException
import org.junit.jupiter.api.Test
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance
import kotlin.test.assertFails

class TestRestServer {

    private fun createServer() =
        Kodein {
            import(restServerModule)
        }.direct.instance<RestServer>()

    @Test
    fun testServerCanStart() {
        // Arrange
        val server = createServer()

        // Act
        val promise = server.start()
        val result = promise.get()
        var error = assertFails { promise.getError() } as FailedException

        // Assert
        assertThat("Successful start", result, present())
        assertThat("no error", error, present())
    }
}



