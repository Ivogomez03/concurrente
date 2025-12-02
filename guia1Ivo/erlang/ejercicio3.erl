% Escribir un proceso que permita modelar un contador
-module(ejercicio3).
-export([func/1,start/0]).
start() ->
    spawn(ejercicio3,func,[0]).
func(Contador) ->
    receive
        incrementar ->
            NuevoCont = Contador + 1,
            io:format("El contador vale: ~p~n",[NuevoCont]),
            func(NuevoCont);
        decrementar -> 
            NuevoCont = Contador - 1,
            io:format("El contador vale: ~p~n",[NuevoCont]),
            func(NuevoCont)
    end.
            