/**
 get 	 humidity and  temperature by dht11
 author:apriliner
 company:pinecone
 */

#include "dht11Mega.h"

// DHTLIB_ERROR_TIMEOUT

DHT11::DHT11(byte pin) {
	this->DHT11_PIN = pin;
	pinMode(this->DHT11_PIN, OUTPUT);
	digitalWrite(this->DHT11_PIN, HIGH);
}
void DHT11::read() {
	byte i;
	byte dht11_in;
	digitalWrite(DHT11_PIN, LOW);
	delay(18);
	digitalWrite(DHT11_PIN, HIGH);
	delayMicroseconds(1);
	pinMode(DHT11_PIN, INPUT);
	delayMicroseconds(40);

	if (digitalRead(DHT11_PIN)) {
		Serial.println("dht11 start condition 1 not met"); // wait for DHT response signal: LOW
		delay(1000);
		return;
	}
	delayMicroseconds(80);
	if (!digitalRead(DHT11_PIN)) {
		Serial.println("dht11 start condition 2 not met"); //wair for second response signal:HIGH
		return;
	}

	delayMicroseconds(80); // now ready for data reception
	for (i = 0; i < 5; i++) {
		dht11_dat[i] = read_dht11_dat();
	} //recieved 40 bits data. Details are described in datasheet

	pinMode(DHT11_PIN, OUTPUT);
	digitalWrite(DHT11_PIN, HIGH);
	byte dht11_check_sum = dht11_dat[0] + dht11_dat[2]; // check check_sum
	if (dht11_dat[4] != dht11_check_sum) {
		Serial.println("DHT11 checksum error");
	}
	humidity = dht11_dat[0];
	temperature = dht11_dat[2];
}

byte DHT11::read_dht11_dat() {
	byte i = 0;
	byte result = 0;
	for (i = 0; i < 8; i++) {
		while (!digitalRead(DHT11_PIN))
			;
		delayMicroseconds(30);
		if (digitalRead(DHT11_PIN) != 0)
			bitSet(result, 7 - i);
		while (digitalRead(DHT11_PIN))
			;
	}
	return result;
}
//
// END OF FILE
//
