/* 
 * File:   TPagos.h
 * Author: manager
 *
 * Created on 3 de noviembre de 2021, 11:41 a.m.
 */

#ifndef TPAGOS_H
#define TPAGOS_H


#include <string>


using namespace std;


// **************************************************************************************************
// **************************************************************************************************
// **************************************************************************************************

class TPagos {
private:
    int id;
    int id_adeudo;
    int montoPagado;
    string fechaPago;

public:
    //    TPagos();
    TPagos(int _id, int _id_adeudo, int _montoPagado, string _fechaPago);
    void SetFechaPago(string fechaPago);
    string GetFechaPago() const;
    void SetMontoPagado(int montoPagado);
    int GetMontoPagado() const;
    void SetId_adeudo(int id_adeudo);
    int GetId_adeudo() const;
    int GetId() const;

};

/** 
 * Constructor con parametros
 */
TPagos::TPagos(int _id, int _id_adeudo, int _montoPagado, string _fechaPago) {
    this->id = _id;
    this->id_adeudo = _id_adeudo;
    this->montoPagado = _montoPagado;
    this->fechaPago = _fechaPago;
}

void TPagos::SetFechaPago(string fechaPago) {
    this->fechaPago = fechaPago;
}

string TPagos::GetFechaPago() const {
    return fechaPago;
}

void TPagos::SetMontoPagado(int montoPagado) {
    this->montoPagado = montoPagado;
}

int TPagos::GetMontoPagado() const {
    return montoPagado;
}

void TPagos::SetId_adeudo(int id_adeudo) {
    this->id_adeudo = id_adeudo;
}

int TPagos::GetId_adeudo() const {
    return id_adeudo;
}

int TPagos::GetId() const {
    return id;
}


// **************************************************************************************************
// **************************************************************************************************
// **************************************************************************************************


#define MAX_CANT_PAGOS 10000

/**
 *  Esta cla sirve de contenedor de los clientes
 */
class TListPagos {
private:
    TPagos *myList[MAX_CANT_PAGOS];
    int nextAdd;
    int nextId;


public:
    TListPagos();

    TPagos *CreateAdd(int _id, int _id_adeudo, int _montoPagado, string _fechaPago);
    TPagos *Read(int _idx);
    TPagos *Update(int _idx, int _id_adeudo, int _montoPagado, string _fechaPago);
    bool Delete(int _idx);
    bool IsFull() const;

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
TListPagos::TListPagos() {

    for (int i = 0; i < MAX_CANT_PAGOS; i++) {
        this->myList[i] = 0;
    }

    nextAdd = 0;
    nextId = 1;
}

/**
 * Crear y Agregar un Elemento
 * 
 */
TPagos * TListPagos::CreateAdd(int _id, int _id_adeudo, int _montoPagado, string _fechaPago) {

    // Lista llena no se puede agregar
    if (IsFull()) {
        return 0; // retorna sin hacer algo
    }

    // crea el Elemento
    TPagos *aux;
    if (_id <= 0) {
        aux = new TPagos(nextId, _id_adeudo, _montoPagado, _fechaPago);
    } else {
        aux = new TPagos(_id, _id_adeudo, _montoPagado, _fechaPago);
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
TPagos *TListPagos::Read(int _idx) {

    // Validar el indice    
    if ((_idx < 0) || (_idx >= MAX_CANT_PAGOS) || (_idx >= nextAdd)) {
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
TPagos *TListPagos::Update(int _idx, int _id_adeudo, int _montoPagado, string _fechaPago) {

    // Validar el indice
    if ((_idx < 0) || (_idx >= MAX_CANT_PAGOS) || (_idx >= nextAdd)) {
        return 0;
    }

    // Lista llena no se puede agregar
    if (IsFull()) {
        return 0; // retorna sin hacer algo
    }

    // el idx ya se ha validado
    TPagos *old = this->myList[_idx];

    // Segunda validacion
    if (NULL == 0) {
        return 0; // abortar
    }

    // crear el Elemento, con el mismo id, ya que se trata de una actualizacion
    TPagos *updateElemento = new TPagos(old->GetId(), _id_adeudo, _montoPagado, _fechaPago);

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
bool TListPagos::Delete(int _idx) {

    // Validar el indice
    if ((_idx < 0) || (_idx >= MAX_CANT_PAGOS) || (_idx >= nextAdd)) {
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
bool TListPagos::IsFull() const {

    return nextAdd >= MAX_CANT_PAGOS;

}

bool TListPagos::ReadArchivo() {

    string fileName = "pagos.txt";

    int id;
    int id_adeudo;
    int montoPagado;
    string fechaPago;

    ifstream myfile(fileName);
    if (myfile.is_open()) {
        int cant = 0;
        while (!myfile.eof() && (cant < MAX_CANT_PAGOS)) {
            myfile >> id >> id_adeudo >> montoPagado >> fechaPago;
            CreateAdd(id, id_adeudo, montoPagado, fechaPago);
            cout << id << id_adeudo << montoPagado << endl << fechaPago << endl;
            cant++;
        }
        myfile.close();
    }

    return true;

}

bool TListPagos::WriteArchivo() {

    string fileName = "pagos.txt";

    // Create and open a text file
    ofstream myfile(fileName);

    for (int idx = 0; idx < nextAdd; idx++) {
        TPagos *aux = myList[idx];

        if (aux != NULL) {
            myfile << aux->GetId() << endl << aux->GetId_adeudo() << endl << aux->GetMontoPagado() << endl << aux->GetFechaPago() << endl;
        } else {
            break;
        }
    }

    // Close the file
    myfile.close();

    return true;
}


#endif /* TPAGOS_H */

