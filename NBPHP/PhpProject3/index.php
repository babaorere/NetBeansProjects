<?php

define("UF", 27000);

print("\n\nManejo de Valores Inmuebles en UF");
print("\n");

$handle = fopen("php://stdin", "r");

print("\nValor del Inmueble en UF = ? ");
$dato1 = fgets($handle);

try {
    $valorPesos = intval($dato1) * UF;
    print("\nValor en Pesos es = $valorPesos\n\n");
} catch (Exception $e) {
    print("\nValor de entrada invalido\n\n");
}

fclose($handle);


