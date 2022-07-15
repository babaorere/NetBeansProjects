/* 
 * File:   clases.h
 * Author: manager
 *
 * Created on 2 de noviembre de 2021, 3:42 p.m.
 */

#ifndef CLASES_H
#define CLASES_H

#include <string>

#include "TCliente.h"

using namespace std;

// **************************************************************************************************
// **************************************************************************************************
// **************************************************************************************************

class TAutomovil {
private:
    int id;
    string marca;
    string modelo;
    int year;
    int precioVenta;
    bool disponible;


public:
    //    TAutomovil();
    TAutomovil(int _id, string _marca, string modelo, int _year, int _precioVenta, bool _disponible);

    // Getters y Setters
    void SetDisponible(bool usado);
    bool IsDisponible() const;
    void SetPrecioVenta(int precioVenta);
    int GetPrecioVenta() const;
    void SetYear(int year);
    int GetYear() const;
    void SetModelo(string modelo);
    string GetModelo() const;
    void SetMarca(string marca);
    string GetMarca() const;
    int GetId() const;

};


// Constructor con parametros

TAutomovil::TAutomovil(int _id, string _marca, string _modelo, int _year, int _precioVenta, bool _disponible) {
    this->id = _id;
    this->marca = _marca;
    this->modelo = _modelo;
    this->year = _year;
    this->precioVenta = _precioVenta;
    this->disponible = _disponible;
}

void TAutomovil::SetDisponible(bool usado) {
    this->disponible = usado;
}

bool TAutomovil::IsDisponible() const {
    return disponible;
}

void TAutomovil::SetPrecioVenta(int precioVenta) {
    this->precioVenta = precioVenta;
}

int TAutomovil::GetPrecioVenta() const {
    return precioVenta;
}

void TAutomovil::SetYear(int year) {
    this->year = year;
}

int TAutomovil::GetYear() const {
    return year;
}

void TAutomovil::SetModelo(string modelo) {
    this->modelo = modelo;
}

string TAutomovil::GetModelo() const {
    return modelo;
}

void TAutomovil::SetMarca(string marca) {
    this->marca = marca;
}

string TAutomovil::GetMarca() const {
    return marca;
}

int TAutomovil::GetId() const {
    return id;
}


// **************************************************************************************************
// **************************************************************************************************
// **************************************************************************************************


#define CANT_MAX_AUTO 100

// Esta cla sirve de contenedor de los automoviles

class TListAuto {
private:
    TAutomovil *myList[CANT_MAX_AUTO];
    int nextAdd;
    int nextId;


public:
    TListAuto();

    TAutomovil *CreateAdd(int _id, string _marca, string modelo, int _year, int _precioVenta, bool _disponible);
    TAutomovil *Read(int _idx);
    TAutomovil *GetByID(int _id);
    TAutomovil *Update(int _idx, string _marca, string modelo, int _year, int _precioVenta, bool _disponible);
    TAutomovil *UpdateById(int _id, string _marca, string modelo, int _year, int _precioVenta, bool _disponible);
    bool Delete(int _idx);
    bool IsFull() const;
    bool IsEmpty() const;

    int GetTam() const {
        return nextAdd;
    }

    bool ReadArchivo();
    bool WriteArchivo();

};

/**
 * Constructor
 */
TListAuto::TListAuto() {

    for (int i = 0; i < CANT_MAX_AUTO; i++) {
        this->myList[i] = 0;
    }

    nextAdd = 0;
    nextId = 1;
}

/**
 * Crear y Agregar un Automovil
 * 
 * @param _id
 * @param _marca
 * @param modelo
 * @param _year
 * @param _precioVenta
 * @param _disponible
 * @return 
 */
TAutomovil * TListAuto::CreateAdd(int _id, string _marca, string modelo, int _year, int _precioVenta, bool _disponible) {

    // Lista llena no se puede agregar
    if (IsFull()) {
        return 0; // retorna sin hacer algo
    }

    // crea el Automovil
    TAutomovil *aux;
    if (_id < 0) {
        aux = new TAutomovil(nextId, _marca, modelo, _year, _precioVenta, _disponible);
    } else {
        aux = new TAutomovil(_id, _marca, modelo, _year, _precioVenta, _disponible);
    }

    // Incrementamos el id
    nextId++;

    // Agrega el Automovil a la lista/vector
    this->myList[nextAdd] = aux;

    // Incrementa la siguiente posicion
    nextAdd++;

    // retorna el elemento recien creado
    return aux;
}

/**
 * 
 * @param _idx
 * @return 
 */
