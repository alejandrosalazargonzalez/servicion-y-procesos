# Ejecución y comprensión de procesos en linux

## Bloque 1

1. Define qué es un proceso y en qué se diferencia de un programa  
   Un proceso es un programa en ejecucion y un programa son las intrucciones para que el dispositivo sepa que hacer con los datos entregados.  
2. Explica qué es el kernel y su papel en la gestión de procesos.  
    El kernel se encarga de gestionar los recursos de los procesos, parar los mismos y decidir su orden de ejecucion  
3. ¿Qué son PID y PPID? Explica con un ejemplo.  
    PID es el id del proceso y el PPID es el id del proceso padre.  
4. Describe qué es un cambio de contexto y por qué es costoso.  
    Porque hay que guardar mucha informacion para ello.  
5. Explica qué es un PCB (Process Control Block) y qué información almacena.  
    Es un registro que guarda la informacion y el estado de los procesos antes de cerrarlos.  
6. Diferencia entre proceso padre y proceso hijo.
7. Explica qué ocurre cuando un proceso queda huérfano en Linux.
8. ¿Qué es un proceso zombie? Da un ejemplo de cómo puede ocurrir.
9.  Diferencia entre concurrencia y paralelismo.
10. Explica qué es un hilo (thread) y en qué se diferencia de un proceso.

## Bloque 2

11. Usa echo $$ para mostrar el PID del proceso actual.

12. Usa echo $PPID para mostrar el PID del proceso padre.

13. Ejecuta pidof systemd y explica el resultado.

14. Abre un programa gráfico (ejemplo: gedit) y usa pidof para obtener sus PID.

15. Ejecuta ps -e y explica qué significan sus columnas.

16. Ejecuta ps -f y observa la relación entre procesos padre e hijo.

17. Usa ps -axf o pstree para mostrar el árbol de procesos y dibújalo.

18. Ejecuta top o htop y localiza el proceso con mayor uso de CPU.

19. Ejecuta sleep 100 en segundo plano y busca su PID con ps.

20. Finaliza un proceso con kill y comprueba con ps que ya no está.

## Bloque 3

21. Identifica el PID del proceso init/systemd y explica su función.
22. Explica qué ocurre con el PPID de un proceso hijo si su padre termina antes.
23. Ejecuta un programa que genere varios procesos hijos y observa sus PIDs con ps.
24. Haz que un proceso quede en estado suspendido con Ctrl+Z y réanúdalo con fg.
25. Lanza un proceso en segundo plano con & y obsérvalo con jobs.
26. Explica la diferencia entre los estados de un proceso: Running, Sleeping, Zombie, Stopped.
27. Usa ps -eo pid,ppid,stat,cmd para mostrar los estados de varios procesos.
28. Ejecuta watch -n 1 ps -e y observa cómo cambian los procesos en tiempo real.
29. Explica la diferencia entre ejecutar un proceso con & y con nohup.
30. Usa ulimit -a para ver los límites de recursos de procesos en tu sistema.
