import org.gradle.kotlin.dsl.implementation

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.github.davidmc24.gradle.plugin.avro") version "1.9.1"
}

group = "com.bonespirito"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://packages.confluent.io/maven/") }
	mavenLocal()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.3")
    }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter:3.5.3")
	implementation("org.springframework.boot:spring-boot-starter-web:3.5.3")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.kafka:spring-kafka:3.2.3")
	implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka:4.1.3")
	implementation("org.apache.avro:avro:1.12.0")
	implementation("io.confluent:kafka-avro-serializer:7.6.0")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.5.3")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose:3.5.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.5.3")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.kafka:spring-kafka-test:3.2.3")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	main {
		java {
			srcDirs("build/generated-main-avro-java")
		}
	}
}
