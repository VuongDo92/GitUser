plugins {
    alias(libs.plugins.tymex.jvm.library)
    alias(libs.plugins.tymex.jvm.junit5)
}

dependencies {
    implementation(projects.core.domain)

    implementation(projects.gituser.domain)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.junit5.api)
    implementation(libs.coroutines.test)
}