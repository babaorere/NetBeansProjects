/* 
 * File:   main.cpp
 * Author: manager
 *
 * Created on 2 de noviembre de 2021, 3:41 p.m.
 */

#include <cstdlib>
#include <iostream>
#include <limits> // numeric_limits<streamsize>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <fcntl.h>

#include <cstdlib>
#include "TCliente.h"
#include "TAutomovil.h"
#include "TAdeudo.h"
#include "TPagos.h"


using namespace std;


void DarAltaAuto();
void ListarAutosDisp();
void HacerCotizacion();
void RealizarCompra();
void BuscarAuto();
void EditarAuto();
void EditarCliente();
void RealizarPago();

char MenuCppInt() {

    string opcion;

    do {
        cout << "\n\n";
        cout << "\n********************************************************************************";
        cout << "\n       ************************ MENU PRINCIPAL ************************";
        cout << "\n";
        cout << "\n1.- Dar de alta un nuevo Auto";
        cout << "\n2.- Listar autos disponibles";
        cout << "\n3.- Hacer una cotizacion";
        cout << "\n4.- Realizar una compra";
        cout << "\n5.- Buscar un auto";
        cout << "\n6.- Editar auto";
        cout << "\n7.- Editar Cliente";
        cout << "\n8.- Realizar un pago";

        cout << "\n0.- Salir";
        cout << "\n\nOpcion = ? ";

        // leer un unico digito, como caracter
        cin >> opcion;

        // Limpia el buffer de entrada
        cin.ignore(numeric_limits<streamsize>::max(), '\n');

        if (opcion.length() != 1) {
            cout << "\nOpcion invalida, intente de nuevo";
        } else {
            break; // salir del ciclo
        }

    } while (true);

    return opcion[0];
}


// *****************************************************************************************************************************
// *****************************************************************************************************************************

TListAuto *listAuto = new TListAuto();
TListCliente *listCliente = new TListCliente();

TListAdeudo *listAdeudo = new TListAdeudo();
TListPagos *listpPagos = new TListPagos();

/*
 * 
 */
int main(int argc, char** argv) {


    listAuto->ReadArchivo();
    listCliente->ReadArchivo();

    listAdeudo->ReadArchivo();
    listpPagos->ReadArchivo();

    bool continuar = true;
    while (continuar) {

        switch (MenuCppInt()) {

            case '1': DarAltaAuto();
                break;

            case '2': ListarAutosDisp();
                break;

            case '3': HacerCotizacion();
                break;

            case '4': RealizarCompra();
                break;

            case '5': BuscarAuto();
                break;

            case '6': EditarAuto();
                break;

            case '7': EditarCliente();
                break;

            case '8': RealizarPago();
                break;

            default:
                continuar = false;
        }


    }

    return 0;
}



// *****************************************************************************************************************************

void DarAltaAuto() {

    if (listAuto->IsFull()) {
        cout << "\n\nLista llena";
        return;
    }


    string marca;
    string modelo;
    int year;
    int precioVenta;
    bool disponible;

    cout << "\n\n";
    cout << "\n********************************************************************************";
    cout << "\n       ***************** MENU DAR ALTA NUEVO AUTO";

    cout << "\n\nMarca= ? ";
    cin >> marca;
    for (auto & c : marca) c = toupper(c);


    cout << "Modelo= ? ";
    cin >> modelo;
    for (auto & c : modelo) c = toupper(c);

    cout << "Año= ? ";
    cin >> year;

    cout << "Precio de Venta ? ";
    cin >> precioVenta;

    listAuto->CreateAdd(-1, marca, modelo, year, precioVenta, true);

    listAuto->WriteArchivo();

    std::cout << "\nPresione enter para continuar ... ";
    std::cin.get();
}

void ListarAutosDisp() {
    if (listAuto->IsEmpty()) {
        cout << "\n\nLista Automoviles Vacia";
        return;
    }


    string marca;
    string modelo;
    int year;
    int precioVenta;
    bool disponible;

    cout << "\n\n";
    cout << "\n********************************************************************************";
    cout << "\n       ***************** MENU LISTADO AUTOMOVILES DISPONIBLES";
    cout << "\n\n";

    cout << " ID    MARCA         MODELO         AÑO      PRECIO-VENTA  " << endl;

    for (int idx = 0; idx < listAuto->GetTam(); idx++) {
        TAutomovil *aux = listAuto->Read(idx);

        if ((aux != NULL) && aux->IsDisponible()) {
            cout << aux->GetId() << " : " << aux->GetMarca() << " : " << aux->GetModelo() << " : " << aux->GetYear() << " : " << aux->GetPrecioVenta() << endl;
        }
    }

    std::cout << "\nPresione enter para continuar ... ";
    std::cin.get();
}

