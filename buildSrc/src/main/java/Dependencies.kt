object Kotlin {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val core = "androidx.core:core-ktx:${Versions.core}"
}

object AndroidX {
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val design = "com.google.android.material:material:${Versions.design}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragment}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationUi}"
    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.room}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"

    const val recyclerview =  "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
}

object Google {
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}

object Di {
    const val core = "org.koin:koin-core:${Versions.koin}"
    const val core_ext = "org.koin:koin-core-ext:${Versions.koin}"
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val scope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val fragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    const val ext = "org.koin:koin-androidx-ext:${Versions.koin}"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object Network {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"
}

object Timber {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val timberkt = "com.github.ajalt:timberkt:${Versions.timberkt}"
}

object Test {
    const val junit = "junit:junit:${Versions.junit}"
    const val core = "android.arch.core:core-testing:1.1.1"
    const val test_core = "androidx.test:core:1.3.0"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.0"
    const val mockk = "io.mockk:mockk:1.11.0"
}