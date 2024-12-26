import cv2
import os
import imutils

personName="jhon"
ruta="C:/Users/USUARIO/Desktop/proyecto aplicaciones distribuidas/reconocimiento_facial/imagenes"
personpath=ruta+"/"+personName

print(personpath)
#para crear carpeta si no existe
if not os.path.exists(personpath):
    print("carpeta inexistente"+ personpath)
    os.makedirs(personpath)

###

print(cv2.__version__)

#cap=cv2.VideoCapture(0,cv2.CAP_DSHOW)
cap=cv2.VideoCapture("video_jhon.mp4")
length = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
print(length, "Total de frames")
#modelo de reconocimiento
face_cascade=cv2.CascadeClassifier(cv2.data.haarcascades+"haarcascade_frontalface_default.xml")
count=0

"""
while True:
    ret, frame=cap.read()
    gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    cv2.imshow('frame', frame)
    #para cerrar el programa
    if cv2.waitKey(1)==27:
        break
"""
xxx=0
while xxx<=length:
    
    #print(xxx)
    ret, frame=cap.read()

    if ret==False:
        break

    frame=imutils.resize(frame,width=320)
    gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    auxf=frame.copy()

    faces=face_cascade.detectMultiScale(gray,1.3,5)


    for(x,y, width, height) in faces:
       
       #print("hola")
       #pinta el rectangulo
       cv2.rectangle(frame,(x,y),(x+width,y+height),(0,255,0),thickness=2) 
       rostro=auxf[y:y+height,x:x+width]
       rostro=cv2.resize(rostro,(720,720),interpolation=cv2.INTER_CUBIC)
       cv2.imwrite(personpath+'/rostro_{}.jpg'.format(count),rostro)
       count=count+1
    cv2.imshow('frame', frame)
    xxx=xxx+1
    #para cerrar el programa
    cv2.waitKey(1)
    #if cv2.waitKey(1)==27 or count>=300:
    #    break

cap.release()
cv2.destroyAllWindows()

