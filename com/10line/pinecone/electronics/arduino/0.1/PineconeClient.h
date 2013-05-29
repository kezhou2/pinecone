/*

 PineconeClient.h

 This sketch connects to pinecone platform (http://www.pinecone.cc)
 using an Arduino Wiznet Ethernet shield.

 created 4 Mar 2013
 by Bill Xue, Apriliner Wang

 */

#ifndef PineconeClient_h
#define PineconeClient_h

#include <Arduino.h>
#include <Ethernet.h>
#include <EEPROM.h>
#include <HttpClient.h>
#include <PubSubClient.h>

class PineconeClient : HttpClient, PubSubClient {

public:

	// Constructor
	PineconeClient( char* code, char* username, char* password,
			void ( *receiveMessage ) ( char*, uint8_t*, unsigned int ) );

	// Initialize Client
	void initialize( void ( *initializeVariable ) ( String ) );

	// Make HTTP POST request to create variable
	String createVariable( char* name, char* type, String device );

	// Make HTTP POST request to create item
	void createItem( char* value, String variable );

	// Loop to execute MQTT logic
	void doLoop( void ( *sendVariable ) ( void ) );

	// Send MQTT message to server
	void sendMessage( String id, String value );

	// Receive variable id from MQTT message
	String receiveVariableId( String message, unsigned int length );

	// Receive variable value from MQTT message
	String receiveVariableValue( String message, unsigned int length );

	// Get data from EEPROM
	long get( byte index );

	// Index of data in EEPROM
	byte indexOf( long data );

private:

	// Enter a MAC address for your controller
	byte *mac;

	// Define Ethernet client for connecting to server
	EthernetClient client;

	// Define unique code of this client (arduino device)
	char *code;

	// Define user name and password for server authorization
	char *username;
	char *password;

	// Define server we want to connect to
	char *host;

	// Make HTTP GET request to find device by code
	String findDeviceByCode( );

	// Make HTTP POST request to create device
	String createDevice( );

	// Make HTTP GET request to REST service
	String makeGetRequest( char* path );

	// Make HTTP POST request to REST service
	String makePostRequest( char* path, char* type, char* content );

	// Fetch data from HTTP response
	String fetchDataFromResponse( );

	// Put data to EEPROM
	void put( long data );

	// Set data to EEPROM
	void set( byte index, long data );

	// Clean all data from EEPROM
	void cleanAll( );

	// Clean data from EEPROM
	void clean( byte index );

	// Get MQTT topic
	String getTopic( );

	// Subscribe MQTT topic
	void subscribeTopic( );

	// Publish to MQTT topic
	void publishToTopic( char* message );

};

#endif
