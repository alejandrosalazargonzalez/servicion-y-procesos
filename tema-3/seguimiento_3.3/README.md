
# 1. Ejercicio 1 – 🏰 Servidor de Mazmorras Online (Fixed Thread Pool + Runnable)

## 1.1. Responde y comenta la salida ejecutando los cambios que se proponen

- Solo se usan **3 hilos** (3 GM bots) para atender a todos los jugadores, que esta sucediendo.
  - se crea un cuello de botella, porque por muchas peticiones que haya solo pasan 3 a la vez
  
  ```bash
    [22:34:35.941] [MAIN] --- Iniciando Servidor de Login ---
    [22:34:35.981] [pool-1-thread-1] Recibido a Link. Tiempo en cola: 2ms
    [22:34:35.981] [pool-1-thread-3] Recibido a Geralt. Tiempo en cola: 1ms
    [22:34:35.981] [pool-1-thread-2] Recibido a Zelda. Tiempo en cola: 1ms
    [22:34:35.986] [pool-1-thread-1] Preparando instancia 'Hyrule'...
    [22:34:35.986] [pool-1-thread-2] Preparando instancia 'Torre'...
    [22:34:35.986] [pool-1-thread-3] Preparando instancia 'Moria'...
    [22:34:37.362] [pool-1-thread-2] >> Mazmorra 'Torre' LISTA para Zelda 🎮
    [22:34:37.363] [pool-1-thread-2] Recibido a Yennefer. Tiempo en cola: 1407ms
    [22:34:37.363] [pool-1-thread-2] Preparando instancia 'Estrella Muerte'...
    [22:34:37.623] [pool-1-thread-3] >> Mazmorra 'Moria' LISTA para Geralt 🎮
    [22:34:37.623] [pool-1-thread-3] Recibido a Gandalf. Tiempo en cola: 1667ms
    [22:34:37.623] [pool-1-thread-3] Preparando instancia 'Nido'...
    [22:34:37.739] [pool-1-thread-1] >> Mazmorra 'Hyrule' LISTA para Link 🎮
  ```

- Los mismos hilos procesan varias peticiones → **reutilización de hilos**. ¿Qué significa esto?
  -que el pool esta reutilizando los hilos en vez de crear nuevos

    ```bash
    [22:34:35.981] [pool-1-thread-2] Recibido a Zelda. Tiempo en cola: 1ms
    ....
    [22:34:35.986] [pool-1-thread-2] Preparando instancia 'Torre'...
    ....
    [22:34:37.362] [pool-1-thread-2] >> Mazmorra 'Torre' LISTA para Zelda 🎮
    [22:34:37.363] [pool-1-thread-2] Recibido a Yennefer. Tiempo en cola: 1407ms
    [22:34:37.363] [pool-1-thread-2] Preparando instancia 'Estrella Muerte'...
    ```