// *****************************************************************************************************************************

void HacerCotizacion() {

    if (listAuto->IsEmpty()) {
        cout << "\n\nNo Hay Autos Disponibles";
        return;
    }


    string marca;
    string modelo;
    int year;
    int precioVenta;
    bool disponible;

    cout << "\n\n";
    cout << "\n********************************************************************************";
    cout << "\n       ***************** MENU COTIZACION";
    cout << "\n\n";

    cout << " ID    MARCA         MODELO         AÑO      PRECIO-VENTA  " << endl;

    for (int idx = 0; idx < listAuto->GetTam(); idx++) {
        TAutomovil *aux = listAuto->Read(idx);

        if ((aux != NULL) && aux->IsDisponible()) {
            cout << aux->GetId() << " : " << aux->GetMarca() << " : " << aux->GetModelo() << " : " << aux->GetYear() << " : " << aux->GetPrecioVenta() << endl;
        }
    }

    int id;
    cout << "\n\nIndique el ID del automovil= ? ";
    cin >> id;

    string cliente;
    cout << "\nIndique el Cliente Nombre= ? ";
    cin >> cliente;
    for (auto & c : cliente) c = toupper(c);

    TAutomovil *aux = listAuto->GetByID(id);

    if (aux == NULL) {
        cout << "Auto no encontrado";
        std::cout << "\nPresione enter para continuar ... ";
        std::cin.get();
        return;
    }

    int precio1 = aux->GetPrecioVenta()*1.3;
    int mensual1 = precio1 / (12 * 1);

    int precio3 = aux->GetPrecioVenta()*1.6;
    int mensual3 = precio3 / (12 * 3);

    int precio5 = aux->GetPrecioVenta()*1.9;
    int mensual5 = precio5 / (12 * 5);

    // Resumen
    cout << "\n\nCotizacion";
    cout << "\nCliente   Nombre= " << cliente;
    cout << "\nAutomovil  marca= " << aux->GetMarca();
    cout << "\nAutomovil modelo= " << aux->GetModelo();
    cout << "\nAutomovil precio contado= " << aux->GetPrecioVenta();
    cout << "\nAutomovil precio financiado";
    cout << "\n1 Año, total a pagar= " << precio1;
    cout << "\n1 Año, Mensualidad= " << mensual1;

    cout << "\n3 Años, total a pagar= " << precio3;
    cout << "\n3 Años, Mensualidad= " << mensual3;

    cout << "\n5 Años, total a pagar= " << precio5;
    cout << "\n5 Años, Mensualidad= " << mensual5;

    cout << "\n\nGracias a Ud. \n";

    // Limpia el buffer de entrada
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    std::cout << "\nPresione enter para continuar ... ";
    std::cin.get();
    std::cin.get();
}


// *****************************************************************************************************************************

