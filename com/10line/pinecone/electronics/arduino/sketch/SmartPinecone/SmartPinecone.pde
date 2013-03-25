/*
this example shows smart housekeeper.
 it can read temp and humidity after voice request,and can turn light on or off acoording voice code..
 @author :wangyq
 @company :Pinecone
 */
#include <Wire.h>
#include <Voice.h>
#include "Syn6288.h"
Syn6288 syn;
#define SUM 10
#define DHT11_PIN 0      //ģ���0
byte dht11_dat[5];
uint8  nAsrStatus=0;
char sRecog[SUM][80] = {
  "song guo yi hao","xian zai wen du zen me yang","kai deng","guan deng","xu yao kai deng ma","song guo","song","yi hao"};
uint8_t text1[]={
  0xD2,0xBB,0xB6,0xFE,0xC8,0xFD,0xCB,0xC4,0xCE,0xE5,0xC1,0xF9,0xC6,0xDF,0xB0,0xCB,0xBE,0xC5,0xCA,0xAE,0xC1,0xE3};
uint8_t text2[]={
  0xC4,0xFA,0xBA,0xC3};// ���
uint8_t text3[]={
  0xB5,0xB1,0xC7,0xB0,0xCE,0xC2,0xB6,0xC8,0xCE,0xAA};//��ǰ�¶�Ϊ
uint8_t text4[]={
  0xCA,0xAA,0xB6,0xC8,0xCE,0xAA,0xB0,0xD9,0xB7,0xD6,0xD6,0xAE};//ʪ��Ϊ�ٷ�֮
uint8_t text5[]={
  0xC9,0xE3,0xCA,0xCF,0xB6,0xC8};//���϶�
uint8_t text6[]="soundn";//������
uint8_t text7[]={
  0xC7,0xEB,0xD7,0xA2,0xD2,0xE2,0xA3,0xAC,0xD3,0xD0,0xCE,0xA3,0xCF,0xD5,0xC6,0xF8,0xCC,0xE5,0xD0,0xB9,0xC2,0xA9};//��ע�⣬��Σ������й©
uint8_t text8[]={
  0xD5,0xFD,0xD4,0xDA,0xBF,0xAA,0xB5,0xC6};//���ڿ���
uint8_t text9[]={
  0xD5,0xFD,0xD4,0xDA,0xB9,0xD8,0xB5,0xC6};//���ڹص�
uint8_t text10[]={
  0xC7,0xEB,0xCE,0xCA,0xC4,0xE3,0xD4,0xDA,0xCB,0xB5,0xCA,0xB2,0xC3,0xB4,0xA3,0xBF};//��������˵ʲô��
uint8_t text11[]={
  0xB9,0xE2,0xCF,0xDF,0xB2,0xBB,0xD7,0xE3,0x20,0xA3,0xAC,0xD5,0xFD,0xD4,0xDA,0xBF,0xAA,0xB5,0xC6};//���߲��㣬���ڿ���
uint8_t text12[]={
  0xCF,0xD6,0xD4,0xDA,0xB9,0xE2,0xCF,0xDF,0xC3,0xF7,0xC1,0xC1,0xA3,0xAC,0xB2,0xBB,0xD0,0xE8,0xD2,0xAA,0xBF,0xAA,0xB5,0xC6};//��������������Ҫ����
uint8_t text[2];
uint8_t textt[2];
int gas,shine;
int dat1,dat2;
int flag;
int led=8;
int pig1,pig2,pig3,pig4;
void DHT11()
{
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
  if(dht11_dat[4]!= dht11_check_sum)
  {
    Serial.println("DHT11 checksum error");
  }
  dat1=dht11_dat[2];
  dat2=dht11_dat[0];
}

