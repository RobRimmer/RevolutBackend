package revolut.datastore

import org.kodein.di.Kodein

val datastoreModule = Kodein.Module("DatastoreModule") {
    import(transactionStoreModule)
    import(accountStoreModule)
}
