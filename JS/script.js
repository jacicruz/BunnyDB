// Verifica si hay datos almacenados en el localStorage y cárgalos
var basesDeDatos = JSON.parse(localStorage.getItem('basesDeDatos')) || [];

$(document).ready(function () {
  // Agrega un evento de submit al formulario
  $('#myForm').submit(function (event) {
    // Evita que el formulario se envíe de forma predeterminada
    event.preventDefault();

    // Llama a la función crearBD
    crearBD();
  });

  // Actualiza la tabla con las bases de datos almacenadas
  actualizarTablaBDs();
});

function crearBD() {
  // Obtener los valores del formulario
  var nombreBD = $('#nombre').val();
  var tipoCotejamiento = $('#tipoCotejamiento').val();

  // Validar que se haya ingresado un nombre de base de datos
  if (nombreBD.trim() === '') {
    // Mostrar un toast de error indicando que se necesita un nombre
    mostrarToast('Error', 'Por favor, ingrese un nombre de base de datos.');
    return;
  }

  // Verificar si el nombre de la base de datos ya existe
  if (esEntradaDuplicada(nombreBD)) {
    // Mostrar un toast de error indicando que la entrada es duplicada
    mostrarToast('Error', 'Entrada duplicada. Este nombre ya existe en la lista.');
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
  actualizarTablaBDs();

  // Guardar las bases de datos en el localStorage
  localStorage.setItem('basesDeDatos', JSON.stringify(basesDeDatos));

  // Mostrar un toast de éxito
  mostrarToast('Éxito', 'La base de datos se creó con éxito.');
}

function mostrarToast(tipo, mensaje) {
  // Crea un nuevo objeto de toast usando Bootstrap
  var colorFondo = tipo === 'Éxito' ? '#0091ff' : '#dc3545'; // Verde para Éxito, Rojo para Error
  var toast = $('<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3000" style="position: absolute; top: 0; right: 0;">\
                      <div class="toast-header" style="background-color: ' + colorFondo + '; color: #ffffff;">\
                        <strong class="mr-auto">' + tipo + '</strong>\
                      </div>\
                      <div class="toast-body">' + mensaje + '</div>\
                    </div>');

  // Agrega el toast al cuerpo del documento
  $('body').append(toast);

  // Muestra el toast
  toast.toast('show');

  // Cierra automáticamente el toast después de 2 segundos
  setTimeout(function () {
    toast.toast('hide');
  }, 2000);
}

function esEntradaDuplicada(nombreBD) {
  // Verificar si el nombre de la base de datos ya existe en la lista
  return basesDeDatos.some(function (bd) {
    return bd.nombre === nombreBD;
  });
}

function actualizarTablaBDs() {
  // Limpiar la tabla actual
  $('#tablaBDs').empty();

  // Iterar sobre la lista de bases de datos y agregar cada una como una fila de tabla
  basesDeDatos.forEach(function (bd) {
    var fila = $('<tr><td>' + bd.nombre + '</td><td>' + bd.cotejamiento + '</td></tr>');
    $('#tablaBDs').append(fila);
  });
}