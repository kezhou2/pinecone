/*

 Pinecone Web Client
 
 This sketch connects to pinecone platform (http://www.pinecone.cc)
 using an Arduino Wiznet Ethernet shield.
 
 created 4 Mar 2013
 by Bill Xue, Apriliner Wang
 
 */

#include <SPI.h>
#include <Ethernet.h>
#include <HttpClient.h>

// Enter a MAC address for your controller below.
// Newer Ethernet shields have a MAC address printed on a sticker on the shield
byte mac[ ] = {  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02 };

// Enter server host we want to connect to
const char host[ ] = "pinecone-service.cloudfoundry.com";

// Define unique code of this arduino device
const char code[ ] = "123456";

// Define HTTP client for connecting to server
EthernetClient client;
HttpClient http( client );

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
  findDeviceByCode( );
}

void loop( ) {
  
}

// Get Id from HTTP response
String getId( ) {
  String id = NULL;
  if ( http.contentLength() > 0 ) {
    while ( http.available( ) ) {
      byte data = http.read( );
      if ( data >= 48 && data <= 57 ) {
        id += ( char ) data;
      }
    }
  }
  return id;
}

// Make HTTP GET request to find device by code
void findDeviceByCode( ) {
  char path[ 33 + String( code ).length( ) ];
  strcpy( path, "/rest/m/device/search/codes?code=" );
  strcat( path, code ); 
  String id = makeGetRequest( path );
  if ( id != NULL ) {
    Serial.println(id);
  } else {
    createDevice( );
  }
}

// Make HTTP POST request to create device
void createDevice( ) {
  char content[ 15 + String( code ).length( ) ];
  strcpy( content, "{\"code\":\"" );
  strcat( content, code );
  strcat( content, "\"}" );
  String id = makePostRequest( "/rest/device", "application/json", content );
}

// Make HTTP POST request to create variable
void createVariable( ) {
  char content[ 15 ];
  makePostRequest( "/rest/variable", "application/json", content );
}

// Make HTTP GET request to REST service
String makeGetRequest( char* path ) {
  http.beginRequest( );
  http.startRequest( host, 80, path, "GET", NULL );
  http.sendBasicAuth( "admin", "admin" );
  http.endRequest( );
  http.skipResponseHeaders( );
  String id = getId( );
  http.stop( );
  return id;
}

// Make HTTP POST request to REST service
String makePostRequest( char* path, char* type, char* content ) {
  http.beginRequest( );
  http.startRequest( host, 80, path, "POST", NULL );
  http.sendBasicAuth( "admin", "admin" );
  http.sendHeader( "Content-Type", type );
  http.sendHeader( "Content-Length", String( content ).length( ) );
  http.endRequest( );
  http.print( content );
  http.println( );
  http.skipResponseHeaders( );
  String id = getId( );
  http.stop( );
  return id;
}
