// tag::buildscript_block[]
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.4.1")
    }
}

apply(plugin = "org.springframework.boot")
// end::buildscript_block[]
