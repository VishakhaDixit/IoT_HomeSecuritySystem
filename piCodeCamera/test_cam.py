from imutils.video import VideoStream
from flask import Response
from flask import Flask
from flask import render_template
import threading
import argparse
import datetime
import imutils
import time
import cv2
from cv2 import VideoWriter
from cv2 import VideoWriter_fourcc
from motionDetection import motionDetector


video = VideoWriter('webcam.avi', VideoWriter_fourcc(*'MJPG'), 20.0, (640, 480))


# init the output frame
outputFrame = None
lock = threading.Lock()
motionBool=False

# init flask object
app = Flask(__name__)

# init the video stream 
vs = VideoStream(src=0).start()
time.sleep(2.0)

@app.route("/")
def index():
    # return the rendered template
    return render_template("index.html")

def detectMotion(frameCount):
    global vs, outputFrame, lock, motionBool

    md = motionDetector(accumWeight=0.1) # init detector with total frames 
    total = 0

    try:
        while True:
            # Read image from web cam and store it in frame 
            # Convert the frame to grayscale & blur it
            frame = vs.read()

            frame = imutils.resize(frame, width=400)
            gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
            gray = cv2.GaussianBlur(gray, (7, 7), 0)
            
            
            video.write(frame)
        
        
            timestamp = datetime.datetime.now()
            cv2.putText(frame, timestamp.strftime(
                "%A %d %B %Y %I:%M:%S%p"), (10, frame.shape[0] - 10),
                cv2.FONT_HERSHEY_SIMPLEX, 0.35, (0, 0, 255), 1)

            # Process total frames
            if total > frameCount:
                motion = md.detect(gray)

                if motion is not None:
                    motionBool = True
                    (thresh, (minX, minY, maxX, maxY)) = motion
                    cv2.rectangle(frame, (minX, minY), (maxX, maxY),
                        (0, 0, 255), 2)
                        
                else:
                    motionBool = False
        
            md.update(gray)
            total += 1
        
            # Acquire lock, set the output frame & release the lock
            with lock:
                outputFrame = frame.copy()
    except KeyboardInterrupt:
        print("Captured")
        vs.release()
        video.release()
        return
            
def generate():
    global outputFrame, lock

    while True:
        # Wait till lock is acquired
        with lock:
            if outputFrame is None:
                continue
                
            # encode the frame in JPEG format
            (flag, encodedImage) = cv2.imencode(".jpg", outputFrame)
            
            # ensure the frame was successfully encoded
            if not flag:
                continue

        # yield the output frame in the byte format
        yield(b'--frame\r\n' b'Content-Type: image/jpeg\r\n\r\n' + 
            bytearray(encodedImage) + b'\r\n')
            
@app.route("/motion_detect")
def motion_detect():
	print(motionBool)
	if motionBool is True:
		return "True"
	else:
		return "False"

@app.route("/video_feed")
def video_feed():
    return Response(generate(),
        mimetype = "multipart/x-mixed-replace; boundary=frame")


if __name__ == '__main__':
    ap = argparse.ArgumentParser()
    ap.add_argument("-i", "--ip", type=str, required=True,
        help="ip address of the device")
    ap.add_argument("-o", "--port", type=int, required=True,
        help="ephemeral port number of the server (1024 to 65535)")
    ap.add_argument("-f", "--frame-count", type=int, default=32,
        help="# of frames used to construct the background model")
    args = vars(ap.parse_args())
    
    # Start thread
    t = threading.Thread(target=detectMotion, args=(
        args["frame_count"],))
    t.daemon = True
    t.start()

    
    # Start Flask 
    app.run(host=args["ip"], port=args["port"], debug=True,
        threaded=True, use_reloader=False)



