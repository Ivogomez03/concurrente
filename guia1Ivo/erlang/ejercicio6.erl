% Modelar un ascensor para un edificio que tiene solo tres niveles: planta baja (PB), primer piso (P1)
% y segundo piso (P2). Ademas el ascensor tiene solo dos botones asociados a dos acciones: subir (↑) y
% bajar (↓). El ascensor debera parar en cada nivel por el cual pase.


-module(ejercicio6).
-export([func/1,start/0]).
start() ->
    spawn(ejercicio6,func,[0]).
func(Piso) ->
     receive
        subir -> 
            if
                Piso == 2 ->
                    io:format("No puede subir mas arriba del piso 2.~n"),
                    func(Piso);
                true ->
                    PisoNuevo = Piso + 1,
                    io:format("Subiendo al piso ~p~n",[PisoNuevo]),
                    func(PisoNuevo)
            end;
        bajar ->
            if
                Piso == 0 ->
                    io:format("No puede bajar mas abajo del piso 0.~n"),
                    func(Piso);
                true ->
                    PisoNuevo = Piso - 1,
                    io:format("Bajando al piso ~p~n",[PisoNuevo]),
                    func(PisoNuevo)
            end
    end.


