##
import random
import datetime
import csv
import os
import progressbar







bar1 = progressbar.ProgressBar(max_value=13)
bar1.update(0)

entradas=0

nombre = ["Maria","Jose","Luis","Luz","Ana","Carlos","Juan","Luz Marina","Juan Carlos","Maria del Carmen"
    ,"Luis Alberto","Carlos Alberto","Luis Eduardo","Sandra Milena","Ana Maria","Santiago","Valentina","Sebastian"
    ,"Daniela","Mariana","Natalia","Alejandro","Nicolas","Samuel","Camila","Juan Camilo", "Maria Alejandra"
    ,"Juan David","Juan Esteban","Monica","Paula","Melisa","Marcela","Jorge","Enrique","Antonio","Antonia","Paulina"
    ,"Maria Paula","Gloria","Liliana","Claudia","Olga","Cecilia","Julian","Hernando","Valeria","Edgar","Andrés"
    ,"Felipe","Andrea","Laura","Isabella","Isabel","Maria Isabel","Veronica","Federico","Juliana","Juana","Angela"
    ,"Sofia","Bernardo","Leonardo","Manuela","Manuel","Tania","Nicole","Christian","German","Maria Camila","Ana Sofia"
    ,"William","Oscar","Adrian","Angie","Fernando","Joaquin","Jairo","Tomas","Eva","Maria Juliana","Hernan","Elizabeth"
    ,"Socorro","Luz Mila","Daniel","Francisco","Ricardo","Pedro","Katherine","Amparo","Juan Felipe","Rafael","Saul"
    ,"Jessica","Nelly","Alberto","David","Sonia","Rocio","Ariel","Guillermo","Mario","Martha","Mateo","Ivan"]
rolesUsuario = ["Profesor", "Profesor Invitado", "Estudiante", "Egresado", "Empleado", "Padre Estudiante", "Invitado" ]

rolesPersonaJuridica=["Hotel", "Hostal", "Vivienda"]

rolesPersonaNatural=["Miembro Comunidad", "Vecino"]

tiposInmueble=["Vivienda", "Habitacion Hotel", "Habitacion Hostal", "Apartamento", "Habitacion Vivienda"]
habHot=["Estandar", "Semisuite", "Suite"]

serviciosMenajes = {'Servicio' : ['Consulta médica general', 'Consulta odontológica', 'Consulta con enfermería',
                                  'Urgencias', 'Consulta de control', 'Oftalmología', 'Ginecología', 'Pedriatría'
                                  'Medicina interna', 'Ortopedia','Urología', 'Dermatología', 'Oncología', 'Psicología',
                                  'Psiquiatría', 'Neurología', 'Rayos X', 'Ecografía', 'Tomografía', 'Piscina',
                                  'Limpieza'],
                    "Menaje": ['Endoscopia','Citología' , 'Hemograma', 'Electrocardiograma','Resonancia magnética',
                               'UCI', 'Hospitalización por cirugía', 'Unidad de quemados',
                               'Hospitalización por tratami ento', 'Quimioterapia', 'Radioterapia',
                               'Terapia respiratoria', 'Fisioterapia', 'Diálisis', 'Trasplante', 'Intervención',
                               'Cirugía ambulatoria', 'Cuchara', 'Radio']
                    }



