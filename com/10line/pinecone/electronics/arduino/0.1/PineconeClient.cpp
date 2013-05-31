/*

 PineconeClient.cpp

 This sketch connects to pinecone platform (http://www.pinecone.cc)
 using an Arduino Wiznet Ethernet shield.

 created 4 Mar 2013
 by Bill Xue, Apriliner Wang

 */

#include "PineconeClient.h"

// Constructor
PineconeClient::PineconeClient( char* code, char* username, char* password,
	void ( *receiveMessage ) ( char*, uint8_t*, unsigned int ) ) :
	HttpClient( client ), PubSubClient( host, 1883, receiveMessage, client ) {
//	this->host = "www.pinecone.cc";
	this->host = "pinecone-service.cloudfoundry.com";
	this->code = code;
	this->username = username;
	this->password = password;
}

// Initialize Client
void PineconeClient::initialize( void ( *initializeVariable ) ( String ) ) {
  int i = 0;
  mac = ( byte* ) malloc( 6 * sizeof( byte ) );
  mac[ i++ ] = 0x00; mac[ i++ ] = 0xAA;
  mac[ i++ ] = 0xBB; mac[ i++ ] = 0xCC;
  mac[ i++ ] = 0xDE; mac[ i++ ] = 0x02;
  while ( Ethernet.begin( mac ) != 1 ) {
	  delay( 15000 );
  }
  delay( 1000 );
  if ( findDeviceByCode( ) == NULL ) {
	  cleanAll( ); initializeVariable( createDevice( ) );
  }
}

// Get MQTT topic
String PineconeClient::getTopic( ) {
  char topic[ 16 + String( code ).length( ) ];
  strcpy( topic, "pinecone@device." );
  strcat( topic, code );
  return String( topic );
}

// Subscribe MQTT topic
void PineconeClient::subscribeTopic( ) {
  if ( connectTo( code, username, password ) ) {
    String topic = getTopic( );
	char topicBuffer[ topic.length( ) + 1 ];
	topic.toCharArray( topicBuffer, topic.length( ) + 1 );
	if ( subscribe( topicBuffer ) ) {}
  }
}

// Publish to MQTT topic
void PineconeClient::publishToTopic( char* message ) {
  String topic = getTopic( );
  char topicBuffer[ topic.length( ) + 1 ];
  topic.toCharArray( topicBuffer, topic.length( ) + 1 );
  if ( publish( topicBuffer, message ) ) {}
}

// Send MQTT message to server
void PineconeClient::sendMessage( String id, String value ) {
  char messageId[ id.length( ) + 1 ];
  id.toCharArray( messageId, id.length( ) + 1 );
  char messageValue[ value.length( ) + 1 ];
  value.toCharArray( messageValue, value.length( ) + 1 );
  char message[ 28 + id.length( ) + value.length( ) ];
  strcpy( message, "{\"id\":\"" );
  strcat( message, messageId );
  strcat( message, "\",\"value\":\"");
  strcat( message, messageValue );
  strcat( message, "\"}" );
  publishToTopic( message );
}

// Receive variable id from MQTT message
String PineconeClient::receiveVariableId( String message, unsigned int length ) {
  return message.substring( message.indexOf( ':' ) + 2, message.indexOf( ',' ) - 1 );
}

// Receive variable value from MQTT message
String PineconeClient::receiveVariableValue( String message, unsigned int length ) {
  return message.substring( message.lastIndexOf( ':' ) + 2, length - 2 );
}

// Loop to execute MQTT logic
void PineconeClient::doLoop( void ( *sendVariable ) ( void ) ) {
  delay( 500 );
  if ( loop( ) ) {
	sendVariable( );
  } else {
    subscribeTopic( );
  }
}

// Make HTTP GET request to find device by code
String PineconeClient::findDeviceByCode( ) {
  char path[ 33 + String( code ).length( ) ];
  strcpy( path, "/rest/m/device/search/codes?code=" );
  strcat( path, code );
  return makeGetRequest( path );
}

// Make HTTP POST request to create device
String PineconeClient::createDevice( ) {
  char content[ 15 + String( code ).length( ) ];
  strcpy( content, "{\"code\":\"" );
  strcat( content, code );
  strcat( content, "\"}" );
  return makePostRequest( "/rest/m/device", "application/json", content );
}

// Make HTTP POST request to create variable
String PineconeClient::createVariable( char* name, char* type, String device ) {
  char content[ 29 + String( name ).length( ) + String( type ).length( ) ];
  strcpy( content, "{\"name\":\"" );
  strcat( content, name );
  strcat( content, "\",\"type\":\"" );
  strcat( content, type );
  strcat( content, "\"}" );
  String response = makePostRequest( "/rest/m/variable", "application/json", content );
  put( response.toInt( ) );
  char variableId[ response.length( ) + 1 ];
  response.toCharArray( variableId, response.length( ) + 1 );
  char path[ 22 + response.length( ) ];
  strcpy( path, "/rest/variable/" );
  strcat( path, variableId );
  strcat( path, "/device" );
  char deviceId[ device.length( ) + 1 ];
  device.toCharArray( deviceId, device.length( ) + 1 );
  char uri[ 20 + String( host ).length( ) + device.length( ) ];
  strcpy( uri, "http://" );
  strcat( uri, host );
  strcat( uri, "/rest/device/" );
  strcat( uri, deviceId );
  makePostRequest( path, "text/uri-list", uri );
  return response;
}

