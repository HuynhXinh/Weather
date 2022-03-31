import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlin_parcelize)
    id(Plugins.kotlinKapt)
    jacoco
}

android {
    compileSdkVersion(Apps.compileSdk)
    buildToolsVersion(Apps.buildTools)

    defaultConfig {
        applicationId = Apps.id
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = getVersionCode()
        versionName = getVersionName()

        multiDexEnabled = true

        applicationVariants.all {
            outputs.map { it as? ApkVariantOutputImpl }.forEach {
                it?.outputFileName = "Weather_${versionName}(${versionCode})_${buildType.name}.apk"
                println(it?.outputFile?.name)
            }
        }

        buildConfigField("String", "APP_NAME", "\"Weather\"")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true

            buildConfigField("Boolean", "LOGGING", "true")
        }

        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false

            buildConfigField("Boolean", "LOGGING", "false")

            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type"
    }

    dexOptions {
        javaMaxHeapSize = "4g"
        preDexLibraries = true
    }

    //ignore duplicate package
    packagingOptions {
        exclude("META-INF/*")
    }

    bundle {
        language {
            enableSplit = false
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }

    buildFeatures {
        viewBinding = true
    }

    lintOptions {
        warning("InvalidPackage")
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Modules.data))
    implementation(project(Modules.domain))

    implementation(Kotlin.core)
    implementation(Kotlin.kotlin)

    implementation(Di.core)
    implementation(Di.core_ext)
    implementation(Di.koin)
    implementation(Di.scope)
    implementation(Di.viewmodel)
    implementation(Di.fragment)
    implementation(Di.ext)

    implementation(Coroutines.coroutines)

    implementation(AndroidX.recyclerview)
    implementation(AndroidX.multidex)
    implementation(AndroidX.design)
    implementation(AndroidX.appcompat)
    implementation(AndroidX.constraint)
    implementation(AndroidX.navigationFragment)
    implementation(AndroidX.navigationUi)
    implementation(AndroidX.room)
    implementation(AndroidX.viewModel)
    implementation(AndroidX.java8)
    implementation(AndroidX.liveData)
    implementation(AndroidX.fragment_ktx)

    implementation(Network.retrofit)
    implementation(Network.gson)
    implementation(Network.okhttp_logging_interceptor)

    implementation(Timber.timber)
    implementation(Timber.timberkt)

    implementation(Google.gson)

    testImplementation(Test.junit)
    testImplementation(Test.coroutine)
    testImplementation(Test.core)
    testImplementation(Test.test_core)
    testImplementation(Test.mockk)
}

fun getVersionCode(): Int {
    val properties = Properties().apply {
        load(FileInputStream(rootProject.file("app_version.properties")))
    }

    val majorVersion = properties.getProperty("MAJOR_VERSION").trim().toInt()
    val minorVersion = properties.getProperty("MINOR_VERSION").trim().toInt()
    val patchVersion = properties.getProperty("PATCH_VERSION").trim().toInt()

    return calculateVersionCode(
        majorVersion = majorVersion,
        minorVersion = minorVersion,
        patchVersion = patchVersion
    )
}

fun calculateVersionCode(majorVersion: Int = 1, minorVersion: Int = 0, patchVersion: Int = 0): Int {
    return (majorVersion * 1_000_000) + (minorVersion * 100_000) + (patchVersion * 10_000)
}

fun getVersionName(): String {
    val properties = Properties().apply {
        load(FileInputStream(rootProject.file("app_version.properties")))
    }

    val majorVersion = properties.getProperty("MAJOR_VERSION").trim().toInt()
    val minorVersion = properties.getProperty("MINOR_VERSION").trim().toInt()
    val patchVersion = properties.getProperty("PATCH_VERSION").trim().toInt()

    return "$majorVersion.$minorVersion.$patchVersion"
}

fun increaseMajorVersion() {
    val properties = Properties().apply {
        load(FileInputStream(rootProject.file("app_version.properties")))
    }
    val majorVersion = properties.getProperty("MAJOR_VERSION").trim().toInt() + 1
    properties.setProperty("MAJOR_VERSION", majorVersion.toString())

    properties.setProperty("MINOR_VERSION", "0")
    properties.setProperty("PATCH_VERSION", "0")

    properties.setProperty(
        "VERSION_CODE",
        calculateVersionCode(majorVersion = majorVersion).toString()
    )

    properties.store(FileOutputStream(rootProject.file("app_version.properties")), "")
}

fun increaseMinorVersion() {
    val properties = Properties().apply {
        load(FileInputStream(rootProject.file("app_version.properties")))
    }

    val minorVersion = properties.getProperty("MINOR_VERSION").trim().toInt() + 1

    if (minorVersion >= 100_000) {
        increaseMajorVersion()
    } else {
        properties.setProperty("MINOR_VERSION", minorVersion.toString())

        properties.setProperty("PATCH_VERSION", "0")

        val majorVersion = properties.getProperty("MAJOR_VERSION").trim().toInt()
        properties.setProperty(
            "VERSION_CODE",
            calculateVersionCode(
                majorVersion = majorVersion,
                minorVersion = minorVersion
            ).toString()
        )

        properties.store(FileOutputStream(rootProject.file("app_version.properties")), "")
    }
}

