# Import List [![Build Status](https://travis-ci.org/my-flow/importlist.svg?branch=master)](https://travis-ci.org/my-flow/importlist)

Import List is an open-source extension for
[Moneydance](http://www.moneydance.com) monitoring a given directory for
transaction files to import. For more information on where to download and how
to install the extension please visit the
[project page](http://my-flow.github.io/importlist/).

## Build Prerequisites
* Java Development Kit, version 7

## Building the extension
1. `git clone git@github.com:my-flow/importlist.git` creates a copy of the
repository.
2. `./gradlew core:assemble` produces the distributable.

## Signing the extension
1. `./gradlew genKeys` generates a passphrase-protected key pair.
2. `./gradlew sign` signs the extension.

## Running the extension
After the build process has succeeded, the resulting extension file
`core/build/distributions/importlist.mxt` can be added to Moneydance®.

## Project Structure
The project consists of 2 gradle sub-projects:
- The **core** sub-project contains the source code which must be audited.
- The **support** sub-project contains the test infrastructure (stubs and
mock-ups) and the test cases. It depends from the core project.

## Documentation
* [Import List API](http://my-flow.github.io/importlist/docs/api/)
* [JMockit Coverage Report](http://my-flow.github.io/importlist/docs/coverage-report/)

## License
Copyright 2011-2018 [Florian J. Breunig](http://www.my-flow.com). Import List
is released under the
[GNU General Public License](http://www.gnu.org/licenses/gpl.html).
