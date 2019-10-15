# TODO
- transfer fails on controller test

# Revolut Backend
Revolut Backend coding test<br>
Built on Windows PC using intellij & jdk-10

## Usage
By default, REST server listens on port 8123, this can be configured in the file kovert.conf (deployed with app)
Run _test_app, app.kt, fun main()<br>
APIs can be tested using curl (or Postmaster)<br>
Ctrl-C will quit the app<br>
The API urls are<br>
#### Create Account
PUT /api/account/create/:name/:initialBalance<br>
:name - Account name (String)<br>
:initialBalance - Initial balance of account (Int) (represents pennies/cents)
returns: AccountId (Long)
#### Get Account
GET /api/accounts/details/:accountId<br>
:accountId - Id of account as returned by create above (Long)<br>
return: Account object (Json)<br>
#### Transfer funds
PUT /api/account/transfer/:from/:to/:amount<br>
:from - ID of account to take money from (Long)<br>
:to - ID of account to send money to (Long)<br>
:amount: Amount in pennies/cents (Int)<br>
returns: Transaction ID

## Comments
I have tried to find the balance between the absolute simplest implementation that fulfils the spec (ie TDD/Agile) and a solution that represents my ability as a developer of enterprise products.<br>
I would also like to note that Kotlin is not my first language and, as such, my code may not be the most "idiomatic". 
### GitHub Repository
- Some commits are retained to demonstrate development process.  In an enterprise solution, some commits may have been merged when pushed to master to keep the repository cleaner.
### Improvements
The following items would be coded in an enterprise solution but are omitted from the challenge, either for the sake of clarity or because of time constraints.
#### General
- There is no logging
#### REST
The following improvements were not coded due to lack of familiarity with the REST library used
- HTTPS would be more secure
- Data (such as transfer details) should be POST as json body rather than PUT
#### Datastore
- Data model is used in both API and datastore, this creates  a coupling which I would normally break by creating a Data Abstraction Layer.  This has been omitted for simplicity
- Services do not perform error checks (null names, negative balance, etc).  These checks would be done early in the service and
- Datastore is clearly too simplistic for an enterprise solution.
- Transactions, using a suitable DB, transactions would be supported to prevent partial transactions (eg taking funds from source account but failing to add them to the target account)