void RealizarCompra() {

    if (listAuto->IsEmpty()) {
        cout << "\n\nNo Hay Autos Disponibles";
        return;
    }


    string marca;
    string modelo;
    int year;
    int precioVenta;
    bool disponible;

    cout << "\n\n";
    cout << "\n********************************************************************************";
    cout << "\n       ***************** MENU COMPRA AUTOMOVIL";
    cout << "\n\n";


    cout << " Clientes  " << endl;
    cout << " ID    NOMBRES         APELLIDOS  " << endl;

    for (int idx = 0; idx < listCliente->GetTam(); idx++) {
        TCliente *aux = listCliente->Read(idx);

        if (aux != NULL) {
            cout << aux->GetId() << " : " << aux->GetNombres() << " : " << aux->GetApellidos() << endl;
        }
    }

    int idclte;
    cout << "\n\nIndique el ID del Cliente (0 -> nuevo cliente= ? ";
    cin >> idclte;


    string nombres;
    string apellidos;
    string fechaNacimiento;
    string email;

    TCliente *clte;

    // Cliente Nuevo
    if (idclte == 0) {

        cout << "\nNombres= ? ";
        cin >> nombres;
        for (auto & c : nombres) c = toupper(c);

        cout << "\nApellidos= ? ";
        cin >> apellidos;
        for (auto & c : apellidos) c = toupper(c);

        cout << "\nFecha Nacimiento= ? ";
        cin >> fechaNacimiento;

        cout << "\nNuevo email= ? ";
        cin >> email;
        for (auto & c : email) c = toupper(c);

        clte = listCliente->CreateAdd(-1, nombres, apellidos, fechaNacimiento, email);

        if (clte == NULL) {
            cout << "\nError al tratar de Crear Nuevo Cliente";
            // Limpia el buffer de entrada
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            std::cout << "\nPresione enter para continuar ... ";
            std::cin.get();
            return;
        } else {
            listCliente->WriteArchivo();
            cout << "\nCliente creado con exito";
        }

    } else {

        clte = listCliente->GetById(idclte);

        if (clte == NULL) {
            cout << "\nError al tratar de hallar el Cliente";
            // Limpia el buffer de entrada
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            std::cout << "\nPresione enter para continuar ... ";
            std::cin.get();

            return;
        }

    }


    cout << "\nAutomoviles Disponibles " << endl;
    cout << " ID    MARCA         MODELO         AÑO      PRECIO-VENTA  " << endl;

    for (int idx = 0; idx < listAuto->GetTam(); idx++) {
        TAutomovil *aux = listAuto->Read(idx);

        if ((aux != NULL) && aux->IsDisponible()) {
            cout << aux->GetId() << " : " << aux->GetMarca() << " : " << aux->GetModelo() << " : " << aux->GetYear() << " : " << aux->GetPrecioVenta() << endl;
        }
    }

    int idauto;
    cout << "\n\nIndique el ID del automovil= ? ";
    cin >> idauto;

    TAutomovil * automovil = listAuto->GetByID(idauto);

    if (automovil == NULL) {
        cout << "Auto no encontrado";
        std::cout << "\nPresione enter para continuar ... ";
        std::cin.get();
        return;
    }

    int precio1 = automovil->GetPrecioVenta() * 1.3;
    int mensual1 = precio1 / (12 * 1);

    int precio3 = automovil->GetPrecioVenta() * 1.6;
    int mensual3 = precio3 / (12 * 3);

    int precio5 = automovil->GetPrecioVenta() * 1.9;
    int mensual5 = precio5 / (12 * 5);

    // Resumen
    cout << "\n\nCotizacion";
    cout << "\nCliente   Nombre= " << clte->GetNombres();
    cout << "\nAutomovil  marca= " << automovil->GetMarca();
    cout << "\nAutomovil modelo= " << automovil->GetModelo();
    cout << "\nAutomovil precio contado= " << automovil->GetPrecioVenta();
    cout << "\nAutomovil precio financiado";
    cout << "\n1 Año, total a pagar= " << precio1;
    cout << "\n1 Año, Mensualidad= " << mensual1;

    cout << "\n3 Años, total a pagar= " << precio3;
    cout << "\n3 Años, Mensualidad= " << mensual3;

    cout << "\n5 Años, total a pagar= " << precio5;
    cout << "\n5 Años, Mensualidad= " << mensual5;

    string op;
    do {
        cout << "\nTipo Operacion - Contado - Credito";
        cout << "\nC.- Contado";
        cout << "\n1.- Financiamiento 1 Año";
        cout << "\n3.- Financiamiento 3 Años";
        cout << "\n5.- Financiamiento 5 Año";

        cout << "\n\nOpcion= ? ";
        cin >> op;
        for (auto & c : op) c = toupper(c);

    } while (op != "C" && op != "1" && op != "3" && op != "5");


    int id_cliente = clte->GetId();
    int id_auto = automovil->GetId();

    string fechaOp;
    cout << "Indique Fecha Operacion= ? ";
    cin >> fechaOp;

    int periodo_financiamiento;
    int montoOp;
    int montoPagado;
    int mensualidad;

    if (op == "C") {
        periodo_financiamiento = 0;
        montoOp = automovil->GetPrecioVenta();
        montoPagado = automovil->GetPrecioVenta();
        mensualidad = 0;
    } else if (op == "1") {
        periodo_financiamiento = 12;
        montoOp = automovil->GetPrecioVenta();
        montoPagado = 0;
        mensualidad = mensual1;
    } else if (op == "3") {
        periodo_financiamiento = 12 * 3;
        montoOp = automovil->GetPrecioVenta();
        montoPagado = 0;
        mensualidad = mensual3;

    } else if (op == "5") {
        periodo_financiamiento = 12 * 5;
        montoOp = automovil->GetPrecioVenta();
        montoPagado = 0;
        mensualidad = mensual5;
    }

    TAdeudo *tmp = listAdeudo->CreateAdd(-1, id_cliente, id_auto, fechaOp, periodo_financiamiento, montoOp, montoPagado, mensualidad);

    if (tmp == NULL) {
        cout << "\nERROR en Operacion";
    } else {
        cout << "\nOperacion realizada con Exito";
        listAdeudo->WriteArchivo();
    }

    cout << "\n\nGracias a Ud. \n";

    // Limpia el buffer de entrada
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    std::cout << "\nPresione enter para continuar ... ";
    std::cin.get();

}


