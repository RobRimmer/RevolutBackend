version = "1.0.0"

dependencies {
    compile(project(":backend"))
    compile(project(":model"))
    compile(project(":datastore"))

    testCompile(group="khttp", name="khttp", version="1.0.0")
    testCompile(group="com.fasterxml.jackson.module", name="jackson-module-kotlin", version="2.10.0")
}
