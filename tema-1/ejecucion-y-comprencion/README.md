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

```
**Pregunta:** ¿Cuál es tu proceso con mayor `%CPU` en ese momento?  

**Respuesta:**

---

**20.** Traza syscalls de **tu propio proceso** (p. ej., el `sleep` anterior).
> Nota: Sin root, no podrás adjuntarte a procesos de otros usuarios ni a algunos del sistema.

```bash
pid=$(pgrep -u "$USER" -x sleep | head -n1)
strace -p "$pid" -e trace=nanosleep -tt -c -f 2>&1 | sed -n '1,10p'
```

**Salida (fragmento):**

```text

```
**Pregunta:** Explica brevemente la syscall que observaste.  

**Respuesta:**

---

### 2.4 — Estados y jerarquía (sin root)

**21.** Árbol de procesos con PIDs.

```bash
pstree -p | head -n 50
```

**Salida (recorta):**

```text

```
**Pregunta:** ¿Bajo qué proceso aparece tu `systemd --user`?  

**Respuesta:**

---

**22.** Estados y relación PID/PPID.

```bash
ps -eo pid,ppid,stat,cmd | head -n 20
```
**Salida:**

```text

```
**Pregunta:** Explica 3 flags de `STAT` que veas (ej.: `R`, `S`, `T`, `Z`, `+`).  

**Respuesta:**

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

```
**Pregunta:** ¿Qué flag indicó la suspensión?  

**Respuesta:**

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

```
**Pregunta:** ¿Por qué el estado `Z` y qué lo limpia finalmente?  

**Respuesta:**

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
