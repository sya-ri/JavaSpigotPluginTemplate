import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask
import groovy.lang.Closure
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.4.0"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("com.palantir.git-version") version "0.12.3"
    id("dev.s7a.gradle.minecraft.server") version "1.0.2"
}

val gitVersion: Closure<String> by extra

repositories {
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
    mavenCentral()
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains:annotations:22.0.0")
}

configure<BukkitPluginDescription> {
    main = "sample.Main" // TODO JavaPlugin を継承したクラスとパッケージを入力する
    version = gitVersion()
    apiVersion = "1.17"
}

task<LaunchMinecraftServerTask>("buildAndLaunchServer") {
    dependsOn("build")
    doFirst {
        copy {
            from(buildDir.resolve("libs/${project.name}.jar"))
            into(buildDir.resolve("MinecraftServer/plugins"))
        }
    }

    jarUrl.set("https://papermc.io/api/v1/paper/1.17.1/latest/download")
    jarName.set("server.jar")
    serverDirectory.set(buildDir.resolve("MinecraftServer"))
    nogui.set(false)
    agreeEula.set(true)
}
