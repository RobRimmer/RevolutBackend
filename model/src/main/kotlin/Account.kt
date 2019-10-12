package revolut.backend.datastore

typealias AccountId = Long

// Balance in pennies (or cents, etc)
data class Account(val id: AccountId, val name: String, val balance: Int)