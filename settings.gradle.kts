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
include(
    ":features:actors:domain",
    ":features:actors:ui",
    ":features:actors:data"
)
include(":lib")
include(":lib:common_data")
include(":lib:common_ui")
include(":lib:common")
include(":features:home_ui")