fun increasePatchVersion() {
    val properties = Properties()
    properties.load(FileInputStream(rootProject.file("app_version.properties")))

    val patchVersion = properties.getProperty("PATCH_VERSION").trim().toInt() + 1

    if (patchVersion >= 10_000) {
        increaseMinorVersion()
    } else {

        properties.setProperty("PATCH_VERSION", patchVersion.toString())

        val majorVersion = properties.getProperty("MAJOR_VERSION").trim().toInt()
        val minorVersion = properties.getProperty("MINOR_VERSION").trim().toInt()
        properties.setProperty(
            "VERSION_CODE",
            calculateVersionCode(
                majorVersion = majorVersion,
                minorVersion = minorVersion,
                patchVersion = patchVersion
            ).toString()
        )

        properties.store(FileOutputStream(rootProject.file("app_version.properties")), "")
    }
}

fun logVersionChanged(function: () -> Unit) {
    val current = "${getVersionName()}(${getVersionCode()})"
    function.invoke()
    val newVersion = "${getVersionName()}(${getVersionCode()})"
    println("Version changed: $current -> $newVersion")
}

fun createReleaseBranch() {
    println(">>>>>>>>>>> createReleaseBranch()")
    val versionCode: Int = getVersionCode()
    val versionName = getVersionName()
    val releaseName = "release_${versionName}($versionCode)"
    val description = "Release version: $versionName build: $versionCode"

    val branchName = "release/$releaseName"
    runGitCommand("git", "checkout", "-b", branchName)
    runGitCommand("git", "commit", "-am", description)
//    runGitCommand("git", "push", "--set-upstream","origin", branchName)
}

fun addGitTagForRelease() {
    println(">>>>>>>>>>>addGitTagForRelease()")
    val versionCode: Int = getVersionCode()
    val versionName = getVersionName()
    val releaseName = "release_${versionName}($versionCode)"
    val branchName = "release/$releaseName"
    val description = "Release version: $versionName build: $versionCode"

    runGitCommand("git", "checkout", "master")
    runGitCommand("git", "fetch")
    runGitCommand("git", "merge", branchName)
    runGitCommand("git", "commit", "-am", description)
    runGitCommand("git", "tag", "-a", "-m", description, releaseName)
    //    runGitCommand("git", "push", "origin", "--follow-tags")

    runGitCommand("git", "checkout", "develop")
    runGitCommand("git", "fetch")
    runGitCommand("git", "merge", branchName)
    runGitCommand("git", "commit", "-am", description)
    //    runGitCommand("git", "push", "origin", "--follow-tags")

    runGitCommand("git", "branch", "-d", branchName)
//    runGitCommand("git", "push", "--delete", tagName)
}

fun createGitHotFix() {
    println(">>>>>>>>>>>createGitHotFix()")
    val versionCode = getVersionCode()
    val versionName = getVersionName()
    val tagName = "release_${versionName}($versionCode)"

    runGitCommand("git", "fetch")
    runGitCommand("git", "checkout", "-b", "hotfix/$tagName", tagName)

//    runGitCommand("git", "push", "--set-upstream", "origin", hotFixBranch)
}

fun haveDoneHotFix() {
    println(">>>>>>>>>>>haveDoneHotFix()")
    val versionCode: Int = getVersionCode()
    val versionName = getVersionName()
    val hotfixName = "hotfix_${versionName}($versionCode)"
    val branchName = "hotfix/$hotfixName"
    val description = "Hot fix version: $versionName build: $versionCode"

    runGitCommand("git", "checkout", "master")
    runGitCommand("git", "fetch")
    runGitCommand("git", "merge", branchName)
    runGitCommand("git", "commit", "-am", description)
    runGitCommand("git", "tag", "-a", "-m", description, hotfixName)
    //    runGitCommand("git", "push", "origin", "--follow-tags")

    runGitCommand("git", "checkout", "develop")
    runGitCommand("git", "fetch")
    runGitCommand("git", "merge", branchName)
    runGitCommand("git", "commit", "-am", description)
    //    runGitCommand("git", "push", "origin", "--follow-tags")

    runGitCommand("git", "branch", "-d", branchName)
//    runGitCommand("git", "push", "--delete", tagName)
}

fun runGitCommand(vararg command: String) {
    val commands = command.toList()
    val process = ProcessBuilder(commands).start()
    process.waitFor()
    process.errorStream?.getString()?.print("Command: ${commands.joinToString(" ")} fail")
    process.inputStream.getString()?.print("Command: ${commands.joinToString(" ")} info")
    process.destroy()
}

fun String.print(tag: String) {
    println("$tag: $this")
}

fun InputStream?.getString(): String? {
    this ?: return null
    return bufferedReader().use { it.readText() }
}

tasks.register("haveDoneHotFix") {
    doLast {
        haveDoneHotFix()
    }
}

tasks.register("createGitHotFix") {
    doLast {
        createGitHotFix()
    }
}

tasks.register("addGitTagForRelease") {
    doLast {
        addGitTagForRelease()
    }
}

tasks.register("createReleaseBranch") {
    doLast {
        createReleaseBranch()
    }
}

tasks.register("increaseMinorVersion") {
    doLast {
        logVersionChanged {
            increaseMinorVersion()
        }
    }
}

tasks.register("increaseMajorVersion") {
    doLast {
        logVersionChanged {
            increaseMajorVersion()
        }
    }
}

tasks.register("increasePatchVersion") {
    doLast {
        logVersionChanged {
            increasePatchVersion()
        }
    }
}