// Make HTTP POST request to create item
void PineconeClient::createItem( char* value, String variable ) {
  char content[ 16 + String( value ).length( ) ];
  strcpy( content, "{\"value\":\"" );
  strcat( content, value );
  strcat( content, "\"}" );
  String response = makePostRequest( "/rest/m/item", "application/json", content );
  char itemId[ response.length( ) + 1 ];
  response.toCharArray( itemId, response.length( ) + 1 );
  char path[ 20 + response.length( ) ];
  strcpy( path, "/rest/item/" );
  strcat( path, itemId );
  strcat( path, "/variable" );
  char variableId[ variable.length( ) + 1 ];
  variable.toCharArray( variableId, variable.length( ) + 1 );
  char uri[ 22 + String( host ).length( ) + variable.length( ) ];
  strcpy( uri, "http://" );
  strcat( uri, host );
  strcat( uri, "/rest/variable/" );
  strcat( uri, variableId );
  makePostRequest( path, "text/uri-list", uri );
}

// Make HTTP GET request to REST service
String PineconeClient::makeGetRequest( char* path ) {
  beginRequest( );
  startRequest( host, 80, path, "GET", NULL );
  sendBasicAuth( username, password );
  endRequest( );
  skipResponseHeaders( );
  String response = fetchDataFromResponse( );
  stop( );
  return response;
}

// Make HTTP POST request to REST service
String PineconeClient::makePostRequest( char* path, char* type, char* content ) {
  beginRequest( );
  startRequest( host, 80, path, "POST", NULL );
  sendBasicAuth( username, password );
  sendHeader( "Content-Type", type );
  sendHeader( "Content-Length", String( content ).length( ) );
  endRequest( );
  print( content );
  println( );
  skipResponseHeaders( );
  String response = fetchDataFromResponse( );
  stop( );
  return response;
}

// Fetch data from HTTP response
String PineconeClient::fetchDataFromResponse( ) {
  String response = NULL;
  if ( contentLength() > 0 ) {
    while ( available( ) ) {
      byte data = read( );
      if ( data >= 48 && data <= 57 ) {
        response += ( char ) data;
      }
    }
  }
  return response;
}

// Put data to EEPROM
void PineconeClient::put( long data ) {
  byte index = 0; long result = get( index );
  while( result != -1 ) { result = get( ++index ); }
  set( index, data );
}

// Set data to EEPROM
void PineconeClient::set( byte index, long data ) {
  int buffer[ 4 ] = { 0, 0, 0, 0 };
  buffer[ 0 ] = ( unsigned char ) ( ( 0xff000000 & data ) >> 24 );
  buffer[ 1 ] = ( unsigned char ) ( ( 0xff0000 & data ) >> 16 );
  buffer[ 2 ] = ( unsigned char ) ( ( 0xff00 & data ) >> 8 );
  buffer[ 3 ] = ( unsigned char ) ( 0xff & data );
  EEPROM.write( index * 4 + 0, buffer[ 0 ] );
  EEPROM.write( index * 4 + 1, buffer[ 1 ] );
  EEPROM.write( index * 4 + 2, buffer[ 2 ] );
  EEPROM.write( index * 4 + 3, buffer[ 3 ] );
}

// Index of data in EEPROM
byte PineconeClient::indexOf( long data ) {
  byte index = 0;
  long result = get( index );
  while( result != -1 ) {
    if ( result == data ) return index;
    else result = get( ++index );
  }
  return -1;
}

// Clean all data from EEPROM
void PineconeClient::cleanAll( ) {
  byte index = 0;
  while( get( index ) != -1 ) {
    clean( index ); index++;
  }
}

// Clean data from EEPROM
void PineconeClient::clean( byte index ) {
  set( index, -1 );
}

// Get data from EEPROM
long PineconeClient::get( byte index ) {
  byte data[ 4 ];
  data[ 0 ] = EEPROM.read( index * 4 );
  data[ 1 ] = EEPROM.read( index * 4 + 1 );
  data[ 2 ] = EEPROM.read( index * 4 + 2 );
  data[ 3 ] = EEPROM.read( index * 4 + 3 );
  return ( ( long ) ( data[ 0 ] ) << 24 ) +
         ( ( long ) ( data[ 1 ] ) << 16 ) +
         ( ( long ) ( data[ 2 ] ) << 8 ) +
         ( ( long ) ( data[ 3 ] ) );
}