- ¿Qué pasa si cambias el tamaño del pool a 1? ¿Y a 10?
  - con un tamaño de 1 atenderia a los jugadores de 1 en 1, porque un solo hilo haria todo el trabajo y tarda mas en completar la lista
  
    ```bash
    [22:44:52.682] [MAIN] --- Iniciando Servidor de Login ---
    [22:44:52.720] [pool-1-thread-1] Recibido a Link. Tiempo en cola: 1ms
    [22:44:52.727] [pool-1-thread-1] Preparando instancia 'Hyrule'...
    [22:44:53.858] [pool-1-thread-1] >> Mazmorra 'Hyrule' LISTA para Link 🎮
    [22:44:53.859] [pool-1-thread-1] Recibido a Zelda. Tiempo en cola: 1163ms
    [22:44:53.859] [pool-1-thread-1] Preparando instancia 'Torre'...
    [22:44:55.354] [pool-1-thread-1] >> Mazmorra 'Torre' LISTA para Zelda 🎮
    [22:44:55.355] [pool-1-thread-1] Recibido a Geralt. Tiempo en cola: 2659ms
    [22:44:55.355] [pool-1-thread-1] Preparando instancia 'Moria'...
    [22:44:57.327] [pool-1-thread-1] >> Mazmorra 'Moria' LISTA para Geralt 🎮
    [22:44:57.328] [pool-1-thread-1] Recibido a Yennefer. Tiempo en cola: 4632ms
    [22:44:57.329] [pool-1-thread-1] Preparando instancia 'Estrella Muerte'...
    [22:44:59.199] [pool-1-thread-1] >> Mazmorra 'Estrella Muerte' LISTA para Yennefer 🎮
    [22:44:59.200] [pool-1-thread-1] Recibido a Gandalf. Tiempo en cola: 6504ms
    [22:44:59.200] [pool-1-thread-1] Preparando instancia 'Nido'...
    [22:45:01.173] [pool-1-thread-1] >> Mazmorra 'Nido' LISTA para Gandalf 🎮
    [22:45:01.174] [pool-1-thread-1] Recibido a Frodo. Tiempo en cola: 8478ms
    [22:45:01.174] [pool-1-thread-1] Preparando instancia 'Hyrule'...
    [22:45:02.415] [pool-1-thread-1] >> Mazmorra 'Hyrule' LISTA para Frodo 🎮
    [22:45:02.416] [pool-1-thread-1] Recibido a Aragorn. Tiempo en cola: 9720ms
    [22:45:02.417] [pool-1-thread-1] Preparando instancia 'Torre'...
    [22:45:04.209] [pool-1-thread-1] >> Mazmorra 'Torre' LISTA para Aragorn 🎮
    [22:45:04.210] [pool-1-thread-1] Recibido a Leia. Tiempo en cola: 11513ms
    [22:45:04.210] [pool-1-thread-1] Preparando instancia 'Moria'...
    [22:45:06.133] [pool-1-thread-1] >> Mazmorra 'Moria' LISTA para Leia 🎮
    [22:45:06.133] [pool-1-thread-1] Recibido a Luke. Tiempo en cola: 13437ms
    [22:45:06.135] [pool-1-thread-1] Preparando instancia 'Estrella Muerte'...
    [22:45:07.546] [pool-1-thread-1] >> Mazmorra 'Estrella Muerte' LISTA para Luke 🎮
    [22:45:07.547] [pool-1-thread-1] Recibido a Vader. Tiempo en cola: 14851ms
    [22:45:07.548] [pool-1-thread-1] Preparando instancia 'Nido'...
    [22:45:08.708] [pool-1-thread-1] >> Mazmorra 'Nido' LISTA para Vader 🎮
    ```

  - con un maño de 10 coge a 10 jugadore por lo que tarda menos, porque habria 10 hilos para hacerlo todo, como hay 10 jugadores acaba mucho mas rapido

    ```bash
    [22:46:22.016] [MAIN] --- Iniciando Servidor de Login ---
    [22:46:22.059] [pool-1-thread-1] Recibido a Link. Tiempo en cola: 1ms
    [22:46:22.060] [pool-1-thread-6] Recibido a Frodo. Tiempo en cola: 1ms
    [22:46:22.060] [pool-1-thread-5] Recibido a Gandalf. Tiempo en cola: 1ms
    [22:46:22.059] [pool-1-thread-2] Recibido a Zelda. Tiempo en cola: 0ms
    [22:46:22.059] [pool-1-thread-4] Recibido a Yennefer. Tiempo en cola: 1ms
    [22:46:22.059] [pool-1-thread-7] Recibido a Aragorn. Tiempo en cola: 1ms
    [22:46:22.060] [pool-1-thread-9] Recibido a Luke. Tiempo en cola: 2ms
    [22:46:22.060] [pool-1-thread-10] Recibido a Vader. Tiempo en cola: 2ms
    [22:46:22.060] [pool-1-thread-3] Recibido a Geralt. Tiempo en cola: 2ms
    [22:46:22.060] [pool-1-thread-8] Recibido a Leia. Tiempo en cola: 2ms
    [22:46:22.069] [pool-1-thread-5] Preparando instancia 'Nido'...
    [22:46:22.069] [pool-1-thread-6] Preparando instancia 'Hyrule'...
    [22:46:22.069] [pool-1-thread-3] Preparando instancia 'Moria'...
    [22:46:22.069] [pool-1-thread-1] Preparando instancia 'Hyrule'...
    [22:46:22.069] [pool-1-thread-9] Preparando instancia 'Estrella Muerte'...
    [22:46:22.069] [pool-1-thread-10] Preparando instancia 'Nido'...
    [22:46:22.069] [pool-1-thread-2] Preparando instancia 'Torre'...
    [22:46:22.069] [pool-1-thread-4] Preparando instancia 'Estrella Muerte'...
    [22:46:22.069] [pool-1-thread-7] Preparando instancia 'Torre'...
    [22:46:22.069] [pool-1-thread-8] Preparando instancia 'Moria'...
    [22:46:23.309] [pool-1-thread-10] >> Mazmorra 'Nido' LISTA para Vader 🎮
    [22:46:23.340] [pool-1-thread-3] >> Mazmorra 'Moria' LISTA para Geralt 🎮
    [22:46:23.361] [pool-1-thread-4] >> Mazmorra 'Estrella Muerte' LISTA para Yennefer 🎮
    [22:46:23.485] [pool-1-thread-1] >> Mazmorra 'Hyrule' LISTA para Link 🎮
    [22:46:23.613] [pool-1-thread-9] >> Mazmorra 'Estrella Muerte' LISTA para Luke 🎮
    [22:46:23.691] [pool-1-thread-5] >> Mazmorra 'Nido' LISTA para Gandalf 🎮
    [22:46:23.779] [pool-1-thread-7] >> Mazmorra 'Torre' LISTA para Aragorn 🎮
    [22:46:23.828] [pool-1-thread-8] >> Mazmorra 'Moria' LISTA para Leia 🎮
    [22:46:23.869] [pool-1-thread-2] >> Mazmorra 'Torre' LISTA para Zelda 🎮
    [22:46:24.037] [pool-1-thread-6] >> Mazmorra 'Hyrule' LISTA para Frodo 🎮
    ```

