/*

 Pinecone Ethernet Example
 
 This sketch connects to pinecone platform (http://www.pinecone.cc)
 using an Arduino Wiznet Ethernet shield.
 
 created 4 Mar 2013
 by Bill Xue, Apriliner Wang
 
 */

#include <SPI.h>
#include <Ethernet.h>
#include <HttpClient.h>
#include <PubSubClient.h>

// Enter a MAC address for your controller below
byte mac[ ] = {  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02 };

// Define unique code of this arduino device
const char code[ ] = "33445566";

// Define user name and password for server authorization
char username[ ] = "admin";
char password[ ] = "admin";

// Define REST server host we want to connect to
const char host[ ] = "pinecone-service.cloudfoundry.com";

// Define HTTP client for connecting to REST server
EthernetClient httpClient;
HttpClient http( httpClient );

// Define MQTT server IP we want to connect to
byte ip[ ] = { 198, 41, 30, 241 };

// Define MQTT client for connecting to MQTT server
EthernetClient mqttClient;
PubSubClient mqtt( ip, 1883, callback, mqttClient );

void setup( ) {
  // Open serial communications and wait for port to open
  Serial.begin( 9600 ); 
  // Start the Ethernet connection
  while ( Ethernet.begin( mac ) != 1 ) {
    Serial.println( "Error getting IP address, trying again..." );
    delay( 15000 );
  }
  // Give the Ethernet shield a second to initialize
  delay( 1000 );
  // Check device whether existing or not
  if ( findDeviceByCode( ) != NULL ) {
    Serial.println( "Device is existed" );
  } else {
    String device = createDevice( );
    String variable = createVariable( "Light Switch", "write", device );
    createItem( "On", variable );
    createItem( "Off", variable );
    createVariable( "Temperature", "read", device );
    createVariable( "Humidity", "read", device );
    Serial.println( "Device is created" );
  }
}

void loop( ) {
  if ( mqtt.loop( ) ) {
    if ( mqtt.publish( "pinecone@device.", "hello world" ) ) {
      Serial.println( "Message is published" );
    }
  } else {
    if ( mqtt.connect( "pinecone@device.", username, password ) ) {
      if ( mqtt.subscribe( "pinecone@device." ) ) {
        Serial.println( "Topic is subscribed" );
      }
    }
  }
}

// Handle MQTT message arrived from server
void callback( char* topic, byte* payload, unsigned int length ) {
  Serial.println( "Message is arrived" );
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
  Serial.print( "Variable is created -> " );
  Serial.println( response );
  char variableId[ response.length( ) + 1 ];
  response.toCharArray( variableId, response.length( ) + 1 );
  char path[ 22 + response.length( ) ];
  strcpy( path, "/rest/variable/" );
  strcat( path, variableId );
  strcat( path, "/device" );
  Serial.print( "Path ->" );
  Serial.println( path );
  char deviceId[ device.length( ) + 1 ];
  device.toCharArray( deviceId, device.length( ) + 1 );
  char uri[ 20 + String( host ).length( ) + device.length( ) ];
  strcpy( uri, "http://" );
  strcat( uri, host );
  strcat( uri, "/rest/device/" );
  strcat( uri, deviceId );
  Serial.print( "URI ->" );
  Serial.println( uri );
  makePostRequest( path, "text/uri-list", uri );
  Serial.println( "Variable is updated" );
  return response;
}

// Make HTTP POST request to create item
void createItem( char* value, String variable ) {
  char content[ 16 + String( value ).length( ) ];
  strcpy( content, "{\"value\":\"" );
  strcat( content, value );
  strcat( content, "\"}" );
  String response = makePostRequest( "/rest/m/item", "application/json", content );
  Serial.print( "Item is created -> " );
  Serial.println( response );
  char itemId[ response.length( ) + 1 ];
  response.toCharArray( itemId, response.length( ) + 1 );
  char path[ 20 + response.length( ) ];
  strcpy( path, "/rest/item/" );
  strcat( path, itemId );
  strcat( path, "/variable" );
  Serial.print( "Path ->" );
  Serial.println( path );
  char variableId[ variable.length( ) + 1 ];
  variable.toCharArray( variableId, variable.length( ) + 1 );
  char uri[ 22 + String( host ).length( ) + variable.length( ) ];
  strcpy( uri, "http://" );
  strcat( uri, host );
  strcat( uri, "/rest/variable/" );
  strcat( uri, variableId );
  Serial.print( "URI ->" );
  Serial.println( uri );
  makePostRequest( path, "text/uri-list", uri );
  Serial.println( "Item is updated" );
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
