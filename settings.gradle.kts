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
include(":features:movies:domain")
include(":features:movies:data")
include(":features:movies:ui")
include(":lib")
include(":lib:common")
include(":lib:common_data")
include(":lib:common_ui")
