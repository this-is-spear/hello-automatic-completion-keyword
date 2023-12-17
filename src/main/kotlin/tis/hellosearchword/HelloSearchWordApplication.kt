package tis.hellosearchword

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloSearchWordApplication

fun main(args: Array<String>) {
	runApplication<HelloSearchWordApplication>(*args)
}
