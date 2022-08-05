/* 
 * File:   TOperacion.h
 * Author: manager
 *
 * Created on 3 de noviembre de 2021, 9:46 a.m.
 */

#ifndef TOPERACION_H
#define TOPERACION_H


#include <string>

using namespace std;

// **************************************************************************************************
// **************************************************************************************************
// **************************************************************************************************

class TAdeudo {
private:
    int id;
    int id_cliente;
    int id_auto;
    string fechaOp;

    int periodo_financiamiento;
    int montoOp;
    int montoPagado;
    int mensualidad;

public:
    TAdeudo(int _id, int _id_cliente, int _id_auto, string _fechaOp, int _periodo_financiamiento, int _montoOP, int _montoPagado, int _mensualidad);
    void SetMontoPagado(int montoPagado);
    int GetMontoPagado() const;
    void SetMontoOp(int montoOp);
    int GetMontoOp() const;
    void SetPeriodo_financiamiento(int periodo_financiamiento);
    int GetPeriodo_financiamiento() const;
    void SetFechaOp(string fechaOp);
    string GetFechaOp() const;
    void SetId_auto(int id_auto);
    int GetId_auto() const;
    void SetId_cliente(int id_cliente);
    int GetId_cliente() const;
    int GetId() const;
    int GetMensualidad() const;
};

/** 
 * Constructor con parametros
 */
TAdeudo::TAdeudo(int _id, int _id_cliente, int _id_auto, string _fechaOp, int _periodo_financiamiento, int _montoOP, int _montoPagado, int _mensualidad) {
    this->id = _id;
    this->id_cliente = _id_cliente;
    this->id_auto = _id_auto;
    this->fechaOp = _fechaOp;
    this->periodo_financiamiento = _periodo_financiamiento;
    this->montoOp = _montoOP;
    this->montoPagado = _montoPagado;
    this->mensualidad = _mensualidad;
}

void TAdeudo::SetMontoPagado(int montoPagado) {
    this->montoPagado = montoPagado;
}

int TAdeudo::GetMontoPagado() const {
    return montoPagado;
}

void TAdeudo::SetMontoOp(int montoOp) {
    this->montoOp = montoOp;
}

int TAdeudo::GetMontoOp() const {
    return montoOp;
}

void TAdeudo::SetPeriodo_financiamiento(int periodo_financiamiento) {
    this->periodo_financiamiento = periodo_financiamiento;
}

int TAdeudo::GetPeriodo_financiamiento() const {
    return periodo_financiamiento;
}

void TAdeudo::SetFechaOp(string fechaOp) {
    this->fechaOp = fechaOp;
}

string TAdeudo::GetFechaOp() const {
    return fechaOp;
}

void TAdeudo::SetId_auto(int id_auto) {
    this->id_auto = id_auto;
}

int TAdeudo::GetId_auto() const {
    return id_auto;
}

void TAdeudo::SetId_cliente(int id_cliente) {
    this->id_cliente = id_cliente;
}

int TAdeudo::GetId_cliente() const {
    return id_cliente;
}

int TAdeudo::GetId() const {
    return id;
}

int TAdeudo::GetMensualidad() const {
    return mensualidad;
}


// **************************************************************************************************
// **************************************************************************************************
// **************************************************************************************************


#define MAX_CANT_ADEUDO 10000

/**
 *  Esta cla sirve de contenedor de los clientes
 */
class TListAdeudo {
private:
    TAdeudo *myList[MAX_CANT_ADEUDO];
    int nextAdd;
    int nextId;


public:
    TListAdeudo();

    TAdeudo *CreateAdd(int _id, int _id_cliente, int _id_auto, string _fechaOp, int _periodo_financiamiento, int _montoOP, int _montoPagado, int _mensualidad);
    TAdeudo *Read(int _idx);
    TAdeudo *Update(int _idx, int _id_cliente, int _id_auto, string _fechaOp, int _periodo_financiamiento, int _montoOP, int _montoPagado, int _mensualidad);
    bool Delete(int _idx);
    bool IsFull() const;
    TAdeudo *GetById(int _id);

    bool IsEmpty() const {
        return nextAdd <= 0;
    }

    int GetTam() const {
        return nextAdd;
    }

    bool ReadArchivo();
    bool WriteArchivo();
};

/**
 * Constructor
 */
TListAdeudo::TListAdeudo() {

    for (int i = 0; i < MAX_CANT_ADEUDO; i++) {
        this->myList[i] = 0;
    }

    nextAdd = 0;
    nextId = 1;
}

/**
 * Crear y Agregar un Elemento
 * 
 */
