pluginManagement {
    plugins {
        id("org.springframework.boot") version "3.5.3"
        id("io.spring.dependency-management") version "1.1.7"
    }
}

rootProject.name = "nano"

include("server")
