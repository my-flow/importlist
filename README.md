Import List [![Build Status](https://travis-ci.org/my-flow/importlist.svg?branch=master)]
(https://travis-ci.org/my-flow/importlist)
===========

Import List is an open-source extension for [Moneydance®]
(http://www.moneydance.com) monitoring a given directory for transaction files 
to import. For more information on where to download and how to install the 
extension please visit the [project page]
(http://my-flow.github.io/importlist/).

Build Prerequisites
-------------------
* Java Development Kit, version 6
* [Apache Ant™](http://ant.apache.org), version 1.7 or newer

Building the extension
----------------------
1. `git clone git@github.com:my-flow/importlist.git` creates a copy of the 
repository.
2. `ant importlist` compiles the extension (and signs it if an applicable key 
pair is found).

Signing the extension
---------------------
1. `ant genkeys` generates a passphrase-protected key pair.
2. `ant sign` signs the extension.

Running the extension
---------------------
After the build process has succeeded, the resulting extension file 
`dist/importlist.mxt` can be added to Moneydance®.

Documentation
-------------
* [Import List API](http://my-flow.github.io/importlist/docs/api/)
* [CheckStyle Audit]
(http://my-flow.github.io/importlist/docs/checkstyle-report/)
* [JMockit Coverage Report]
(http://my-flow.github.io/importlist/docs/coverage-report/)
* [PMD report]
(http://my-flow.github.io/importlist/docs/pmd-report/pmd-report.html)

License
-------
Copyright 2011-2014 [Florian J. Breunig](http://www.my-flow.com). Import List is
released under the [GNU General Public License]
(http://www.gnu.org/licenses/gpl.html).
