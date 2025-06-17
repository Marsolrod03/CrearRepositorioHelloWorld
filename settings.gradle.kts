pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MovieDatabase"
include(":app")
include(
    ":features:actors:domain",
    ":features:actors:ui",
    ":features:actors:data",
    ":features:movies:domain",
    ":features:movies:data",
    ":features:movies:ui",
    ":features:series:data",
    ":features:series:ui",
    ":features:series:domain",
    ":features:home:home_ui",
    ":lib",
    ":lib:common_data",
    ":lib:common_ui",
    ":lib:common"
)
