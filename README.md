# sbt-unpack

Simple plugin to unpack JARs in your project. Loosely based on ideas presented in Josh Suereth's book 
[sbt in Action](https://www.manning.com/books/sbt-in-action) 
[Chapter 7](https://github.com/jsuereth/sbt-in-action-examples/blob/master/chapter7/website/build.sbt) 
and packaged into an auto plugin.

## Installation

Add this to `plugins.sbt`:

```
addSbtPlugin("com.mariussoutier.sbt" % "sbt-unpack" % "0.9.5")
```

## Usage

First, enable the plugin on the project you want to use it with.

```scala
lazy val root = project(...)
  .enablePlugins(com.mariussoutier.sbt.UnpackPlugin)

```

Then run `unpackJars` manually or execute it automatically by adding to a generator, for example:

```scala
import com.mariussoutier.sbt.UnpackKeys
sourceGenerators in Compile += Def.sequential(UnpackKeys.unpackJars, ...)
```

## Configuration

| Key                         | Description                                                   |
| --------------------------- | ------------------------------------------------------------- |
| `dependenciesJarDirectory`  | Location of the unpacked dependency JARs                      | 
| `dependencyFilter`          | Which dependencies to unpack                                  | 
| `fileExcludeFilter`         | Files inside the JARs that should be excluded while unpacking |

Example:

```scala
import com.mariussoutier.sbt.UnpackKeys
import NameFilter._
.settings(
  UnpackKeys.dependencyFilter := { (fileName: String) => fileName.startsWith("example-") },
)
```
