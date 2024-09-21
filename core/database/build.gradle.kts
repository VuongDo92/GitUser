plugins {
    alias(libs.plugins.tymex.android.library)
    alias(libs.plugins.tymex.android.room)
}

android {
    namespace = "com.tymex.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
}