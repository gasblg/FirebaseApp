import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.github.gasblg.firebaseapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)

}


gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "firebaseapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "firebaseapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidDistribution") {
            id = "firebaseapp.android.distribution"
            implementationClass = "AndroidDistributionPlugin"
        }
    }
}
