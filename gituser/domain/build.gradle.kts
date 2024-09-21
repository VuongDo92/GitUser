plugins {
    alias(libs.plugins.tymex.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.domain)
//    implementation(libs.androidx.paging.common.jvm)
}
