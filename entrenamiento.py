import cv2
import os
import numpy as np

data="D:\jhon 9no semestre\CIENCIA, TECNOLOGÍA, SOCIEDAD E INNOVACIÓN\proyectofinal\reconocimiento_facial\imagenes"

lista_person=os.listdir(data)

print("Lista personas",lista_person)

labels=[]
facesdata=[]

label=0


for nombre_person in lista_person:
    personRuta=data+"/"+nombre_person
    print("Leyendo.......")

    for fileName in os.listdir(personRuta):
        print("Rostros: ",nombre_person+"/"+fileName)
        labels.append(label)

        facesdata.append(cv2.imread(personRuta+'/'+fileName,0))
        #image=cv2.imread(personRuta+"/"+fileName,0)
        #############
        #cv2.imshow("image",image)
        #cv2.waitKey(10)
        ######

    label=label+1

cv2.destroyAllWindows()

#####################
print('labels= ',labels)



face_recognizer=cv2.face.LBPHFaceRecognizer_create()

print("Entrenando")

face_recognizer.train(facesdata, np.array(labels))


face_recognizer.write("reconocedorjhon.xml")

print("Modelo guardado")