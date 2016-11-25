# webcrawler
An example of a simple crawler made for one page only

TO BUILD

It is a basic console application. You can build either with javac or using Eclipse. 

To RUN

It has minimum 2, maximum 3 arguments. Arguments are: 
- website
- level
- file path (optional)

There are no switches, just ordered arguments. Also almost no checks - so it could blow up if args are not correct. Examples would be
- java -cp C:\Users\alen\workspace\webcrawler\bin alen.test.webcrawler.WebCrawler http://wiprodigital.com 2 
- java -cp C:\Users\alen\workspace\webcrawler\bin alen.test.webcrawler.WebCrawler http://wiprodigital.com 2 E:\\\webmap.txt.


COMMENTS

This is a VERY simple and quick hack. No external libraries are used, parsing is basic and trivial. No multithreading. It just uses a recursion to inspect any webpage in depth. It is tested only on the example webpage up to level 3, so on any other web it could blow up in a hundred ways. 
It can print out web map to console and to a specified text file.

To properly do it I would consider: 
- using one of the accessible open source libraries for HTML parsing (such as JSoup) taking into consideration license, performance, bus factor, etc.
- do it by using multithreading so we can open URL's using multiple connections at once. In this case I would put a default cap with an optional argument so we don't get the torrent effect and kill the network
- In a more advanced scenario we could add links filters as optional regex arguments, provide interfaces for the user to implement his own, provide more output scenarios (database connectors, formatting) etc.

There are also other possibilities, but those 3 would be basic ones to start from the existing code.
