/*

 Pinecone Ethernet Example
 
 This sketch connects to pinecone platform (http://www.pinecone.cc)
 using an Arduino Wiznet Ethernet shield.
 
 created 4 Mar 2013
 by Bill Xue, Apriliner Wang
 
 */
#include <EEPROM.h>
#include <EEPROMPINECONE.h>
#include <SPI.h>
#include <Ethernet.h>
#include <HttpClient.h>
#include <PubSubClient.h>
#include <MemoryFree.h>
#include <dht11Mega.h>
#include <aJSON.h>

// Enter a MAC address for your controller below
byte mac[ ] = {  
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02 };

// Define unique code of this arduino device
char code[]="33445568";
char * MQTTCLIENTID = "pinecone@mqtt.33445568";
char * TOPIC_NAME = "pinecone@device.33445568";
// Define user name and password for server authorization
char username[ ] = "admin";
char password[ ] = "admin";

// Define REST server host we want to connect to
const char host[ ] = "pinecone-service.cloudfoundry.com";

// Define HTTP client for connecting to REST server
EthernetClient httpClient;
HttpClient http( httpClient );
// publish Interval times
int Interval = 10000;
// Define MQTT client for connecting to MQTT server
// Define MQTT server IP we want to connect to
byte server[ ] = { 
  198, 41, 30, 241 };
EthernetClient mqttClient;
PubSubClient mqtt( server, 1883, callback, mqttClient );
/////////////////////////////////////////////////////
DHT11 dht(54);
/////////////////////////////////////////////////////
String device ;
String variableSwi;
String itemOn ;
String itemOff ;
String variableTem ;
String variableHum ;
////////////////////////////////////////////////////
void setup( ) {
  // Open serial communications and wait for port to open
  Serial.begin( 9600 ); 
  // Start the Ethernet connection
  if (Ethernet.begin(mac)) {
    Serial.println("Ethernet  DHCP connection OK!");
  } 
  else{
    exit(-1); 
  }
  // Give the Ethernet shield a second to initialize
  delay( 1000 );
  // Check device whether existing or not
  if ( findDeviceByCode( ) != NULL ) {
    Serial.println( "Device is existed" );
    // save 
    device = (String)EEPROMPinecone.readPinecone(0);
    variableSwi = (String) EEPROMPinecone.readPinecone(1);
    itemOn = (String) EEPROMPinecone.readPinecone(2);
    itemOff = (String)EEPROMPinecone.readPinecone(3);
    variableHum = (String)EEPROMPinecone.readPinecone(4);
    variableTem = (String)EEPROMPinecone.readPinecone(5);
    Serial.println( device );
    Serial.println( variableSwi );
    Serial.println( itemOn);
    Serial.println( itemOff );
    Serial.println( variableTem );
    Serial.println( variableHum );
  } 
  else {
    String device = createDevice( );
    Serial.print(F(" device "));
    Serial.println( device );
    String variableSwi = createVariable( "Light Switch", "write", device );
    Serial.print(F(" variableSwi "));
    Serial.println( variableSwi );
    itemOn = createItem( "On", variableSwi );
    Serial.print(F(" itemOn "));
    Serial.println( itemOn);
    itemOff = createItem( "Off", variableSwi );
    Serial.print(F(" itemOff "));
    Serial.println( itemOff );
    variableTem = createVariable( "Temperature", "read", device );
    Serial.print(F(" variableTem "));
    Serial.println( variableTem );
    variableHum = createVariable( "Humidity", "read", device );
    Serial.print(F(" variableHum "));
    Serial.println( variableHum );
    Serial.println( "Device is created" );

    // save 
    EEPROMPinecone.savePinecone(0,device.toInt());
    EEPROMPinecone.savePinecone(1,variableSwi.toInt());
    EEPROMPinecone.savePinecone(2,itemOn.toInt());
    EEPROMPinecone.savePinecone(3,itemOff.toInt());
    EEPROMPinecone.savePinecone(4,variableHum.toInt());
    EEPROMPinecone.savePinecone(5,variableTem.toInt());
  }

  if ( mqtt.connect( MQTTCLIENTID) ) {
    Serial.println("Connected to MQTT Server...");
    if ( mqtt.subscribe(TOPIC_NAME) ) {
      Serial.println( "Topic is subscribed" );
    }
  }
}

