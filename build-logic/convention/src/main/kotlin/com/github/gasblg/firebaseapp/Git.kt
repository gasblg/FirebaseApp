package com.github.gasblg.firebaseapp

import com.github.gasblg.firebaseapp.ext.cmd
import org.gradle.api.Project

object Git {

    private fun git(
        project: Project,
        vararg args: String
    ): String {
        return project.cmd { it.commandLine("git", *args) }
    }

    fun currentBranch(
        project: Project
    ) = git(project, "rev-parse", "--abbrev-ref", "HEAD")


    fun getLastCommitAuthor(
        project: Project,
        format: String = "%an"
    ) = git(project, "log", "-1", "--pretty=$format")

}
