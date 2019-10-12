package revolut.backend.datastore

import com.github.salomonbrys.kodein.*

val datastoreModule = Kodein.Module {
     import(transactionStoreModule)
     import(accountStoreModule)
}
