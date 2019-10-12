# Revolut Backend
Revolut Backend coding test

## Comments
### Repository
- Some commits are retained to demonstrate development process.  In an enterprise solution, some commits may have been merged when pushed to master to keep the repository cleaner.
### Improvements
The following items would be coded in an enterprise solution but are omitted from the challenge for the sake of clarity.  I consider them to be trivial/boiler plate code and take it as an assumption that any developer could implement these.
#### Datastore
- Data model is used in both API and datastore, this creates  a coupling which I would normally break by creating a Data Abstraction Layer.  This has been omitted for simplicity
- Services do not perform error checks (null names, negative balance, etc).  These checks would be done early in the service and 
