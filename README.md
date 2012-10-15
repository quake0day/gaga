# Gaga

![Mou icon](http://foxallaccess.blogs.fox.com/files/2012/05/129.jpg)

## Overview

**gaga**, a brandnew java TCP server + client software for *CSE 589 project*.

### Usage
First you need to compile those bunch of *.java files by running makefile. Type the following command in ./src/echo folder.

	make all
	
Then go back to ./src floder and then type the following command

	java echo.echoer <tcp-port> <udp-port>

Here are some commands that you can choose:

- info
- connect <tcp-port> <udp-port>
- show
- send <conn-id> <message>
- sendto <ip-address> <udp-port> <message>
- disconnect <conn-id>

### Things I have implement

1. All commands are working properly.
2. This program is able to handle error input and other runtime errors.
3. Is able to handle more than 7 outgoing TCP connections. (I'm using Thread pool)
4. Multithreading
5. Block self-connection

### Things I have Not implement

Due to some reason, this program cannot block duplicate connections. (It can block self-connection)

### Team Member
Si Chen

Xuhui Jin

### More.
Part of the code are based on Recatation slides and my reference book 
\<TCP/IP Sockets in Java: practical guide for programmers> 

Author: KENNETH L.CALVERT MICHAEL J. DONAHOO


