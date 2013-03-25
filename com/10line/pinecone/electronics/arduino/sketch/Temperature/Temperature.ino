/*
this example detects temperature ,and shows it through serial output.
 @author :wangyq
 @company :Pinecone
 */

#define DHT11_PIN 0 
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
  Serial.begin(9600);
  DDRC |= _BV(DHT11_PIN);
  PORTC |= _BV(DHT11_PIN);

}
void loop(){  
  getTempHumidity();
  delay(1000);
}

