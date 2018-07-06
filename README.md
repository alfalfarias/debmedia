## Deb media - test

### Requerimientos
- Java Development Kit [JDK-1.8.0_171](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Back-end [Play Framework v2.2.4](https://downloads.typesafe.com/play/2.2.4/play-2.2.4.zip)
- Front-end [AngularJS v1.5.0](https://code.angularjs.org/1.5.0)
- ORM [Ebean](https://www.playframework.com/documentation/2.2.4/JavaEbean)
- IDE [Netbeans IDE 8.2 FULL](https://netbeans.org/downloads/)

### Enunciado del ejercicio:
Una empresa maneja una cartera de productos que están compuestos por uno o más insumos
(para simplificar el enunciado un producto solo puede tener una unidad de cada insumo), un
nombre y una descripción de hasta 500 caracteres. Los insumos al mismo tiempo están
compuestos por un nombre y un stock (el mismo no puede ser negativo). La empresa vende
productos a sus clientes.

Se requiere implementar un sistema utilizando las tecnologías anteriormente mencionadas que
cumpla con la siguiente funcionalidad:
- Una vista para listar, crear, modificar y borrar insumos. En esta vista se podrá editar tanto
el nombre del insumo como el stock.
- Una vista para listar, crear, modificar y borrar productos. Al momento de crear el producto
se deberán poder seleccionar qué insumos lo conforman, y al momento de editarlo esta
lista podrá también ser editada.
- Una vista para listar y crear ventas donde cada venta contendrá un campo comprador
(para simplificar este punto este campo será solo de texto), un producto (no se podrá
seleccionar más de un producto) y una cantidad. Al momento de realizar la venta el stock
de los ítems deberá ser descontado. Cada venta deberá quedar registrada en la base de
datos y no puede ser editada.

### Consideraciones
- Utilizar el **patrón de diseño MVC** [**Documentación**](https://www.playframework.com/documentation/1.0/main#mvc)
- Utilizar **API REST** (datos en formato JSON) [**Documentación**](https://www.playframework.com/documentation/2.2.x/JavaJsonRequests)
- Utilizar las **anotaciones de Ebean** (*@Entity,  @Id*) [**Documentación**](https://www.playframework.com/documentation/2.2.x/JavaEbean#Usingthe-play.db.ebean.Model-superclass)

### Puntos extras
### 1. Considerar la posibilidad de que varios usuarios estén realizando al mismo tiempo acciones sobre las mismas tablas.
> **Caso de ejemplo:** Producto A necesita el ​ Insumo 1 e Insumo 2, en stock quedan uno de cada insumo. ¿Como salvaguardar el caso en que dos vendedores intentan vender al mismo tiempo un ​ Producto A?

 Para evitar este tipo de situaciones existen las transacciones. Estas permiten la ejecución de bloques completos de operaciones (visualizados como una unidad entera), verificando que se realicen todas las acciones a realizar en la base de datos. Como medida de seguridad, las tablas con las que interactúa se bloquean hasta terminar la ejecución de la transacción. De modo que si la tabla "supplies" se ve implicada en una venta, esta será bloqueada hasta terminar la operación y luego liberada cuando la misma termine.

 En caso de que ocurra algún error durante este proceso, el motor de base de datos se devuelve al estado previo, anterior a la ejecución de la transacción. De este modo, se conserva la integridad de los registros y la consistencia de los mismos. Es decir, las transacciones hacen una cola para el procesos de venta y al terminar una, desbloquean las tablas implicadas para que la siguente transacción pueda ejecutarse.

 No obstante, el database locking no proteje al desarrollador del código que escribe. Por ejemplo: si se tiene una transacción que bloquea la tabla A, y una segunda transaccion bloquea la tabla B, la transacción inicial pide un bloqueo a la tabla B, la cual esta bloqueada por la segunda transacción y esta a su vez requiere un bloqueo a la tabla A, la cual esta bloqueada por la primera transaccion, es allí donde se genera un deadlock. Para estos casos se necesita de un [mutex](https://en.wikipedia.org/wiki/Lock_(computer_science)) para establecer límites de acceso cuando existe mucha concurrencia y muchos ORM no ofrecen soporte para ello, por lo que estaría limitado el desarrollador a la lógica que implemente en la sincronización de peticiones.

 Es necesario indicar que este caso es muy particular y específico. Sin embargo, para el caso particular de la aplicación, al efectuar la venta no hay problema, ya que se realizan las transacciones en secuencia, dando lugar a que se ejecute una transacción tras otra, formando una cola correspondiente a la operación de venta de los insumos.

### 2. ¿Cómo implementarías la cancelación de una venta.

La cancelación de una venta contempla dos escenarios posibles:
- **Retornar los productos intactos a la empresa**. En este caso, se tomaría el registro de la venta y se agregarían el producto devuelto a la lista de productos (en caso de que no exista o se haya eliminado). Seguidamente, se suman los productos que lo componen a la cantidad actual de insumos en stock (si no existe el stock, se crea). Finalmente, se eliminaría el registro de la venta (y a causa de la integridad referencial, se borra por defecto la data restante de la venta en las tablas relacionadas con "sales").
- **Los insumos del producto no pueden regresar al stock del almacén**. Si se necesita autar la información sobre ventar realizadas con éxito y canceladas, se agregaría un nuevo campo a la tabla "sales" indicando si la venta fue cancelada o no. De otro modo, con eliminar el registro sería suficiente, puesto que no sería necesario reponerlo en inventario.

### 3. ¿Cómo indicarías en rojo los items que estan sin stock?
  + **Back-end**: Con una consulta a la tabla "supplies" es suficiente, ya que el campo "quantity" indica la cantidad en existencia del producto en cuestión, para este caso sería cero (0), de esta forma se puede saber que no hay items en el stock.

  + **Front-end**: Al mismo tiempo en la vista del usuario se puede hacer uso de la directiva ng-class, que al cumplirse la condición de quantity igual a 0, coloque una clase 'danger' predefinida por bootstrap, para indicar con color rojo que la existencia del producto está agotada.

### 4. ¿Qué tests realizarías sobre está aplicación? Mencionarlos.
- **Automatización de pruebas del API usando Postman**. Mediante el uso del software Postman se realizan request automáticas al api, probando cada endpoint y buscando fallas en su comportamiento al verificar el estatus de la respuesta http.
- **Pruebas unitarias de Java con JUnit**. Se pueden efectuar pruebas mas especializadas, ya que tiene una interacción directa con los metodos y clases codificados en la aplicación.

- **Pruebas unitarias de Angularjs con Karma**. Mediante el uso del framework Karma se evalua el comportamiento de los distintos servicios, controladores y filtros que conforman la aplicación. Personalizando el test unitario a realizar en cada uno de estos elementos.

### 5. ¿Cómo agregarías autenticación y autorización básicas a esta aplicación?
+ **Autenticación**. Play Framework en su versión 2.2.4 ofrece una lista de paquetes para implementar la autenticación, dos de ellos es [joscha/play-authenticate](https://github.com/joscha/play-authenticate/blob/master/README.md) y se puede encontrar en la sección de [modulos](https://www.playframework.com/documentation/2.2.4/Modules) de la página oficial.
  En su versión más reciente, Play Framework ofrece el uso de [JWT](https://www.playframework.com/documentation/2.6.x/SettingsSession) que es una alternativa cada vez más utilizada, porque contiene muy pocas limitaciones, pero para efectos de la aplicación actual sería necesario migrar a la versión 2.6 ya que para la versión 2.2.4 no se posee una librería oficial que brinde soporte a JWT. El funcionamiento de esta tecnología, puede explicarse en un ejemplo práctico de la siguiente manera:
  > El usuario se autentica en nuestra aplicación, bien con un par usuario/contraseña, o a través de un proveedor como puede ser Twitter, Facebook o Google por ejemplo. A partir de entonces, cada petición HTTP que haga el usuario va acompañada de un Token en la cabecera. Este Token no es más que una firma cifrada que permite a nuestro API identificar al usuario. Pero este Token no se almacena en el servidor, si no en el lado del cliente (por ejemplo en localStorage o sessionStorage) y el API es el que se encarga de descrifrar ese Token y redirigir el flujo de la aplicación en un sentido u otro.

  > Como los tokens son almacenados en el lado del cliente, no hay información de estado y la aplicación se vuelve totalmente escalable. Podemos usar el mismo API para diferentes apliaciones (Web, Mobile, Android, iOS, ...) solo debemos preocuparnos de enviar los datos en formato JSON y generar y descrifrar tokens en la autenticación y posteriores peticiones HTTP a través de un middleware.

  *[Carlos Azaustre - **¿Qué es la autenticación basada en Token?**](https://carlosazaustre.com/que-es-la-autenticacion-con-token/)*

  Es este sentido, JWT es una buena alternativa para la autenticación. Sin embargo, ¿cómo implementar ésta tecnología? Pues, hoy en día existen paquetes que permiten la manipulación de token JWT en Github para Java, como lo es el caso de [jwtk/jjwt](https://github.com/jwtk/jjwt), donde se haría uso del mismo para generar el token JWT, manipularlo y validarlo en cada uno de los controladores. Para ello se puede hacer uso de las [actions](https://www.playframework.com/documentation/2.5.x/JavaActionsComposition) que son básicamente anotaciones capáces de interceptar las peticiones HTTP y validar la información proporcionada.

+ **Autorización.** Son los permisos necesarios que necesita un usuario para acceder a un recurso (endpoint), la verificación de dichos permisos se lleva a cabo a través de los middlewares, los cuales son simplemente un filtro de peticiones. En la versión 2.2.4 de PLay framework no existe un concepto llamado middleware, pero es posible emular su comportamiento a través de las [actions](https://www.playframework.com/documentation/2.5.x/JavaActionsComposition) (interceptando las peticiones HTTP y validar la información proporcionada), de manera que por medio de un identificador (presente en la petición) es posible verificar si se tiene o no permisos (autorización) para acceder al recurso en cuestión.

### 6. ¿Qué técnica conoces para distribuir correctamente los assets de una aplicación cuando muchos usuarios la utilizan?

En el caso de los assets de terceros una buena práctica sería el uso de los CDN para el manejo de estos, debido a que de esta forma se evita hacer peticiones excesivas al servidor solo para la carga de los archivos básicos. Adicionalmente, para aligerar la carga del servidor, los assets propios de la aplicación pueden ser renderizados desde otros servidores dedicados al almacenamiento de estos.
