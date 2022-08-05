<?php

function Suma($num1, $num2) {
    return $num1 + $num2;
}

function Potencia($num1, $num2) {

    $resp = 1;
    for ($i = 1; $i <= $num2; $i++) {
        $resp *= $num1;
    }

    return $resp;
}

function Multiplicacion($num1, $num2) {
    $resp = 0;
    for ($i = 1; $i <= $num2; $i++) {
        $resp += $num1;
    }

    return $resp;
}

function Division($num1, $num2) {

    if ($num2 != 0) {
        $resp = $num1 / $num2;
    } else {
        $resp = "Error, no se puede dividir entre 0";
    }

    return $resp;
}

$continuar_app = true;

do {
    echo PHP_EOL;
    echo PHP_EOL;
    echo PHP_EOL . "Calculadora PHP: ";

    echo PHP_EOL . "Ingrese un primer número: ? ";
    fscanf(STDIN, "%s", $num1);

    echo PHP_EOL . "Ingrese un segundo número: ? ";
    fscanf(STDIN, "%s", $num2);

    echo PHP_EOL . "Opciones:";
    echo PHP_EOL . "    1.- Suma";
    echo PHP_EOL . "    2.- Potencia:";
    echo PHP_EOL . "    3.- Multiplicación";
    echo PHP_EOL . "    4.- División:";
    echo PHP_EOL . "Escoja una opción [1-4] ";
    fscanf(STDIN, "%s", $opcion);

    $opcion = trim($opcion);

    if ($opcion === "1") {
        echo PHP_EOL . "La Suma de " . $num1 . " y " . $num2 . " es: " . Suma($num1, $num2);
    } elseif ($opcion === "2") {
        echo PHP_EOL . "La Potencia de " . $num1 . " elevado a " . $num2 . " es: " . Potencia($num1, $num2);
    } elseif ($opcion === "3") {
        echo PHP_EOL . "La Multiplicación de " . $num1 . " por " . $num2 . " es: " . Multiplicacion($num1, $num2);
    } elseif ($opcion === "4") {
        echo PHP_EOL . "La División de " . $num1 . " entre " . $num2 . " es: " . Division($num1, $num2);
    } else {
        echo PHP_EOL . "Error en el número de opción";
    }

    echo PHP_EOL . PHP_EOL . "Desea continuar [S/N] ? ";
    fscanf(STDIN, "%s", $opcion);

    $opcion = strtoupper(trim($opcion));

    $continuar_app = strcmp($opcion, "S") == 0;
} while ($continuar_app);
?>
