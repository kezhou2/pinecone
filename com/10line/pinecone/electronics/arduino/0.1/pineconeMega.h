#ifndef PINECONEMEGA_h
#define PINECONEMEGA_h
#include <SPI.h>
#include <Arduino.h>
#include <Ethernet.h>
#include <EthernetClient.h>
#include <HttpClient.h>
// Define user name and password for server authorization
const char username[] = "admin";
const char password[] = "admin";
// Define REST server host we want to connect to
const char host[] = "pinecone-service.cloudfoundry.com";

class PineconeMegaClass {
public:
	PineconeMegaClass(HttpClient client,char*);
	String getDeviceID();
	String findDeviceByCode(void);
	String createDevice();
	String createVariable(char* name, char* type);
	String createItem(char* value, String variable);
private:
	char *code;
	// Define HTTP client for connecting to REST server
	HttpClient http;
	// device id
	String deviceID;
	String makeGetRequest(char* path);
	String makePostRequest(char* path, char* type, char* content);
	String fetchDataFromResponse();

};

extern PineconeMegaClass PineconeMega;
#endif