void loop( ) {

  if ((millis() % Interval) == 0) {
    dht.read();
    Serial.print("temp: ");
    Serial.print(dht.temperature);
    Serial.print("C");
    Serial.print("    humidity: ");
    Serial.print(dht.humidity);
    Serial.println("%");
    /////////////////////////
    Serial.print("free:");
    Serial.println(freeMemory());
    aJsonObject *root =aJson.createObject();
    aJsonObject * tem = aJson.createItem(variableTem.getBuffer());
    aJson.addItemToObject(root,"id",tem);
    aJsonObject * temVar = aJson.createItem(((String)dht.temperature).getBuffer());
    aJson.addItemToObject(root,"value",temVar);
    char * data1=aJson.print(root);
    delay(200);
    Serial.println(data1);
    // "{\"id\":\"46\",\"value\":\"",23;
    if(mqtt.connected()){
      if ( mqtt.publish( TOPIC_NAME, data1 ) ) {
        Serial.println( "data1 is published" );
      }
    }
    else{
      if ( mqtt.connect( MQTTCLIENTID) ) {
        Serial.println("Reconnected to MQTT Server...");
        if ( mqtt.subscribe(TOPIC_NAME) ) {
          Serial.println( "Topic is subscribed" );
        }
      }
    }
    //    aJson.deleteItem(tem);
    //    aJson.deleteItem(temVar);
    free(data1);    
    aJson.deleteItem(root);
    ////////////////////
    aJsonObject *root2 =aJson.createObject();
    aJsonObject * hum = aJson.createItem(variableHum.getBuffer());
    aJson.addItemToObject(root2,"id",hum);
    aJsonObject * humVar = aJson.createItem(((String)dht.humidity).getBuffer());
    aJson.addItemToObject(root2,"value",humVar);
    char * data2=aJson.print(root2);
    delay(200);
    Serial.println(data2);
    // "{\"id\":\"46\",\"value\":\"",23;
    if(mqtt.connected()){
      if ( mqtt.publish( TOPIC_NAME, data2 ) ) {
        Serial.println( "data2 is published" );
      }
    }
    else{
      if ( mqtt.connect( MQTTCLIENTID) ) {
        Serial.println("Reconnected to MQTT Server...");
        if ( mqtt.subscribe(TOPIC_NAME) ) {
          Serial.println( "Topic is subscribed" );
        }
      }
    }
    //    aJson.deleteItem(hum);
    //    aJson.deleteItem(humVar);
    free(data2);    
    aJson.deleteItem(root2);
    ////////////////////////////////////////
  } 
  mqtt.loop();
}
// Handle MQTT message arrived from server
void callback( char* topic, byte* payload, unsigned int length ) {
  // handle message arrived
  Serial.println( F("arrived" ));
  aJsonObject* root = aJson.parse((char*)payload);
  if(NULL !=root){
    aJsonObject* id = aJson.getObjectItem(root, "id");
    if(id !=NULL ){
      char * idstr =id->valuestring;
      Serial.println(idstr);
      if(variableSwi.equals(idstr)){
        aJsonObject* value = aJson.getObjectItem(root, "value");
        if(value !=NULL ){
          char * valStr =value->valuestring;
          Serial.println(valStr);
          if(strcmp(valStr,"on")==0){
            Serial.println("on!!");
          }
          else{
            Serial.println("off!!");
          }
        }
        else{
          Serial.println("value  NULL");
        }
      }
      else{
        Serial.println("ignore");
      }
    }
  }
  aJson.deleteItem(root);
}
// Make HTTP GET request to find device by code
String findDeviceByCode( ) {
  char path[ 33 + String( code ).length( ) ];
  strcpy( path, "/rest/m/device/search/codes?code=" );
  strcat( path, code ); 
  return makeGetRequest( path );
}

