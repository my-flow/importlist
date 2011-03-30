Import List
===========

Personal finance manager [Moneydance®](http://www.moneydance.com) includes a [feature to import transaction files](http://moneydance.com/userguide-contents/importing%20additional%20information%20into%20moneydance.html), such as from Quicken™. Using third-party background applications to download transaction files on a regular basis I felt that Moneydance® lacks an overview of which files still have to be imported, which, in turn, forces the user to constantly check on the file system.

As a result, this extension monitors a given directory and displays all of its transaction files in a sortable list inside the homepage view along with two corresponding buttons to import and to remove each transaction file.


Installing the extension
------------------------

1.	Download the [latest signed version of Import List](http://moneydance.com/download/modules/importlist.mxt) from the official extensions repository.

2.	Add the downloaded extension file to Moneydance® and you will be asked to choose a base directory to monitor. If you want to change the base directory later, you can select **Import List** from the **Extensions** menu.

3.	In order to display Import List in Moneydance®'s homepage view, open the **Preferences** window and click on the **Home Page** tab. The available items should contain an entry called **Import List**. Add it to the left or right column of your homepage view.


Building the extension from scratch
-----------------------------------

### Requirements
*	Java SE Development Kit, version 6
*	Apache Ant™, tested with version 1.8.2
*	Moneydance®, tested with version 2010r3

### Instructions
*	Run `ant genkeys` to generate a passphrase-protected key pair.
*	Run `ant importlist` to compile and sign the extension.
*	The resulting extension file is located in the `dist` directory.

### Further Assistance
Consult the Core API and the developer's kit, both of which are part of the [Moneydance® Developer Resources](http://www.moneydance.com/developer).
