% a) Escribir un servidor que recibe mensajes num´ericos, y con cada n´umero que recibe muestra por
% pantalla el promedio de todos los recibidos.
% b) Modificar el servidor para que en lugar de mostrar por pantalla el promedio, devuelva el promedio
% quien envıa el mensaje.
-module(ejercicio2).
-export([func/2]).

func(ContadorVeces,Suma) ->
        receive 
           Num -> % para el b seria {Num,From}
            NuevoCont = ContadorVeces + 1,
            Suma1 = Suma + Num,
            Promedio = Suma1/NuevoCont,
            % para el b agregaria From ! Promedio,
            io:format("El promedio de los recibidos es: ~p~n",[Promedio]),

            func(NuevoCont,Suma1)
        end.
