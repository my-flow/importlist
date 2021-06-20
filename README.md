# Import List [![Build Status](https://travis-ci.com/my-flow/importlist.svg?branch=develop)](https://travis-ci.com/my-flow/importlist)

Import List is an open-source extension for
[Moneydance](http://www.moneydance.com) monitoring a given directory for
transaction files to import. For more information on where to download and how
to install the extension please visit the
[project page](https://www.my-flow.com/importlist/).

## Build Prerequisites
* Java Development Kit, version 11

## Building the extension
1. `git clone git@github.com:my-flow/importlist.git` creates a copy of the
repository.
2. `./gradlew assemble` produces the distributables.

## Signing the extension
1. `./gradlew genKeys` generates a passphrase-protected key pair.
2. `./gradlew sign` signs the extension.

## Running the extension
After the build process has succeeded, the resulting MXT files are located in
- `target2012/build/distributions`
- `target2015/build/distributions`

These MXT files can be added to Moneydance.

## Project structure
The project consists of the following main projects:
- `target2012` contains source code specific to Moneydance 2012 and 2014.
- `target2015` contains source code specific to Moneydance 2015 and later.
- `core` contains common source code.

Support projects provide test infrastructure and test cases:
- `target2012-test` contains tests of the *target2012* project.
- `target2015-test` contains tests of the *target2015* project.
- `core-test` contains tests of the *core* project.

## Documentation
* [Changelog](CHANGELOG.md)
* [SonarCloud](https://sonarcloud.io/organizations/importlist/)

## License
Copyright 2011-2021 [Florian J. Breunig](http://www.my-flow.com). Import List
is released under the
[GNU General Public License](http://www.gnu.org/licenses/gpl.html).