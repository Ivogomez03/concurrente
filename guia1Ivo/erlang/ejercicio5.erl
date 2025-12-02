% Modelar un semaforo con un proceso

-module(ejercicio5).
-export([func/2,start/0]).

start() ->
    spawn(ejercicio5,func,[0,[]]).

func(Contador,Cola) ->
    receive
        signal ->
            ContadorNuevo = Contador+1,
            LongCola = length(Cola),
            if 
                LongCola == 0 ->
                    io:format("No hay proceso para liberar~n"),
                    func(ContadorNuevo,Cola);
                true ->
                    [Levantado | Resto] = Cola,
                    io:format("Liberando proceso: ~p~n",[Levantado]),
                    Levantado ! ok,
                    func(ContadorNuevo,Resto)
            end;
            
        {wait,From} ->
            
            if
                Contador == 0 ->
                    io:format("Contador igual a 0, bloqueando proceso ~p~n",[From]),
                    ColaNueva = [From | Cola],
                    func(Contador,ColaNueva);
                Contador > 0 ->
                    ContadorNuevo = Contador-1,
                    From ! ok,
                    io:format("Contador decrementado, proceso ~p sin bloquear~n",[From]),
                    func(ContadorNuevo,Cola)
            end
        end.




