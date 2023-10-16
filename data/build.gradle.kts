plugins {
    id("firebaseapp.android.library")
    id("com.google.protobuf") version "0.9.1"
}

android {
    namespace = "com.github.gasblg.firebaseapp.data"
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":analytics"))

    implementation(libs.androidx.dataStore.core)
    implementation(libs.protobuf.javalite)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:21.0-rc-1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}