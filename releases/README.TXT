WTF?

Linoratix Intranet Search is a Desktopsearch alike tool for indexing all the documents in your network.
Requirements

Java 1.5+
MySQL 4+
Howto install it

First download the zip-archiv.
On the server side

Create a new MySQL Database and an user for this database.
Use the table.sql file inside the zip-archiv to create all needed tables.

mysql -uUSER -pPASSWORD database < /path/to/archiv/install/tables.sql


Create a directory where you store the server's configuration file "server.xml" (for example /etc/lintrasearch)
Copy the file install/conf/server.xml into that directory and edit this file.

Now create an init script to start the server during boot. You can use startServer.sh to see how to start the server.
On the client side

Create a directory lintrasearch inside your Homedirectory and copy the file install/conf/client.xml to that directory. Edit this file to suit your needs.

Copy the icons directory to the lintrasearch directory in your Homedirectory.
Howto run the client

Copy the file "install/file/file_w" to a path in your $PATH variable.

See startClient.sh script to see how to start the client.
