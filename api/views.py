from django.shortcuts import render
from rest_framework.decorators import api_view
import json
from rest_framework.response import Response
from rest_framework import status
from api.REP import *
import uuid
# Create your views here.

__ERROR_SISTEMA='Ha ocurrido un error en el sistema'




@api_view(['POST'])
def prueba(request):
    data={}

    if request.method == 'POST':
        print(request.data["saludo"])
        data["success"]="todo realizado con exito"
        data["estado"]=True
        data=json.dumps(data) #convierte en cadena json
        return Response(data, status=status.HTTP_200_OK)
    else:
        data["error"]=__ERROR_SISTEMA
        data=json.dumps(data) #convierte en cadena j


@api_view(['POST'])
def asignar_recurso(request):
    data={}

    if request.method == 'POST':
        #print(request.data)
        video_base_64=request.data["video"]
        #print(video_base_64)
        nombre_usuario_ooid=str(uuid.uuid4())
        rep=REP(nombre_usuario_ooid,video_base_64)
        rep.segmentar_video()
        data["success"]="todo realizado con exito"
        data["ooid"]=nombre_usuario_ooid
        data=json.dumps(data)
        return Response(data, status=status.HTTP_200_OK)
    else:
        data["error"]=__ERROR_SISTEMA
        data=json.dumps(data) #convierte en cadena json


@api_view(['POST'])
def entrenar_modelo(request):
    data={}

    if request.method == 'POST':
        #print(request.data)
        rep=REP(None,None)
        name_model=rep.entrenar()
        data["success"]="todo realizado con exito"
        data["nombre_modelo"]=name_model
        data=json.dumps(data) #convierte en cadena json
        return Response(data, status=status.HTTP_200_OK)
    else:
        data["error"]=__ERROR_SISTEMA
        data=json.dumps(data) #convierte en cadena json


@api_view(['POST'])
def reconocer(request):
    data={}

    if request.method == 'POST':
        #print(request.data)
        imagen_base_64=request.data["imagen"]
        modelo=request.data["model"]
        rep=REP(None,None)
        etiqueta_reconocimiento=rep.reconocer_imagen(imagen_base_64,modelo)
        data["success"]="todo realizado con exito"
        data["etiqueta"]=etiqueta_reconocimiento[0]
        data["reconocio"]=etiqueta_reconocimiento[1]
        data["base64img"]=etiqueta_reconocimiento[2]
        data=json.dumps(data) #convierte en cadena json
        return Response(data, status=status.HTTP_200_OK)
    else:
        data["error"]=__ERROR_SISTEMA
        data=json.dumps(data) #convierte en cadena json



"""@api_view(['POST'])
def reconocer_varios(request):
    data={}

    if request.method == 'POST':
        #print(request.data)
        imagen_base_64=request.data["imagen"]
        modelo=request.data["model"]
        rep=REP(None,None)
        etiqueta_reconocimiento=rep.reconocer_imagen(imagen_base_64,modelo)
        data["success"]="todo realizado con exito"
        data["etiqueta"]=etiqueta_reconocimiento[0]
        data["reconocioall"]=etiqueta_reconocimiento[1]
        data["base64img"]=etiqueta_reconocimiento[2]
        data=json.dumps(data) #convierte en cadena json
        return Response(data, status=status.HTTP_200_OK)
    else:
        data["error"]=__ERROR_SISTEMA
        data=json.dumps(data) #convierte en cadena json
"""