// *****************************************************************************************************************************

void BuscarAuto() {
    if (listAuto->IsEmpty()) {
        cout << "\n\nNo Hay Autos Disponibles";
        return;
    }

    cout << "\n\n";
    cout << "\n********************************************************************************";
    cout << "\n       ***************** MENU BUSCAR AUTO";
    cout << "\n\n";


    string modelo;
    cout << "\nIndique el Automovil Modelo= ? ";
    cin >> modelo;

    cout << "\n\n ID    MARCA         MODELO         AÑO      PRECIO-VENTA  " << endl;

    int cont = 0;
    for (int idx = 0; idx < listAuto->GetTam(); idx++) {
        TAutomovil *aux = listAuto->Read(idx);

        if ((aux != NULL) && aux->IsDisponible() && (aux->GetModelo() == modelo)) {
            cout << aux->GetId() << " : " << aux->GetMarca() << " : " << aux->GetModelo() << " : " << aux->GetYear() << " : " << aux->GetPrecioVenta() << endl;
        }
    }

    if (cont <= 0) {
        cout << "\n\nNo hay autos con ese Modelo";
    }


    // Limpia el buffer de entrada
    cin.ignore(numeric_limits<streamsize>::max(), '\n');
    std::cout << "\n\nPresione enter para continuar ... ";
    std::cin.get();

}


// *****************************************************************************************************************************

void EditarAuto() {
    if (listAuto->IsEmpty()) {
        cout << "\n\nLista Automoviles Vacia";
        return;
    }


    string marca;
    string modelo;
    int year;
    int precioVenta;
    bool disponible;

    cout << "\n\n";
    cout << "\n********************************************************************************";
    cout << "\n       ***************** MENU EDITAR AUTOMOVIL";
    cout << "\n\n";

    cout << " ID    MARCA         MODELO         AÑO      PRECIO-VENTA  " << endl;

    for (int idx = 0; idx < listAuto->GetTam(); idx++) {
        TAutomovil *aux = listAuto->Read(idx);

        if ((aux != NULL) && aux->IsDisponible()) {
            cout << aux->GetId() << " : " << aux->GetMarca() << " : " << aux->GetModelo() << " : " << aux->GetYear() << " : " << aux->GetPrecioVenta() << endl;
        }
    }

    int id;
    cout << "\n\nIndique el ID del automovil= ? ";
    cin >> id;

    TAutomovil *aux = listAuto->GetByID(id);

    if (aux == NULL) {
        cout << "Auto no encontrado";
        std::cout << "\nPresione enter para continuar ... ";
        std::cin.get();
        return;
    }

    cout << "\nAutomovil ID= " << aux->GetId();

    cout << "\nAnterior Marca= " << aux->GetMarca();
    cout << "\nNueva Marca= ";
    cin >> marca;
    for (auto & c : marca) c = toupper(c);

    cout << "\nAnterior Modelo= " << aux->GetModelo();
    cout << "\nNuevo Modelo= ?";
    cin >> modelo;
    for (auto & c : modelo) c = toupper(c);

    cout << "\nAnterior Año= " << aux->GetYear();
    cout << "\nNuevo Año= ?";
    cin >> year;

    cout << "\nAnterior Precio Venta= " << aux->GetPrecioVenta();
    cout << "\nNuevo Precio Venta= ";
    cin >> precioVenta;

    string strDisponible;
    cout << "\nAnterior Disponible= " << aux->IsDisponible();
    cout << "\nNuevo Disponible [s/n]= " << strDisponible;
    cin >> strDisponible;

    // toUpper
    for (auto & c : strDisponible) c = toupper(c);

    disponible = (strDisponible == "S");

    TAutomovil *tmpx = listAuto->UpdateById(aux->GetId(), marca, modelo, year, precioVenta, disponible);

    if (tmpx == NULL) {
        cout << "\nError al tratar de Actualizar";
    } else {
        listAuto->WriteArchivo();
        cout << "\nAutomovil modificado con exito";
    }

    std::cout << "\nPresione enter para continuar ... ";
    std::cin.get();

}


// *****************************************************************************************************************************

