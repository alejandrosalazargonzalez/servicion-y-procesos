# <img src=../../../../../images/computer.png width="40"> Code, Learn & Practice  

**Procesos y Servicios (modo alumno, sin root) — Trabajo en `$HOME/dam` con *user units* de systemd**

> **Importante:** Esta guía está adaptada para **usuarios sin privilegios de root**.  
> Todo se hace **en tu carpeta** `~/dam` usando **systemd --user** (un administrador por usuario), sin tocar `/etc` ni `/usr/local`.  
> Pega **salidas reales** y responde a las preguntas cortas.

---

## Preparación

Crea tu área de trabajo y variables útiles:

```bash
mkdir -p ~/dam/{bin,logs,units}
export DAM=~/dam
echo 'export DAM=~/dam' >> ~/.bashrc
```

Comprobar versión de systemd y que el *user manager* está activo:

```bash
systemctl --user --version | head -n1
systemctl --user status --no-pager | head -n5
```

**Pega salida aquí:**

```text
 systemctl --user --version |head -n1
systemd 255 (255.4-1ubuntu8.6)

 systemctl --user status --no-pager |head -n5
● a108pc12
    State: running
    Units: 261 loaded (incl. loaded aliases)
     Jobs: 0 queued
   Failed: 0 units

```

**Reflexiona la salida:**

```text
El primer comando muestra la version actual del proceso systemctl que
inicia el sistema.

El segundo comprueba el estado del proceso systemctl.
```

---

## Bloque 1 — Conceptos (breve + fuentes)

1) ¿Qué es **systemd** y en qué se diferencia de SysV init?  

**Respuesta:** Ambos son sistemas de inicializacion, SysV fue uno de los primeros sistemas de inicializacion de linux y systemd es el más moderno.  
SysV se basa en un diseño secuencial donde los servicios se inician uno por uno, mientras que sistemd usa un enfoque parelelo y secuencial, pudiendo asi iniciar varios servicios simultaneamoente.

_Fuentes:_[maxizamorano](https://www.maxizamorano.com/entrada/19/proceso-de-arranque-en-linux-systemd-vs-sysv-init)

1) **Servicio** vs **proceso** (ejemplos).  

**Respuesta:** Los servicios son un subconjunto de procesos, sin interfaz grafica y gestionados por un programa central, como systemd en Linux. El propio systemd es un ejempro de servicio, no tiene interfaz y se ejecuta permanentemente en segundo plano, mientras que los procesos son aquellos programas que suelen tener interfaz grafica y no funcionan permanentemente, como juegos o buscadores.    

_Fuentes:_[reddit](https://www.reddit.com/r/explainlikeimfive/comments/1n6jm99/eli5_what_is_the_difference_between_a_process_and/?tl=es-419)

1) ¿Qué son los **cgroups** y para qué sirven?  

**Respuesta:** es una funcion del kernel de linux que permite agrupar perocesos y controlar los recursos que consumen, esta herramienta permite crear una gerarquia de control asiganando limites, y permitiendo al sistema monitorear, limitar, priorizar y aislar procesos de una forma más eficiente.  

_Fuentes:_[sergiobelkin](https://sergiobelkin.com/posts/que-son-los-cgroups-y-para-que-sirven/)

1) ¿Qué es un **unit file** y tipos (`service`, `timer`, `socket`, `target`)?  

**Respuesta:** un unit file es un archivo que le dice al sistema como gestionar los recursos con ciertos procesos. Los tipos contienen directivas especificas para cada tipo.  

_Fuentes:_ [suse](https://documentation.suse.com/es-es/sle-micro/6.0/html/Micro-systemd-basics/index.html)

5) ¿Qué hace `journalctl` y cómo ver logs **de usuario**?  

**Respuesta:** journalctl muestra registros de evento, tambien llamados ficheros log, se pueden filtrar los logs por id lo que permite ver los logs de un usuario concreto.  

