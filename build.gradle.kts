plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.31"
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
    apply(plugin = "org.jetbrains.kotlin.jvm")
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        compile(group="org.kodein.di", name="kodein-di-core", version="6.4.1")
        compile(group="uy.kohesive.kovert", name="kovert-vertx", version="1.5.0")
        compile(group="org.slf4j", name="slf4j-api", version="1.7.28")
        compile(group="org.slf4j", name="slf4j-simple", version="1.7.28")

        testImplementation(group="com.nhaarman.mockitokotlin2", name="mockito-kotlin", version="2.1.0")
        testCompile(group="org.jetbrains.kotlin", name="kotlin-test-junit5", version="1.3.21")
        testCompile(group="com.natpryce", name="hamkrest", version="1.7.0.0")
    }
}
