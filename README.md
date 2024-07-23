# Server-Client Application

## Overview

This application demonstrates a basic server-client architecture using Java. The server can handle multiple clients, manage different protocols, and serve files from different directories. The client can connect to the server, request files, and download them.

## Directory Structure

```
.
├── Client
│   ├── downloads
│   ├── ClientProtocols.java
│   ├── RunClient.java
├── Server
│   ├── folders
│   │   ├── folder1
│   │   ├── folder2
│   │   └── folder3
│   ├── ConsoleLog.txt
│   ├── RunServer.java
│   ├── Server.java
│   ├── ServerProtocols.java
```

## Description

### Server

The server-side code is located in the `Server` directory. It includes the following main components:

- **RunServer.java**: The main class to start the server.
- **Server.java**: The core server functionality handling incoming client connections.
- **ServerProtocols.java**: Defines the protocols used for communication between the server and clients.
- **folders**: Contains subdirectories (`folder1`, `folder2`, `folder3`) with files that the server can serve to clients.
- **ConsoleLog.txt**: Logs generated during server operation.

### Client

The client-side code is located in the `Client` directory. It includes the following main components:

- **RunClient.java**: The main class to start the client.
- **ClientProtocols.java**: Defines the protocols used for communication between the client and server.
- **downloads**: Directory where downloaded files from the server are stored.

## Usage

### Running the Server

1. **Navigate to the Server Directory:**
   ```bash
   cd Server
   ```

2. **Compile the Server Code:**
   ```bash
   javac RunServer.java Server.java ServerProtocols.java
   ```

3. **Run the Server:**
   ```bash
   java RunServer
   ```

### Running the Client

1. **Navigate to the Client Directory:**
   ```bash
   cd Client
   ```

2. **Compile the Client Code:**
   ```bash
   javac RunClient.java ClientProtocols.java
   ```

3. **Run the Client:**
   ```bash
   java RunClient
   ```

### Features

- **Server**:
  - Handles multiple client connections.
  - Serves files from different directories.
  - Logs activities to `ConsoleLog.txt`.

- **Client**:
  - Connects to the server.
  - Requests and downloads files.
  - Stores downloaded files in the `downloads` directory.

### Customization

You can customize the server by adding more directories or files under the `Server/folders` directory. The server is designed to serve any files placed within these directories.

### Logs

Server activities and client requests are logged in the `ConsoleLog.txt` file located in the `Server` directory. This helps in monitoring and debugging the server's performance and activities.

## Contributing

If you wish to contribute to this project, please fork the repository and create a pull request with your changes. Ensure that your code adheres to the coding standards and is well-documented.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.
