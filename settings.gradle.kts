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

/** 해당 메서드 추가시 implementation(projects.feature.home) 와 같은 형식으로 안전하게 project 의존성 추가 가능 */
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "alba-time"
include(":app")
include(":data")
include(":domain")
include(":common")
include(":feature:home")
include(":feature:calendar")
include(":feature:community")
include(":feature:main")
include(":feature:workplacesetting")
include(":feature:workplacedetail")
include(":feature:workplaceedit")
include(":designsystem")

