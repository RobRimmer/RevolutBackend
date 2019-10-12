# Revolut Backend
Revolut Backend coding test

## Comments
I have tried to find the balance between the absolute simplest implementation that fulfils the spec (ie TDD/Agile) and a solution that represents my ability as a developer of enterprise products.<br>
I would also like to note that Kotlin is not my first language and, as such, my code may not be the most "idiomatic". 
### GitHub Repository
- Some commits are retained to demonstrate development process.  In an enterprise solution, some commits may have been merged when pushed to master to keep the repository cleaner.
### Improvements
The following items would be coded in an enterprise solution but are omitted from the challenge for the sake of clarity.  I consider them to be trivial/boiler plate code and take it as an assumption that any developer could implement these.
#### General
- There is no logging
#### Datastore
- Data model is used in both API and datastore, this creates  a coupling which I would normally break by creating a Data Abstraction Layer.  This has been omitted for simplicity
- Services do not perform error checks (null names, negative balance, etc).  These checks would be done early in the service and 
- Datastore is clearly too simplistic for an enterprise solution.