<h1>Este sistema está formado por microservicios que permiten gestionar un sistema de reservas y comentarios.</h1>
<p>El sistema está formado por los siguientes microservicios, los cuales se podrán comunicar entre ellos:</p>
<ul>
  <li>Servicio de Reservas de Hoteles (API Rest + MySQL)</li>
  <li>Servicio de Usuarios (API Rest + MySQL)</li>
  <li>Servicio de Comentarios y Valoraciones (API GraphQL + MongoDB)</li>
  <li>Servidor Eureka</li>
  <li>API Gateway</li>
</ul>
<p>Los ejemplos de prueba de microservicios (capturas) se harán usando Postman. Cuando deban recibir datos como objeto, harán uso de DTOs.</p>
<p>Muchas de las funciones de los microservicios requieren incluir los datos nombre y contraseña de usuario para validarse. Es importante que no haya dos usuarios con el mismo nombre.</p>

<h2>Detalles de cada microservicio</h2>
<h3>Servidor de Eureka</h3>
<p>El servidor Eureka atenderá las peticiones de conexión en el puerto 8700.</p>
<p>Su tarea será permitir el registro de los servicios que forman el sistema.</p>

<h3>API Gateway</h3>
<p>Estará ejecutándose en el puerto 8080. Será el punto de entrada y de utilización de las funcionalidades del sistema.</p>
<p>Todas las peticiones de los servicios deberán poder ser accesibles y funcionales a través de este sistema.</p>
<p>Deberá permitir utilizar las API Rest de los servicios que lo implementen y GraphIQL del servicio que lo implemente.</p>

<h3>Microservicio de Usuarios</h3>
<p>Implementa una API Rest para la comunicación entre el exterior y el servicio.</p>
<p>La API tendrá como ruta raíz /usuarios y, a partir de ella, se irán construyendo el resto de rutas.</p>
<p>Utilizará una base de datos MySQL llamada usuariosProyecto. Tiene configurado Hibernate en modo validate para no alterar la estructura de la base de datos. El script de generación está incluido dentro de la carpeta del proyecto.</p>
<h4>Ejemplos de algunas de las funcionalidades</h4>
<ul>
  <li>
    <p>Post - Crear usuario</p>
    <img src="https://github.com/JavierJAG/MicroserviciosHoteles_Rest_GraphQL/assets/74993072/9aaf0bee-adcb-4509-b8f0-d0aba173f0fc">
  </li>
  <li>
    <p>Put - Actualizar usuario</p>
    <img src="https://github.com/JavierJAG/MicroserviciosHoteles_Rest_GraphQL/assets/74993072/70eac531-33aa-4c24-98ff-adf944a2bed0">
  </li>
  <li>
    <p>Delete - Eliminar usuario</p>
    <img src="https://github.com/JavierJAG/MicroserviciosHoteles_Rest_GraphQL/assets/74993072/e019c2ad-958b-42b2-9547-925f02e03a83">
  </li>
</ul>

<h3>Microservicio de Reservas</h3>
<p>Implementa una API Rest para la comunicación entre el exterior y el servicio.</p>
<p>La ruta raíz de la API será /reservas.</p>
<p>Utilizará una base de datos MySQL llamada reservasProyecto. Tiene configurado Hibernate en modo validate para no alterar la estructura de la base de datos. El script de generación está incluido dentro de la carpeta del proyecto.</p>
<h4>Ejemplos de algunas de las funcionalidades</h4>
<ul>
  <li>
    <p>Get - Listar reservas de usuario</p>
    <img src="https://github.com/JavierJAG/MicroserviciosHoteles_Rest_GraphQL/assets/74993072/5437176d-b475-4d98-93f2-b3ccfdb0d0d2">
  </li>
  <li>
    <p>Post - Obtener nombre de hotel a partir de id(A través de URL)</p>
    <img src="https://github.com/JavierJAG/MicroserviciosHoteles_Rest_GraphQL/assets/74993072/3062d7c6-5d5e-474f-99a8-38aade611549">
  </li>
</ul>


<h3>Microservicio de Comentarios</h3>
<p>Implementa una API GraphQL para la comunicación entre el exterior y el servicio.</p>
<p>GraphIQL está habilitado para la realización de peticiones.</p>
<p>GraphIQL utiliza el endpoint /comentarios para resolver las peticiones.</p>
<p>Utiliza una base de datos MongoDB llamada comentariosProyecto. La base de datos MongoDB tendrá una colección llamada comentarios. El script de generación está incluido dentro de la carpeta del proyecto.</p>
<h4>Ejemplos de algunas de las funcionalidades</h4>
<ul>
  <li>
    <p>Post - Mutation - Crear comentario</p>
    <img src="https://github.com/JavierJAG/MicroserviciosHoteles_Rest_GraphQL/assets/74993072/37436eba-756b-4701-aaad-2bd7d8e3d2c4">
  </li>
  <li>
    <p>Post - Query - Mostrar comentarios de usuario</p>
    <img src="https://github.com/JavierJAG/MicroserviciosHoteles_Rest_GraphQL/assets/74993072/d34878c1-4989-4904-9e45-1a81cb5afc477">
  </li>
</ul>



