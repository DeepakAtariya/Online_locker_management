from django.shortcuts import render
from django.http import HttpResponse 


# Create your views here.
def home(request):
    return HttpResponse('<h1>Cutomer</<h1>')

def register(request):
    # return HttpResponse('<h1>Register</<h1>')
    return render(request, 'customer/register.html')
