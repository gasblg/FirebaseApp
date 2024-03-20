package com.github.gasblg.firebaseapp.ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.process.ExecSpec
import java.io.ByteArrayOutputStream

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.cmd(cmd: (ExecSpec) -> Unit): String {
    return ByteArrayOutputStream().use {
        this.exec {
            cmd.invoke(this)
            standardOutput = it
        }
        it.toString().trim()
    }
}
