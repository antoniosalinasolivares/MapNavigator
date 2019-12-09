# Map Navigator

This proyect uses sockets and and buffers in order to provide a Client-Server experience inorder to get information about a map.

It user a class called Link which provides a framework to easily send and receives Messages.

```java
Link connection = new Link(yoursocket);
connection.output.Send(new Message("identifier","value","secondary value"));
Message received = connection.output.readMessage();
```
Message is algo a class that, to put it simple, encapsulates three Strings.

Link has a method to close the connection called close();

## Client and Server
This project user two main parts the Client and the Server, both are just interpreters of what they receive as a message

Server is a thread, which runs on the same principle as the client.

feel free to take a look at the code in order to inspect the code in more detail.
