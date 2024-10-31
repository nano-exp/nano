pluginManagement {
    plugins {
        id("org.springframework.boot") version "3.3.0"
        id("io.spring.dependency-management") version "1.1.5"
    }
}

rootProject.name = "nano"

include("server", "web")
