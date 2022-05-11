# Mongo Store Db


```
use admin

db.auth('root','example')

use store

db.createUser({user: "store_manager", pwd: "store_password", roles: [{ role: "readWrite", db: "store" }], passwordDigestor: "server", mechanisms: ["SCRAM-SHA-1","SCRAM-SHA-256"]})


```