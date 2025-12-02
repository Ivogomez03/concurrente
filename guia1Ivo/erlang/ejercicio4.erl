% Escribir un proceso que modele a una pila concurrente
-module(ejercicio4).
-export([func/1,start/0]).

start() ->
    spawn(ejercicio4,func,[[0]]).

func([Tope | Resto]) ->
    receive
        {push,Num} -> 
            PilaActual = [Tope | Resto],
            PilaNueva = [Num | PilaActual],
            io:format("La pila nueva es: ~p~n",[PilaNueva]),
            func(PilaNueva);
        pop -> 
            PilaNueva = Resto,
            io:format("La pila nueva es: ~p~n",[PilaNueva]),
            func(PilaNueva);
        tope ->
            io:format("El tope de la pila es: ~p~n",[Tope]),
            func([Tope | Resto])

    end.
