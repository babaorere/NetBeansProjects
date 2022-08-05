
/* 
 * File:   TCliente.h
 * Author: manager
 *
 * Created on 3 de noviembre de 2021, 8:44 a.m.
 */

#ifndef TCLIENTE_H
#define TCLIENTE_H


#include <string>
#include <ios>
#include <iostream>
#include <fstream> 

using namespace std;

// **************************************************************************************************
// **************************************************************************************************
// **************************************************************************************************

class TCliente {
private:
    int id;
    string nombres;
    string apellidos;
    string fechaNacimiento;
    string email;

public:
    TCliente(int _id, string _nombres, string _apellidos, string _fechaNaciomiento, string _email);
    TCliente(TCliente *_other);

    // Getters y Setters
    void SetEmail(string email);
    string GetEmail() const;
    void SetFechaNacimiento(string fechaNacimiento);
    string GetFechaNacimiento() const;
    void SetApellidos(string apellidos);
    string GetApellidos() const;
    void SetNombres(string nombres);
    string GetNombres() const;
    int GetId() const;

};

void TCliente::SetEmail(string email) {
    this->email = email;
}

string TCliente::GetEmail() const {
    return email;
}

void TCliente::SetFechaNacimiento(string fechaNacimiento) {
    this->fechaNacimiento = fechaNacimiento;
}

string TCliente::GetFechaNacimiento() const {
    return fechaNacimiento;
}

void TCliente::SetApellidos(string apellidos) {
    this->apellidos = apellidos;
}

string TCliente::GetApellidos() const {
    return apellidos;
}

void TCliente::SetNombres(string nombres) {
    this->nombres = nombres;
}

string TCliente::GetNombres() const {
    return nombres;
}

int TCliente::GetId() const {
    return id;
}

/** 
 * Constructor con parametros
 */
TCliente::TCliente(int _id, string _nombres, string _apellidos, string _fechaNacimiento, string _email) {
    this->id = _id;
    this->nombres = _nombres;
    this->apellidos = _apellidos;
    this->fechaNacimiento = _fechaNacimiento;
    this->email = _email;
}

/** 
 * Copy Contructor
 */
TCliente::TCliente(TCliente *_other) {
    if (_other != 0) {
        this->id = _other->id;
        this->nombres = _other->nombres;
        this->apellidos = _other->apellidos;
        this->fechaNacimiento = _other->fechaNacimiento;
        this->email = _other->email;
    } else {
        this->id = -1;
        this->nombres = "";
        this->apellidos = "";
        this->fechaNacimiento = "";
        this->email = "";
    }
}


// **************************************************************************************************
// **************************************************************************************************
// **************************************************************************************************


#define MAX_CANT_CLTE 100

/**
 *  Esta cla sirve de contenedor de los clientes
 */
class TListCliente {
private:
    TCliente *myList[MAX_CANT_CLTE];
    int nextAdd;
    int nextId;


public:
    TListCliente();
    TListCliente(TListCliente *_other);

