from django.urls import path,include
from api.views import  *


urlpatterns = [
    path('prueba/', prueba),
    path('asignar_recurso/', asignar_recurso),
    path('entrenar_modelo/', entrenar_modelo),
    path('reconocer/', reconocer),
]