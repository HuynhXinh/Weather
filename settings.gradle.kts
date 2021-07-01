rootDir
    .walk()
    .maxDepth(1)
    .filter {
        it.name != "buildSrc" &&
            it.name != "Weather" &&
            it.isDirectory &&
            file("${it.absolutePath}/build.gradle.kts").exists()
    }
    .forEach {
        include(":${it.name}")
        println("Setting: ${it.name}")
    }

rootProject.name = "Weather"
include(":data")
include(":domain")
