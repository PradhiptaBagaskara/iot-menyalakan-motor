#include <FirebaseArduino.h>
#include <ESP8266WiFi.h>
#define FIREBASE_HOST "nyala-montor.firebaseio.com"
#define FIREBASE_AUTH "Hw31cGx7tuFn3j8vcJMTSu7LZWqTRolWlJZBKYrJ"
#define WIFI_SSID "WIFI.HOMEe"
#define WIFI_PASSWORD "esjerukseger"
#define starterPin 12 //d6
#define kunciPin 2 //d4

void setup() {
  // put your setup code here, to run once:
   Serial.begin(115200);
   pinMode(starterPin, OUTPUT);
   pinMode(kunciPin, OUTPUT);
   digitalWrite(starterPin, LOW);
   digitalWrite(kunciPin, LOW);
   WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    while (WiFi.status() != WL_CONNECTED) {
      Serial.print("not connected");
     
      delay(500);
      
      }
   Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
   

}

void loop() {
  int Delay = 100;
  bool kunci = Firebase.getBool("kunci");
  int k_relay = Firebase.getInt("k_relay");
  bool starter = Firebase.getBool("starter");
  int s_relay = Firebase.getInt("s_relay");

  if(kunci){
    if(k_relay == 0){
      digitalWrite(kunciPin, HIGH);
      Firebase.setInt("k_relay", 1); 
    }
    
    if(starter){
      if(s_relay == 0){
      digitalWrite(starterPin, HIGH);
      Firebase.setInt("s_relay", 1); 
    }
      Delay = 200;
      
    }else{
       Serial.println("starter mode off");
      if(s_relay == 1){
      digitalWrite(starterPin, LOW);
      Firebase.setInt("s_relay", 0); 
    }
      Delay = 5000;
      
    }
     Serial.println("kunci mode on");
      
  }else{
     if(k_relay == 1){
      digitalWrite(kunciPin, LOW);
      Serial.println("kunci mode off");
      Firebase.setInt("k_relay", 0); 
    }
    Delay = 3000;
    
  }
  delay(Delay);
  // put your main code here, to run repeatedly:

}
