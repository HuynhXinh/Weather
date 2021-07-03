import com.android.build.api.dsl.SigningConfig
import java.io.FileInputStream
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
        versionCode = Apps.versionCode
        versionName = Apps.versionName

        multiDexEnabled = true

        buildConfigField("String", "APP_NAME", "\"Weather\"")
    }

    fun setSigningConfig(signingProperties: Properties, signingConfig: SigningConfig) {
        signingConfig.apply {
            storeFile = rootProject.file(signingProperties.getProperty("storeFile"))
            storePassword = signingProperties.getProperty("storePassword")
            keyAlias = signingProperties.getProperty("keyAlias")
            keyPassword = signingProperties.getProperty("keyPassword")
        }
    }

    signingConfigs {
        val debugProperties = Properties()
        debugProperties.load(FileInputStream(rootProject.file("debug.properties")))
        getByName("debug") {
            setSigningConfig(signingProperties = debugProperties, signingConfig = this)
        }

        val releaseProperties = Properties()
        releaseProperties.load(FileInputStream(rootProject.file("release.properties")))
        create("release") {
            setSigningConfig(signingProperties = releaseProperties, signingConfig = this)
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true

            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("Boolean", "LOGGING", "true")
        }

        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true

            isDebuggable = true

            setUseProguard(true)

            signingConfig = signingConfigs.getByName("release")

            buildConfigField("Boolean", "LOGGING", "false")

            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "app-app-proguard-rules.pro",

                "../proguard-rules/retrofit.pro",
                "../proguard-rules/okhttp.pro",
                "../proguard-rules/okio.pro"
            )
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
