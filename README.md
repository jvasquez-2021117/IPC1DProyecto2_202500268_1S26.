# GameZone Pro

GameZone Pro es una aplicación de escritorio desarrollada en **Java Swing** que centraliza diferentes experiencias gamer en una sola plataforma.  
El sistema fue construido con interfaz gráfica manual, navegación por paneles usando `CardLayout`, estructuras de datos implementadas desde cero y persistencia en archivos de texto.

## Descripción general

La aplicación permite al usuario interactuar con distintos módulos:

- **Tienda de Videojuegos**
- **Álbum de Cartas Coleccionables**
- **Eventos Especiales / Torneos**
- **Gamificación**
- **Generación de Reportes HTML**
- **Ver Datos del Estudiante**

Cada módulo se encuentra integrado dentro de una ventana principal y puede abrirse desde el menú principal.

---

## Tecnologías utilizadas

- **Lenguaje:** Java
- **Interfaz gráfica:** Swing
- **IDE:** NetBeans
- **Persistencia:** Archivos `.txt`
- **Reportes:** HTML + CSS embebido
- **Navegación:** `JFrame` + `CardLayout`
- **Concurrencia:** `Thread` y `synchronized`

---

## Estructura general del proyecto

El proyecto está organizado en paquetes para separar la lógica de la interfaz y los datos:

- `main` → clase principal del programa
- `vista` → paneles y ventanas del sistema
- `modelo` → clases de dominio
- `estructuras` → estructuras de datos implementadas desde cero
- `servicios` → lógica de negocio, persistencia y reportes
- `util` → clases auxiliares
- `recursos` → archivos de datos del sistema

---

## Funcionamiento del programa

### 1. Menú principal
Al iniciar la aplicación, se muestra una ventana principal desde la cual el usuario puede acceder a cada módulo del sistema.

### 2. Tienda de Videojuegos
Este módulo permite:

- visualizar el catálogo de juegos
- buscar por nombre o código
- filtrar por género y plataforma
- ver detalles de cada juego
- agregar juegos al carrito
- editar cantidades o eliminar productos
- confirmar compras
- visualizar historial de compras

El catálogo se carga desde `catalogo.txt` y el historial se guarda en `historial.txt`.

### 3. Álbum de Cartas Coleccionables
Este módulo permite:

- visualizar un álbum en forma de cuadrícula
- agregar cartas
- ver detalles de la carta seleccionada
- buscar cartas por nombre, tipo o rareza
- intercambiar cartas entre posiciones
- guardar el estado del álbum

La estructura interna del álbum se maneja mediante una **matriz ortogonal de nodos enlazados**.  
La información se guarda en `album.txt`.

### 4. Eventos Especiales / Torneos
Este módulo permite:

- visualizar torneos disponibles
- seleccionar un torneo
- inscribir usuarios a una cola de espera
- procesar la venta de tickets con dos taquillas simultáneas
- ver el estado de las taquillas en tiempo real
- ver la cola restante
- ver el historial de tickets vendidos

Los torneos se cargan desde `torneos.txt` y las ventas se guardan en `tickets_vendidos.txt`.

### 5. Gamificación
Este módulo muestra el progreso del usuario dentro del sistema:

- experiencia acumulada (XP)
- nivel actual
- rango
- barra de progreso
- logros desbloqueados
- podio de mejores usuarios
- leaderboard

La experiencia se obtiene por acciones como:

- comprar juegos
- conseguir cartas legendarias
- completar filas del álbum
- inscribirse a torneos
- iniciar sesión

El leaderboard se guarda en `leaderboard.txt`.

### 6. Reportes HTML
El sistema puede generar automáticamente reportes en formato HTML:

- **Inventario de Tienda**
- **Ventas**
- **Álbum**
- **Torneos**

Cada reporte:

- se genera con **CSS embebido**
- se guarda con **timestamp**
- se abre automáticamente en el navegador

Los reportes se almacenan en la carpeta `reportes`.

### 7. Datos del Estudiante
El sistema incluye una pantalla con la información del estudiante desarrollador del proyecto:

- nombre completo
- número de carné
- correo universitario
- sección
- semestre y año
- descripción breve del proyecto

---

## Estructuras de datos implementadas

Para cumplir con los requisitos del proyecto, se implementaron estructuras propias sin usar Java Collections Framework:

- **Lista Enlazada Simple**
- **Matriz ortogonal con nodos enlazados**
- **Cola**
- **Ordenamiento manual para el leaderboard**

---

## Persistencia de datos

El sistema utiliza archivos de texto para conservar la información entre ejecuciones.

### Archivos utilizados

- `catalogo.txt`
- `historial.txt`
- `album.txt`
- `torneos.txt`
- `tickets_vendidos.txt`
- `leaderboard.txt`

Además, los reportes HTML se generan dentro de la carpeta:

- `reportes/`

---

## Cómo ejecutar el programa

### Opción 1: Desde NetBeans
1. Abrir el proyecto en NetBeans.
2. Verificar que los archivos de la carpeta `recursos` estén presentes.
3. Ejecutar la clase principal del proyecto.

### Opción 2: Desde archivo `.jar`
1. Asegurarse de tener Java instalado.
2. Ejecutar el archivo `.jar` del proyecto.
3. Usar el menú principal para navegar entre los módulos.

---
