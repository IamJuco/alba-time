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
        maven { url = uri("https://www.jitpack.io" ) }
    }
}

rootProject.name = "alba-time"
include(":app")
include(":data")
include(":domain")
include(":common")
include(":feature:home")
include(":feature:calendar")
include(":feature:main")
include(":feature:workplacesetting")
include(":feature:workplacedetail")
include(":feature:workplaceedit")
include(":designsystem")
