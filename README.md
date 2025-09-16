 #LANMessenger

## Overview

LANMessenger is a Java-based multi-client chat application that allows users connected on the same Local Area Network (LAN) to communicate in real time. The project demonstrates core Java socket programming, multithreading, and Swing GUI design to build an interactive messaging system.

---

## Features

- Multi-threaded server for handling multiple client connections simultaneously.
- Real-time message broadcasting to all clients except the sender.
- Intuitive GUI with distinct styling for sent and received messages.
- Display of message timestamp and sender IP below each message.
- Message delivery confirmation with visual acknowledgment marks.
- Server-side logging of all chat activities for record-keeping.
- Runnable client executable packaged as a JAR for easy deployment.

---

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or later installed.
- Compatible operating system (Windows, Linux, macOS).
- Network connectivity between server and clients on the same LAN.

### Installation

1. Clone this repository:

git clone https://github.com/KhushiSharma56/LANMessenger.git


2. Navigate to the project directory and compile:

cd LANMessenger/src
javac chat/core/.java chat/ui/.java


3. Package the client as a JAR (optional):

jar cvfm ChatClient.jar ../MANIFEST.MF chat/ui/.class chat/core/.class


---

## Running the Application

### Start the Server

Run the server first to listen for clients:

java chat.core.Server


### Start the Client(s)

Run one or more clients specifying the server IP and port:

java -jar ChatClient.jar <server-ip> 5000


Example for localhost testing:

java -jar ChatClient.jar 127.0.0.1 5000


---

## Usage

- Type messages in the input box and hit "Send" or press Enter.
- Your sent messages appear on the right in green bubbles with acknowledgment ticks.
- Received messages appear on the left with timestamp and sender’s IP.
- The interface auto-scrolls to the latest message for convenience.

---

## Contributing

Contributions are welcome! Please open issues or submit pull requests with improvements or bug fixes.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Acknowledgements

- Inspired by classic client-server chat applications.
- Utilizes standard Java libraries for networking and GUI.
- Thanks to open-source communities for tutorials and resources.
