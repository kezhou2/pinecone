#include <EEPROM.h>
#include <EEPROMPINECONE.h>
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  long data =1234567890  ;
  EEPROMPinecone.savePinecone(5,data);
  Serial.println();
  long value = EEPROMPinecone.readPinecone(5);
  Serial.println(value);
}

void loop() {
  // put your main code here, to run repeatedly: 

}