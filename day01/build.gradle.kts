plugins {
	id("org.jetbrains.kotlin.jvm") version "1.9.10"

	application
}

repositories {
	mavenCentral()
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(20))
	}
}

application {
	mainClass.set("com.shifteleven.adventofcode2023.day01.AppKt")
}
