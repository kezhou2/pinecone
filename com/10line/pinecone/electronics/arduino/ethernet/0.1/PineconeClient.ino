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
char host[ ] = "pinecone-service.cloudfoundry.com";

// Define unique code of this arduino device
char code[] = "123456";

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

// Make HTTP GET request to find device by code
void findDeviceByCode( ) {
  String url = "/rest/m/device/search/codes?code=";
  url += String( code ); char path[ url.length( ) + 1 ];
  url.toCharArray( path, url.length( ) + 1 );
  makeGetRequest( path );
}

// Make HTTP POST request to create device
void createDevice( ) {
  makePostJsonRequest( "/rest/device", "" );
}

// Make HTTP GET request to REST service
void makeGetRequest( char* path ) {
  http.beginRequest( );
  http.startRequest( host, 80, path, "GET", NULL );
  http.sendBasicAuth( "admin", "admin" );
  http.endRequest( );
  http.skipResponseHeaders( );
  http.stop( );
}

// Make HTTP POST Json request to REST service
void makePostJsonRequest( char* path, String content ) {
  http.beginRequest( );
  http.startRequest( host, 80, path, "POST", NULL );
  http.sendBasicAuth( "admin", "admin" );
  http.sendHeader( "Content-Type", "application/json" );
  http.sendHeader( "Content-Length", content.length( ) );
  http.endRequest( );
  http.print( content );
  http.println( );
  http.skipResponseHeaders( );
  http.stop( );
}
