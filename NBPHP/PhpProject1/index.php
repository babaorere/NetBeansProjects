<?php

// Creacion del array, con la info solicitada
$info = array(
    array('trabajador' => 'Maria Luisa Rojas',
        'horario' => '8 am a 3 pm',
        'departamento' => 'Producción'),
    array('trabajador' => 'Jose Perez Marquez',
        'horario' => '3 pm a 10 pm',
        'departamento' => 'Seguridad'),
    array('trabajador' => 'Catalina Donoso Correa',
        'horario' => '10 pm a 6 pm',
        'departamento' => 'Producción'),
    array('trabajador' => 'Raúl Lavín',
        'horario' => '8 am a 3 pm',
        'departamento' => 'Produccion'),
    array('trabajador' => 'Jorge Luis Venegas',
        'horario' => '3 pm a 10 pm',
        'departamento' => 'logística')
);

// Definir el estilo de la tabla
echo '<style>';
echo 'table {';
echo '  font-family: arial, sans-serif;';
echo '  border-collapse: collapse;';
echo '  width: 100%;';
echo '  border:"1"';
echo '}';

echo 'td, th {';
echo '  border: 1px solid #dddddd;';
echo '  text-align: left;';
echo '  padding: 8px;';
echo '}';

echo 'tr:nth-child(even) {';
echo '  background-color: #dddddd;';
echo '}';
echo '</style>';

echo '<table >';
echo '<caption>Monitor de Horarios</caption>';
echo '<tr>';
echo'<th>TRABAJADOR</th>';
echo '<th>HORARIO</th>';
echo '<th>DEPARTAMENTO</th>';
echo '</tr>';

for ($i = 0; $i < count($info); $i++) {
    echo '<tr>';
    echo '<td>' . $info[$i]['trabajador'] . '</td>';
    echo '<td>' . $info[$i]['horario'] . '</td>';
    echo '<td>' . $info[$i]['departamento'] . '</td>';
    echo '</tr>';
}

echo '</table>';
