#Jhon Leturne
#Reconocedor de rostros

import cv2
import os
import imutils
import numpy as np
import uuid

__NUMERO_IMAGENES=300
__VIDEO_RECURSO="video_jhon.mp4";
nombre="jhon"
ruta="D:/jhon 9no semestre/CIENCIA, TECNOLOGIA, SOCIEDAD E INNOVACION/proyectofinal/reconocimiento_facial/imagenes"
personpath=ruta+"/"+nombre

#https://github.com/opencv/opencv/tree/master/data/haarcascades
#abre el reconocedor descargado
face_cascade=cv2.CascadeClassifier(cv2.data.haarcascades+"haarcascade_frontalface_default.xml")

#******************** METODOS *******************************

#Verificar cuantos archivos contiene una ruta y ademas si se pasan de la cantidad que define la 
#variable  __NUMERO_IMAGENES se elimina la diferencia de imagenes. Es decir todas las muestras de imagenes
#quedaran con 300 imagenes maximo.
#retorna true si las imagenes se pasaron de 300 y se tuvieron que eliminar
#retorna false si hacen falta imagenes para lo cual indicara al usuario que tiene que pasar otro video
def verificar_cantidad_archivos():
    cont_files = 0
    #Cuenta cuantos archivos hay dentro de la ruta.
    dir = os.path.join(ruta, nombre)
    for path in os.listdir(dir):
        if os.path.isfile(os.path.join(dir, path)):
            cont_files += 1
    print("Cantidad de archivos= ",cont_files)
    if not cont_files<=__NUMERO_IMAGENES:
        diferencia=cont_files-__NUMERO_IMAGENES
        cont=0
        for path in os.listdir(dir):
            cont=cont+1
            if cont>diferencia:
                break
            else:
                os.remove(os.path.join(dir, path))
        return True
    else:
        return False



def segmentar_video():
    #para crear carpeta si no existe
    if not os.path.exists(personpath):
        print("carpeta inexistente"+ personpath)
        os.makedirs(personpath)

    #Recoge la referencia del video se le pasa la ruta como parametro
    cap=cv2.VideoCapture(__VIDEO_RECURSO)
    #obtenemos el total de frames
    length = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    print(length, "Total de frames")
    #Si existe error en el video lo cierra
    if (cap.isOpened() == False):
        print("Error al abrir el video")

    while True:
        ret, frame=cap.read()
        
        #Si no hay frames cierra el while
        if ret==False:
            break
        
        #redimenciona los frames
        frame=imutils.resize(frame,width=320)
        #aplica escala de grises
        gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        #saca una copia
        auxf=frame.copy()

        faces=face_cascade.detectMultiScale(gray,1.3,5)


        for(x,y, width, height) in faces:
            #obtiene el rostro 
            rostro=auxf[y:y+height,x:x+width]
            #redimenciona
            rostro=cv2.resize(rostro,(720,720),interpolation=cv2.INTER_CUBIC)
            #guarda la imagen
            cv2.imwrite(personpath+'/img_{}.jpg'.format(uuid.uuid4()),rostro)

    #liberar recursos del hardware
    cap.release()
    return True


#Esta funcion se encarga de entrenar un modelo deacuerdo a las imagenes que se hallan segmentado
def entrenar():
    #convierte la ruta en un vector
    #Es decir si hay dentro del directorio de imagenes 3 carpeta (C1,C2,C3)
    #Estas tres carpeta los nombres se vuelven en vector
    lista_usuarios=os.listdir(ruta)

    labels=[]
    facesdata=[]

    label=0


    for nombre_person in lista_usuarios:
        #completa la ruta con el usuario actual
        personRuta=ruta+"/"+nombre_person
        print(nombre_person)
        for fileName in os.listdir(personRuta):
            facesdata.append(cv2.imread(personRuta+'/'+fileName,0))
            labels.append(label)
        label=label+1


    #EigenFaceRecognizer, LBPHFaceRecognizer_create y FisherFaceRecognizer_create
    #Existen mas modelos de reconocimientos aunquen utilizamos este por la rapidez y optimizacion de espacio
    face_recognizer=cv2.face.LBPHFaceRecognizer_create()

    print("Entrenando")

    face_recognizer.train(facesdata, np.array(labels))

    face_recognizer.write("model.xml")

    print("Modelo guardado correctamente")

    return True


