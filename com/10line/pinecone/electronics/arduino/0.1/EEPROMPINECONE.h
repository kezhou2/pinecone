/*
  EEPROM save
  pinecone
  apriliner wang
  beijing pinecone company.
*/

#ifndef EEPROMPINECONE_h
#define EEPROMPINECONE_h

#include <arduino.h>
#include <inttypes.h>

class EepromPineconeClass
{
  public:
    void savePinecone(byte,long);
    long readPinecone(byte);
};

extern EepromPineconeClass EEPROMPinecone;

#endif