---

## 2. Ejercicio 2 – ⚔️ Calculadora de Daño Crítico (Callable + Future)

### 2.1. Responde y comenta la salida ejecutando los cambios que se proponen

- Diferencia con:
  - `execute(Runnable)` → no hay valor de retorno. → Muestra las salidas que demuestran esto.
  - `submit(Callable<V>)` → devuelve un `Future<V>` del que puedes sacar el resultado. → Muestra las salidas que demuestran esto.
    - Runnable es un metodo void del que no se sabe directamente cuando calculo y que valor calculo, Callable retona un future, una promesa
- Cómo se pueden lanzar muchos cálculos de daño en paralelo y luego recogerlos todos.
  - fan-out : el primer bucle for envía las tareas rapidísimo, sin esperar a que terminen. Solo recoge los tickets y los guarda en una lista.
  - fan-in : el segundo bucle recorre la lista de futuros. Al llamar a futuros.get(i).get(), el hilo principal se bloquea hasta que esa tarea específica termine. Como las tareas se ejecutaron en paralelo en el paso 1, la espera total es mucho menor que si las hubieras hecho una a una.
- Probar a cambiar la probabilidad de crítico y ver cómo sube/baja el daño total.
  - pob.critico normal danio

    ```bash
    [pool-1-thread-4] Arquera Élfica ¡CRÍTICO! -> daño: 242
    [pool-1-thread-3] Pícaro ¡CRÍTICO! -> daño: 270
    [pool-1-thread-1] Mago del Fuego golpe normal -> daño: 120
    [pool-1-thread-2] Guerrero golpe normal -> daño: 150
    [pool-1-thread-3] Paladín golpe normal -> daño: 130
    [pool-1-thread-4] Invocador golpe normal -> daño: 80
    [pool-1-thread-1] Bárbaro golpe normal -> daño: 160
    [pool-1-thread-2] Nigromante golpe normal -> daño: 100
    Daño total de la raid: 1252
    ```

  - prob.critico reducida danio

    ```bash
    [pool-1-thread-3] Pícaro ¡CRÍTICO! -> daño: 270
    [pool-1-thread-2] Guerrero golpe normal -> daño: 150
    [pool-1-thread-4] Arquera Élfica golpe normal -> daño: 110
    [pool-1-thread-1] Mago del Fuego golpe normal -> daño: 120
    [pool-1-thread-1] Nigromante golpe normal -> daño: 100
    [pool-1-thread-3] Invocador golpe normal -> daño: 80
    [pool-1-thread-4] Bárbaro golpe normal -> daño: 160
    [pool-1-thread-2] Paladín golpe normal -> daño: 130
    Daño total de la raid: 1120
    ```
  - prob.critico aumentada
    ```bash
    [pool-1-thread-2] Guerrero ¡CRÍTICO! -> daño: 300
    [pool-1-thread-4] Arquera Élfica ¡CRÍTICO! -> daño: 242
    [pool-1-thread-3] Pícaro ¡CRÍTICO! -> daño: 270
    [pool-1-thread-1] Mago del Fuego ¡CRÍTICO! -> daño: 300
    [pool-1-thread-2] Invocador ¡CRÍTICO! -> daño: 224
    [pool-1-thread-1] Nigromante ¡CRÍTICO! -> daño: 229
    [pool-1-thread-4] Paladín ¡CRÍTICO! -> daño: 234
    [pool-1-thread-3] Bárbaro ¡CRÍTICO! -> daño: 336
    Daño total de la raid: 2135
    ```

