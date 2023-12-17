package tis.hellosearchword

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@ExtendWith(MongodbTestcontainersExtension::class)
@ExtendWith(RedisTestcontainersExtension::class)
class HelloSearchWordApplicationTests {

    @Test
    fun contextLoads() {
    }
}
