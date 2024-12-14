/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module Negocio {
    requires Persistencia;
    requires mysql.connector.j;
    requires com.google.protobuf;
    requires org.eclipse.persistence.core;
    requires org.eclipse.persistence.asm;
    requires eclipselink.antlr;
    requires org.eclipse.persistence.jpa;
    requires org.eclipse.persistence.jpa.jpql;
    requires org.eclipse.persistence.moxy;
    requires java.persistence;
    
    // mostrar solo ciertos paquetes al exterior
    exports com.restaurante.negocio.bo.implementaciones;
    exports com.restaurante.negocio.bo.interfaces;
    exports com.restaurante.negocio.excepciones;
    exports com.restaurante.negocio.dtos;
}
