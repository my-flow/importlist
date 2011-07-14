Import List
===========

Import List is an open-source extension for [Moneydance®](http://www.moneydance.com) monitoring a given directory for transaction files to import. For more information on where to download and how to install the extension please visit the [project page](http://my-flow.github.com/importlist/).

Build Prerequisites
-------------------
*	Java Development Kit, version 6
*	Apache Ant™, tested with version 1.8.2

Building the extension
----------------------
1.	`git clone git@github.com:my-flow/importlist.git` creates a copy of the repository.
2.	`ant genkeys` generates a passphrase-protected key pair.
3.	`ant importlist` compiles and signs the extension.

Running the extension
---------------------
After the build process has succeeded, the resulting extension file `dist/importlist.mxt` can be added to Moneydance®. However, it can also run in stand-alone mode, which allows for easy testing with fast feedback:

*	`java -jar dist/importlist.mxt` runs the extension in stand-alone mode.
*	`java -jar dist/importlist.mxt $HOME/Downloads/` runs the extension using a predefined base directory.
*	`java -Dlog4j.configuration=file:///$HOME/log4j.properties -jar dist/importlist.mxt` runs the extension using an [Apache log4j™](http://logging.apache.org/log4j/) configuration file.

Further Assistance
------------------
Consult the [Import List API](http://my-flow.github.com/importlist/docs/api/index.html) and/or the [Core API](http://www.moneydance.com/dev/apidoc/index.html).