void EditarCliente() {

    if (listCliente->IsEmpty()) {
        cout << "\n\nLista Clientes Vacia";
        return;
    }


    int id;
    string nombres;
    string apellidos;
    string fechaNacimiento;
    string email;

    cout << "\n\n";
    cout << "\n********************************************************************************";
    cout << "\n       ***************** MENU EDITAR CLIENTE";
    cout << "\n\n";

    cout << " ID    NOMBRES         APELLIDOS  " << endl;

    for (int idx = 0; idx < listCliente->GetTam(); idx++) {
        TCliente *aux = listCliente->Read(idx);

        if ((aux != NULL)) {
            cout << aux->GetId() << " : " << aux->GetNombres() << " : " << aux->GetApellidos() << endl;
        }
    }

    cout << "\n\nIndique el ID del Cliente= ? ";
    cin >> id;

    TCliente *aux = listCliente->GetById(id);

    if (aux == NULL) {
        cout << "Cliente no encontrado";
        std::cout << "\nPresione enter para continuar ... ";
        std::cin.get();
        return;
    }

    cout << "\nCliente ID= " << aux->GetId();

    cout << "\nAnterior Nombres= " << aux->GetNombres();
    cout << "\nNuevo Nombres= ";
    cin >> nombres;
    for (auto & c : nombres) c = toupper(c);

    cout << "\nAnterior Apellidos= " << aux->GetApellidos();
    cout << "\nNuevo Apellidos= ? ";
    cin >> apellidos;
    for (auto & c : apellidos) c = toupper(c);

    cout << "\nAnterior Fecha Nacimiento= " << aux->GetFechaNacimiento();
    cout << "\nNuevo  Fecha Nacimiento= ? ";
    cin >> fechaNacimiento;

    cout << "\nAnterior email= " << aux->GetEmail();
    cout << "\nNuevo email= ";
    cin >> email;


    // toUpper
    for (auto & c : email) c = toupper(c);

    TCliente *tmpx = listCliente->UpdateById(aux->GetId(), nombres, apellidos, fechaNacimiento, email);

    if (tmpx == NULL) {
        cout << "\nError al tratar de Actualizar";
    } else {
        listCliente->WriteArchivo();
        cout << "\nCliente modificado con exito";
    }

    std::cout << "\nPresione enter para continuar ... ";
    std::cin.get();

}


// *****************************************************************************************************************************

void RealizarPago() {
    if (listAdeudo->IsEmpty()) {
        cout << "\n\nRealizar Pagos Vacia ";
        return;
    }


    cout << "\n\n";
    cout << "\n********************************************************************************";
    cout << "\n       ***************** MENU REALIZAR PAGO";
    cout << "\n\n";

    cout << "\nListado de Adeudos";
    cout << "\nID    CLIENTE" << endl;

    for (int idx = 0; idx < listAdeudo->GetTam(); idx++) {
        TAdeudo *aux = listAdeudo->Read(idx);

        if ((aux != NULL) && (aux->GetMensualidad() != 0) && (aux->GetMontoPagado() < aux->GetMontoOp())) {
            TCliente *clte = listCliente->GetById(aux->GetId_cliente());

            cout << aux->GetId() << " : " << clte->GetNombres() << " : " << clte->GetApellidos() << endl;
        }
    }

    int id;
    int id_adeudo;
    int montoPagado;
    string fechaPago;

    cout << "\n\nIndique el ID del Adeudo= ? ";
    cin >> id;

    TAdeudo *auxAdeudo = listAdeudo->GetById(id);

    if (auxAdeudo == NULL) {
        cout << "Adeudo no encontrado";
        std::cout << "\nPresione enter para continuar ... ";
        std::cin.get();
        return;
    }

    cout << "\nAdeudo ID= " << auxAdeudo->GetId();

    cout << "\nMonto Pago= ? ";
    cin >> montoPagado;

    cout << "\nFecha de Pago= ? ";
    cin >> fechaPago;

    TPagos *tmpx = listpPagos->CreateAdd(-1, auxAdeudo->GetId(), montoPagado, fechaPago);

    // Actualizar el pagos en el Adeudo
    auxAdeudo->SetMontoPagado(auxAdeudo->GetMontoPagado() + montoPagado);

    if (tmpx == NULL) {
        cout << "\nError al tratar de Crear el Pago";
    } else {
        listpPagos->WriteArchivo();
        listAdeudo->WriteArchivo();
        cout << "\nPago realizado con Exito";
    }

    std::cout << "\nPresione enter para continuar ... ";
    std::cin.get();

}
