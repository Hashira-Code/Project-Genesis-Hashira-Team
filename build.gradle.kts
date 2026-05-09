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
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    implementation("io.insert-koin:koin-core:4.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}
