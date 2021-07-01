import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.kotlin)
    jacoco
}

dependencies {
    implementation(Kotlin.core)
    implementation(Kotlin.kotlin)

    implementation(Di.core)

    implementation(Coroutines.core)
    implementation(Coroutines.coroutines)

    testImplementation(Test.junit)
    testImplementation(Test.coroutine)
    testImplementation(Test.mockk)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
