package tis.hellosearchword

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

open class RedisTestcontainersExtension : Extension, BeforeAllCallback {
    companion object {
        private val redisContainer: GenericContainer<*> = GenericContainer(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379)
    }

    override fun beforeAll(context: ExtensionContext) {
        if (redisContainer.isRunning) {
            return
        }

        redisContainer.start()
    }
}