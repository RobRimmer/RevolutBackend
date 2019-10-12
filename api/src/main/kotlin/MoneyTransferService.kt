package revolut.backend.api

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import revolut.backend.datastore.AccountId

val moneyTransferServiceModule = Kodein.Module {
    bind<MoneyTransferService>() with singleton { MoneyTransferServiceImpl() }
}

// Service for transferring money between accounts
interface MoneyTransferService {
    // Transfer money
    fun transferFunds(from: AccountId, to: AccountId, amount: Int)
}

private class MoneyTransferServiceImpl: MoneyTransferService {
    override fun transferFunds(from: AccountId, to: AccountId, amount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

