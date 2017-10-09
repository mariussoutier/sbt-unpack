package com.mariussoutier.sbt

import _root_.sbt.plugins.JvmPlugin
import _root_.sbt._
import Keys._

object UnpackPlugin extends AutoPlugin {

  override def requires = JvmPlugin
  override def trigger: PluginTrigger = noTrigger

  val autoImport = UnpackKeys

  def unpack(target: File, f: File, log: Logger, excludes: NameFilter): Set[java.io.File] = {
    log.info("unpacking " + f.getName)
    if (!f.isDirectory) sbt.IO.unzip(f, target, (name: String) => !excludes.accept(name))
    else Set.empty
  }

  import UnpackKeys._

  override def projectSettings: Seq[Def.Setting[_]] = super.projectSettings ++ Seq(
    dependenciesJarDirectory := target.value / "unpack",
    fileExcludeFilter := new ExactFilter("meta-inf") | new ExactFilter("license"),
    dependencyFilter := AllPassFilter,
    InternalTasks.createDependenciesJarDirectory := {
      val log = streams.value.log
      val dir = dependenciesJarDirectory.value
      log.debug(s"Creating unpack dir $dir")
      IO.delete(dir)
      IO.createDirectory(dir)
      dir
    },
    unpackJars := {
      val log = streams.value.log
      val dir = InternalTasks.createDependenciesJarDirectory.value
      // TODO Checkout Classpaths.managedJars to filter Modules instead of files
      val artifacts = (dependencyClasspath in Compile).value.filter { attrFile =>
        dependencyFilter.value.accept(attrFile.data)
      }
      Attributed.data(artifacts)
        .flatMap(file => unpack(dir, file, log, fileExcludeFilter.value).toSeq)
    }
  )
}

object UnpackKeys {

  val dependenciesJarDirectory = settingKey[File]("Location of the unpacked dependency JARs")
  val dependencyFilter = settingKey[FileFilter]("Which dependencies to unpack")
  val fileExcludeFilter = settingKey[NameFilter]("Files that should be excluded while unpacking JARs")

  val unpackJars = taskKey[Seq[java.io.File]]("unpacks dependent jars into dependentJarDirectory")

  private[sbt] object InternalTasks {
    val createDependenciesJarDirectory = taskKey[File]("Create the unpacked dependency JARs directory")
  }
}
