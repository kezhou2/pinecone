/*
  this example displays realtime tempterature and humidity on lcd screen .
 * LCD RS pin to digital pin 8
 * LCD Enable pin to digital pin 9
 * LCD D4 pin to digital pin 4
 * LCD D5 pin to digital pin 5
 * LCD D6 pin to digital pin 6
 * LCD D7 pin to digital pin 7
 * LCD BL pin to digital pin 10
 * KEY pin to analogl pin 0
 @author :wangyq
 @company :Pinecone
 */

#include <LiquidCrystal.h>

LiquidCrystal lcd(8, 13, 9, 4, 5, 6, 7);

#define DHT11_PIN 5 
byte dht11_dat[5];
char tempValue[3];
char humidityValue[3];
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
    Serial.println("dht11 start condition 1 not met");
    return;
  }
  delayMicroseconds(80);

  dht11_in = PINC & _BV(DHT11_PIN);

  if(!dht11_in){
    Serial.println("dht11 start condition 2 not met");
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
    Serial.println("DHT11 checksum error");
  }

  itoa(dht11_dat[2],tempValue,10);
  itoa(dht11_dat[0],humidityValue,10);
  Serial.print("temp: ");
  Serial.print(tempValue);
  Serial.print("C");
  Serial.print("    humidity: ");
  Serial.print(humidityValue);
  Serial.println("%");

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
void setup(){
  lcd.clear(); 
  lcd.begin(16, 2);
  lcd.setCursor(0,0); 
  lcd.print("Hello Pinecone!"); 

  //
  Serial.begin(9600);
  DDRC |= _BV(DHT11_PIN);
  PORTC |= _BV(DHT11_PIN);
  delay(5000);
}

void loop(){
  getTempHumidity();
  lcd.clear(); 
  lcd.setCursor(0,0); 
  lcd.print("Temperature:"); 
  lcd.print(tempValue);
  lcd.print("'C");
  lcd.setCursor(0, 1);
  lcd.print("Humidity   :");
  lcd.print(humidityValue);
  lcd.print("%");
  delay(1000);
}