TAdeudo * TListAdeudo::CreateAdd(int _id, int _id_cliente, int _id_auto, string _fechaOp, int _periodo_financiamiento, int _montoOP, int _montoPagado, int _mensualidad) {

    // Lista llena no se puede agregar
    if (IsFull()) {
        return 0; // retorna sin hacer algo
    }


    // crea el Elemento
    TAdeudo *aux;

    if (_id <= 0) {
        aux = new TAdeudo(nextId, _id_cliente, _id_auto, _fechaOp, _periodo_financiamiento, _montoOP, _montoPagado, _mensualidad);
    } else {
        aux = new TAdeudo(_id, _id_cliente, _id_auto, _fechaOp, _periodo_financiamiento, _montoOP, _montoPagado, _mensualidad);
    }

    // Incrementamos el id
    nextId++;

    // Agrega el Elemento a la lista/vector
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
TAdeudo *TListAdeudo::Read(int _idx) {

    // Validar el indice    
    if ((_idx < 0) || (_idx >= MAX_CANT_ADEUDO) || (_idx >= nextAdd)) {
        return 0; // abortar
    }

    return this->myList[_idx];
}

/**
 * Actualiza un nuevo elemento en la posicion idx
 * 
 * @param idx
 * @return 
 */
TAdeudo *TListAdeudo::Update(int _idx, int _id_cliente, int _id_auto, string _fechaOp, int _periodo_financiamiento, int _montoOP, int _montoPagado, int _mensualidad) {

    // Validar el indice
    if ((_idx < 0) || (_idx >= MAX_CANT_ADEUDO) || (_idx >= nextAdd)) {
        return 0;
    }

    // Lista llena no se puede agregar
    if (IsFull()) {
        return 0; // retorna sin hacer algo
    }

    // el idx ya se ha validado
    TAdeudo *old = this->myList[_idx];

    // Segunda validacion
    if (old == 0) {
        return 0; // abortar
    }

    // crear el Elemento, con el mismo id, ya que se trata de una actualizacion
    TAdeudo *updateElemento = new TAdeudo(old->GetId(), _id_cliente, _id_auto, _fechaOp, _periodo_financiamiento, _montoOP, _montoPagado, _mensualidad);

    // Eliminarlo de ser posible
    delete old;

    // Actualizar el automovil
    this->myList[_idx] = updateElemento;

    // retorna el elemento recien creado
    return updateElemento;

}

/**
 * Eliminar el Elemento de la lista, y de la memoria
 * 
 * @param _idx
 * @return 
 */
bool TListAdeudo::Delete(int _idx) {

    // Validar el indice
    if ((_idx < 0) || (_idx >= MAX_CANT_ADEUDO) || (_idx >= nextAdd)) {
        return false;
    }

    // Eliminarlo de ser posible
    if (this->myList[_idx] != 0) {
        delete this->myList[_idx];
        this->myList[_idx] = 0;
    }

    // desplazar el resto de los elementos
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
bool TListAdeudo::IsFull() const {

    return nextAdd >= MAX_CANT_ADEUDO;

}

bool TListAdeudo::ReadArchivo() {

    string fileName = "adeudo.txt";

    int id;
    int id_cliente;
    int id_auto;
    string fechaOp;

    int periodo_financiamiento;
    int montoOp;
    int montoPagado;
    int mensualidad;
    string restoLinea;

    ifstream myfile(fileName);
    if (myfile.is_open()) {
        int cant = 0;
        while (!myfile.eof() && (cant < MAX_CANT_CLTE)) {
            myfile >> id >> id_cliente >> id_cliente >> fechaOp >> periodo_financiamiento >> montoOp >> montoPagado >> mensualidad >> restoLinea;
            CreateAdd(id, id_cliente, id_auto, fechaOp, periodo_financiamiento, montoOp, montoPagado, mensualidad);
            cout << id << " : " << id_cliente << " : " << id_auto << " : " << fechaOp << " : " << periodo_financiamiento << " : " << montoOp << " : " << montoPagado << " : " << mensualidad << endl;
            cant++;
        }
        myfile.close();
    }

    return true;

}

bool TListAdeudo::WriteArchivo() {

    string fileName = "adeudo.txt";

    // Create and open a text file
    ofstream myfile(fileName);

    for (int idx = 0; idx < nextAdd; idx++) {
        TAdeudo *aux = myList[idx];

        if (aux != NULL) {
            myfile << aux->GetId() << endl << aux->GetId_cliente() << endl << aux->GetId_auto() << endl << aux->GetFechaOp() << endl << aux->GetPeriodo_financiamiento() << endl << aux->GetMontoOp() << endl << aux->GetMontoPagado() << endl << aux->GetMensualidad() << endl;
        } else {
            break;
        }
    }

    // Close the file
    myfile.close();

    return true;
}

/**
 * 
 * @param _idx
 * @return 
 */
TAdeudo *TListAdeudo::GetById(int _id) {

    // Validar el indice    
    if ((_id < 0) || (_id >= MAX_CANT_CLTE)) {
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


#endif /* TOPERACION_H */

