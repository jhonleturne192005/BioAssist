# R = RECONOCIMIENTO
# E = ENTRENAMINETO
# P = PROCESAMIENTO

import cv2
import os
import imutils
import numpy as np
import uuid
from apiproject.settings import STATICFILES_DIRS
import base64
from io import BytesIO
from datetime import datetime
from PIL import Image

__NUMERO_IMAGENES=500
ruta=os.path.join(STATICFILES_DIRS[0],"imagenes/")
ruta_video=os.path.join(STATICFILES_DIRS[0],"video/")
ruta_models=os.path.join(STATICFILES_DIRS[0],"models/")
ruta_imagen_reconocimiento_all=os.path.join(STATICFILES_DIRS[0],"imgrec/")
face_cascade=cv2.CascadeClassifier(cv2.data.haarcascades+"haarcascade_frontalface_default.xml")


class REP:

    def __init__(self, nombre,videoBASE64):

        if(nombre and videoBASE64):
            self.nombre = nombre
            self.personpath=ruta+"/"+nombre
            self.nombre_video="{fecha}{nombre}.mp4".format(fecha=str(datetime.now()).replace(":","."),nombre=nombre)

            buffer = BytesIO(base64.b64decode(videoBASE64))
            out_file = open(ruta_video+self.nombre_video, "wb") # open for [w]riting as [b]inary
            out_file.write(buffer.getvalue())
            out_file.close()


    #Verificar cuantos archivos contiene una ruta y ademas si se pasan de la cantidad que define la 
    #variable  __NUMERO_IMAGENES se elimina la diferencia de imagenes. Es decir todas las muestras de imagenes
    #quedaran con 300 imagenes maximo.
    #retorna true si las imagenes se pasaron de 300 y se tuvieron que eliminar
    #retorna false si hacen falta imagenes para lo cual indicara al usuario que tiene que pasar otro video
    def verificar_cantidad_archivos(self):
        cont_files = 0
        #Cuenta cuantos archivos hay dentro de la ruta.
        dir = os.path.join(ruta, self.nombre)
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

    
    
    def segmentar_video(self):
        #para crear carpeta si no existe
        if not os.path.exists(self.personpath):
            print("carpeta inexistente"+ self.personpath)
            os.makedirs(self.personpath)

        #Recoge la referencia del video se le pasa la ruta como parametro
        cap=cv2.VideoCapture(ruta_video+self.nombre_video)
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
                cv2.imwrite(ruta+self.nombre+'/img_{}.jpg'.format(uuid.uuid4()),rostro)

        #liberar recursos del hardware
        self.verificar_cantidad_archivos()
        cap.release()
        return True
    

    #Esta funcion se encarga de entrenar un modelo deacuerdo a las imagenes que se hallan segmentado
    def entrenar(self):
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

                print(fileName)

                facesdata.append(cv2.imread(personRuta+'/'+fileName,0))
                labels.append(label)
            label=label+1


        #EigenFaceRecognizer, LBPHFaceRecognizer_create y FisherFaceRecognizer_create
        #Existen mas modelos de reconocimientos aunquen utilizamos este por la rapidez y optimizacion de espacio
        face_recognizer=cv2.face.LBPHFaceRecognizer_create()

        print("Entrenando")

        face_recognizer.train(facesdata, np.array(labels))

        name_model="model{ooid}".format(ooid=uuid.uuid4())
        ruta_name_model="{rutamodel}{namemodel}.xml".format(rutamodel=ruta_models,namemodel=name_model)
        face_recognizer.write(ruta_name_model)

        print("Modelo guardado correctamente")

        return name_model
    


    def reconocer_imagen(self,base_64_image,nombre_modelo):

        lista_usuarios=os.listdir(ruta)


        face_recognizer=cv2.face.LBPHFaceRecognizer_create()

        #captura el video (el videocapture recibe la ruta donde esta el video)
        #image=cv2.imread("imagengrupal.jpg")
        buff= BytesIO(base64.b64decode(base_64_image))
        imagematriz = np.frombuffer(buff.getvalue(), np.uint8)
        image=cv2.imdecode(imagematriz, cv2.IMREAD_COLOR)
        #lee el modelo
        face_recognizer.read(ruta_models+nombre_modelo)

        result=None

        #cambia a escala de grises
        gray=cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

        faces=face_cascade.detectMultiScale(gray,
                                            scaleFactor=1.05, #indica la reduccion de la imagen (valores pequeño aseguran la deteccion)
                                            minNeighbors=5,  #indica los minimos de rectangulos que puede tener un rostro para ser detectado
                                            minSize=(10,10), #indica el tamaño minimo del objeto para ser detectado
                                            maxSize=(300,300)) #indica el tamaño maximo del objeto

        etiqueta_reconocida_all="N/A"
        reconocio=False
        for (x,y,w,h) in faces:

            cv2.rectangle(image,(x,y),(x+w,y+h),(0,255,0),2)
            
            rostro=gray[y:y+h,x:x+w]
            rostro=cv2.resize(rostro,(150,150),interpolation=cv2.INTER_CUBIC)
            #aqui hace la prediccion del rostro
            result=face_recognizer.predict(rostro)
            print(result)

            if(result[1]<=80):
                reconocio=True
                etiqueta_reconocida_all=lista_usuarios[result[0]]
                cv2.putText(image,'{}'.format(lista_usuarios[result[0]]),(x,y+40),2,1.1,(0,0,0),1,cv2.LINE_AA)
            else:
                cv2.putText(image,'{}'.format("D"),(x,y+40),2,1.1,(0,0,0),1,cv2.LINE_AA)
    
        
        imagenpillow = Image.fromarray(image) #convierte la imagen a matriz

        buffercode=BytesIO()
        imagenpillow.save(buffercode, format='png')
        buffercode.seek(0)
        
        base64img=base64.b64encode(buffercode.read()).decode('utf-8')
        #f = open(ruta_models+"myfile.txt",'w')
        #f.write(str(base64img))
        #f.close()

        #print(base64img)
        #print(type(image))
        #cv2.imshow('image',image)
        #cv2.imwrite(ruta_imagen_reconocimiento_all+'{}.png',image)
        #cv2.waitKey(0)
        #cv2.destroyAllWindows()
        return [etiqueta_reconocida_all,reconocio,base64img]



    #Esta funcion se encarga de reconocer los rostros con el modelo entrenado anteriormente
    """def reconocer(self,base_64_image,nombre_modelo):

        face_recognizer=cv2.face.LBPHFaceRecognizer_create()

        #captura el video (el videocapture recibe la ruta donde esta el video)
        cap=cv2.VideoCapture("video_jhon.mp4")

        #lee el modelo
        face_recognizer.read(ruta_models+nombre_modelo)
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
        print(result[1])"""

           