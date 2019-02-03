from django.urls import path
from . import views

urlpatterns = [
    path('', views.home, name = 'customer-home'),
    path('register', views.register, name = 'customer-register')
    
]
