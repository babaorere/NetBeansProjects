JFLAGS = -g
JC = javac
JVM= java 

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = AppMain.java Druida.java IEnemigo.java INPC.java Jugador.java Malo.java Montana.java Planicie.java \
 Bosque.java Enemigo.java IJugador.java ITierra.java Mago.java Mision.java Neutro.java Tierra.java \
 Bueno.java Guerrero.java Mision.java Jefe_Final.java Monstruo.java NPC.java

default: classes

classes: $(CLASSES:.java=.class)

run:
	java AppMain
        
clean: $(RM) *.class