TAutomovil *TListAuto::Read(int _idx) {

    // Validar el indice    
    if ((_idx < 0) || (_idx >= CANT_MAX_AUTO) || (_idx >= nextAdd)) {
        return 0; // abortar
    }

    return this->myList[_idx];
}

/**
 * 
 * @param _idx
 * @return 
 */
TAutomovil *TListAuto::GetByID(int _id) {

    // Validar el indice    
    if ((_id < 0) || (_id >= CANT_MAX_AUTO)) {
        return 0; // abortar
    }

    // Recorrer la lista, y retornar si se encuantra el ID
    for (int i = 0; i < nextAdd; i++) {
        if (_id == myList[i]->GetId()) {
            return myList[i];
        }
    }

    return NULL;
}

/**
 * Actualiza un nuevo elemento en la posicion idx
 * 
 * @param idx
 * @return 
 */
TAutomovil *TListAuto::Update(int _idx, string _marca, string modelo, int _year, int _precioVenta, bool _disponible) {

    // Validar el indice
    if ((_idx < 0) || (_idx >= CANT_MAX_AUTO) || (_idx >= nextAdd)) {
        return 0;
    }


    // el idx ya se ha validado
    TAutomovil *old = this->myList[_idx];

    // Segunda validacion
    if (old == NULL) {
        return 0; // abortar
    }

    // crear el Automovil, con el mismo id, ya que se trata de una actualizacion
    TAutomovil *updateAuto = new TAutomovil(old->GetId(), _marca, modelo, _year, _precioVenta, _disponible);

    // Eliminarlo de ser posible
    delete old;

    // Actualizar el automovil
    this->myList[_idx] = updateAuto;

    // retorna el elemento recien creado
    return updateAuto;

}

/**
 * Actualiza un nuevo elemento en la posicion idx
 * 
 * @param idx
 * @return 
 */
TAutomovil *TListAuto::UpdateById(int _id, string _marca, string _modelo, int _year, int _precioVenta, bool _disponible) {

    // Validar el indice
    if ((_id < 0) || (_id >= CANT_MAX_AUTO)) {
        return 0;
    }

    // Recorrer la lista, y retornar si se encuantra el ID
    int idx = -1;
    for (int i = 0; i < nextAdd; i++) {
        if (_id == myList[i]->GetId()) {
            idx = i;
            break;
        }
    }

    if (idx < 0) {
        return NULL;
    }

    return Update(idx, _marca, _modelo, _year, _precioVenta, _disponible);
}

/**
 * Eliminar el Automovil de la lista, y de la memoria
 * 
 * @param _idx
 * @return 
 */
bool TListAuto::Delete(int _idx) {

    // Validar el indice
    if ((_idx < 0) || (_idx >= CANT_MAX_AUTO) || (_idx >= nextAdd)) {
        return false;
    }

    // Eliminarlo de ser posible
    if (this->myList[_idx] != 0) {
        delete this->myList[_idx];
        this->myList[_idx] = 0;
    }

    // desplazar el resto de los Automoviles
    for (int i = _idx; i < (nextAdd - 1); i++) {
        this->myList[i] = this->myList[i + 1];
    }

    this->myList[nextAdd - 1] = 0;

    // retorna 
    return true;
}

/**
 * Indica si la lista esta llena
 * 
 * @return 
 */
bool TListAuto::IsFull() const {

    return nextAdd >= CANT_MAX_AUTO;

}

bool TListAuto::IsEmpty() const {

    return nextAdd <= 0;

}

bool TListAuto::ReadArchivo() {

    string fileName = "automoviles.txt";

    int id;
    string marca;
    string modelo;
    int year;
    int precioVenta;
    bool disponible;
    string restoLinea;

    ifstream myfile(fileName);
    if (myfile.is_open()) {
        int cant = 0;
        while (!myfile.eof() && (cant < CANT_MAX_AUTO)) {
            myfile >> id >> marca >> modelo >> year >> precioVenta >> disponible >> restoLinea;
            CreateAdd(id, marca, modelo, year, precioVenta, disponible);
            cant++;
        }
        myfile.close();
    }

    return true;

}

bool TListAuto::WriteArchivo() {

    string fileName = "automoviles.txt";

    // Create and open a text file
    ofstream myfile(fileName, ios::trunc);

    for (int idx = 0; idx < nextAdd; idx++) {
        TAutomovil *aux = myList[idx];

        if (aux != NULL) {
            // Write to the file
            myfile << aux->GetId() << endl << aux->GetMarca() << endl << aux->GetModelo() << endl << aux->GetYear() << endl << aux->GetPrecioVenta() << endl << aux->IsDisponible() << endl;
        } else {
            break;
        }
    }

    // Close the file
    myfile.close();

    return true;
}





#endif /* CLASES_H */
