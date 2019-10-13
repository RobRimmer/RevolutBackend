package revolut.backend.services

import revolut.datastore.AccountId

abstract class ApiException(message: String, cause: Throwable? = null) : Throwable(message, cause)

class AccountNotFoundException(accountId: AccountId) : ApiException("Account $accountId does not exist")