#Esta funcion se encarga de reconocer los rostros con el modelo entrenado anteriormente
def reconocer():

    face_recognizer=cv2.face.LBPHFaceRecognizer_create()

    #captura el video (el videocapture recibe la ruta donde esta el video)
    cap=cv2.VideoCapture("video_jhon.mp4")

    #lee el modelo
    face_recognizer.read("model.xml")
    result=None
    while True:
        ret, frame=cap.read()
        #si captura frame
        if ret==False:
            break
        
        #cambia a escala de grises
        gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        #realiza una copia del frame
        auxf=gray.copy()

        faces=face_cascade.detectMultiScale(gray,1.3,5)

        for (x,y,w,h) in faces:
            rostro=auxf[y:y+h,x:x+w]
            rostro=cv2.resize(rostro,(150,150),interpolation=cv2.INTER_CUBIC)
            #aqui hace la prediccion del rostro
            result=face_recognizer.predict(rostro)
            print(result)

    lista_usuarios=os.listdir(ruta)
    print("***")
    print(lista_usuarios[result[0]])
    print(result[1])

        #return result[1]<70   
   
        
    
def reconocer_imagen():

    lista_usuarios=os.listdir(ruta)


    face_recognizer=cv2.face.LBPHFaceRecognizer_create()

    #captura el video (el videocapture recibe la ruta donde esta el video)
    #image=cv2.imread("imagengrupal.jpg")
    image=cv2.imread("mes.jpg")
    #lee el modelo
    face_recognizer.read("model.xml")

    result=None

    #cambia a escala de grises
    gray=cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    faces=face_cascade.detectMultiScale(gray,
                                        scaleFactor=1.05, #indica la reduccion de la imagen (valores pequeño aseguran la deteccion)
                                        minNeighbors=5,  #indica los minimos de rectangulos que puede tener un rostro para ser detectado
                                        minSize=(10,10), #indica el tamaño minimo del objeto para ser detectado
                                        maxSize=(300,300)) #indica el tamaño maximo del objeto

    for (x,y,w,h) in faces:

        cv2.rectangle(image,(x,y),(x+w,y+h),(0,255,0),2)
        
        rostro=gray[y:y+h,x:x+w]
        rostro=cv2.resize(rostro,(150,150),interpolation=cv2.INTER_CUBIC)
        #aqui hace la prediccion del rostro
        result=face_recognizer.predict(rostro)
        print(result)

        if(result[1]<=80):
            cv2.putText(image,'{}'.format(lista_usuarios[result[0]]),(x,y+40),2,1.1,(0,0,0),1,cv2.LINE_AA)
        else:
            cv2.putText(image,'{}'.format("D"),(x,y+40),2,1.1,(0,0,0),1,cv2.LINE_AA)
 
    cv2.imshow('image',image)
    cv2.imwrite('pepa1.png',image)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


#Existen mas modelos de reconocimientos aunquen utilizamos este por la rapidez y optimizacion de espacio
#face_recognizer=cv2.face.LBPHFaceRecognizer_create()

#E = ENTRENAR 
#R = RECONOCER
#S = SEGMENTAR VIDEO
#O = RECONOCER IMAGEN

tipo="O"

if(tipo=="S"):
    f=segmentar_video()
    if(f):
        print("Video segmentado correctamente")
elif (tipo=="E"):
    f=entrenar()
    if(f):
        print("Modelo realizado correctamente")
elif (tipo=="O"):  
    reconocer_imagen();   
else:
    print("Reconocer")
    valor=reconocer()