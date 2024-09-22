plugins {
    alias(libs.plugins.tymex.jvm.library)
    alias(libs.plugins.tymex.jvm.junit5)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.domain)
}
