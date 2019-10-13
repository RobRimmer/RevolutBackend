package revolut.datastore

typealias TransactionId = Long

data class Transaction(val id: TransactionId, val accounts: List<AccountId>, val details: String)