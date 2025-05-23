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

rootProject.name = "CrearRepositorio"
include(":app")
include(":features")
include(":features:series")
include(":features:series:data")
include(":features:series:ui")
include(":features:series:domain")
include(":lib")
include(":lib:common_data")
include(":lib:common_ui")
