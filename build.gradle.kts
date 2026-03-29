plugins {
    kotlin("jvm") version "2.2.20"
}

group = "org.bytebloom"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.google.truth:truth:1.4.5")
    testImplementation("io.mockk:mockk:1.14.9")
    implementation("io.insert-koin:koin-core:4.1.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}