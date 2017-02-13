package com.dzaitsev.rxviper.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.dzaitsev.rxviper.plugin.RxViperExtension.Companion.NAME as RXVIPER_EXT
import com.dzaitsev.rxviper.plugin.RxViperTask.Companion.NAME as RXVIPER_TASK

class RxViperPlugin : Plugin<Project> {
  override fun apply(project: Project) = with(project) {
    check(applied<LibraryPlugin>() || applied<AppPlugin>()) {
      "RxViper plugin can only be applied to Android projects.\n" +
          "Required plugins: 'com.android.application' or 'com.android.library'."
    }

    extensions.create(RXVIPER_EXT, aClass<RxViperExtension>(), container(aClass<Screen>(), ScreenFactory(this)))

    afterEvaluate {
      task(mapOf("type" to aClass<RxViperTask>()), name)
    }
  }

  private inline fun <reified P : Plugin<*>> Project.applied() = plugins.hasPlugin(aClass<P>())
}