# Import List [![Build Status](https://travis-ci.org/my-flow/importlist.svg?branch=master)](https://travis-ci.org/my-flow/importlist)

Import List is an open-source extension for
[Moneydance](http://www.moneydance.com) monitoring a given directory for
transaction files to import. For more information on where to download and how
to install the extension please visit the
[project page](http://my-flow.github.io/importlist/).

## Build Prerequisites
* Java Development Kit, version 8

## Building the extension
1. `git clone git@github.com:my-flow/importlist.git` creates a copy of the
repository.
2. `./gradlew assemble` produces the distributable.

## Signing the extension
1. `./gradlew genKeys` generates a passphrase-protected key pair.
2. `./gradlew sign` signs the extension.

## Running the extension
After the build process has succeeded, the resulting MXT file located in
`target2015/build/distributions` can be added to Moneydance.

## Project structure
The project consists of the following main projects that must be audited:
- The *core* project contains the common source code.
- The *target2015* project contains source code specific to Moneydance
versions 2015 and above.

The following support projects provide test infrastructure and test cases:
- The *core-test* project contains tests for the *core* project.
- The *target2015-test* project contains tests for the *target2015* project.

## License
Copyright 2011-2018 [Florian J. Breunig](http://www.my-flow.com). Import List
is released under the
[GNU General Public License](http://www.gnu.org/licenses/gpl.html).
