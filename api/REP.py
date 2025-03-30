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
from keras_preprocessing.image import ImageDataGenerator, img_to_array
import random

ruta=os.path.join(STATICFILES_DIRS[0],"imagenes/")
ruta_video=os.path.join(STATICFILES_DIRS[0],"video/")
ruta_models=os.path.join(STATICFILES_DIRS[0],"models/")
ruta_imagen_reconocimiento_all=os.path.join(STATICFILES_DIRS[0],"imgrec/")
face_cascade=cv2.CascadeClassifier(cv2.data.haarcascades+"haarcascade_frontalface_default.xml")
_NUMERO_IMAGENES=1000


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
        else:
            print("error no atributos")


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
        if not cont_files<=_NUMERO_IMAGENES:
            diferencia=cont_files-_NUMERO_IMAGENES
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
        #Data Augmentation
        cant_images_increased=15

        train_datagen = ImageDataGenerator(
            rotation_range=20,        # Rota imágenes aleatoriamente hasta 20 grados.
            zoom_range=0.2,           # Aplica zoom aleatorio (hasta un 20% de aumento/reducción).
            width_shift_range=0.1,     # Desplaza imágenes horizontalmente (hasta un 10% del ancho).
            height_shift_range=0.1,   # Desplaza imágenes verticalmente (hasta un 10% del alto).
            horizontal_flip=True,     # Voltea imágenes horizontalmente de forma aleatoria.
            vertical_flip=False        # No voltea imágenes verticalmente.
        )


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


        print("fase uno") 
        contaa=0

        while True:
            ret, frame=cap.read()
            
            #Si no hay frames cierra el while
            if ret==False:
                break
            
            #redimenciona los frames
            frame=imutils.resize(frame,width=300,height=350)
            #aplica escala de grises
            gray=cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
            #desenfocar imagen
            gray = cv2.GaussianBlur(gray, (3,3), 0)
            # Mejora el contraste en la imagen
            gray = cv2.equalizeHist(gray)  
            #saca una copia
            auxf=frame.copy()

            faces=face_cascade.detectMultiScale(gray,
                                            scaleFactor=1.3, #indica la reduccion de la imagen (valores pequeño aseguran la deteccion)
                                            minNeighbors=5,  #indica los minimos de rectangulos que puede tener un rostro para ser detectado
                                            minSize=(30,30), #indica el tamaño minimo del objeto para ser detectado
                                            maxSize=(1000,1000)) #indica el tamaño maximo del objeto

            for(x,y, width, height) in faces:
                contaa+=1
                #obtiene el rostro 
                rostro=auxf[y:y+height,x:x+width]
                #redimenciona
                rostro=cv2.resize(rostro,(720,720),interpolation=cv2.INTER_CUBIC)
                p=rostro/255
                p=np.expand_dims(p,axis=0)
                #guarda la imagen
                cv2.imwrite(ruta+self.nombre+'/img_{}.jpg'.format(uuid.uuid4()),rostro)

                cont=0

                for output_batch in train_datagen.flow(p, batch_size=1):
                    imagen = output_batch[0] * 255           
                    imgfinal = imagen.astype('uint8')  
                    cv2.imwrite(self.personpath+'/img_{}.jpg'.format(uuid.uuid4()),imgfinal)
                    cont+=1
                    if(cont>cant_images_increased):
                        break
        
            #evitar esperar mas tiempó a recorrer todos los frames
            #quitar si quieres recorrer todos los frames y adicionalmente generar mas imagenes segun la variable __NUMERO_IMAGENES
            lista_usuario=os.listdir(ruta+"/{}".format(self.nombre))
            longitud_lista=len(lista_usuario)
            if(longitud_lista>=_NUMERO_IMAGENES):
                break
        
        print("fase dos") 

        while True:
            lista_usuario=os.listdir(ruta+"/{}".format(self.nombre))
            longitud_lista=len(lista_usuario)
            print(len(lista_usuario))

            if(longitud_lista>=_NUMERO_IMAGENES):
                break

            posaleatoria = random.randint(0, longitud_lista-1)
            print(posaleatoria)
            image=cv2.imread(ruta+"/{0}/{1}".format(self.nombre,lista_usuario[posaleatoria]))
            imaged=cv2.resize(image,(720,720),interpolation=cv2.INTER_CUBIC)
            p=imaged/255
            p=np.expand_dims(p,axis=0)

            cont=0

            for output_batch in train_datagen.flow(p, batch_size=1):
                imagen = output_batch[0] * 255           
                imgfinal = imagen.astype('uint8')  
                cv2.imwrite(self.personpath+'/img_{}.jpg'.format(uuid.uuid4()),imgfinal)
                cont+=1
                if(cont>cant_images_increased):
                    break

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

                #print(fileName)

                facesdata.append(cv2.imread(personRuta+'/'+fileName,0))
                labels.append(label)
            label=label+1


        #EigenFaceRecognizer, LBPHFaceRecognizer_create y FisherFaceRecognizer_create
        #Existen mas modelos de reconocimientos aunquen utilizamos este por la rapidez y optimizacion de espacio
        #face_recognizer=cv2.face.LBPHFaceRecognizer_create(radius=2, neighbors=12, grid_x=10, grid_y=10)
        face_recognizer=cv2.face.LBPHFaceRecognizer_create(radius=1, neighbors=8, grid_x=8, grid_y=8)

        print("Entrenando")

        face_recognizer.train(facesdata, np.array(labels))

        name_model="model{ooid}".format(ooid=uuid.uuid4())
        ruta_name_model="{rutamodel}{namemodel}.xml".format(rutamodel=ruta_models,namemodel=name_model)
        face_recognizer.write(ruta_name_model)

        print("Modelo guardado correctamente")

        return name_model
    
    def reconocer_imagen(self,base_64_image,nombre_modelo):

        lista_usuarios=os.listdir(ruta)
        face_recognizer=cv2.face.LBPHFaceRecognizer_create(radius=1, neighbors=8, grid_x=8, grid_y=8)

        f=open(ruta_models+"/ott.txt","w")
        f.write(str(base_64_image))
        f.close()

        #captura el video (el videocapture recibe la ruta donde esta el video)
        #image=cv2.imread("imagengrupal.jpg")
        buff= BytesIO(base64.b64decode(base_64_image))
        imagematriz = np.frombuffer(buff.getvalue(), np.uint8)
        image=cv2.imdecode(imagematriz, cv2.IMREAD_COLOR)
        #lee el modelo
        face_recognizer.read(ruta_models+nombre_modelo)

        #redimenciona la imagen para mayor eficacia en el modelo (anchoxalto)
        image = cv2.resize(image, (300,350), interpolation=cv2.INTER_AREA)


        imagecopia=image.copy()

        result=None

        #cambia a escala de grises
        gray=cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
        gray = cv2.GaussianBlur(gray, (3,3), 0)
        gray = cv2.equalizeHist(gray)  # Mejora el contraste en la imagen

        faces=face_cascade.detectMultiScale(gray,
                                            scaleFactor=1.3, #indica la reduccion de la imagen (valores pequeño aseguran la deteccion)
                                            minNeighbors=5,  #indica los minimos de rectangulos que puede tener un rostro para ser detectado
                                            minSize=(30,30), #indica el tamaño minimo del objeto para ser detectado
                                            maxSize=(1000,1000)) #indica el tamaño maximo del objeto

        etiqueta_reconocida_all="N/A"
        reconocio=False
        for (x,y,w,h) in faces:

            cv2.rectangle(image,(x,y),(x+w,y+h),(0,255,0),2)
            
            rostro=gray[y:y+h,x:x+w]
            rostro=cv2.resize(rostro,(150,150),interpolation=cv2.INTER_CUBIC)
            #aqui hace la prediccion del rostro
            result=face_recognizer.predict(rostro)
            print(result)

            if(int(result[1])<=80): #ignoro decimales con int()
                reconocio=True
                etiqueta_reconocida_all=lista_usuarios[result[0]]
                cv2.putText(imagecopia,'{}'.format(lista_usuarios[result[0]]),(x,y+40),2,1.1,(0,0,0),1,cv2.LINE_AA)
            else:
                cv2.putText(imagecopia,'{}'.format("D"),(x,y+40),2,1.1,(0,0,0),1,cv2.LINE_AA)
    
        
        imagenpillow = Image.fromarray(imagecopia) #convierte la imagen a matriz

        buffercode=BytesIO()
        imagenpillow.save(buffercode, format='png')
        buffercode.seek(0)
        
        base64img=base64.b64encode(buffercode.read()).decode('utf-8')
       

        f=open(ruta_models+"/ott.txt","w")
        f.write(str(base64img))
        f.close()

        return [etiqueta_reconocida_all,reconocio,base64img]