
Arduino Client for connecting to Pinecone platform.
############################################################################

PineconeClient is a library to make it easier to interact with pinecone servers from Arduino.

How to use this library?

1.Visit www.pinecone.cc;

2.Download this library from http://www.pinecone.cc/download/pinecone.zip;

3.Use this library to edit example you want to do;

4.Upload example to your arduino board;

5.Turn on power of your arduino board;

6.Download android app from http://www.pinecone.cc/download/pinecone.apk;

7.Register user with android app;

8.Login and activate device(arduino board) with android app;

9.Monitor device(arduino board) with android app.

How to edit example? (Just 4 steps)

1.Customize Client

  Instantiating Client to add your device(arduino board) to pinecone platform by device code, user name and password.
 
  Device code is unique and able to be found from http://www.pinecone.cc. 

2.Initialize Variables

  Extending function to initialize variables to pinecone platform. Now, it supports 3 types of variable:

  1) readable variable - the status of variable can be showed by android app
  2) writable variable - the status of variable can be modified by android app   
  3) Both of all  

  Tips: "read" stands for readable variable
        "write" stands for writable variable
        "read_write" means this variable can be read and write

3.Send Variables

  Extending function to send message from arduino board to android app for updating the status of readable variable to show.

  Message sent to pinecone server is made up of variable id and value. Id is stored in arduino EEPROM and able to get according to correct index begin with 0. For example, if you initialize 2 variables called "temperature" and "LED" respectively, their indexes are assigned to 0 and 1. Value is from digital or analog pin of arduino board and defined by yourself according to your needs. 

4.Receive Variables

  Extending function to receive message from android app to update the status of writable variable in the side of arduino board.

  Message received from pinecone server is made up of variable id and value. You could index of id to decide how to deal with variable in side of arduino board. Value is the item initialized when instantiating client at first. 
