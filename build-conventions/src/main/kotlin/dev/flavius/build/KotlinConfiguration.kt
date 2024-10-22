package dev.flavius.build

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

internal inline fun <reified T : KotlinTopLevelExtension> Project.allWarningsAsErrors() {
    compilerOptions<T> { allWarningsAsErrors.set(true) }
}

internal inline fun <reified T : KotlinTopLevelExtension> Project.optIns(vararg optIns: String) {
    compilerOptions<T> {
        optIns.forEach { optIn ->
            freeCompilerArgs.add("-opt-in=$optIn")
        }
    }
}

private inline fun <reified T : KotlinTopLevelExtension> Project.compilerOptions(
    crossinline configuration: KotlinJvmCompilerOptions.() -> Unit,
) = configure<T> {
    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> {
            throw UnsupportedOperationException("Unsupported kotlin project extension: ${T::class}")
        }
    }.apply {
        configuration()
    }
}
