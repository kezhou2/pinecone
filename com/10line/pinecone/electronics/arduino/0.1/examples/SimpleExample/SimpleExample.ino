/*

 Pinecone Simple Example
 
 This is an example to illustrate how to transport data between server and client 
 using LM35 Temperature Sensor and LED Module.
 
 created 4 Mar 2013
 by Bill Xue, Apriliner Wang
 
 */

#include <SPI.h>
#include <Ethernet.h>
#include <EEPROM.h>
#include <PineconeClient.h>

PineconeClient client( "7f234c91-c113-417c-aa92-62ec6d91dcc0", "admin", "admin", receiveMessage );

void setup( ) {
  // LED Module is connected to digital pin 13
  pinMode( 13, OUTPUT ); 
  client.initialize( initializeVariable );
}

void loop( ) {
  client.doLoop( sendVariable );
}

void initializeVariable( String device ) {
  String variable = client.createVariable( "LED Switch", "write", device );
  client.createItem( "On", variable );
  client.createItem( "Off", variable );
  client.createVariable( "Temperature", "read", device );
}

void receiveMessage( char* topic, byte* payload, unsigned int length ) {
  String id = client.receiveVariableId( String( ( char* ) payload ), length );
  String value = client.receiveVariableValue( String( ( char* ) payload ), length );
  if ( client.indexOf( id.toInt( ) ) == 0 ) {
    if ( value.equals( "On" ) ) {
      digitalWrite( 13, HIGH ); 
    } else if ( value.equals( "Off" ) ) {
      digitalWrite( 13, LOW ); 
    }
  }
}

void sendVariable( ) {
  // LM35 Temperature Sensor is connected to analog pin 0
  client.sendMessage( String( client.get( 1 ) ), String( ( 125 * analogRead( 0 ) ) >> 8 ) );
}
