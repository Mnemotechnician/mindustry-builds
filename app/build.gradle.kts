 plugins {
        id("org.jetbrains.kotlin.jvm") version "1.6.10"
	kotlin("plugin.serialization") version "1.6.10"
        application
}

val ktorVersion = "2.0.1"

repositories {
        mavenCentral()
}

dependencies {
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// serialization
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.3.2")
	// ktor
	implementation("io.ktor:ktor-client:$ktorVersion")
	implementation("io.ktor:ktor-client-cio:$ktorVersion")
	implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
}

application {
        mainClass.set("com.github.mnemotechnician.mbuilds.AppKt")
}

tasks.jar {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
 
 	manifest {
		attributes["Main-Class"] = "com.github.mnemotechnician.mbuilds.AppKt"
 	}
 
 	from(*configurations.runtimeClasspath.files.map { if (it.isDirectory()) it else zipTree(it) }.toTypedArray())
}