    TCliente *CreateAdd(int _id, string _nombres, string _apellidos, string _fechaNacimiento, string _email);
    TCliente *Read(int _idx);
    TCliente *Update(int _idx, string _nombres, string _apellidos, string _fechaNacimiento, string _email);
    TCliente *UpdateById(int _id, string _nombres, string _apellidos, string _fechaNacimiento, string _email);
    TCliente *GetById(int _id);

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
TListCliente::TListCliente() {

    for (int i = 0; i < MAX_CANT_CLTE; i++) {
        this->myList[i] = 0;
    }

    nextAdd = 0;
    nextId = 1;
}

/**
 * Copy Constructor
 */
TListCliente::TListCliente(TListCliente *_other) {

    // Crea una copia de la otra lista
    int cont = 0;
    for (int i = 0; i < MAX_CANT_CLTE; i++) {
        if (_other->myList[i] != 0) {
            this->myList[i] = new TCliente(_other->myList[i]);
            cont++;
        } else {
            this->myList[i] = 0;
        }
    }

    nextAdd = cont;

    // Suponesmos que el ultimo id, es el mayor de todos, dado que se agregan elemntos al final
    nextId = this->myList[cont - 1]->GetId() + 1;
}

/**
 * Crear y Agregar un Cliente
 * 
 */
TCliente * TListCliente::CreateAdd(int _id, string _nombres, string _apellidos, string _fechaNacimiento, string _email) {

    // Lista llena no se puede agregar
    if (IsFull()) {
        return 0; // retorna sin hacer algo
    }

    // crea el Cliente
    TCliente *aux;

    if (_id <= 0) {
        aux = new TCliente(nextId, _nombres, _apellidos, _fechaNacimiento, _email);
    } else {
        aux = new TCliente(_id, _nombres, _apellidos, _fechaNacimiento, _email);
    }

    // Incrementamos el id
    nextId++;

    // Agrega el Cliente a la lista/vector
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
TCliente *TListCliente::Read(int _idx) {

    // Validar el indice    
    if ((_idx < 0) || (_idx >= MAX_CANT_CLTE) || (_idx >= nextAdd)) {
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
TCliente *TListCliente::Update(int _idx, string _nombres, string _apellidos, string _fechaNacimiento, string _email) {

    // Validar el indice
    if ((_idx < 0) || (_idx >= MAX_CANT_CLTE) || (_idx >= nextAdd)) {
        return 0;
    }

    // Lista llena no se puede agregar
    if (IsFull()) {
        return 0; // retorna sin hacer algo
    }

    // el idx ya se ha validado
    TCliente *old = this->myList[_idx];

    // Segunda validacion
    if (old == NULL) {
        return 0; // abortar
    }

    // crear el Cliente, con el mismo id, ya que se trata de una actualizacion
    TCliente *updateElemento = new TCliente(old->GetId(), _nombres, _apellidos, _fechaNacimiento, _email);

    // Eliminarlo de ser posible
    delete old;

    // Actualizar el automovil
    this->myList[_idx] = updateElemento;

    // retorna el elemento recien creado
    return updateElemento;

}

/**
 * 
 * @param _idx
 * @return 
 */
TCliente *TListCliente::GetById(int _id) {

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

TCliente *TListCliente::UpdateById(int _id, string _nombres, string _apellidos, string _fechaNacimiento, string _email) {

    // Validar el indice
    if ((_id < 0) || (_id >= MAX_CANT_CLTE)) {
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

    return Update(idx, _nombres, _apellidos, _fechaNacimiento, _email);
}

/**
 * Eliminar el Cliente de la lista, y de la memoria
 * 
 * @param _idx
 * @return 
 */
bool TListCliente::Delete(int _idx) {

    // Validar el indice
    if ((_idx < 0) || (_idx >= MAX_CANT_CLTE) || (_idx >= nextAdd)) {
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
bool TListCliente::IsFull() const {

    return nextAdd >= MAX_CANT_CLTE;

}

bool TListCliente::ReadArchivo() {

    char *fileName = "clientes.txt";

    int id;
    string nombres;
    string apellidos;
    string fechaNacimiento;
    string email;
    string restoLinea;

    ifstream myfile(fileName);
    if (myfile.is_open()) {
        int cant = 0;
        while (!myfile.eof() && (cant < MAX_CANT_CLTE)) {
            myfile >> id >> nombres >> apellidos >> fechaNacimiento >> email;
            CreateAdd(id, nombres, apellidos, fechaNacimiento, email);
            cout << id << endl << nombres << endl << apellidos << endl << fechaNacimiento << endl << email << endl;
            cant++;
        }
        myfile.close();
    }

    return true;

}

bool TListCliente::WriteArchivo() {

    string fileName = "clientes.txt";

    // Create and open a text file
    ofstream myfile(fileName);

    for (int idx = 0; idx < nextAdd; idx++) {
        TCliente *aux = myList[idx];

        if (aux != NULL) {
            myfile << aux->GetId() << endl << aux->GetNombres() << endl << aux->GetApellidos() << endl << aux->GetFechaNacimiento() << endl << aux->GetEmail() << endl;
        } else {
            break;
        }
    }

    // Close the file
    myfile.close();

    return true;
}






#endif /* TCLIENTE_H */

