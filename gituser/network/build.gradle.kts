plugins {
    alias(libs.plugins.tymex.android.library)
    alias(libs.plugins.tymex.jvm.ktor)
}

android {
    namespace = "com.tymex.gituser.network"
}

dependencies {
    implementation(libs.bundles.koin)
//    implementation(libs.bundles.paging)

    implementation(projects.core.domain)
    implementation(projects.core.data)
}