// Make HTTP POST request to create device
String createDevice( ) {
  char content[ 15 + String( code ).length( ) ];
  strcpy( content, "{\"code\":\"" );
  strcat( content, code );
  strcat( content, "\"}" );
  return makePostRequest( "/rest/m/device", "application/json", content );
}

// Make HTTP POST request to create variable
String createVariable( char* name, char* type, String device ) {
  char content[ 29 + String( name ).length( ) + String( type ).length( ) ];
  strcpy( content, "{\"name\":\"" );
  strcat( content, name );
  strcat( content, "\",\"type\":\"" );
  strcat( content, type );
  strcat( content, "\"}" );
  String response = makePostRequest( "/rest/m/variable", "application/json", content );
  //Serial.print( "Variable is created -> " );
  //Serial.println( response );
  char variableId[ response.length( ) + 1 ];
  response.toCharArray( variableId, response.length( ) + 1 );
  char path[ 22 + response.length( ) ];
  strcpy( path, "/rest/variable/" );
  strcat( path, variableId );
  strcat( path, "/device" );
  //Serial.print( "Path ->" );
  //Serial.println( path );
  char deviceId[ device.length( ) + 1 ];
  device.toCharArray( deviceId, device.length( ) + 1 );
  char uri[ 20 + String( host ).length( ) + device.length( ) ];
  strcpy( uri, "http://" );
  strcat( uri, host );
  strcat( uri, "/rest/device/" );
  strcat( uri, deviceId );
  //Serial.print( "URI ->" );
  //Serial.println( uri );
  makePostRequest( path, "text/uri-list", uri );
  //Serial.println( "Variable is updated" );
  return response;
}

// Make HTTP POST request to create item
String createItem( char* value, String variable ) {
  char content[ 16 + String( value ).length( ) ];
  strcpy( content, "{\"value\":\"" );
  strcat( content, value );
  strcat( content, "\"}" );
  String response = makePostRequest( "/rest/m/item", "application/json", content );
  //Serial.print( "Item is created -> " );
  //Serial.println( response );
  char itemId[ response.length( ) + 1 ];
  response.toCharArray( itemId, response.length( ) + 1 );
  char path[ 20 + response.length( ) ];
  strcpy( path, "/rest/item/" );
  strcat( path, itemId );
  strcat( path, "/variable" );
  //Serial.print( "Path ->" );
  //Serial.println( path );
  char variableId[ variable.length( ) + 1 ];
  variable.toCharArray( variableId, variable.length( ) + 1 );
  char uri[ 22 + String( host ).length( ) + variable.length( ) ];
  strcpy( uri, "http://" );
  strcat( uri, host );
  strcat( uri, "/rest/variable/" );
  strcat( uri, variableId );
  //Serial.print( "URI ->" );
  //Serial.println( uri );
  makePostRequest( path, "text/uri-list", uri );
  //Serial.println( "Item is updated" );
  return response;
}

// Make HTTP GET request to REST service
String makeGetRequest( char* path ) {
  http.beginRequest( );
  http.startRequest( host, 80, path, "GET", NULL );
  http.sendBasicAuth( username, password );
  http.endRequest( );
  http.skipResponseHeaders( );
  String response = fetchDataFromResponse( );
  http.stop( );
  return response;
}

// Make HTTP POST request to REST service
String makePostRequest( char* path, char* type, char* content ) {
  http.beginRequest( );
  http.startRequest( host, 80, path, "POST", NULL );
  http.sendBasicAuth( username, password );
  http.sendHeader( "Content-Type", type );
  http.sendHeader( "Content-Length", String( content ).length( ) );
  http.endRequest( );
  http.print( content );
  http.println( );
  http.skipResponseHeaders( );
  String response = fetchDataFromResponse( );
  http.stop( );
  return response;
}

// Fetch data from HTTP response
String fetchDataFromResponse( ) {
  String response = NULL;
  if ( http.contentLength() > 0 ) {
    while ( http.available( ) ) {
      byte data = http.read( );
      if ( data >= 48 && data <= 57 ) {
        response += ( char ) data;
      }
    }
  }
  return response;
}













