import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.ApplicationVariant
import com.github.gasblg.firebaseapp.Git
import com.github.gasblg.firebaseapp.ext.cmd
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Locale
import java.util.Properties

class AndroidDistributionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {

            with(pluginManager) {
                apply("com.google.firebase.appdistribution")
            }

            project.plugins.withId("com.android.application") {
                val androidComponents =
                    project.extensions.getByType(ApplicationAndroidComponentsExtension::class.java)
                androidComponents.onVariants { variant ->
                    collectUploadTask(project, variant)
                }
            }

            afterEvaluate {
                val uploadTasks = tasks.filter { it.name.contains(taskPrefix) }
                tasks.register(UploadName.GROUP_UPLOAD) {
                    group = "distribution"
                    doLast {
                        print("Versions upload success")
                    }
                    dependsOn(uploadTasks.toTypedArray())
                }
            }
        }
    }


    private fun collectUploadTask(
        project: Project,
        variant: ApplicationVariant
    ) {
        val pathToApk = createPath(project, variant)
        val name = variant.name.capitalized()

        val branchName = Git.currentBranch(project)
        val userName = Git.getLastCommitAuthor(project)

        val notes = "type: ${name.replaceFirstChar { it.lowercase(Locale.ROOT) }}\n" +
                "branch: $branchName\n" +
                "user: $userName"

        println("\nrelease notes:\n$notes")

        createUploadTask(
            project,
            pathToApk,
            name,
            notes
        )
    }


    private fun createPath(
        project: Project,
        variant: ApplicationVariant
    ): String {
        val flavorName = if (variant.flavorName.isNullOrEmpty()) "" else "-${variant.flavorName}"
        //can be modified if signed
        val buildType =
            if (variant.buildType == "release") variant.buildType + "-unsigned" else variant.buildType
        val outputDir =
            File("${project.buildDir.absolutePath}/outputs/apk/${variant.flavorName}/${variant.buildType}")
        val outputFileName = "app$flavorName-$buildType.apk"
        val outputFile = File("$outputDir/$outputFileName")
        println("\noutputFile: " + outputFile.path)

        return outputFile.path
    }

    private fun createUploadTask(
        project: Project,
        pathToApk: String,
        name: String,
        notes: String
    ) {
        project.tasks.register("$taskPrefix$name") {
            group = "distribution"
            doLast {
                println("Uploading task: ${this.name}")
                println(
                    upload(
                        project, pathToApk, notes
                    )
                )
            }
           dependsOn("assemble${name}")
        }
    }

    companion object {
        private const val taskPrefix = "upload"
    }

    private fun getFirebaseAppId(project: Project): String {
        val file = project.file("${project.projectDir}/${PARAMS.FILE_NAME}")
        if (file.exists()) {
            val properties = Properties()
            properties.load(FileInputStream(file))
            return properties.getProperty(PARAMS.FIREBASE_APP_ID)
        } else {
            throw FileNotFoundException()
        }
    }

    private fun getTestersGroup() = Groups.DEVELOPERS_AND_TESTERS

    private fun upload(
        project: Project,
        pathToApk: String,
        releaseNotes: String
    ): String {
        val firebaseAppId = getFirebaseAppId(project)
        val testers = getTestersGroup()
        return project.cmd {
            it.commandLine(
                "firebase", "appdistribution:distribute", pathToApk,
                "--app", firebaseAppId,
                "--release-notes", releaseNotes,
                "--groups", testers
            )
        }
    }

    object Groups {
        private const val DEVELOPERS = "developers"
        private const val TESTERS = "testers"

        const val DEVELOPERS_AND_TESTERS = "$DEVELOPERS, $TESTERS"
    }

    object UploadName {
        const val GROUP_UPLOAD = "uploadAll"
    }

    object PARAMS {
        const val FILE_NAME = "params.txt"
        const val FIREBASE_APP_ID = "firebase_app_id"
    }
}


