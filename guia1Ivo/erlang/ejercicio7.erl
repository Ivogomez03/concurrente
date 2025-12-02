-module(ejercicio7).
-export([func/3,start/1, depositar/2, retirar/2, congelar/2]).

start(Saldo) ->
    spawn(ejercicio7,func,[Saldo,false,[]]).

depositar(Cuenta, Monto) ->
    Cuenta ! {depositar,self(),Monto},
    receive
        {ok,NuevoSaldo} -> NuevoSaldo
    end.

retirar(Cuenta, Monto) -> 
    Cuenta ! {retirar,self(),Monto},
    receive
        {ok,NuevoSaldo} -> NuevoSaldo
    end.

congelar(Cuenta, Estado) ->
    Cuenta ! {congelar,self(),Estado},
    receive
        {estadoAnterior, EstadoAnterior} -> EstadoAnterior
    end.

func(Saldo,Congelada,Cola) ->
    receive
        {depositar,From,Monto} ->
            if
                not Congelada ->
                    NuevoSaldo = Saldo + Monto,
                    io:format("El saldo nuevo es de: ~p~n.",[NuevoSaldo]),
                    From ! {ok,NuevoSaldo},
                    func(NuevoSaldo,Congelada,Cola);
                Congelada ->
                    func(Saldo,Congelada,Cola ++ [{depositar,From,Monto}])
            end;
        {retirar,From,Monto} ->
            if
                not Congelada ->
                    NuevoSaldo = Saldo-Monto,
                    if
                        NuevoSaldo < 0  ->
                            io:format("No puede hacer retiros hasta tener fondos suficientes, saldo actual: ~p~n.",[Saldo]),
                            func(Saldo, Congelada, Cola ++ [{retirar, From, Monto}]);
                        true ->
                            io:format("Retiro hecho con exito, saldo actual: ~p~n.",[NuevoSaldo]),
                            From ! {ok,NuevoSaldo},
                            func(NuevoSaldo,Congelada,Cola)
                    end;
                Congelada ->
                    func(Saldo, Congelada, Cola ++ [{retirar, From, Monto}])
            end;

        {congelar,From,NuevoEstado} ->
            From ! {estadoAnterior, Congelada},
            case NuevoEstado of
                false -> procesar_cola(Saldo, NuevoEstado, Cola);
                true  -> func(Saldo, NuevoEstado, Cola)
            end
    end.

procesar_cola(Saldo,NuevoEstado,Cola)->
    case Cola of
        [] -> func(Saldo,NuevoEstado,[]);
        [{depositar,From,Monto} | Resto] ->
            NuevoSaldo = Monto + Saldo,
            From ! {ok, NuevoSaldo},
            procesar_cola(NuevoSaldo,NuevoEstado,Resto);
        [{retirar,From,Monto} | Resto] ->
            NuevoSaldo = Saldo-Monto,
            if
                 NuevoSaldo < 0  ->
                        io:format("No puede hacer retiros hasta tener fondos suficientes, saldo actual: ~p~n.",[Saldo]),
                        func(Saldo, NuevoEstado,[{retirar, From, Monto} | Resto]);
                 true ->
                        io:format("Retiro hecho con exito, saldo actual: ~p~n.",[NuevoSaldo]),
                        From ! {ok,NuevoSaldo},
                        procesar_cola(NuevoSaldo,NuevoEstado,Resto)
            end
    end.


