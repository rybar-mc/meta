plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.diffplug.spotless") version "8.4.0"
}

version = "0.0.1"

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")

    group = "org.rybar.meta"

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:26.1.0")

        compileOnly("org.projectlombok:lombok:1.18.44")
        annotationProcessor("org.projectlombok:lombok:1.18.44")

        testImplementation(platform("org.junit:junit-bom:6.0.3"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.assertj:assertj-core:3.27.7")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    java {
        withSourcesJar()
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
    }

    tasks.withType<Zip> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()

        palantirJavaFormat("2.73.0").formatJavadoc(true)
        formatAnnotations()

        target("src/main/java/**")
    }
}

publishing {
    repositories {
        maven {
            name = "JopgaRepo"
            url = uri("https://repo.jopga.me/releases")
            credentials {
                username = project.findProperty("jopga.user").toString()
                password = project.findProperty("jopga.key").toString()
            }
        }
    }

    publications.create<MavenPublication>("maven") {
        groupId = project.group as String
        version = project.version as String

        pom {
            name.set("meta")
            description.set("A high-performance, zero-reflection, and fully type-safe metadata container library")
        }

        artifact(tasks.named("jar")) {
            classifier = ""
        }

        artifact(tasks.named("sourcesJar"))
    }
}