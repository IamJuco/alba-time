// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
}

// 모든 프로젝트에 적용할 플러그인
subprojects {
    // 루트 프로젝트는 굳이.. 제외
    if (project == rootProject) return@subprojects

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    // Detekt
    extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        config = files("$rootDir/detekt.yml")
        buildUponDefaultConfig = true
    }

    // KtLint
    extensions.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        android.set(true)
    }
}
