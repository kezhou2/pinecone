#include <EEPROM.h>
#include <SPI.h>
#include <EthernetClient.h>
#include <EEPROMPINECONE.h>
#include <HttpClient.h>
#include <pineconeMega.h>
#include <PubSubClient.h>
#include <MemoryFree.h>
#include <dht11Mega.h>
#include <aJSON.h>
//////////////////////////////////////////////////////
//this is your device code
char code[]="0524";
//mqtt unique id for sending and getting msg
char * MQTTCLIENTID = "pinecone@mqtt.0524";
//mqtt topic name define rule pinecone@device.XXXXX, XXXX is device code
char * TOPIC_NAME = "pinecone@device.0524";
//////////////////////////////////////////////////////
ethernet mac address
byte mac[ ] = {
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02 };
// publish Interval times
int Interval = 3000;
// Define HTTP client for connecting to REST server
EthernetClient httpClient;
HttpClient http( httpClient );
//dht variable
DHT11 dht(54);
//pinecone class just used for mega
PineconeMegaClass pinecone(http ,code);
//////////////////////////////////////////////////////
// light switch is connected with Digit pin 1
byte lightPin = 1;
//////////////////////////////////////////////////////
// device id
String device ;
// variable switch id
String variableSwi;
// item on id
String itemOn ;
// item off id
String itemOff ;
// variable tem id
String variableTem ;
// variable hum id
String variableHum ;
////////////////////////////////////////////////////
// Define mqtt server host we want to connect to
byte server[] = {
  198,41,30,241};
// Define MQTT client for connecting to MQTT server
EthernetClient mqttClient;
PubSubClient mqtt(server,1883,callback,mqttClient);
/////////////////////////////////////////////////////////
void setup() {
  //set ligth switch pin mode
  pinMode(lightPin, OUTPUT);  
  // Open serial communications and wait for port to open
  Serial.begin( 9600 ); 
  // Start the Ethernet connection
  // use dhcp, if not support , try other way
  if (Ethernet.begin(mac)) {
    Serial.println("Ethernet  DHCP connection OK!");
  } 
  else{
    exit(-1); 
  }
  // Give the Ethernet shield a second to initialize
  delay( 1000 );
  // Check device whether existing or not
  if ( pinecone.findDeviceByCode(  ) != NULL ) {
    Serial.println( "Device is existed" );
    // read from eeprom memory 
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
    // not exist in remote database, it should add the info ,and save into eeprom memory
    String device = pinecone.createDevice( );
    Serial.print(F(" device "));
    Serial.println( device );
    String variableSwi = pinecone.createVariable( "Light Switch", "write" );
    Serial.print(F(" variableSwi "));
    Serial.println( variableSwi );
    itemOn = pinecone.createItem( "On", variableSwi );
    Serial.print(F(" itemOn "));
    Serial.println( itemOn);
    itemOff = pinecone.createItem( "Off", variableSwi );
    Serial.print(F(" itemOff "));
    Serial.println( itemOff );
    variableTem = pinecone.createVariable( "Temperature", "read" );
    Serial.print(F(" variableTem "));
    Serial.println( variableTem );
    variableHum = pinecone.createVariable( "Humidity", "read" );
    Serial.print(F(" variableHum "));
    Serial.println( variableHum );
    Serial.println( "Device is created" );

    // save into eeprom
    EEPROMPinecone.savePinecone(0,device.toInt());
    EEPROMPinecone.savePinecone(1,variableSwi.toInt());
    EEPROMPinecone.savePinecone(2,itemOn.toInt());
    EEPROMPinecone.savePinecone(3,itemOff.toInt());
    EEPROMPinecone.savePinecone(4,variableHum.toInt());
    EEPROMPinecone.savePinecone(5,variableTem.toInt());
  }
  //connect to mqtt server
  if ( mqtt.connect( MQTTCLIENTID) ) {
    Serial.println("Connected to MQTT Server...");
    // subscribe our device topic to get the message from moblie app
    mqtt.subscribe( TOPIC_NAME );
    Serial.println( "Topic is subscribed" );
  }  
  else{
    Serial.println("failed MQTT Server ...");
    exit(-1); 
  }
}
//
void callback(char* topic, unsigned char * payload,unsigned int length) {
  // handle message arrived , the messgage is like below:
  // {"id":"46","value":"off"} or {"id":"46","value":"on"}
  Serial.println( F("arrived" ));
  // so we use ajson lib to parse it 
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
            digitalWrite(lightPin, HIGH);   // turn the LED on
          }
          else{
            Serial.println("off!!");
            digitalWrite(lightPin, LOW);   // turn the LED off
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

void loop() {
  //at each time ,read now temperature and humidtiy ,publish them to mqtt server.
  if ((millis() % Interval) == 0) {
    dht.read();
    Serial.print("temp: ");
    Serial.print(dht.temperature);
    Serial.print("C");
    Serial.print("    humidity: ");
    Serial.print(dht.humidity);
    Serial.println("%");
    ////////////////////////////////////////////////////////////////////////
    Serial.print("free:");
    Serial.println(freeMemory());
    //publis data is something like 
    // {"id":"46","value":"27"}
    //so we also use ajson to handle it
    aJsonObject *root =aJson.createObject();
    char temp1[variableTem.length()+1];
    variableTem.toCharArray(temp1,variableTem.length()+1);
    aJsonObject * tem = aJson.createItem(temp1);
    aJson.addItemToObject(root,"id",tem);
    String temp_tem = (String)dht.temperature;
    char temp3[temp_tem.length()+1];
    temp_tem.toCharArray(temp3,temp_tem.length()+1);
    aJsonObject * temVar = aJson.createItem(temp3);
    aJson.addItemToObject(root,"value",temVar);
    char * data1=aJson.print(root);
    delay(200);
    Serial.println(data1);
    // "{\"id\":\"46\",\"value\":\"",23;
    publishData(data1);
    free(data1);    
    aJson.deleteItem(root);
    ////////////////////////////////////////////////////////////////////////
    aJsonObject *root2 =aJson.createObject();
    char temp4[variableHum.length()+1];
    variableHum.toCharArray(temp4,variableHum.length()+1);
    aJsonObject * hum = aJson.createItem(temp4);
    aJson.addItemToObject(root2,"id",hum);
    String temp_hum = (String)dht.humidity;
    char temp5[temp_hum.length()+1];
    temp_hum.toCharArray(temp5,temp_hum.length()+1);
    aJsonObject * humVar = aJson.createItem(temp5);
    aJson.addItemToObject(root2,"value",humVar);
    char * data2=aJson.print(root2);
    delay(200);
    Serial.println(data2);
    // "{\"id\":\"46\",\"value\":\"",23;
    publishData(data2);
    free(data2);    
    aJson.deleteItem(root2);
    ////////////////////////////////////////////////////////////////////////
  } 
  mqtt.loop();
}
void publishData(char *data){
  // at sometime ,it may disconnect from mqtt server, so should check it and reconnect.
  if(mqtt.connected()){
    if ( mqtt.publish( TOPIC_NAME, data) ) {
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
}