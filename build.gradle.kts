
import org.h2.tools.RunScript
import java.nio.charset.StandardCharsets
import java.sql.DriverManager
import java.util.*

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.h2database:h2:2.1.214")
    }
}

plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

group = "org.chodura"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.1.214")
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

val loadConfigProperties by tasks.registering {
    doLast {
        val properties = Properties()
        val configFile = file("src/main/resources/config.properties")
        val inputStream = configFile.inputStream()
        properties.load(inputStream)
        inputStream.close()

        val dbUrl = properties.getProperty("db.url")
        val dbUser = properties.getProperty("db.user")
        val dbPassword = properties.getProperty("db.password")

        System.setProperty("db.url", dbUrl)
        System.setProperty("db.user", dbUser)
        System.setProperty("db.password", dbPassword)
    }
}

val createPersonTable by tasks.registering {
    dependsOn(loadConfigProperties)

    doLast {
        val url = System.getProperty("db.url")
        val username = System.getProperty("db.user")
        val password = System.getProperty("db.password")
        val scriptFile = project.file("src/main/resources/create_person_table.sql")

        val connection = DriverManager.getConnection(url, username, password)
        val reader = scriptFile.bufferedReader(StandardCharsets.UTF_8)
        RunScript.execute(connection, reader)
        connection.close()
        reader.close()
    }
}

tasks.test {
    dependsOn(createPersonTable)
    useJUnitPlatform()
}

tasks.register<JavaExec>("runMain") {
    dependsOn(loadConfigProperties, createPersonTable)

    mainClass.set("org.chodura.ProducerConsumerApp")
    classpath = configurations["runtimeClasspath"]

    mainClass.set("org.chodura.ProducerConsumerApp")
    classpath = sourceSets["main"].runtimeClasspath
}