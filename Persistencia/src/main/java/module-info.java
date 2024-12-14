
module Persistencia {
    requires mysql.connector.j;
    requires com.google.protobuf;
    requires org.eclipse.persistence.core;
    requires org.eclipse.persistence.asm;
    requires eclipselink.antlr;
    requires org.eclipse.persistence.jpa;
    requires org.eclipse.persistence.jpa.jpql;
    requires org.eclipse.persistence.moxy;
    requires java.persistence;
    requires org.eclipse.persistence.jpa.modelgen.processor;
    
    // mostrar solo ciertos paquetes
    exports com.restaurante.persistencia.dao.interfaces;
    exports com.restaurante.persistencia.entidades;
    exports com.restaurante.persistencia.excepciones;
    exports com.restaurante.persistencia.dao.implementaciones;
}
