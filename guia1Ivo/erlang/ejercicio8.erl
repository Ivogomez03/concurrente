% 8. Implementar un bufer acotado en Erlang, que proporciona tres operaciones.
% 1-modulo (buffer).
% 2-exportar ([start/1, obtener/1, poner/2]).
% La operacion start(N) crea un nuevo bufer de tamaño N y devuelve su identificador. La operacion
% tomar(Serv) obtiene el elemento mas antiguo del bufer Serv, o se bloquea si el bufer esta vacıo. La
% operacion poner(Serv, X) inserta en el bufer Serv el elemento X, o bloquea si no hay espacio en el
% bufer.
% a) Implemente el modulo buffer Erlang para proporcionar la funcionalidad descrita anteriormente.
-module(ejercicio8).
-export([func/3,start/1, obtener/1, poner/2]).



start(N) ->
    spawn(ejercicio8,func,[[],N,[],0]).

obtener(Serv) -> 
    Serv ! {obtener,self()},
    receive
        {ok,Elemento} -> Elemento
    end.
poner(Serv,X) ->
    Serv ! {poner,self(),X},
    receive
        ok -> ok
    end.
func(Serv,N,Cola,LongBuffer) -> 
    receive
        {obtener,From} ->
            if
                LongBuffer == 0 ->
                    func(Serv,N,Cola ++ [{obtener,From}]);
                true -> 
                    [Elemento | Resto] = Serv,
                    LongBufferActualizado = LongBuffer - 1,
                    From ! {ok,Elemento},
                    io:format("El elemento ~p fue tomado con exito.~n",[Elemento]),
                    io:format("Buffer Actual ~p.~n",[Resto]),
                    procesar_cola(Resto, N, Cola,LongBufferActualizado)
            end;
        {poner,From,X} ->
            if
                LongBuffer < N ->
                    NewBuffer = Serv ++ [X],
                    LongBufferActualizado = LongBuffer + 1,
                    From ! {ok},
                    io:format("El elemento ~p fue puesto con exito.~n",[X]),
                    io:format("Buffer Actual ~p.~n",[NewBuffer]),
                    procesar_cola(NewBuffer, N, Cola,LongBufferActualizado);
                LongBuffer >= N ->
                    func(Serv,N,Cola ++ [{poner,From,X}],LongBuffer)
            end
        end.

procesar_cola(Serv,N,Cola,LongBuffer) ->
    case Cola of
        [] -> func(Serv,N,Cola,LongBuffer);
        [{poner,From,X} | Resto] ->
            if 
                LongBuffer < N -> 
                    NewBuffer = Serv ++ [X],
                    LongBufferActualizado = LongBuffer + 1,
                    From ! {ok},
                    io:format("El elemento ~p fue puesto con exito.~n",[X]),
                    io:format("Buffer Actual ~p.~n",[NewBuffer]),
                    procesar_cola(NewBuffer,N,Resto,LongBufferActualizado);
                true -> 
                    func(Serv,N,[{poner,From,X} | Resto],LongBuffer)
            end;
        [{obtener,From} | Resto] ->
            if
                LongBuffer == 0 ->
                    func(Serv,N,[{obtener,From} | Resto],LongBuffer );
                true -> 
                    [Elemento | RestoBuffer] = Serv,
                    LongBufferActualizado = LongBuffer - 1,
                    From ! {ok,Elemento},
                    io:format("El elemento ~p fue tomado con exito.~n",[Elemento]),
                    io:format("Buffer Actual ~p.~n",[RestoBuffer]),
                    procesar_cola(RestoBuffer,N,Resto,LongBufferActualizado)
            end
        end.









