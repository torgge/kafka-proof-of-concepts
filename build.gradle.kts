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
	implementation("org.springframework.boot:spring-boot-starter:${project.property("spring-boot-starter.version")}")
	implementation("org.springframework.boot:spring-boot-starter-web:${project.property("spring-boot-starter.version")}")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.kafka:spring-kafka:${project.property("spring-kafka.version")}")
	implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka:" +
			"${project.property("spring-cloud-stream-binder-kafka.version")}")
	implementation("org.apache.avro:avro:${project.property("avro.version")}")
	implementation("io.confluent:kafka-avro-serializer:${project.property("kafka-avro-serializer.version")}")
	implementation("org.springframework.boot:spring-boot-starter-actuator:" +
			"${project.property("spring-boot-starter-actuator.version")}")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose:" +
			"${project.property("spring-boot-docker-compose.version")}")
	testImplementation("org.springframework.boot:spring-boot-starter-test:" +
			"${project.property("spring-boot-starter-test.version")}")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.kafka:spring-kafka-test:" +
			"${project.property("spring-kafka-test.version")}")
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
