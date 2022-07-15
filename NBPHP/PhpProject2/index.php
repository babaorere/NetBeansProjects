<?php

print("\n\nManejo de Valores");
print("\n");

$handle = fopen("php://stdin", "r");

print("\nDato1 = ? ");
$dato1 = fgets($handle);

print("\nDato2 = ? ");
$dato2 = fgets($handle);

print("\nDato3 = ? ");
$dato3 = fgets($handle);

try {
    $promedio = (intval($dato1) + intval($dato2) + intval($dato3)) / 3.00;
    print("\nPromedio es = $promedio\n\n");
} catch (Exception $e) {
    print("\nValores de entrada invalidos\n\n");
}

fclose($handle);

