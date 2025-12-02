% Escribir un proceso que permita modelar un servidor que provee la operacion para sumar. Al
% recibir un mensaje de suma y sus dos operandos, debe mostrar el resultado correspondiente por
% pantalla.
% b) Modificar su solucion de manera tal que sirva alternadamente a operaciones de suma y resta.
% c) Modificar su solucion de manera tal que en lugar de mostrar el resultado, lo devuelva al receptor.

-module(ejercicio1a).
-export([func/0]).

func()-> 
    receive
        {sumar,From,A,B} -> 
            R = A+B, 
            From ! R,
            io:format("El resultado de la suma es: ~p~n",[R]);
        {resta,From,A,B} -> 
            R = A-B, 
            From ! R,
            io:format("El resultado de la resta es: ~p~n",[R])
    end.
