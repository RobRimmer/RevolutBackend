package revolut.datastore

import com.github.salomonbrys.kodein.Kodein

val datastoreModule = Kodein.Module {
    import(transactionStoreModule)
    import(accountStoreModule)
}
