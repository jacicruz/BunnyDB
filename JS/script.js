var basesDeDatos = JSON.parse(localStorage.getItem('basesDeDatos')) || [];

$(document).ready(function () {
  // Agrega un evento de clic al botón "Crear"
  $('#crearBtn').click(function () {
    // Llama a la función crearBD
    crearBD();
  });

  // Agrega un evento de clic a las filas de la tabla
  $('#tablaBDs').on('click', 'tr', function (event) {
    // Verifica si el clic fue en el botón de eliminar o ver tablas
    if ($(event.target).hasClass('eliminarBtn')) {
      // Obtiene el texto de la primera celda (nombre de la base de datos)
      var nombreBD = $(this).find('td:first').text();

      // Muestra un toast con el nombre de la base de datos
      mostrarToast('Eliminando base de datos: ' + nombreBD);

      // Elimina la base de datos de la lista
      eliminarBD(nombreBD);

      // Actualiza la tabla en la interfaz
      actualizarTablaBDs();
    } else if ($(event.target).hasClass('verTablasBtn')) {
      // Muestra un toast indicando que se verán las tablas (sin necesidad de obtener el nombre)
      mostrarToast('Viendo tablas de la base de datos');

      // Aquí puedes agregar la lógica para mostrar las tablas de la base de datos
      // Puedes redirigir a una nueva página o realizar alguna otra acción según tus necesidades
      // Ejemplo: window.location.href = 'ver-tablas.html';
    }
  });

  // Agrega un evento de submit al formulario
  $('#myForm').submit(function (event) {
    // Evita que el formulario se envíe de forma predeterminada
    event.preventDefault();

    // Llama a la función crearBD
    crearBD();
  });

  // Lógica para cargar y actualizar la tabla de bases de datos
  function cargarTablaBDs() {
    // Limpiar la tabla actual
    $('#tablaBDs').empty();

    // Iterar sobre la lista de bases de datos y agregar cada una como una fila de tabla
    basesDeDatos.forEach(function (bd) {
      var fila = $('<tr><td>' + bd.nombre + '</td><td>' + bd.cotejamiento + '</td><td><button class="btn btn-danger eliminarBtn">Eliminar</button></td><td><button class="btn btn-primary verTablasBtn">Ver Tablas</button></td></tr>');
      $('#tablaBDs').append(fila);
    });

  }

  // Función para eliminar una base de datos
  function eliminarBD(nombreBD) {
    // Encuentra y elimina la base de datos de la lista
    basesDeDatos = basesDeDatos.filter(function (bd) {
      return bd.nombre !== nombreBD;
    });

    // Vuelve a cargar la tabla con la lista actualizada
    cargarTablaBDs();
  }

  // Actualiza la tabla con las bases de datos almacenadas
  function actualizarTablaBDs() {
    // Lógica para cargar y actualizar la tabla de bases de datos
    cargarTablaBDs();

    // Guarda la lista actualizada en el localStorage
    localStorage.setItem('basesDeDatos', JSON.stringify(basesDeDatos));
  }

  // Función para mostrar un toast
  function mostrarToast(nombreBD) {
    // Crea un nuevo objeto de toast usando Bootstrap
    var toast = $('<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3000" style="position: absolute; top: 0; right: 0;">\
                                  <div class="toast-header" style="background-color: #00f919ab; color: #000000;">\
                                  <strong class="mr-auto">Base de Datos Seleccionada</strong>\
                              </div>\
                              <div class="toast-body">' + nombreBD + '</div>\
                          </div>');

    // Agrega el toast al cuerpo del documento
    $('body').append(toast);

    // Muestra el toast
    toast.toast('show');

    // Cierra automáticamente el toast después de 3 segundos
    setTimeout(function () {
      toast.toast('hide');
    }, 3000);
  }

  // Lógica para cargar la tabla al cargar la página
  cargarTablaBDs();

  // Función para verificar si el nombre de la base de datos ya existe
  function esEntradaDuplicada(nombreBD) {
    // Verificar si el nombre de la base de datos ya existe en la lista
    return basesDeDatos.some(function (bd) {
      return bd.nombre === nombreBD;
    });
  }

  // Agrega un evento de submit al formulario
  $('#myForm').submit(function (event) {
    // Evita que el formulario se envíe de forma predeterminada
    event.preventDefault();

    // Llama a la función crearBD
    crearBD();
  });

  // Actualiza la tabla con las bases de datos almacenadas
  function crearBD() {
    // Obtener los valores del formulario
    var nombreBD = $('#nombre').val();
    var tipoCotejamiento = $('#tipoCotejamiento').val();

    // Validar que se haya ingresado un nombre de base de datos
    if (nombreBD.trim() === '') {
      alert('Por favor, ingrese un nombre de base de datos.');
      return;
    }

    // Verificar si el nombre de la base de datos ya existe
    if (esEntradaDuplicada(nombreBD)) {
      // Mostrar un toast indicando que la entrada es duplicada
      mostrarToast('Entrada duplicada. Este nombre ya existe en la lista.');
      return;
    }

    // Crear un objeto de base de datos
    var nuevaBD = {
      nombre: nombreBD,
      cotejamiento: tipoCotejamiento
    };

    // Agregar la nueva base de datos a la lista
    basesDeDatos.push(nuevaBD);

    // Actualizar la tabla en la interfaz
    cargarTablaBDs();

    // Guardar las bases de datos en el localStorage
    localStorage.setItem('basesDeDatos', JSON.stringify(basesDeDatos));

    // Mostrar el toast
    mostrarToast('La base de datos se creó con éxito.');
  }
});
