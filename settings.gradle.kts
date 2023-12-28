pluginManagement {
    plugins {
        id("org.springframework.boot") version "3.2.0"
        id("io.spring.dependency-management") version "1.1.4"
    }
}

rootProject.name = "nano"

include("service", "www", "wx")