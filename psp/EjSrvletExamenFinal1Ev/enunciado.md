Se organiza un torneo de ahorcado por un monton de bibliotecas de madrid. En
cada biblioteca hay un ordenador, y para empezar a jugar se le he dado una contraseña
a cada biblioteca q tendrá q poner, en el código cada contraseña estará a fuego. En
cada petición se pondrá en la respuesta como cabecera la biblioteca que es. Sin la
contraseña de la bilioteca no empieza nada y deberá de dar error en cualquier llamada.


Dentro de cada biblioteca se juega con n jugadores, jugando dos jugadores a la vez ,
con un único ordenador.


• Los jugadores se identifican por nombre. Hay un servlet de comienzo de
partida al que se le pasan los dos jugadores y la palabra secreta, que la elige
un tercero (por ejemplo, el organizador) y no puede contener números ni
espacios. El servlet sortea aleatoriamente quién adivina primero.

• Los jugadores se alternan al proponer letras, de una en una por llamada. Se
debe indicar siempre el nombre del jugador que llama y verificar que es su
turno. En vez de una letra también se podrá mandar la palabra completa, y si la
adivina se acaba el juego, si no es cuenta como fallo.

• Tras cada llamada se muestra la palabra con las letras acertadas descubiertas
(_ A _ A), las letras ya usadas y los fallos restantes (máximo 6). Una letra
repetida no cuenta como fallo, pero tampoco se admite como movimiento: se
devuelve un aviso.

• La partida termina cuando se descubre la palabra (gana quien puso la última
letra) o se agotan los fallos (no gana nadie, el resultado es “empate”). Al
terminar se guardan las estadísticas de la partida: ganador, número de
movimientos, si puso palabra o letra y número de fallos.

• Una vez acabada la partida se podrá comenzar una nueva.


• Una URL de estadísticas:
o Sin parámetros: victorias de cada jugador.

o Con un nombre de jugador: sus partidas, contra quién jugó y si ganó,
perdió o empató.

o Con un número de partida: el historial de movimientos de esa partida
(jugador y letra o palabra, en orden).



• Un servlet para borrar jugadores del torneo, eliminando también las partidas en
las que participaron.

• Un servlet finTorneo que muestra el podio de los jugadores con más victorias.



Hay que llamarlo veces la primera marca el fin del torneo, y deshablita que haya
mas partidas, y la segunda sacará los jugadores en posicion 1,2,3 que mas
victorias hayan conseguido.

Comprueba todos los errores posibles, como por ejemplo que se empieze una partida
mientras otra esta en curso... etc.