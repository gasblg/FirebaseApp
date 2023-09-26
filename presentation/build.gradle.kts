plugins {
    id("firebaseapp.android.library")
    id("com.google.gms.google-services")
    id("com.google.protobuf") version "0.9.1"
}

android {
    namespace = "com.github.gasblg.firebaseapp.presentation"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":domain"))

    //navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    implementation (libs.play.services.auth)

    //images
    implementation (libs.glide)
    implementation(libs.circleimageview)
}