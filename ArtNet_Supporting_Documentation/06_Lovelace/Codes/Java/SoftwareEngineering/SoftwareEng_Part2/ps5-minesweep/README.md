# Software Construction in Java: PS5-Minesweeper assignment
This Project contains code for assignment 4 of **MIT's Software Construction in Java** [SCiJ](http://web.mit.edu/6.005/www/fa16/).
All code examples were written using Java 8, Eclipse, and Git.

## Server
Server can be started from:

1. Eclipse: Right click on server.Server, Run As..., Run Configuration, with example arguments false -f board_file_3
2. Command line: $cd bin
	* java server.Server --s 15
	* java server.Server --f ../board_file_5
	* java server.Server true  --s 20
	
## Client
1. From command line $telnet localhost 4444
2. Supports multiple users	

## Notes
* Step 4 of the dig method is not implemented
* When running PublishedTest the server must be manually started:
   * $java server.Server false -f ../board_file_3 
	