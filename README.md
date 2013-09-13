Herd
====
Herd is a concatenative programming language derived
from Forth. Herd is intended to be quite compatible with Forth, although
it does not strive to be 100% compatible. It has bindings to the Vaadin UI toolkit
and also a small persistence API based on plain SQL used trough JDBC.


Installing
==========
Herd development is done with the help of maven. This makes running Herd rather easy. You must first clone the
repository. Cloning the master branch is recommended at the moment, as the version branches are simply snapshots
of different development phases and there are no guarantees of stability in any case. So go with master, as it
has all the latest changes.

One thing needs to be changed in the sources. That is the directory that holds all the Herd source files. This is the
Herd-directory at the root of the cloned repository. Change the constant string FILE_DIRECTORY in Files.java
to point to the directory you intend to keep your Herd sources in. This arrangement is only temporary, and
should change once issue #15 has been resolved.

To build Herd and to fetch all its dependencies type this into a console:

	mvn install

After that is done running maven in a jetty instance can be done as follows:

	mvn jetty:run

herd can then be accessed by directing your favorite browser at `http://localhost:8080/Herd` Open a source file
by clicking in the file list. Press the execute button to run it, and inspect the results in the Output tab
next to the code editor.

