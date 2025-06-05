# Sistema de Microservicios de Usuarios y Tareas

Este proyecto es un sistema backend basado en arquitectura de microservicios, desarrollado en Java utilizando Spring Boot. Consiste en una aplicación distribuida enfocada en la gestión de usuarios y sus tareas, como parte del trabajo académico para la asignatura **Desarrollo de Aplicaciones Web** en la Fundación UNIR (Universidad Internacional de La Rioja).

## 📚 Descripción General del Proyecto

El sistema está compuesto por los siguientes microservicios y componentes de infraestructura:

- **User Service**: Gestiona operaciones CRUD para los datos de usuario.
- **Task Service**: Administra las tareas asociadas a los usuarios.
- **Eureka Server**: Descubrimiento de servicios para el registro dinámico.
- **API Gateway**: Punto de entrada central para el enrutamiento de peticiones.
- **Config Server**: Gestión centralizada de configuración para todos los servicios.

Todos los servicios están integrados y se comunican mediante APIs REST y mecanismos de descubrimiento de servicios.

## 🔧 Tecnologías Utilizadas

- Java 17  
- Spring Boot 3.4.x  
- Spring Cloud (Eureka, Config Server, Gateway)  
- Maven  
- MySQL (como almacenamiento persistente)  
- Feign Client para comunicación entre microservicios  
- APIs RESTful  
- Git & GitHub  

## 🚀 Funcionalidades

- Operaciones CRUD completas para usuarios y tareas  
- Búsquedas por ID y por atributo (por ejemplo: nombre o título)  
- Configuración externa a través de Spring Cloud Config  
- Registro y descubrimiento de servicios con Eureka  
- Enrutamiento mediante API Gateway  
- Comunicación entre servicios con Feign Client  
- Modularización siguiendo principios SOLID  
- Manejo de errores con excepciones personalizadas y `@ControllerAdvice`

## 📁 Estructura del Proyecto

user-task-system/ </br>
├── config-server/</br>
├── eureka-server/</br>
├── gateway-server/</br>
├── user-service/</br>
└── task-service/</br>


## 📦 Instrucciones de Ejecución

1. Clona este repositorio  
2. Construye todos los módulos con Maven  
3. Inicia `config-server`, luego `eureka-server`, seguido de `gateway`, `user-service` y `task-service`  
4. Usa Postman o curl para interactuar con las APIs vía el gateway (por ejemplo, `http://localhost:8080/api/user`)
