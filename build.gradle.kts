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
        compile(group="pw.kmp", name="kodeinject", version="1.2.1")

        testImplementation(group="com.nhaarman.mockitokotlin2", name="mockito-kotlin", version="2.1.0")
        testCompile(group="org.jetbrains.kotlin", name="kotlin-test-junit5", version="1.3.21")
        testCompile(group="com.natpryce", name="hamkrest", version="1.7.0.0")
    }
}