_Fuentes:_[ionos](https://www.ionos.es/digitalguide/servidores/herramientas/que-es-journalctl/)

---

## Bloque 2 — Práctica guiada (todo en tu `$DAM`)

> Si un comando pide permisos que no tienes, usa la **versión `--user`** o consulta el **ayuda** con `--help` para alternativas.

### 2.1 — PIDs básicos

**11.** PID de tu shell y su PPID.

```bash
echo "PID=$$  PPID=$PPID"
```

**Salida:**

```text
echo "PID=$$ PPID=$PPID"
PID=8222 PPID=8213

```

**Pregunta:** ¿Qué proceso es el padre (PPID) de tu shell ahora?  

**Respuesta:** 8213   /usr/libexec/gnome-terminal-server

---

**12.** PID del `systemd --user` (manager de usuario) y explicación.

```bash
pidof systemd --user || pgrep -u "$USER" -x systemd
```

**Salida:**

```text
3314
```

**Pregunta:** ¿Qué hace el *user manager* de systemd para tu sesión?  

**Respuesta:** es un proceso systemd que se encarga de gestionar los servicios del usuario.

---

### 2.2 — Servicios **de usuario** con systemd

Vamos a crear un servicio sencillo y un timer **en tu carpeta** `~/.config/systemd/user` o en `$DAM/units` (usaremos la primera para que `systemctl --user` lo encuentre).

**13.** Prepara directorios y script de práctica.

```bash
mkdir -p ~/.config/systemd/user "$DAM"/{bin,logs}
cat << 'EOF' > "$DAM/bin/fecha_log.sh"
#!/usr/bin/env bash
mkdir -p "$HOME/dam/logs"
echo "$(date --iso-8601=seconds) :: hello from user timer" >> "$HOME/dam/logs/fecha.log"
EOF
chmod +x "$DAM/bin/fecha_log.sh"
```

**14.** Crea el servicio **de usuario** `fecha-log.service` (**Type=simple**, ejecuta tu script).

```bash
cat << 'EOF' > ~/.config/systemd/user/fecha-log.service
[Unit]
Description=Escribe fecha en $HOME/dam/logs/fecha.log

[Service]
Type=simple
ExecStart=%h/dam/bin/fecha_log.sh
EOF

systemctl --user daemon-reload
systemctl --user start fecha-log.service
systemctl --user status fecha-log.service --no-pager -l | sed -n '1,10p'
```
**Salida (pega un extracto):**

```text
○ fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log
     Loaded: loaded (/home/dam/.config/systemd/user/fecha-log.service; static)
     Active: inactive (dead)

sep 23 18:54:00 a108pc12 systemd[3314]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.

```
**Pregunta:** ¿Se creó/actualizó `~/dam/logs/fecha.log`? Muestra las últimas líneas:

```bash
tail -n 5 "$DAM/logs/fecha.log"
```

**Salida:**

```text
2025-09-23T18:54:00+01:00 :: hello from user timer
```

**Reflexiona la salida:**

```text
muestra el servicio creado en el ejercicio anterior, dando fecha, hora y un saludo desde el timer
```

---

**15.** Diferencia **enable** vs **start** (modo usuario). Habilita el **timer**.

```bash
cat << 'EOF' > ~/.config/systemd/user/fecha-log.timer
[Unit]
Description=Timer (usuario): ejecuta fecha-log.service cada minuto

[Timer]
OnCalendar=*:0/1
Unit=fecha-log.service
Persistent=true

[Install]
WantedBy=timers.target
EOF

systemctl --user daemon-reload
systemctl --user enable --now fecha-log.timer
systemctl --user list-timers --all | grep fecha-log || true
```

**Salida (recorta):**

```text
Created symlink /home/dam/.config/systemd/user/timers.target.wants/fecha-log.timer → /home/dam/.config/systemd/user/fecha-log.timer.
Tue 2025-09-23 19:00:00 WEST  50s -                                       - fecha-log.timer                fecha-log.service

```

**Pregunta:** ¿Qué diferencia hay entre `enable` y `start` cuando usas `systemctl --user`?  

**Respuesta:** enable configura el servicio para que se inicie con el sistema y start lo inicia directamente.

---

**16.** Logs recientes **del servicio de usuario** con `journalctl --user`.

```bash
journalctl --user -u fecha-log.service -n 10 --no-pager
```

**Salida:**

```text
sep 23 18:54:00 a108pc12 systemd[3314]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
sep 23 19:00:48 a108pc12 systemd[3314]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.
sep 23 19:01:48 a108pc12 systemd[3314]: Started fecha-log.service - Escribe fecha en $HOME/dam/logs/fecha.log.

```

**Pregunta:** ¿Ves ejecuciones activadas por el timer? ¿Cuándo fue la última?  

**Respuesta:** No se ven ejecuciones activas, pero se ve que la ultima ejecucion fue a las 19:01:48.

---

### 2.3 — Observación de procesos sin root

**17.** Puertos en escucha (lo que puedas ver como usuario).

```bash
lsof -i -P -n | grep LISTEN || ss -lntp
```

**Salida:**

```text
java      11409  dam   32u  IPv6  62910      0t0  TCP 127.0.0.1:64120 (LISTEN)
java      42272  dam   32u  IPv6 121071      0t0  TCP 127.0.0.1:64121 (LISTEN)
```

**Pregunta:** ¿Qué procesos *tuyos* están escuchando? (si no hay, explica por qué)  

**Respuesta:** los dos procesos que salen en la ejecucion del comando anterior estan escuchando

---

**18.** Ejecuta un proceso bajo **cgroup de usuario** con límite de memoria.

```bash
systemd-run --user --scope -p MemoryMax=50M sleep 200
ps -eo pid,ppid,cmd,stat | grep "[s]leep 200"
```

**Salida:**

```text
Running as unit: run-r83ad5e443361409898ce33545fa1097b.scope; invocation ID: 0b846b3ce0b747eb834f743ff3e437e9
```

**Pregunta:** ¿Qué ventaja tiene lanzar con `systemd-run --user` respecto a ejecutarlo “a pelo”?  

**Respuesta:**

---

**19.** Observa CPU en tiempo real con `top` (si tienes `htop`, también vale).

```bash
top -b -n 1 | head -n 15
```
**Salida (resumen):**

```text
top - 19:45:43 up  4:43,  1 user,  load average: 0,34, 0,77, 0,92
Tareas: 424 total,   1 ejecutar,  423 hibernar,    0 detener,    0 zombie
%Cpu(s):  1,4 us,  5,4 sy,  0,0 ni, 92,6 id,  0,7 wa,  0,0 hi,  0,0 si,  0,0 st 
MiB Mem :  31453,3 total,  12308,5 libre,   9748,4 usado,  10019,2 búf/caché    
MiB Intercambio:   2048,0 total,   2048,0 libre,      0,0 usado.  21704,9 dispon

    PID USUARIO   PR  NI    VIRT    RES    SHR S  %CPU  %MEM     HORA+ ORDEN
 139996 dam       20   0   17224   5760   3584 R   9,1   0,0   0:00.02 top
      1 root      20   0   23236  14404   9584 S   0,0   0,0   0:01.98 systemd
      2 root      20   0       0      0      0 S   0,0   0,0   0:00.02 kthreadd
      3 root      20   0       0      0      0 S   0,0   0,0   0:00.00 pool_wo+
      4 root       0 -20       0      0      0 I   0,0   0,0   0:00.00 kworker+
      5 root       0 -20       0      0      0 I   0,0   0,0   0:00.00 kworker+
      6 root       0 -20       0      0      0 I   0,0   0,0   0:00.00 kworker+
      7 root       0 -20       0      0      0 I   0,0   0,0   0:00.00 kworker+
```

**Pregunta:** ¿Cuál es tu proceso con mayor `%CPU` en ese momento?  

**Respuesta:** 139996 dam       20   0   17224   5760   3584 R   9,1   0,0   0:00.02 top

---

**20.** Traza syscalls de **tu propio proceso** (p. ej., el `sleep` anterior).
> Nota: Sin root, no podrás adjuntarte a procesos de otros usuarios ni a algunos del sistema.

```bash
pid=$(pgrep -u "$USER" -x sleep | head -n1)
strace -p "$pid" -e trace=nanosleep -tt -c -f 2>&1 | sed -n '1,10p'
```

**Salida (fragmento):**

```text
strace: must have PROG [ARGS] or -p PID
Try 'strace -h' for more information.

```

**Pregunta:** Explica brevemente la syscall que observaste.  

**Respuesta:** sale un error porque no encuentra el proceso

---

### 2.4 — Estados y jerarquía (sin root)

**21.** Árbol de procesos con PIDs.

```bash
pstree -p | head -n 50
```

**Salida (recorta):**

```text
systemd(1)-+-ModemManager(1854)-+-{ModemManager}(1864)
           |                    |-{ModemManager}(1867)
           |                    `-{ModemManager}(1869)
           |-NetworkManager(1827)-+-{NetworkManager}(1859)
           |                      |-{NetworkManager}(1860)
           |                      `-{NetworkManager}(1861)
           |-accounts-daemon(1172)-+-{accounts-daemon}(1196)
           |                       |-{accounts-daemon}(1197)
           |                       `-{accounts-daemon}(1824)
           |-agetty(2233)
           |-apache2(2289)-+-apache2(2317)
           |               |-apache2(2318)
           |               |-apache2(2319)
           |               |-apache2(2321)
           |               `-apache2(2322)
           |-at-spi2-registr(3677)-+-{at-spi2-registr}(3681)
           |                       |-{at-spi2-registr}(3682)
           |                       `-{at-spi2-registr}(3683)
           |-avahi-daemon(1174)---avahi-daemon(1254)
           |-blkmapd(1104)
           |-colord(2018)-+-{colord}(2025)
           |              |-{colord}(2026)
           |              `-{colord}(2028)
           |-containerd(1998)-+-{containerd}(2020)
           |                  |-{containerd}(2021)
           |                  |-{containerd}(2022)
           |                  |-{containerd}(2023)
           |                  |-{containerd}(2024)
           |                  |-{containerd}(2037)
           |                  |-{containerd}(2038)
           |                  |-{containerd}(2043)
           |                  |-{containerd}(2044)
           |                  |-{containerd}(2045)
           |                  |-{containerd}(2053)
           |                  |-{containerd}(2054)
           |                  |-{containerd}(2379)
           |                  |-{containerd}(2380)
           |                  `-{containerd}(2381)
           |-containerd-shim(3141)-+-apache2(3163)-+-apache2(3276)
           |                       |               |-apache2(3277)
           |                       |               |-apache2(3278)
           |                       |               |-apache2(3279)
           |                       |               `-apache2(3280)
           |                       |-{containerd-shim}(3142)
           |                       |-{containerd-shim}(3143)
           |                       |-{containerd-shim}(3144)
           |                       |-{containerd-shim}(3145)
           |                       |-{containerd-shim}(3146)
           |                       |-{containerd-shim}(3147)
           |                       |-{containerd-shim}(3148)
```

**Pregunta:** ¿Bajo qué proceso aparece tu `systemd --user`?  

**Respuesta:**  
  systemd(3306)─┬─(sd-pam)(3307)
           │               ├─chrome_crashpad(4934)─┬─{chrome_crashpad}(4935)
           │               │                       └─{chrome_crashpad}(4936)
           │               ├─code(4903)─┬─code(4912)───code(4951)─┬─{code}(4972+

---

**22.** Estados y relación PID/PPID.

```bash
ps -eo pid,ppid,stat,cmd | head -n 20
```

**Salida:**

```text
    PID    PPID STAT CMD
      1       0 Ss   /sbin/init splash
      2       0 S    [kthreadd]
      3       2 S    [pool_workqueue_release]
      4       2 I<   [kworker/R-rcu_g]
      5       2 I<   [kworker/R-rcu_p]
      6       2 I<   [kworker/R-slub_]
      7       2 I<   [kworker/R-netns]
     10       2 I<   [kworker/0:0H-events_highpri]
     12       2 I<   [kworker/R-mm_pe]
     13       2 I    [rcu_tasks_kthread]
     14       2 I    [rcu_tasks_rude_kthread]
     15       2 I    [rcu_tasks_trace_kthread]
     16       2 S    [ksoftirqd/0]
     17       2 I    [rcu_preempt]
     18       2 S    [migration/0]
     19       2 S    [idle_inject/0]
     20       2 S    [cpuhp/0]
     21       2 S    [cpuhp/1]
     22       2 S    [idle_inject/1]
```

**Pregunta:** Explica 3 flags de `STAT` que veas (ej.: `R`, `S`, `T`, `Z`, `+`).  

**Respuesta:** S significa que esta sleeping, la I es idle kernel, la s que es lider de la sesion y < que tiene alta prioridad.

---

**23.** Suspende y reanuda **uno de tus procesos** (no crítico).

```bash
# Crea un proceso propio en segundo plano
sleep 120 &
pid=$!
# Suspende
kill -STOP "$pid"
# Estado
ps -o pid,stat,cmd -p "$pid"
# Reanuda
kill -CONT "$pid"
# Estado
ps -o pid,stat,cmd -p "$pid"
```

**Pega los dos estados (antes/después):**

```text
    PID STAT CMD
  35613 T    sleep 1200

    PID STAT CMD
  35613 S    sleep 1200

```

**Pregunta:** ¿Qué flag indicó la suspensión?  

**Respuesta:** la T en el STAT

---

**24. (Opcional)** Genera un **zombie** controlado (no requiere root).

```bash
cat << 'EOF' > "$DAM/bin/zombie.c"
#include <stdlib.h>
#include <unistd.h>
int main() {
  if (fork() == 0) { exit(0); } // hijo termina
  sleep(60); // padre no hace wait(), hijo queda zombie hasta que padre termine
  return 0;
}
EOF
gcc "$DAM/bin/zombie.c" -o "$DAM/bin/zombie" && "$DAM/bin/zombie" &
ps -el | grep ' Z '
```
**Salida (recorta):**

```text
1 Z  1001   39208   39207  0  80   0 -     0 -      pts/0    00:00:00 zombie
```
**Pregunta:** ¿Por qué el estado `Z` y qué lo limpia finalmente?  

**Respuesta:** el estado Z indica que es un proceso zombie y se puede limpiar con el comando kill

---

### 2.5 — Limpieza (solo tu usuario)

Detén y deshabilita tu **timer/servicio de usuario** y borra artefactos si quieres.

```bash
systemctl --user disable --now fecha-log.timer
systemctl --user stop fecha-log.service
rm -f ~/.config/systemd/user/fecha-log.{service,timer}
systemctl --user daemon-reload
rm -rf "$DAM/bin" "$DAM/logs" "$DAM/units"
```

---

## ¿Qué estás prácticando?
- [ ] Pegaste **salidas reales**.  
- [ ] Explicaste **qué significan**.  
- [ ] Usaste **systemd --user** y **journalctl --user**.  
- [ ] Probaste `systemd-run --user` con límites de memoria.  
- [ ] Practicaste señales (`STOP`/`CONT`), `pstree`, `ps` y `strace` **sobre tus procesos**.

---

## Licencia 📄
Licencia **Apache 2.0** — ver [LICENSE.md](https://github.com/jpexposito/code-learn-practice/blob/main/LICENSE).
