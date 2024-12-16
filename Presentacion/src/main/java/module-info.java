/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module Presentacion {
    requires Negocio;
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
    requires java.desktop;
    requires java.logging;
    requires com.formdev.flatlaf;
}
