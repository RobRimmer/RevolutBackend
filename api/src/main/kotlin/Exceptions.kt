package revolut.backend.api

import revolut.backend.datastore.AccountId

abstract class ApiException(message: String, cause: Throwable? = null) : Throwable(message, cause)

class AccountNotFoundException(accountId: AccountId) : ApiException("Account $accountId does not exist")