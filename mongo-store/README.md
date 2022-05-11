# Mongo Store Db

E-commerce solution with MongoDB data layer access

```
use admin

db.auth('root','example')

use store

db.createUser({user: "store_manager", pwd: "store_password", roles: [{ role: "readWrite", db: "store" }], passwordDigestor: "server", mechanisms: ["SCRAM-SHA-1","SCRAM-SHA-256"]})


```

# Db Console
```
http://localhost:8081
```
