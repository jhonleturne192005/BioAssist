import cv2

import os


ruta="C:/Users/USUARIO/Desktop/proyecto aplicaciones distribuidas/reconocimiento_facial/imagenes"
#convierte la ruta en un vector
lista_person=os.listdir(ruta)
print("imagePath=",lista_person)

face_recognizer=cv2.face.LBPHFaceRecognizer_create()

#lee el modelo
face_recognizer.read("reconocedorjhon.xml")

#captura el video
#cap=cv2.VideoCapture(0,cv2.CAP_DSHOW)
cap=cv2.VideoCapture("video_toreto.mp4")

#https://github.com/opencv/opencv/tree/master/data/haarcascades
#abre el reconocedor descargado
faceClass=cv2.CascadeClassifier(cv2.data.haarcascades+"haarcascade_frontalface_default.xml")


while True:
    ret, frame=cap.read()
    #si captura frame
    if ret==False:
        break
     
    #cambia a escala de grises
    gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    #realiza una copia del frame
    auxf=gray.copy()

    faces=faceClass.detectMultiScale(gray,1.3,5)

    
    for (x,y,w,h) in faces:
        rostro=auxf[y:y+h,x:x+w]
        rostro=cv2.resize(rostro,(150,150),interpolation=cv2.INTER_CUBIC)
        #aqui hace la prediccion del rostro
        result=face_recognizer.predict(rostro)
        print(result)
        #para dibujar el cuadro de la cara y agg el nombre de la misma
        cv2.putText(frame,'{}'.format(result),(x,y-5),1,1.3,(255,255.0),1,cv2.LINE_AA)

        if result[1]<70:
            cv2.putText(frame,'{}'.format(lista_person[result[0]]),(x,y-25),2,1.1,(0,255,0),1,cv2.LINE_AA)
            cv2.rectangle(frame,(x,y),(x+w,y+h),(0,255,0),2)
        else:
            cv2.putText(frame,'Desconocido',(x,y-20),2,0.8,(0,0,255),1,cv2.LINE_AA)
            cv2.rectangle(frame,(x,y),(x+w,y+h),(0,0,255),2)

#os símbolos “>” (mayor) y “<” (menor) 
    cv2.imshow("frame",frame)

    if cv2.waitKey(1)==27:
        break
    #print("dxsfsd")

cap.release()
cv2.destroyAllWindows()