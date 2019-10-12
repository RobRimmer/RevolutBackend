package revolut.backend.datastore

typealias AccountId = Long

data class Account(val id: AccountId, val name: String, val balance: Int)