---

## 4. Ejercicio 3 – 👹 Spawns de Enemigos en Mundo Abierto (ScheduledExecutorService)

### 4.3. Responde y comenta la salida ejecutando los cambios que se proponen

- `ScheduledExecutorService` permite:
  - `schedule(...)` → una vez en el futuro. ¿Qué significa esto?
  - `scheduleAtFixedRate(...)` → repetidamente, cada X tiempo. ¿Qué significa esto?
    - schedule: ejecuta la tarea una sola vez después de un tiempo de espera.
    - scheduleAtFixedRate: erea un ciclo. ejecuta , espera X tiempo, y  vuelve a ejecutar infinitamente hasta que lo pares.
- Cómo se comporta el sistema si la tarea tarda más que el período. Modifca, muestra el resultado y comenta.

  ```bash
    [23:41:58.346] [MAIN] Iniciando motor de mundo abierto...
    [23:41:58.390] [pool-1-thread-1]  SPAWN DETECTADO:  JEFE DE MUNDO DRAGON en TEMPLO
    [23:42:03.390] [pool-1-thread-1] !!! ALERTA GENERAL: Ha aparecido un boss épico !!!
    [23:42:03.392] [pool-1-thread-1]  SPAWN DETECTADO: SLIME en RUINAS_ANTIGUAS
    [23:42:08.362] [MAIN] Apagando servidores...
  ```

- Probar a cambiar el período (1s, 3s…) y la duración del `sleep` del `main`.  Modifca, muestra el resultado y comenta.
  - main 1 segundo ciclos 1 segundo
  
  ```bash
    [23:44:31.951] [MAIN] Iniciando motor de mundo abierto...
    [23:44:31.983] [pool-1-thread-1]  SPAWN DETECTADO: SLIME en BOSQUE_MALDITO
    [23:44:32.963] [pool-1-thread-1]  SPAWN DETECTADO: DRAGON en CIUDAD_CIBERNETICA
    [23:44:32.964] [MAIN] Apagando servidores...
  ```

    - main 5 segundos ciclo 1 segundo

    ```bash
    [23:45:32.094] [MAIN] Iniciando motor de mundo abierto...
    [23:45:32.124] [pool-1-thread-1]  SPAWN DETECTADO: DRAGON en RUINAS_ANTIGUAS
    [23:45:33.106] [pool-1-thread-1]  SPAWN DETECTADO:  JEFE DE MUNDO LICH en CIUDAD_CIBERNETICA
    [23:45:33.107] [pool-1-thread-1] !!! ALERTA GENERAL: Ha aparecido un boss épico !!!
    [23:45:34.105] [pool-1-thread-1]  SPAWN DETECTADO: SLIME en CIUDAD_CIBERNETICA
    [23:45:35.105] [pool-1-thread-1]  SPAWN DETECTADO: BANDIDO en CIUDAD_CIBERNETICA
    [23:45:36.105] [pool-1-thread-1]  SPAWN DETECTADO: ESQUELETO en CIUDAD_CIBERNETICA
    [23:45:37.105] [pool-1-thread-1]  SPAWN DETECTADO: LICH en RUINAS_ANTIGUAS
    [23:45:37.106] [MAIN] Apagando servidores...
    ```
