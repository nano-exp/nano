plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "nano"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // jetbrains annotations
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.730")
    //
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    processResources {
        from("${rootDir}/www/dist") {
            into("static")
        }
    }
    bootRun {
        workingDir = rootDir
    }
    test {
        useJUnitPlatform()
        workingDir = rootDir
        jvmArgs = listOf("-Xshare:off")
    }
}
