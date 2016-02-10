# Import List [![Build Status](https://travis-ci.org/my-flow/importlist.svg?branch=master)](https://travis-ci.org/my-flow/importlist)

Import List is an open-source extension for [Moneydance]
(http://www.moneydance.com) monitoring a given directory for transaction files
to import. For more information on where to download and how to install the
extension please visit the [project page]
(http://my-flow.github.io/importlist/).

##Build Prerequisites
* Java Development Kit, version 7

## Building the extension
1. `git clone git@github.com:my-flow/importlist.git` creates a copy of the
repository.
2. `gradle dist` produces the distributable.

## Signing the extension
1. `gradle genKeys` generates a passphrase-protected key pair.
2. `gradle sign` signs the extension.

## Running the extension
After the build process has succeeded, the resulting extension file
`core/build/distributions/importlist.mxt` can be added to Moneydance®.

## Documentation
* [Import List API](http://my-flow.github.io/importlist/docs/api/)
* [JMockit Coverage Report]
(http://my-flow.github.io/importlist/docs/coverage-report/)

## License
Copyright 2011-2016 [Florian J. Breunig](http://www.my-flow.com). Import List is
released under the [GNU General Public License]
(http://www.gnu.org/licenses/gpl.html).
