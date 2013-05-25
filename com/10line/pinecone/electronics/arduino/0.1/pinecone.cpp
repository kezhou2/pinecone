/*

 Pinecone api implements.
 developer can use this class connect pinecone rest server,and
 find device,insert device variabl item info into remote server,
 created 4 Mar 2013
 by Bill Xue, Apriliner Wang
 (c) Copyright 2013 beijing pinecone.
 */

#include "pinecone.h"

PineconeClass::PineconeClass(HttpClient client, char *_code) :
		http(client) {
	code = (char*) calloc(strlen(_code) + 1, 1);
	strcpy(code, _code);
}
String PineconeClass::getDeviceID() {
	return deviceID;
}
String PineconeClass::findDeviceByCode(void) {
	char path[33 + strlen(code)];
	strcpy(path, "/rest/m/device/search/codes?code=");
	strcat(path, code);
	Serial.println(path);
	return makeGetRequest(path);
}
String PineconeClass::createDevice() {
	char content[15 + String(code).length()];
	strcpy(content, "{\"code\":\"");
	strcat(content, code);
	strcat(content, "\"}");
	return makePostRequest("/rest/m/device", "application/json", content);
}
String PineconeClass::createVariable(char* name, char* type) {

	char content[29 + String(name).length() + String(type).length()];
	strcpy(content, "{\"name\":\"");
	strcat(content, name);
	strcat(content, "\",\"type\":\"");
	strcat(content, type);
	strcat(content, "\"}");
	String response = makePostRequest("/rest/m/variable", "application/json",
			content);
	//Serial.print("Variable is created -> ");
	//Serial.println(response);
	char variableId[response.length() + 1];
	response.toCharArray(variableId, response.length() + 1);
	char path[22 + response.length()];
	strcpy(path, "/rest/variable/");
	strcat(path, variableId);
	strcat(path, "/device");
	//Serial.print("Path ->");
	//Serial.println(path);
	char deviceId[deviceID.length() + 1];
	deviceID.toCharArray(deviceId, deviceID.length() + 1);
	char uri[20 + String(host).length() + deviceID.length()];
	strcpy(uri, "http://");
	strcat(uri, host);
	strcat(uri, "/rest/device/");
	strcat(uri, deviceId);
	//Serial.print("URI ->");
	//Serial.println(uri);
	makePostRequest(path, "text/uri-list", uri);
	//Serial.println("Variable is updated");
	return response;

}
String PineconeClass::createItem(char* value, String variable) {

	char content[16 + String(value).length()];
	strcpy(content, "{\"value\":\"");
	strcat(content, value);
	strcat(content, "\"}");
	String response = makePostRequest("/rest/m/item", "application/json",
			content);
	//Serial.print("Item is created -> ");
	//Serial.println(response);
	char itemId[response.length() + 1];
	response.toCharArray(itemId, response.length() + 1);
	char path[20 + response.length()];
	strcpy(path, "/rest/item/");
	strcat(path, itemId);
	strcat(path, "/variable");
	//Serial.print("Path ->");
	//Serial.println(path);
	char variableId[variable.length() + 1];
	variable.toCharArray(variableId, variable.length() + 1);
	char uri[22 + String(host).length() + variable.length()];
	strcpy(uri, "http://");
	strcat(uri, host);
	strcat(uri, "/rest/variable/");
	strcat(uri, variableId);
	//Serial.print("URI ->");
	//Serial.println(uri);
	makePostRequest(path, "text/uri-list", uri);
	//Serial.println("Item is updated");
	return response;

}
String PineconeClass::makeGetRequest(char* path) {

	http.beginRequest();
	http.startRequest(host, 80, path, "GET", NULL);
	http.sendBasicAuth(username, password);
	http.endRequest();
	http.skipResponseHeaders();
	String response = fetchDataFromResponse();
	http.stop();
	Serial.println(response);
	return response;

}
String PineconeClass::makePostRequest(char* path, char* type, char* content) {

	http.beginRequest();
	http.startRequest(host, 80, path, "POST", NULL);
	http.sendBasicAuth(username, password);
	http.sendHeader("Content-Type", type);
	http.sendHeader("Content-Length", String(content).length());
	http.endRequest();
	http.print(content);
	http.println();
	http.skipResponseHeaders();
	String response = fetchDataFromResponse();
	http.stop();
	return response;

}
String PineconeClass::fetchDataFromResponse() {
	String response = NULL;
	if (http.contentLength() > 0) {
		while (http.available()) {
			byte data = http.read();
			Serial.print((char) data);
			if (data >= 48 && data <= 57) {
				response += (char) data;
			}
		}
	}
	return response;
}

