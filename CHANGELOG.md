# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com),
and this project does *not* adhere to Semantic Versioning.

## [v16] - 2022-05-12
### Fixed
- Crash in Moneydance 2022.3 (4060) on Windows ([#11]).

## [v15] - 2021-06-20
### Added
- CSV to list of supported file types that can be imported without Text File Importer extension ([#7]).
- Prerequisite: Java 8 or later versions.
- [JaCoCo Java Code Coverage Library](https://www.jacoco.org/jacoco/).
- This changelog file.

### Fixed
- Crash in Moneydance 2021.1 (3069) when using the new "Default" appearance on macOS ([#10]).
- Wrong font size after launching application.

### Removed
- Compatibility with Java 7 and earlier versions.
- JMockit Coverage.

## [v14] - 2018-05-12
### Added
- Compile-time data to manifest file.

### Removed
- Google Analytics tracker.

## [v13] - 2017-12-24
### Changed
- Build automation system from Apache Ant to Gradle.

### Removed
- Crashing file dialog for Mac OS ([#5]).

### Fixed
- Issues reported by [FindBugs](http://findbugs.sourceforge.net/).

## [v12] - 2016-01-04
### Removed
- French and Spanish localizations.

## [v11] - 2015-02-02
### Added
- Continuous integration testing on [Travis CI](https://travis-ci.org/my-flow/importlist).
- Prerequisite: Moneydance 2015 or later.
- Prerequisite: Java 7 or later versions.

### Removed
- Compatibility with Moneydance 2014 and earlier versions.
- Compatibility with Java 6 and earlier versions.

## [v10] - 2013-12-29
### Changed
- Logging component. Replaced Log4j by [Simple Logging Facade for Java (SLF4J)](https://www.slf4j.org).

## [v9] - 2012-10-17
### Added
- [ProGuard](https://www.guardsquare.com/en/products/proguard) build step and reduce file size.
- Sandbox-compatible file dialog for Mac OS ([#3]).
- Compatibility with Moneydance 2012.

## [v8] - 2012-07-15
### Fixed
- Blocking confirmation dialog (file deletion).

## [v7] - 2012-03-10
### Added
- Explicit request to set a base directory.
- Unit test cases with [JUnit](https://junit.org) and [JMockit Coverage](https://jmockit.github.io/tutorial/CodeCoverage.html).

### Fixed
- Issues reported by [Checkstyle](http://checkstyle.sourceforge.net/).

## [v6] - 2011-11-23
### Added
- Buttons to *Import All* files and *Delete All* files at once.
- Keyboard shortcuts.
- Google Analytics tracker.

## [v5] - 2011-09-17
### Added
- CSV to list of supported file types ([#1]).
- French, Spanish, and German localizations.
- GNU General Public License, Version 3.0.

### Fixed
- Issues reported by [PMD Source Code Analyzer](https://pmd.github.io).

## [v4] - 2011-07-08
### Added
- Compatibility with Moneydance 2011.

### Changed
- Replace console logging by [Log4j](https://logging.apache.org/log4j/).

## [v3] - 2011-10-25 [YANKED]
### Added
- Support for saving rearrangement, reordering and resizing of columns between application starts.
- Background monitoring of base directory with [Common IO](https://commons.apache.org/proper/commons-io/)’s file monitor.
- Moneydance icon to confirmation dialog (file deletion).
- Accompanying [project page](https://my-flow.github.io/importlist/).

## [v2] - 2011-04-11
### Added
- Filter for supported files formats: QIF, QFX, OFX, OFC.
- Menu item to change base directory.
- Support for user-defined background colors.
- Prerequisite: Java 6 or later versions.

### Changed
- Name of extension to *Import List*.
- URL of [public repository](https://github.com/my-flow/importlist).

### Removed
- Compatibility with Java 5 and earlier versions.

## [v1] - 2011-03-14
### Added
- First version based on [Apache Ant](https://ant.apache.org)’s build automation system.
- Compatibility with Moneydance 2010.
- Compatibility with Java 1.3 and later versions.

[#11]: https://github.com/my-flow/importlist/issues/11
[#10]: https://github.com/my-flow/importlist/issues/10
[#7]: https://github.com/my-flow/importlist/issues/7
[#5]: https://github.com/my-flow/importlist/issues/5
[#3]: https://github.com/my-flow/importlist/issues/3
[#1]: https://github.com/my-flow/importlist/issues/1

[v1]: https://github.com/my-flow/importlist/commits/v1
[v2]: https://github.com/my-flow/importlist/compare/v1...v2
[v3]: https://github.com/my-flow/importlist/compare/v2...v3
[v4]: https://github.com/my-flow/importlist/compare/v3...v4
[v5]: https://github.com/my-flow/importlist/compare/v4...v5
[v6]: https://github.com/my-flow/importlist/compare/v5...v6
[v7]: https://github.com/my-flow/importlist/compare/v6...v7
[v8]: https://github.com/my-flow/importlist/compare/v7...v8
[v9]: https://github.com/my-flow/importlist/compare/v8...v9
[v10]: https://github.com/my-flow/importlist/compare/v9...v10
[v11]: https://github.com/my-flow/importlist/compare/v10...v11
[v12]: https://github.com/my-flow/importlist/compare/v11...v12
[v13]: https://github.com/my-flow/importlist/compare/v12...v13
[v14]: https://github.com/my-flow/importlist/compare/v13...v14
[v15]: https://github.com/my-flow/importlist/compare/v14...v15
[v16]: https://github.com/my-flow/importlist/compare/v15...v16
