Pinecone API for connecting arduino and sensor with Pinecone web server.
############################################################################
Pinecone.cc supply a useful API and android APP for the IOT.
Pinecone API is very simple, which envelops most useful rest API, such as GET POST.
You can describe  your arduino equipment as a remote device,which contains several variables, and a variable is 
made up with items. A varialbe has 2 types, one is continuous, the other is discrete.
With the help of MQTT, you can publish your instant value to Pinecone server,and subscribe a dependent topic. So you can realize bidirectional communication for remote monitor and control.

With this API, you can do these things:
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

//PineconeMegaClass constructor contains two param, one mean the http varablie ,one means for the device code,and it must be unique, 
PineconeMegaClass Pinecone(http,code);
//you can check whether device code is registered, and return value namely device ID.
Pinecone.findDeviceByCode( );
// create device with the code.
Pinecone.createDevice();
//create variable ,first param is variable name, second param is variable type. you can only choose read or write.
Pinecone.createVariable( "Light Switch", "write" );
//create item, first param is item value, second param is the variable which this item belongs to.
createItem( "On", variableSwi )
when publising value to remote sever, you should follws this format. 
{"id":"46","value":"23"}, "id" means the variable id,"value" means the variable's now value.
So the same to callback payload, when the topic has return values ,the format also like {"id":"56","value":"On"}, so you can know variable which id is 56,should change value to "On". In our example, just means the led light should be on, so we give it high ( digitalWrite(lightPin, HIGH)).
##############################################################################

We do a simple example .In this example, it registers a device with the code "0524", and it has 3 variables ,one var is 
temperature , one var is humidity, the other is switch. As you konw ,fist twos are monitor value, last one is control value.
So I publis the two values, and subsrcibe the switch value. 
When you download the mobile APP, you can monitor and control all the device variables. if the switch value in APP is on, the "callback" will get the "on" data, it turns the led on, when it is off, it turns led off.

##############################################################################
We all konw arduino has very small sram for APPlication, thus we choose Mega 2560 firstly.
After a while, we will short the code size and variable nums for arduino UNO and other boards.