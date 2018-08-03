# Smart Parking

# QuickStart

**Sign up for PubNub click here:**

<a href="https://dashboard.pubnub.com/signup?devrel_gh=Smart-Parking">
    <img alt="PubNub Signup" src="https://i.imgur.com/og5DDjf.png" width=260 height=97/>
</a>

**To Build this, You Need**
- Raspberry Pi (Any Version)
- Breadboard
- HC-SR04 Ultrasonic Sensor
- 1 Female-female + 6 male-female jumper wires
- 1 1k Ω resistor and 1 2k Ω resistor
- MicroSD card + MicroSD to SD Adapter
- Huawei USB Stick Modem
- Soracom Air Sim Card

**Raspberry Pi Setup** 
1. Install [Raspbian OS](https://www.raspberrypi.org/downloads/raspbian/) and flash disk image using [Etcher](https://etcher.io/) onto a microSD card.
2. Insert microSD card into Raspberry Pi. 
3. Plug 3 of your male-female jumper wires into the HC-SR04 ultrasonic sensor in the VCC, Echo, and GND slots. Plug one of your female-female jumper wires into the Trig slot of the sensor.
4. Plug the VCC wire into the positive rail of our breadboard and the GND wire into the negative rail.
5. Plug GPIO 5V on the Raspberry Pi to the positive rail of our breadboard and GPIO GND to the negative rail.
6. Plug the Trig wire into GPIO 23.
7. Plug the Echo wire into a blank rail on the breadboard.
8. Link another blank rail using a 1k Ω resistor. (The reason, we need resistors is so we can lower the voltage output to ensure that our Raspberry Pi is not damaged by the sensor configuration).
9. Then link a blank rail using a 2k Ω resistor to the negative rail of our breadboard leaving a space. If you do not have a 2k Ω resistor, you could create a series of 2 1k Ω resistors.
10. In the space you have left, you will use a male-female jumper wire to link to GPIO 24.
Follow [these instructions](https://www.modmypi.com/blog/hc-sr04-ultrasonic-range-sensor-on-the-raspberry-pi), if you get lost in configuring the sensor. 

**PubNub Setup**
1. create your app in the [PubNub Admin Console](https://admin.pubnub.com/#/login) (It's free). After creating the app in the console, you will see the **publish** and **subscribe keys**, which we will need later.

**Soracom Setup**

