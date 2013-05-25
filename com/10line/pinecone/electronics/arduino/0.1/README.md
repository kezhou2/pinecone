Pinecone API for connecting arduino and sensor with Pinecone web server.
############################################################################
Pinecone.cc supply a useful API and android APP for the IOT.
Pinecone api is very simple, which envelops most useful rest api, such as GET POST.
You can describe  your arduino equipment as a remote device,which contains several variables, and a variable is 
made up with items. A varialbe has 2 types, one is continuous, the other is discrete.
With the help of MQTT, you can publish your instant value to Pinecone server,and subscribe a dependent topic. So 
you can realize bidirectional communication for remote monitor and control.

With this api, you can do these things:
1.Registers your arduino device to Pinecone web server;
2.Registers arduino variable to Pinecone web server;
3.Registers arduino item to Pinecone web server;
4.Save device/variable/item infos into eprom,and can read out when board is power on;
5.connect mqtt server;
6.publish value to Pinecone mqtt server periodically
7.subscribe a dependent topic, and accomplish CALLBACK method,

For the convenience, we supply a full library, such as ajson, httpclient, mqtt, base64,
of course, you can use the lib you like.
##############################################################################




##############################################################################
//PineconeMegaClass constructor contains one param, means the device code,it must be primary key, 
PineconeMegaClass Pinecone(code);
//you can check whether device code is registered, and return value namely device ID.
Pinecone.findDeviceByCode( http);
// create device with the code.
Pinecone.createDevice(http );
//create variable ,secnod param is variable name, third param is variable type. you can only choose read or write.
Pinecone.createVariable( http,"Light Switch", "write" );
//create item, sencond param is item value,third param is the variable which this item belongs to.
createItem(http, "On", variableSwi )
when publising value to remote sever, you should follws this format. 
{"id":"46","value":"23"}, "id" means the variable id,"value" means the variable's now value.
So the same to callback payload, when the topic has return values ,the format also like "On" item.
{"id":"56","value":"On"}, so you can konw "Light Switch" variable id is 56, and its' value is On, just same the value of 
##############################################################################


Automaticly
We do a simple example .In this example, I register a device with the code "33445568", and it has 3 variables ,one var is 
temperature , one var is humidity, the other is swich. As you konw ,fist twos are monitor value, last one is control value.
So I publis the two values, and subsrcibe the swich value. when switch value is on, "callback" turn the led on, when it is off, "callback" 
turn it off.

upload your arduino sensor values ,for example,temperature/humidity, to Pinecone remote server.
We all konw arduino has very small sram for application, thus we choose Mega 2560 firstly.
After a while, we will short the code size and variable nums for arduino UNO and other boards.