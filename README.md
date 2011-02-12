ImportList
==========

Personal finance manager [Moneydance®](http://www.moneydance.com/) includes a feature to import transaction files, such as from Quicken™. Using third-party background applications to download transaction files on a regular basis I felt that Moneydance® lacks an overview of which files still have to be imported, which, in turn, forces the user to constantly check on the file system.

As a result, this extension monitors a given directory and displays all of its transaction files in a sortable list inside the homepage view along with two corresponding buttons to import and to remove each transaction file.


Installing the extension
------------------------
Having added the extension file to Moneydance®, you will be asked to choose a base directory to monitor. If you do not choose any directory, the extension will assume you want to monitor the directory of the transaction file that was imported last. If you have never imported any transaction file before using the extension, it will monitor the current user's home directory. If you want to change the base directory later, you have to either reinstall the extension or modify the `importlist.import_dir` user preferences variable.

In order to display ImportList in Moneydance®'s homepage view, open the **Preferences** window and click on the **Home Page** tab. The available items should contain an entry called **ImportList**. Add it to the left or right column of your homepage view.


Building the extension from scratch
-----------------------------------

### Requirements
* Java Development Kit, down to version 1.3 ;-)
* Apache Ant, tested with version 1.8.1
* Moneydance®, tested with version 2010r3

### Instructions
* Run `ant genkeys` to generate a passphrase-protected key pair.
* Run `ant importlist` to compile and sign the extension.
* The resulting extension file is located in the `dist` directory.

### Further Assistance
Consult the Core API and the developer's kit, both of which are part of the [Moneydance® Developer Resources](http://www.moneydance.com/developer).