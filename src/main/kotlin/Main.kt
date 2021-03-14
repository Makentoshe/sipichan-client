package com.makentoshe.sclient

import kotlinx.coroutines.runBlocking
import space.jetbrains.api.runtime.resources.applications
import space.jetbrains.api.runtime.resources.chats
import space.jetbrains.api.runtime.resources.projects
import space.jetbrains.api.runtime.resources.teamDirectory
import space.jetbrains.api.runtime.types.Issue
import space.jetbrains.api.runtime.types.IssuesSorting
import space.jetbrains.api.runtime.types.PR_Project
import space.jetbrains.api.runtime.types.ProjectIdentifier

/**
 * There are some examples of retrieving several data from the Space instance.
 * We can process this info as we want - create analytics, office dashboards, and so on.
 */
fun main() = runBlocking {
    val channels = spaceClient.chats.channels.listAllChannels("").data
    println("Channels: ${channels.map { "${it.name}(${it.channelId})" }}")

    // Application: View application rights allows to see all applications
    // If rights were not accepted - the application can see only self.
    val applications = spaceClient.applications.getAllApplications("")
    println("Applications: ${applications.map { "${it.name}(${it.id})" }}")

    // Works only with Members: View member profile rights
    val profiles = spaceClient.teamDirectory.profiles.getAllProfiles().data
    println("Profiles: ${profiles.map { "${it.username}(${it.id})" }}")

    // Works only with Project Parameters: View parameters rights
    // This rights can be managed for selected projects or for whole projects at one time.
    val projects = spaceClient.projects.getAllProjects().data
    println("Projects: ${projects.map { "${it.name}(${it.id})" }}")

    // Works only with Project Issues: View issues rights
    val issues = projects.firstOrNull()?.let { getProjectIssues(it) }
    println("Issues: ${issues?.map { "${it.title}(${it.id})" }} ")
}

private suspend fun getProjectIssues(project: PR_Project): List<Issue> {
    return spaceClient.projects.planning.issues.getAllIssues(
        project = ProjectIdentifier.Id(project.id),
        assigneeId = emptyList(),
        statuses = emptyList(),
        sorting = IssuesSorting.CREATED,
        descending = true
    ).data
}