if os.path.exists("servicioMenaje.csv"):
    os.remove("servicioMenaje.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
file = open("servicioMenaje.csv", "w")



for servicioMenaje in serviciosMenajes['Servicio']:
    file.writelines(servicioMenaje+",Servicio\n")
    entradas+=1
for servicioMenaje in serviciosMenajes['Menaje']:
    file.writelines(servicioMenaje + ",Menaje\n")
    entradas+=1
file.writelines("\n")

file.close()
print("Servicios y Menajes creados ")
bar1.update(1)
if os.path.exists("usuarios.csv"):
    os.remove("usuarios.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
file = open("usuarios.csv", "w")


i = 0
usuarios={}
while i<100000:
    nom= random.choice(nombre)
    rol= random.choice(rolesUsuario)
    L=  str(i+1)+ ","+ nom+"," +nom+ str(i)+"@hotmail.com"+", 8468291264"+"," + rol
    if rol in usuarios:
        lista= usuarios[rol]
        lista.append(str(i+1))
        usuarios[rol]=lista
    else:
        lista = []
        lista.append(str(i + 1))
        usuarios[rol] = lista
    file.writelines(L)
    entradas+=1
    file.writelines("\n")
    i += 1
file.writelines("\n")
file.close()
print("Usuarios creados ")
bar1.update(2)
if os.path.exists("personaJuridicaCategoriaNULL.csv"):
    os.remove("personaJuridicaCategoriaNULL.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
filePersonaJuridicaCategoriaNULL = open("personaJuridicaCategoriaNULL.csv", "w")

if os.path.exists("personaJuridicaPrecioNULL.csv"):
    os.remove("personaJuridicaPrecioNULL.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
filePersonaJuridicaPrecioNULL = open("personaJuridicaPrecioNULL.csv", "w")

if os.path.exists("personaJuridicaAmbosNULL.csv"):
    os.remove("personaJuridicaAmbosNULL.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
filePersonaJuridicaAmbosNULL = open("personaJuridicaAmbosNULL.csv", "w")

if os.path.exists("operador.csv"):
    os.remove("operador.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileOperador = open("operador.csv", "w")

i = 0
personaJuridica={}

while i<60000:
    nom= random.choice(nombre)
    rol= random.choice(rolesPersonaJuridica)
    L= str(i+1)+","+ nom+ ","+ nom+str(i)+"@amazonaws.com"+", 6167445220"
    LJ=""
    if rol=="Hotel":
        LJ= str(i+1)+","+ str(i+1)+","+ str(i+1)+"," + str(random.randint(1,5))+","+ rol
        filePersonaJuridicaPrecioNULL.writelines(LJ)
        filePersonaJuridicaPrecioNULL.writelines("\n")

    elif rol== "Hostal":
        LJ = str(i + 1) + "," + str(i + 1) + "," + str(i + 1) + ","+ str(random.randint(50,500000))+"," + rol
        filePersonaJuridicaCategoriaNULL.writelines(LJ)
        filePersonaJuridicaCategoriaNULL.writelines("\n")
    else:
        LJ = str(i + 1) + "," + str(i + 1) + "," + str(i + 1) + " ," + rol
        filePersonaJuridicaAmbosNULL.writelines(LJ)
        filePersonaJuridicaAmbosNULL.writelines("\n")
    if rol in personaJuridica:
        lista= personaJuridica[rol]
        lista.append(str(i+1))
        personaJuridica[rol]=lista
    else:
        lista = []
        lista.append(str(i + 1))
        personaJuridica[rol] = lista
    fileOperador.writelines(L)
    entradas+=1
    fileOperador.writelines("\n")

    entradas+=1

    i += 1

filePersonaJuridicaPrecioNULL.close()
filePersonaJuridicaPrecioNULL.close()
filePersonaJuridicaAmbosNULL.close()
print("Personas Juridicas creadas")
bar1.update(3)
if os.path.exists("personaNatural.csv"):
    os.remove("personaNatural.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
filePersonaNatural = open("personaNatural.csv", "w")




# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
#fileOperador = open("operador.csv", "w")

personaNatural={}

while i<120000:#150000:

    nom= random.choice(nombre)
    rol= random.choice(rolesPersonaNatural)
    L= str(i+1)+","+ nom+ ","+ nom+str(i)+"@amazonaws.com"+", 6167445220"
    LJ=str(i+1)+","+rol


    if rol in personaNatural:
        lista= personaNatural[rol]
        lista.append(str(i+1))
        personaNatural[rol]=lista
    else:
        lista = []
        lista.append(str(i + 1))
        personaNatural[rol] = lista
    fileOperador.writelines(L)
    entradas+=1
    fileOperador.writelines("\n")
    filePersonaNatural.writelines(LJ)
    entradas+=1
    filePersonaNatural.writelines("\n")
    i += 1


fileOperador.close()
filePersonaNatural.close()
print("Personas naturales creadas")

bar1.update(4)

if os.path.exists("inmueble.csv"):
    os.remove("inmueble.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileInmueble = open("inmueble.csv", "w")


if os.path.exists("apartamento.csv"):
    os.remove("apartamento.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileApartamento = open("apartamento.csv", "w")


if os.path.exists("habitacion.csv"):
    os.remove("habitacion.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileHabitacion = open("habitacion.csv", "w")


if os.path.exists("vivienda.csv"):
    os.remove("vivienda.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileVivienda = open("vivienda.csv", "w")


if os.path.exists("habitacionHostal.csv"):
    os.remove("habitacionHostal.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileHabitacionHostal = open("habitacionHostal.csv", "w")

if os.path.exists("habitacionHotel.csv"):
    os.remove("habitacionHotel.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileHabitacionHotel = open("habitacionHotel.csv", "w")


if os.path.exists("habitacionVivienda.csv"):
    os.remove("habitacionVivienda.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileHabitacionVivienda = open("habitacionVivienda.csv", "w")


i = 1
inmueble={}

listaCom=personaNatural["Miembro Comunidad"]

listaVecino=personaNatural["Vecino"]
listaAmbas=listaCom+listaVecino
listaHotel= personaJuridica["Hotel"]
listaHostal= personaJuridica["Hostal"]
listaVivienda= personaJuridica["Vivienda"]

while i<100000:

    tipo= random.choice(tiposInmueble)
    if i % 6 == 0:
        tipo = "Apartamento"
    elif i % 6 == 1:
        tipo = "Habitacion"
    elif i % 6 == 2:
        tipo = "Vivienda"
    elif i % 6 == 3:
        tipo = "Habitacion Hostal"
    elif i % 6 == 4:
        tipo = "Habitacion Vivienda"
    elif i % 6 == 5:
        tipo = "Habitacion Hotel"
    L=""
    LJ=""
    if tipo=="Apartamento":
        dueno = str(random.choice(listaCom))
        L = str(i)+", 86656 Roth Alley,Apartamento,"+ str(random.randint(1,15))+","+ str(1)+""
        LJ= str(i)+","+ str(random.randint(0,1))+ ","+ str(random.randint(100000,500000))+","+ dueno
        fileInmueble.writelines(L)
        entradas+=2
        fileInmueble.writelines("\n")
        fileApartamento.writelines(LJ)
        fileApartamento.writelines("\n")
    elif tipo=="Habitacion":
        dueno = str(random.choice(listaAmbas))
        L = str(i) + ", 86656 Roth Alley,Habitacion," + str(random.randint(1, 15)) + "," + str(1) + ""
        LJ = str(i)+","+str(random.randint(200,1200))+","+str(random.randint(400000,600000))+","+dueno
        fileInmueble.writelines(L)
        entradas += 2
        fileInmueble.writelines("\n")
        fileHabitacion.writelines(LJ)
        fileHabitacion.writelines("\n")
    elif tipo=="Vivienda":
        dueno = str(random.choice(listaAmbas))
        L =  str(i) + ", 86656 Roth Alley,Vivienda," + str(random.randint(1, 15)) + "," + str(1) + ""
        LJ = str(i)+","+str(random.randint(1,10))+","+str(random.randint(50000,900000))+","+str(0)+", "+dueno
        fileInmueble.writelines(L)
        entradas += 2
        fileInmueble.writelines("\n")
        fileVivienda.writelines(LJ)
        fileVivienda.writelines("\n")
    elif tipo=="Habitacion Hostal":
        dueno = str(random.choice(listaHostal))
        L = str(i) + ", 86656 Roth Alley,Habitacion Hostal," + str(random.randint(1, 15)) + "," + str(1) + ""
        LJ= str(i)+","+dueno+","+str(random.randint(1,100))
        fileInmueble.writelines(L)
        entradas += 2
        fileInmueble.writelines("\n")
        fileHabitacionHostal.writelines(LJ)
        fileHabitacionHostal.writelines("\n")
    elif tipo== "Habitacion Hotel":
        dueno = str(random.choice(listaHotel))
        L = str(i) + ", 86656 Roth Alley,Habitacion Hotel," + str(random.randint(1, 15)) + "," + str(1) + ""
        LJ = str(i)+","+dueno+","+str(random.randint(1,100)) +","+str(random.choice(habHot))+","+str(random.randint(100000,500000))+str(random.randint(200,500))+","+str(random.randint(100,200))+",avenida siempre viva # 123"
        fileInmueble.writelines(L)
        entradas += 2
        fileInmueble.writelines("\n")
        fileHabitacionHotel.writelines(LJ)
        fileHabitacionHotel.writelines("\n")
    elif tipo=="Habitacion Vivienda":
        dueno = str(random.choice(listaVivienda))
        L =  str(i) + ", 86656 Roth Alley,Habitacion Vivienda," + str(random.randint(1, 15)) + "," + str(1) + ""
        LJ = str(i)+","+dueno+","+str(random.randint(1,200))+","+str(random.randint(1000000,2000000))+","+str(random.randint(100000,200000))+","+str(random.randint(10,20))+",In a galaxy far far away,"+str(random.randint(0,1))
        fileInmueble.writelines(L)
        entradas += 2
        fileInmueble.writelines("\n")
        fileHabitacionVivienda.writelines(LJ)
        fileHabitacionVivienda.writelines("\n")
    if tipo in inmueble:
        lista = inmueble[tipo]
        lista.append((i, dueno))
    else:
        lista = []
        lista.append((i, dueno))
        inmueble[tipo] = lista




    i += 1


fileInmueble.close()
fileApartamento.close()
fileHabitacion.close()
fileHabitacionHostal.close()
fileHabitacionHotel.close()
fileHabitacionVivienda.close()
print("Inmuebles creados")
bar1.update(5)
if os.path.exists("horario.csv"):
    os.remove("horario.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
file = open("horario.csv", "w")


for hostal in personaJuridica['Hostal']:
    for dia in ['L', 'M', "I", "J", "V", "S", "D"]:
        hA = random.randint(5, 10)
        hC = random.randint(17, 23)
        file.writelines(str(hostal)+"," + dia +
                        "," + str(hA) + "," + str(hC) + "\n")
        entradas += 1
file.writelines("\n")
file.close()
print("Horarios creados")
bar1.update(6)
if os.path.exists("ofreceServicioCostoNULL.csv"):
    os.remove("ofreceServicioCostoNULL.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileofreceServicioCostoNULL = open("ofreceServicioCostoNULL.csv", "w")

if os.path.exists("ofreceServicioCantidadNULL.csv"):
    os.remove("ofreceServicioCantidadNULL.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
fileofreceServicioCantidadNULL = open("ofreceServicioCantidadNULL.csv", "w")


for tipoInmueble in tiposInmueble:
    for id, dueno in inmueble[tipoInmueble]:
        number_services = random.randint(0, 1)
        number_menajes = random.randint(0, 1)
        listServicios = serviciosMenajes['Servicio'].copy()
        listMenajes = serviciosMenajes['Menaje'].copy()
        random.shuffle(listServicios)
        random.shuffle(listMenajes)
        for i in range(0, number_services):
            servicio = listServicios[i]
            costo = random.random()*20000
            fileofreceServicioCantidadNULL.writelines(servicio+","+str(id)+","+str(costo)+"\n")
            entradas+=1
        for i in range(0, number_menajes):
            menaje = listMenajes[i]
            cantidad = random.randint(1, 20)
            fileofreceServicioCostoNULL.writelines(menaje+","+str(id)+"," + str(cantidad)+"\n")
            entradas+=1
fileofreceServicioCostoNULL.writelines("\n")
fileofreceServicioCantidadNULL.writelines("\n")
fileofreceServicioCantidadNULL.close()
fileofreceServicioCostoNULL.close()
print("Ofrece servicios creados")
bar1.update(7)
if os.path.exists("reserva.csv"):
    os.remove("reserva.csv")

# Archivo en el que se escriben las sentencias SQL para la poblacion de datos.
file = open("reserva.csv", "w")


def random_date(fromDate= None, toDate= None):
    delta = toDate - fromDate
    diffDays = delta.days
    selected = random.randint(0, diffDays)
    return fromDate+datetime.timedelta(days=selected)

reservasUsuarios = {}
todos = []
for key in usuarios.keys():
    todos += usuarios[key]
fechaInicio = datetime.datetime(2019, 1, 1)
fechaFin = datetime.datetime(2030, 1, 1)
random.shuffle(todos)
bar2 = progressbar.ProgressBar(max_value=len(inmueble['Apartamento'])+1)
count = 0
bar2.update(count)
numero_reservas = 120
i = 1
for apto, dueno in inmueble['Apartamento']:
    ultimaFechaApto = None
    for j in range(0, numero_reservas):
        usuario = random.choice(todos)
        if ultimaFechaApto is None:
            fC = random_date(fechaInicio, fechaFin)
        else:
            fC = random_date(ultimaFechaApto+datetime.timedelta(days=1), fechaFin)
        fF = fC+datetime.timedelta(days=31)
        fCan = fC-datetime.timedelta(days=8)
        notOverlapping = True
        if usuario in reservasUsuarios:
            for reserva in reservasUsuarios[usuario]:
                if (reserva[0] <= fC <= reserva[1]) or (reserva[0] <= fF <= reserva[1]):
                    notOverlapping = False
                    break
        if notOverlapping:
            ultimaFechaApto = fF
            file.writelines(str(i)+","+fC.strftime("%d-%b-%Y")+","+fF.strftime("%d-%b-%Y")+","+ str(random.random()*20000)+","+fCan.strftime("%d-%b-%Y") + ",0,"+str(random.randint(1, 10))+","+str(2*random.randint(0, 1))+","+str(dueno)+","+str(usuario)
                            + ","+str(apto)+",0\n")
            entradas+=1
            if usuario in reservasUsuarios:
                reservasUsuarios[usuario].append((fC, fF))
            else:
                lista = list()
                lista.append((fC, fF))
                reservasUsuarios[usuario] = lista
            i += 1
        if ultimaFechaApto is not None and (ultimaFechaApto + datetime.timedelta(days=2)) >= fechaFin:
            break
    count += 1
    # if count%100 == 0:
    bar2.update(count)
file.writelines("\n")



print("Termino aptos")
bar1.update(8)
for habitacion, dueno in inmueble['Habitacion']:
    ultimaFechaApto = None
    for j in range(0, numero_reservas):
        usuario = random.choice(todos)
        if ultimaFechaApto is None:
            fC = random_date(fechaInicio, fechaFin)
        else:

            fC = random_date(ultimaFechaApto+datetime.timedelta(days=1), fechaFin)
        fF = fC+datetime.timedelta(days=31)
        fCan = fC-datetime.timedelta(days=8)
        notOverlapping = True
        if usuario in reservasUsuarios:
            for reserva in reservasUsuarios[usuario]:
                if (reserva[0] <= fC <= reserva[1]) or (reserva[0] <= fF <= reserva[1]):
                    notOverlapping = False
                    break
        if notOverlapping:
            ultimaFechaApto = fF
            file.writelines(str(i)+","+fC.strftime("%d-%b-%Y")+","+fF.strftime("%d-%b-%Y")+","+ str(random.random()*20000)+","+fCan.strftime("%d-%b-%Y") + ",0,"+str(random.randint(1, 10))+","+str(2*random.randint(0, 1))+","+str(dueno)+","+str(usuario)
                            + ","+str(habitacion)+",0\n")
            entradas+=1
            if usuario in reservasUsuarios:
                reservasUsuarios[usuario].append((fC, fF))
            else:
                lista = list()
                lista.append((fC, fF))
                reservasUsuarios[usuario] = lista
            i += 1
        if ultimaFechaApto is not None and (ultimaFechaApto + datetime.timedelta(days=2)) >= fechaFin:
            break
file.writelines("\n")

print("Termino habitacion")
bar1.update(9)
for habitacionhotel, dueno in inmueble['Habitacion Hotel']:
    ultimaFechaApto = None
    for j in range(0, numero_reservas):
        usuario = random.choice(todos)
        if ultimaFechaApto is None:

            fC = random_date(fechaInicio, fechaFin)
        else:

            fC = random_date(ultimaFechaApto+datetime.timedelta(days=1), fechaFin)
        fF = fC+datetime.timedelta(days=31)
        fCan = fC-datetime.timedelta(days=8)
        notOverlapping = True
        if usuario in reservasUsuarios:
            for reserva in reservasUsuarios[usuario]:
                if (reserva[0] <= fC <= reserva[1]) or (reserva[0] <= fF <= reserva[1]):
                    notOverlapping = False
                    break
        if notOverlapping:
            ultimaFechaApto = fF
            file.writelines(str(i)+","+fC.strftime("%d-%b-%Y")+","+fF.strftime("%d-%b-%Y")+","+ str(random.random()*20000)+","+fCan.strftime("%d-%b-%Y") + ",0,"+str(random.randint(1, 10))+","+str(2*random.randint(0, 1))+","+str(dueno)+","+str(usuario)
                            + ","+str(habitacionhotel)+",0\n")
            entradas+=1
            if usuario in reservasUsuarios:
                reservasUsuarios[usuario].append((fC, fF))
            else:
                lista = list()
                lista.append((fC, fF))
                reservasUsuarios[usuario] = lista
            i += 1
        if ultimaFechaApto is not None and (ultimaFechaApto + datetime.timedelta(days=2)) >= fechaFin:
            break
file.writelines("\n")

print("Termino habitacion hotel")
bar1.update(10)
for habitacionhostal, dueno in inmueble['Habitacion Hostal']:
    ultimaFechaApto = None
    for j in range(0, numero_reservas):
        usuario = random.choice(todos)
        if ultimaFechaApto is None:

            fC = random_date(fechaInicio, fechaFin)
        else:

            fC = random_date(ultimaFechaApto+datetime.timedelta(days=1), fechaFin)
        fF = fC+datetime.timedelta(days=2)
        fCan = fC-datetime.timedelta(days=3)
        notOverlapping = True
        if usuario in reservasUsuarios:
            for reserva in reservasUsuarios[usuario]:
                if (reserva[0] <= fC <= reserva[1]) or (reserva[0] <= fF <= reserva[1]):
                    notOverlapping = False
                    break
        if notOverlapping:
            ultimaFechaApto = fF
            file.writelines(str(i)+","+fC.strftime("%d-%b-%Y")+","+fF.strftime("%d-%b-%Y")+","+ str(random.random()*20000)+","+fCan.strftime("%d-%b-%Y") + ",0,"+str(random.randint(1, 10))+","+str(2*random.randint(0, 1))+","+str(dueno)+","+str(usuario)
                            + ","+str(habitacionhostal)+",0\n")
            entradas+=1
            if usuario in reservasUsuarios:
                reservasUsuarios[usuario].append((fC, fF))
            else:
                lista = list()
                lista.append((fC, fF))
                reservasUsuarios[usuario] = lista
            i += 1
        if ultimaFechaApto is not None and (ultimaFechaApto + datetime.timedelta(days=2)) >= fechaFin:
            break
file.writelines("\n")

print("Termino habitacion hostal")
bar1.update(11)
todos = []
permitidosVivienda = ["Profesor", "Profesor Invitado", "Estudiante", "Empleado"]
for key in usuarios.keys():
    if key in permitidosVivienda:
        todos += usuarios[key]

for habitacionvivienda, dueno in inmueble['Habitacion Vivienda']:
    ultimaFechaApto = None
    for j in range(0, numero_reservas):
        usuario = random.choice(todos)
        if ultimaFechaApto is None:

            fC = random_date(fechaInicio, fechaFin)
        else:

            fC = random_date(ultimaFechaApto+datetime.timedelta(days=1), fechaFin)
        fF = fC+datetime.timedelta(days=2)
        fCan = fC-datetime.timedelta(days=3)
        notOverlapping = True
        if usuario in reservasUsuarios:
            for reserva in reservasUsuarios[usuario]:
                if (reserva[0] <= fC <= reserva[1]) or (reserva[0] <= fF <= reserva[1]):
                    notOverlapping = False
                    break
        if notOverlapping:
            ultimaFechaApto = fF
            file.writelines(str(i)+","+fC.strftime("%d-%b-%Y")+","+fF.strftime("%d-%b-%Y")+","+ str(random.random()*20000)+","+fCan.strftime("%d-%b-%Y") + ",0,"+str(random.randint(1, 10))+","+str(2*random.randint(0, 1))+","+str(dueno)+","+str(usuario)
                            + ","+str(habitacionhostal)+",0\n")
            entradas+=1
            if usuario in reservasUsuarios:
                reservasUsuarios[usuario].append((fC, fF))
            else:
                lista = list()
                lista.append((fC, fF))
                reservasUsuarios[usuario] = lista
            i += 1
        if ultimaFechaApto is not None and (ultimaFechaApto + datetime.timedelta(days=2)) >= fechaFin:
            break
file.writelines("\n")

print("Termino habitacion vivienda")
bar1.update(12)

print("Reservas creadas")

file.writelines("COMMIT\n")
file.close()
print(entradas)


