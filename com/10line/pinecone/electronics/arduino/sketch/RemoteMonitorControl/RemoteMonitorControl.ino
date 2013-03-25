/**
this example publish temp and hum to remote client, 
and receive remote control code to change the light
 @author :wangyq
 @company :Pinecone
 */
#include <aJSON.h>
#include <SPI.h>
#include <Ethernet.h>
#include <Wire.h>
#include <PubSubClient.h>
#include <util.h>

#define CLIENTID "pinecone@device.arduino.7"
#define TOPICNAME "pinecone@device.7"
//#define TOPICNAME_LIGHT "pinecone@variable.8"
#define VAR_TEM_ID "9"
#define VAR_HUM_ID "46"
//polling 10s
#define POLLINTERVAL 10000
unsigned long boardTime = 0 ;
//Connect to m2m.eclipse.org server
byte server[] = {
  198,41,30,241};
//Connect to test.mosquitto.org server
//byte server[] = {
//  85,119,83,194};
//Our MQTT client
PubSubClient arduinoClient(server, 1883, callback) ;
byte pubRst;
//
byte mac[] = {
  0x00, 0x1D, 0x72, 0x82, 0x35, 0x9D};
byte ip[]={
  192,168,1,11};
//define light led 
#define LIGHT_PIN 7

// define sensor var
#define DHT11_PIN 0 
byte dht11_dat[5];
char tempValue[3];
char humidityValue[3];
// get value
void getTempHumidity(){
  byte i;
  byte dht11_in;
  PORTC &= ~_BV(DHT11_PIN);
  delay(18);
  PORTC |= _BV(DHT11_PIN);
  delayMicroseconds(40);
  DDRC &= ~_BV(DHT11_PIN);
  delayMicroseconds(40);
  dht11_in = PINC & _BV(DHT11_PIN);
  if(dht11_in){
//    Serial.println("dht11 start condition 1 not met");
    return;
  }
  delayMicroseconds(80);
  dht11_in = PINC & _BV(DHT11_PIN);
  if(!dht11_in){
//    Serial.println("dht11 start condition 2 not met");
    return;
  }
  delayMicroseconds(80);
  for (i=0; i<5; i++)
    dht11_dat[i] = read_dht11_dat();
  DDRC |= _BV(DHT11_PIN);
  PORTC |= _BV(DHT11_PIN);
  byte dht11_check_sum = dht11_dat[0]+dht11_dat[1]+dht11_dat[2]+dht11_dat[3];
  // check check_sum
  if(dht11_dat[4]!= dht11_check_sum)  {
//    Serial.println("DHT11 checksum error");
  }
  itoa(dht11_dat[2],tempValue,10);
  itoa(dht11_dat[0],humidityValue,10);
}

byte read_dht11_dat(){
  byte i = 0;
  byte result=0;
  for(i=0; i< 8; i++){
    while(!(PINC & _BV(DHT11_PIN)));  // wait for 50us
    delayMicroseconds(30);
    if(PINC & _BV(DHT11_PIN)) 
      result |=(1<<(7-i));
    while((PINC & _BV(DHT11_PIN)));  // wait '1' finish		
  }
  return result;
}

void callback(char* topic, byte* payload,int length) {
  // handle message arrived
//  Serial.print("Callback   ");
//  Serial.write(payload,length);
//  Serial.println((char)payload[7]);
  if((char)payload[7]=='8'){
//    Serial.println(payload[19]);
    // var id ==8 which is light switch
//    Serial.println("   Come on Get IT!!!!!!!!!   ");
    if((char)payload[20]=='f'){
//      Serial.println("The Light Turn Down!!!!");
      digitalWrite(LIGHT_PIN, LOW);   // turn the LED on (HIGH is the voltage level)
    }
    else{
//      Serial.println("The Light Turn On!!!!");
      digitalWrite(LIGHT_PIN, HIGH);   // turn the LED on (HIGH is the voltage level)
    }
  }

}
void setup(void) {
  Serial.begin(9600);
  pinMode(LIGHT_PIN, OUTPUT);
  DDRC |= _BV(DHT11_PIN);
  PORTC |= _BV(DHT11_PIN);
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
  } 
  else {
    Ethernet.begin(mac, ip) ;
    Serial.println("Ethernet configuration OK");
  }
  int connRC = arduinoClient.connect(CLIENTID) ;
  if (connRC) {
    Serial.println("Connected to MQTT Server...");
    arduinoClient.subscribe(TOPICNAME);
  }
  else {
//    Serial.println("Could not connect to MQTT Server");
//    Serial.println("Please reset the arduino to try again");
    delay(1000);
    exit(-1);
  }
}

void myPublsih(){
  getTempHumidity();
  // temp
  char * data = (char*)malloc(23);
  memcpy(data,"{\"id\":\"9\",\"value\":\"",23);
  data =  strcat(data,tempValue);
  data =  strcat(data,"`C\"}");
//  Serial.println(TOPICNAME);
//  Serial.print(" temp: ");
//  Serial.print(data);
  pubRst = arduinoClient.publish(TOPICNAME ,data) ;
//  Serial.print(" Result :" );
//  Serial.println(pubRst);
//  // hum
  char * hum = (char*)malloc(24);
  memcpy(hum,"{\"id\":\"46\",\"value\":\"",23);
  hum =  strcat(hum,humidityValue);
  hum =  strcat(hum,"%\"}");
//  Serial.print(" .hum: ");
//  Serial.println(hum);
  pubRst =arduinoClient.publish(TOPICNAME ,hum) ;
//  Serial.print(" Result :" );
//  Serial.println(pubRst);
//  Serial.println("Publish  OK!!");
}
void loop(void) {
  boardTime = millis();
  if ((boardTime % POLLINTERVAL) == 0) {
    if(!arduinoClient.connected()){
//      Serial.print("Retry connect to server----");
      if(arduinoClient.connect(CLIENTID) ){
//        Serial.println("OK!");
        myPublsih(); 
      }
      else{
//        Serial.println("Failed");
      }
    }
    else{
      myPublsih();
    }
  }
  arduinoClient.loop();
}
