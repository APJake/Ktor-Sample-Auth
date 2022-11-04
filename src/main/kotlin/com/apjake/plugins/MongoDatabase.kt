package com.apjake.plugins

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun configureDatabase(): CoroutineDatabase {
    val mongoUsername = System.getenv("MONGO_USERNAME")
    val mongoPwd = System.getenv("MONGO_PASSWORD")
    val dbName = "ktor-sample-auth"
    val connectionString = "mongodb+srv://$mongoUsername:$mongoPwd@clusterjake.j0u2xmu.mongodb.net/" +
            "$dbName?retryWrites=true&w=majority"
    return KMongo
        .createClient(connectionString)
        .coroutine
        .getDatabase(dbName)
}