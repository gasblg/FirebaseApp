import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.github.gasblg.firebaseapp.ext.configureKotlinAndroid
import com.github.gasblg.firebaseapp.ext.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
                apply("com.google.firebase.crashlytics")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 33
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {

                //testing
                add("testImplementation", "junit:junit:4.13.2")
                add("androidTestImplementation", "androidx.test.ext:junit:1.1.5")
                add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.5.1")

                //android
                add("implementation", "androidx.core:core-ktx:1.9.0")
                add("implementation", "androidx.appcompat:appcompat:1.6.1")
                add("implementation", "com.google.android.material:material:1.9.0")

                //firebase
                add("implementation", platform("com.google.firebase:firebase-bom:32.2.2"))
                add("implementation", "com.google.firebase:firebase-auth")
                add("implementation", "com.google.firebase:firebase-analytics-ktx")
                add("implementation", "com.google.firebase:firebase-crashlytics-ktx")
                add("implementation", "com.google.firebase:firebase-config-ktx")
                add("implementation", "com.google.firebase:firebase-database")

                //dagger
                add("implementation", "com.google.dagger:dagger:2.48")
                add("implementation", "com.google.dagger:dagger-android-support:2.48")
                add("kapt", "com.google.dagger:dagger-compiler:2.48")
                add("kapt", "com.google.dagger:dagger-android-processor:2.48")
            }
        }
    }
}
