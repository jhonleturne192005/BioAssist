import pathlib
import os


cont_files = 0
dir = "D:/jhon 9no semestre\CIENCIA, TECNOLOGÍA, SOCIEDAD E INNOVACIÓN/proyectofinal/reconocimiento_facial/imagenes/jhon"
for path in os.listdir(dir):
    if os.path.isfile(os.path.join(dir, path)):
        cont_files += 1
        print(os.path.join(dir, path))
