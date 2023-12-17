package tis.hellosearchword

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.MongoDBContainer

open class MongodbTestcontainersExtension : Extension, BeforeAllCallback {
    companion object {
        private val mongodbContainer: MongoDBContainer = MongoDBContainer("mongo:latest")
    }

    override fun beforeAll(context: ExtensionContext) {
        if (mongodbContainer.isRunning) {
            return
        }

        mongodbContainer.start()
        System.setProperty("spring.data.mongodb.uri", mongodbContainer.replicaSetUrl)
    }
}