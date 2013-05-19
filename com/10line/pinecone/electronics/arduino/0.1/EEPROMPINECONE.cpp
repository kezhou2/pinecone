/*
 EEPROM save
 pinecone
 apriliner wang
 beijing pinecone company.
 */
#include "EEPROMPINECONE.h"
#include <Arduino.h>
#include <EEPROM.h>

void EepromPineconeClass::savePinecone(byte no, long value) {
	int abyte[4] = { 0, 0, 0, 0 };
	abyte[0] = (unsigned char) ((0xff000000 & value) >> 24);
	abyte[1] = (unsigned char) ((0xff0000 & value) >> 16);
	abyte[2] = (unsigned char) ((0xff00 & value) >> 8);
	abyte[3] = (unsigned char) (0xff & value);
	EEPROM.write(no * 4 + 0, abyte[0]);
	EEPROM.write(no * 4 + 1, abyte[1]);
	EEPROM.write(no * 4 + 2, abyte[2]);
	EEPROM.write(no * 4 + 3, abyte[3]);
}

long EepromPineconeClass::readPinecone(byte no) {
	byte data[4];
	data[0] = EEPROM.read(no * 4);
	data[1] = EEPROM.read(no * 4 + 1);
	data[2] = EEPROM.read(no * 4 + 2);
	data[3] = EEPROM.read(no * 4 + 3);

	return ((long) (data[0]) << 24) + ((long) (data[1]) << 16)
			+ ((long) (data[2]) << 8) + ((long) (data[3]));
}

EepromPineconeClass EEPROMPinecone;
