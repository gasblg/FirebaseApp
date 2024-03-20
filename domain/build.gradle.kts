plugins {
    id("firebaseapp.android.library")
}

android {
    namespace = "com.github.gasblg.firebaseapp.domain"
}

dependencies {

    implementation (libs.androidx.dataStore.core)
    implementation (libs.protobuf.javalite)
}
