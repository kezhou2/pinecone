/**
get 	 humidity and  temperature by dht11
author:apriliner
company:pinecone
 */


#ifndef dht11mega_h
#define dht11mega_h

#if defined(ARDUINO) && (ARDUINO >= 100)
#include <Arduino.h>
#else
#include <WProgram.h>
#endif


class DHT11
{
public:
	DHT11(byte pin);
    void read();
	byte humidity;
	byte temperature;
private:
	byte dht11_dat[5];
	byte DHT11_PIN;
	byte read_dht11_dat();
};
#endif
//
// END OF FILE
//
