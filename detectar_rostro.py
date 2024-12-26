import cv2

cap=cv2.VideoCapture(0,cv2.CAP_DSHOW)

face_cascade=cv2.CascadeClassifier(cv2.data.haarcascades+"haarcascade_frontalface_default.xml")
eyes_cascade=cv2.CascadeClassifier(cv2.data.haarcascades+"haarcascade_eye_tree_eyeglasses.xml")

def draw_faces(detected,image,color:tuple):
    for(x,y, width, height) in detected:
       cv2.rectangle(image,(x,y),(x+width,y+height),color,thickness=3) 


while True:

    ret, frame=cap.read()
    
    #escala de grises
    gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    #detecta caras de cada frame
    detectar_rostro=face_cascade.detectMultiScale(image=gray, scaleFactor=1.3, minNeighbors=4)
    detectar_ojos=eyes_cascade.detectMultiScale(image=gray, scaleFactor=1.3, minNeighbors=4)
    draw_faces(detectar_rostro,frame,(0,255,255))
    draw_faces(detectar_ojos,frame,(0,0,255))
    cv2.imshow('Detectar rostro', frame)

    #para cerrar el programa
    if cv2.waitKey(1)==27:
        break




cap.release()
cv2.destroyAllWindows()