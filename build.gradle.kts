// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply {
    plugin(Plugins.koin)
    plugin(Plugins.jacoco)
}

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(ClassPaths.android_tools_gradle)
        classpath(ClassPaths.kotlin_gradle_plugin)
        classpath(ClassPaths.koin_gradle_plugin)
        classpath(ClassPaths.jacoco_gradle_plugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

val mergeTestReport = tasks.register<TestReport>("mergeTestReports") {
    destinationDir = file("$buildDir/reports/tests/test")
    reportOn(subprojects.mapNotNull {
        it.tasks.findByPath("test")
    })
    reportOn(subprojects.mapNotNull {
        it.tasks.findByPath("testDebugUnitTest")
    })
}