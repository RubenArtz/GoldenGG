plugins {
    id ("java")
    id ("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ruben_artz.spigot"
version = "2.1.21"

repositories {
    mavenCentral()

    maven {
        name = ("spigotmc-repo")
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = ("sonatype")
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = ("andrei1058-repo")
        url = uri("https://repo.andrei1058.dev/releases/")
    }
    maven {
        url = uri("https://repo.marcely.de/repository/maven-public/")
    }

    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
}

dependencies {
    compileOnly ("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT")

    /*
    Keep up to date
    Url: https://www.spigotmc.org/resources/6245/
    */
    compileOnly ("me.clip:placeholderapi:2.11.6")
    compileOnly ("org.jetbrains:annotations:23.0.0")
    compileOnly ("com.andrei1058.bedwars:bedwars-api:22.3.4")
    compileOnly ("de.marcely.bedwars:API:5.4.15")

    implementation ("org.bstats:bstats-bukkit:3.0.0")

    compileOnly ("org.projectlombok:lombok:1.18.24")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")
}

tasks {
    shadowJar {
        archiveFileName.set("Golden GG.jar")

        relocate("org.bstats", "ruben_artz.dependencies.bstats")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}