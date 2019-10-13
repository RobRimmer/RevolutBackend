package revolut.backend.services

import revolut.backend.BackendException
import revolut.datastore.AccountId

abstract class ServicesException(message: String, cause: Throwable? = null) : BackendException(message, cause)

class AccountNotFoundException(accountId: AccountId) : ServicesException("Account $accountId does not exist")