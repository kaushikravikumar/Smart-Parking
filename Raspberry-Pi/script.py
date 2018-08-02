import RPi.GPIO as GPIO
import time
import signal
import sys
import subprocess
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub
from pubnub.exceptions import PubNubException

# use Raspberry Pi board pin numbers
GPIO.setmode(GPIO.BCM)

# set GPIO Pins
pinTrigger = 23
pinEcho = 24
occupied = False

def close(signal, frame):
	print("\nTurning off ultrasonic distance detection...\n")
	GPIO.cleanup()
	sys.exit(0)

signal.signal(signal.SIGINT, close)

def setup_sensor():
	# set GPIO input and output channels
	GPIO.setup(pinTrigger, GPIO.OUT)
	GPIO.setup(pinEcho, GPIO.IN)

def get_distance():
	# set Trigger to HIGH
	GPIO.output(pinTrigger, True)
	# set Trigger after 0.01ms to LOW
	time.sleep(0.00001)
	GPIO.output(pinTrigger, False)

	startTime = time.time()
	stopTime = time.time()

	# save start time
	while 0 == GPIO.input(pinEcho):
		startTime = time.time()

	# save time of arrival
	while 1 == GPIO.input(pinEcho):
		stopTime = time.time()

	# time difference between start and arrival
	TimeElapsed = stopTime - startTime
	# multiply with the sonic speed (34300 cm/s)
	# and divide by 2, because there and back
	distance = (TimeElapsed * 34300) / 2

	return distance

def initial_check():
	occupied = True if get_distance() < 5 else False
	# try:
	# 	pubnub.publish().channel("parking_spot").message({
	# 		'occupied': occupied
	# 	}).sync()
	# 	print("initial publish complete")
	# except PubNubException as e:
	# 	print(e)
	# DO Terminal command here!
	subprocess.Popen(["mosquitto_pub", "-h", "beam.soracom.io", "-p", "1883", "-t", "parking_spot", "-m", occupied], stdout=subprocess.PIPE)
	print("initial publish complete")

if __name__ == '__main__':
	# pnconfig = PNConfiguration()
	# pnconfig.subscribe_key = 'sub-c-e36bba74-8c65-11e8-85ee-866938e9174c'
	# pnconfig.publish_key = 'pub-c-559f5d98-9a8a-42e0-8a38-dfe760065056'
	# pubnub = PubNub(pnconfig)

	setup_sensor()
	initial_check()
	while True:
		if (occupied and (get_distance() >= 5)) or (not occupied and (get_distance() < 5)):
			# try:
			occupied = not occupied
			subprocess.Popen(["mosquitto_pub", "-h", "beam.soracom.io", "-p", "1883", "-t", "parking_spot", "-m", occupied], stdout=subprocess.PIPE)
			print("momentary publish")
			# 	pubnub.publish().channel("parking_spot").message({
			# 		'occupied': occupied
			# 	}).sync()
			# 	print("Success publishing")
			# except PubNubException as e:
			# 	print(e)
		time.sleep(5)
