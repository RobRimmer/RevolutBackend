import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.instance
import revolut.backend.api.RestServer
import revolut.backend.backendModule
import revolut.datastore.datastoreModule

fun main() {
    val kodein = Kodein {
        import(backendModule)
        import(datastoreModule)
    }.direct

    println("Creating REST server")
    val server = kodein.instance<RestServer>()
    println("Starting REST server")
    val promise = server.start()
    println("REST server started: ${promise.get()}")
    println()
    println("Kill console (Ctrl-C) to quit")
    // Kovert framework doesn't like to quit unless killed, there is no programmatic "stop" function
}