byte read_dht11_dat()
{
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
void finally (unsigned char n)
{
  //Serial.println(n); 
  switch(n)
  {
  case 0: 
    syn.play(text2,sizeof(text2),0);
    flag=1; 
    break;
  case 1:
    if(flag==1)
    {
      pig1=dat1/10;
      pig2=dat1%10;
      pig3=dat2/10;
      pig4=dat2%10;
      syn.play(text3,sizeof(text3),0); 
      if(pig1>0)
      {
        text[0]=text1[pig1*2-2];
        text[1]=text1[pig1*2-1];
        syn.play(text,sizeof(text),0); 
        text[0]=text1[18];
        text[1]=text1[19];
        syn.play(text,sizeof(text),0); 
      }
      if(pig2>0)
      {
        text[0]=text1[pig2*2-2];
        text[1]=text1[pig2*2-1];
        syn.play(text,sizeof(text),0); 
      }
      syn.play(text5,sizeof(text5),0); 
      syn.play(text4,sizeof(text4),0);
      if(pig3>0)
      {
        //  Serial.println(pig3);
        textt[0]=text1[pig3*2-2];
        textt[1]=text1[pig3*2-1];
        syn.play(textt,sizeof(textt),0); 
        textt[0]=text1[18];
        textt[1]=text1[19];
        syn.play(textt,sizeof(textt),0); 
      }
      if(pig4>0)
      {
        //   Serial.println(pig4);
        textt[0]=text1[pig4*2-2];
        textt[1]=text1[pig4*2-1];
        syn.play(textt,sizeof(textt),0); 
      }
      flag=0;
    }
    break;
  case 2:
    if(flag==1)
    {
      syn.play(text8,sizeof(text8),0); 
      digitalWrite(led,HIGH);
      //delay(100);
      flag=0;  
    }
    break;	
  case 3:
    if(flag==1)
    {
      syn.play(text9,sizeof(text9),0); 
      digitalWrite(led,LOW);
      //delay(100);
      flag=0;
    }
    break;
  case 4:
    if(flag==1)
    {
      shine=analogRead(2);
      if(shine<20)
      {
        syn.play(text11,sizeof(text11),0); 
        digitalWrite(led,HIGH);
      }
      else 
      {
        syn.play(text12,sizeof(text12),0); 
        digitalWrite(led,LOW);
      }
      flag=0;
    }
    break;
  default:
    syn.play(text10,sizeof(text10),0);
    flag=0;
    break;       
  }
}

void ExtInt0Handler ()
{
  Voice.ProcessInt0();

}
void setup()
{
  Wire.begin(); 
  Serial.begin(9600);
  Voice.Initialise(MIC);
  attachInterrupt(0,ExtInt0Handler,LOW);
  DDRC |= _BV(DHT11_PIN);
  PORTC |= _BV(DHT11_PIN);
  pinMode(led,OUTPUT);
  pinMode(7,OUTPUT);
}
void loop()
{  
  uint8 nAsrRes;
  nAsrStatus = LD_ASR_NONE;
  // DHT11();   
  while(1)
  {
    DHT11();
    gas=analogRead(1);
    if(gas>700)
    { // get some bugs here
//      syn.play(text6,sizeof(text6),0);
//      syn.play(text7,sizeof(text7),0);
    }
    delay(100);
    switch(nAsrStatus)
    {
    case LD_ASR_RUNING:
    case LD_ASR_ERROR:		
      break;
    case LD_ASR_NONE:
      {
        nAsrStatus=LD_ASR_RUNING;
        if (Voice.RunASR(SUM,80,sRecog)==0)
        {
          nAsrStatus= LD_ASR_ERROR;
          Serial.println( "ASR_ERROR"); 
        }

        Serial.println( "ASR_RUNING.....");
        digitalWrite(7,HIGH);
        break;
      }
    case LD_ASR_FOUNDOK:
      {
        digitalWrite(7,LOW);
        nAsrRes =Voice. LD_GetResult();//	һ��ASRʶ�����̽���ȥȡASRʶ����	
        if(nAsrRes==0&&flag==0)
        {
          syn.play(text2,sizeof(text2),0);
          flag=1;
        }
        else if(flag==1)
        {
          finally(nAsrRes);                           
        }
        nAsrStatus = LD_ASR_NONE;
        break;
      }
    case LD_ASR_FOUNDZERO:
    default:
      {	
        digitalWrite(7,LOW);
        nAsrStatus = LD_ASR_NONE;
        break;
      }
    }// switch
    delay(500);
  }// while
}

