package tis.hellosearchword

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestHelloSearchWordApplication {

	@Bean
	@ServiceConnection
	fun mongoDbContainer(): MongoDBContainer {
		return MongoDBContainer(DockerImageName.parse("mongo:latest"))
	}

	@Bean
	@ServiceConnection(name = "redis")
	fun redisContainer(): GenericContainer<*> {
		return GenericContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379)
	}
}

fun main(args: Array<String>) {
	fromApplication<HelloSearchWordApplication>().with(TestHelloSearchWordApplication::class).run(*args)
}
