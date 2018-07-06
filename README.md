<p align="center"><img src="https://debmedia.com/wp-content/themes/debmedia-child/dist/images/debmedia/logo.svg"></p>

## Deb media - test

### Requerimientos
- **Java Development Kit** [JDK-1.8.0_171](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.h
tml)
- **Back-end** [Play Framework v2.2.4](https://downloads.typesafe.com/play/2.2.4/play-2.2.4.zip)
- **Front-end** [AngularJS v1.5.0](https://code.angularjs.org/1.5.0)
- **ORM** [Ebean](https://www.playframework.com/documentation/2.2.4/JavaEbean)
- **IDE** [Netbeans IDE 8.2 FULL](https://netbeans.org/downloads/)

### Enunciado del ejercicio:
>Una empresa maneja una cartera de productos que están compuestos por uno o más insumos
(para simplificar el enunciado un producto solo puede tener una unidad de cada insumo), un
nombre y una descripción de hasta 500 caracteres. Los insumos al mismo tiempo están
compuestos por un nombre y un stock (el mismo no puede ser negativo). La empresa vende
productos a sus clientes.
{: style="text-align: justify"}

>Se requiere implementar un sistema utilizando las tecnologías anteriormente mencionadas que
cumpla con la siguiente funcionalidad:
> - Una vista para listar, crear, modificar y borrar insumos. En esta vista se podrá editar tanto
el nombre del insumo como el stock.
> - Una vista para listar, crear, modificar y borrar productos. Al momento de crear el producto
se deberán poder seleccionar qué insumos lo conforman, y al momento de editarlo esta
lista podrá también ser editada.
> - Una vista para listar y crear ventas donde cada venta contendrá un campo comprador
(para simplificar este punto este campo será solo de texto), un producto (no se podrá
seleccionar más de un producto) y una cantidad. Al momento de realizar la venta el stock
de los ítems deberá ser descontado. Cada venta deberá quedar registrada en la base de
datos y no puede ser editada.

### Consideraciones
- **Utilizar el patrón de diseño MVC** [Documentación](https://www.playframework.com/documentation/1.0/main#mvc)
- **Utilizar APIs RESTful (datos en formato JSON)** [Documentación](https://www.playframework.com/documentation/2.2.x/JavaJsonRequests) (https://downloads.typesafe.com/play/2.2.4/play-2.2.4.zip)
- **Utilizar las anotaciones de Ebean (@Entity y @Id)** [Documentación](https://www.playframework.com/documentation/2.2.x/JavaEbean#Usingthe-play.db.ebean.Model-superclass)

### Puntos extras
1) Considerar la posibilidad de que varios usuarios estén realizando al mismo tiempo
acciones sobre las mismas tablas. Caso de ejemplo: ​ Producto A necesita el ​ Insumo 1 e
Insumo 2, en stock quedan uno de cada insumo. ¿Como salvaguardar el caso en que dos
vendedores intentan vender al mismo tiempo un ​ Producto A?
- Para evitar este tipo de situaciones existen las transacciones. Estas permiten la ejecución de bloques completos de operaciones (visualizados como una unidad entera), verificando que se realicen todas las acciones a realizar en la base de datos y en caso de que ocurra algúnb error, no se ejecute ninguna. Dentro de este bloque, se agregan las validaciones respectivas a la cantidad de insumos disponibles en el stock (para el momento en que se hizo la consulta), dando lugar a que se ejecute la siguiente transacción en cola correspondiente a la operación sobre los insumos que solicite el usuario consecuente a la transsacción previa.

2) Cómo implementarías la cancelación de una venta.
- La cancelación de una venta contempla dos escenarios posibles:
    > - **Retornar los productos intactos a la empresa**. En este caso, se tomaría el registro de la venta y se agregarían el producto devuelto a la lista de productos (en caso de que no exista o se haya eliminado). Seguidamente, se suman los productos que lo componen a la cantidad actual de insumos en stock (si no existe el stock, se crea). Finalmente, se eliminaría el registro de la venta (y a causa de la integridad referencial, se borra por defecto la data restante de la venta en las tablas relacionadas con "sales").
    > - **Los insumos del producto no pueden regresar al stock del almacén**. Si se necesita autar la información sobre ventar realizadas con éxito y canceladas, se agregaría un nuevo campo a la tabla "sales" indicando si la venta fue cancelada o no. De otro modo, con eliminar el registro sería suficiente, puesto que no sería necesario reponerlo en inventario.

3) Cómo indicarías en rojo los items que estan sin stock.
  - Con una consulta a la tabla "supplies" es suficiente, ya que el campo "quantity" indica la cantidad en existencia del producto en cuestión, para este caso sería cero (0), de esta forma se puede saber que no hay items en el stock.

4) Qué tests realizarías sobre está aplicación? Mencionarlos.

5) Cómo agregarías autenticación y autorización básicas a esta aplicación?
    5.1) Autenticación. La autenticación es el proceso por el cual se identifica a un usuario en específico. En ese sentido, existen muchas herramientas en el desarrollo web, como lo es el caso del uso de tokens o cookies.
    Para el caso de la aplicación, al separar la arquitectura en capas, construyento un API REST, sería una alternativa el uso de cookies para consumir el servicio usando un navegador, pero si se quiere brindar un nivel más amplio de escalabilidad hay que tomar en cuenta que el cliente web puede ser una aplicación de un SmartPhone o cualquier otro dispositivo, estos no necesariamente pueden soportar cache y el uso del mismo sería una limitante.

    Por otra parte, es posible usar un token bearer. Al ser un API REST no guarda estado, esto quiere decir que "Un servidor procesa las solicitudes basándose sólo en la información proporcionada con cada solicitud, nunca en la información de solicitudes anteriores". De esta manera, solo se necesita agregar un header a cualquier petición al API (proporcionado por la misma) con la que el usuario pueda identificarse (todo esto en segundo plano para el usuario final).

    Claramente, la alternativa más viable sería el uso de un token bearer, porque es más sencillo y menos limitante. Además, al token ser una sección de información codificada, es posible almacenar cualquier data que se desee, siendo accesible solo por el servidor. Una ejemplo de esto es el Token JWT.


    5.2) Autorización. Son los permisos necesarios que necesita un usuario para acceder a un recurso (endpoint).

6) ¿Qué técnica conoces para distribuir correctamente los assets de una aplicación cuando
muchos usuarios la utilizan?
