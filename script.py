import RPi.GPIO as GPIO
import time
import logging

TRIG = 23
ECHO = 24

def setup_sensor():

    GPIO.setmode(GPIO.BCM)

    print "Distance Measurement In Progress"

    GPIO.setup(TRIG, GPIO.OUT)
    GPIO.setup(ECHO, GPIO.IN)

def get_location():

    GPIO.output(TRIG, True)
    time.sleep(0.00001)
    GPIO.output(TRIG, False)

    startTime = time.time()
	stopTime = time.time()

	# save start time
	while 0 == GPIO.input(ECHO):
		startTime = time.time()

	# save time of arrival
	while 1 == GPIO.input(ECHO):
		stopTime = time.time()

	# time difference between start and arrival
	TimeElapsed = stopTime - startTime
	# multiply with the sonic speed (34300 cm/s)
	# and divide by 2, because there and back
	distance = (TimeElapsed * 34300) / 2

	print ("Distance: %.1f cm" % distance)
	time.sleep(1)


if __name__ == '__main__':
    setup_sensor()
    try:
        while True:
            logging.warning('called')
            get_location()
    # Stop on Cmd+C and clean up
    except KeyboardInterrupt:
        GPIO.cleanup()
