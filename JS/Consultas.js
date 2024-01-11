document.addEventListener('DOMContentLoaded', function () {
    // Obtén el parámetro de la URL (nombre de la base de datos)
    const urlParams = new URLSearchParams(window.location.search);
    const nombreBDParam = urlParams.get('base');

    // Obtén las bases de datos del almacenamiento local
    var basesDeDatos = JSON.parse(localStorage.getItem('basesDeDatos')) || [];

    // Encuentra la base de datos seleccionada
    var baseDatosSeleccionada = basesDeDatos.find(function (bd) {
        return bd.nombre === nombreBDParam;
    });

    if (baseDatosSeleccionada) {
        // Llena el nombre de la base de datos
        var nombreBD = document.getElementById('nombreBD');
        nombreBD.textContent = baseDatosSeleccionada.nombre;

        // Llena el menú desplegable con las tablas de la base de datos
        var tablasDropdown = document.getElementById('tablasDropdown');
        llenarTablasDropdown(tablasDropdown, baseDatosSeleccionada.tablas);

        // Añade un evento al cambio en el menú desplegable
        tablasDropdown.addEventListener('change', function () {
            // Llena el formulario de actualización con los campos de la tabla seleccionada
            llenarFormulario(baseDatosSeleccionada.tablas, tablasDropdown.value);
            // Llena el formulario de inserción con los campos de la tabla seleccionada
            llenarFormularioInsercion(baseDatosSeleccionada.tablas, tablasDropdown.value);
        });

        // Añade un evento al formulario para la actualización de datos
        var actualizarDatosForm = document.getElementById('actualizarDatosForm');
        actualizarDatosForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Evita que el formulario se envíe

            // Lógica para actualizar los datos
            // Obtén los valores de los campos y realiza la actualización
            var datosActualizados = obtenerDatosFormulario(baseDatosSeleccionada.tablas, tablasDropdown.value);
            console.log('Datos actualizados:', datosActualizados);

            // Puedes agregar la lógica adicional para realizar la actualización de datos
        });

        // Añade un evento al formulario para la inserción de datos
        var insertarDatosForm = document.getElementById('insertarDatosForm');
        insertarDatosForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Evita que el formulario se envíe

            // Lógica para la inserción de datos
            // Obtén los valores de los campos y realiza la inserción
            var datosInsercion = obtenerDatosFormularioInsercion(baseDatosSeleccionada.tablas, tablasDropdown.value);
            console.log('Datos de inserción:', datosInsercion);

            // Puedes agregar la lógica adicional para realizar la inserción de datos
        });

        // Llena el formulario de actualización con los campos de la primera tabla
        llenarFormulario(baseDatosSeleccionada.tablas, tablasDropdown.value);
        // Llena el formulario de inserción con los campos de la primera tabla
        llenarFormularioInsercion(baseDatosSeleccionada.tablas, tablasDropdown.value);
    }
});

// Función para llenar el menú desplegable con las tablas de la base de datos
function llenarTablasDropdown(selectElement, tablas) {
    // Limpia las opciones actuales
    selectElement.innerHTML = '';

    // Crea y agrega las opciones al menú desplegable
    tablas.forEach(function (tabla) {
        var option = document.createElement('option');
        option.text = tabla.nombre;
        selectElement.add(option);
    });

    // Disparar el evento 'change' manualmente para llenar el formulario con la primera tabla
    selectElement.dispatchEvent(new Event('change'));
}

// Función para llenar el formulario de actualización con los campos de la tabla seleccionada
// Función para llenar el formulario de actualización con los campos de la tabla seleccionada
function llenarFormulario(tablas, tablaSeleccionada) {
    // Encuentra la tabla seleccionada
    var tabla = tablas.find(function (t) {
        return t.nombre === tablaSeleccionada;
    });

    // Llena el formulario de actualización con los campos respectivos
    var columnasContainer = document.getElementById('columnasContainer');
    columnasContainer.innerHTML = ''; // Limpia el contenido actual

    if (tabla && tabla.columnas) {
        tabla.columnas.forEach(function (columna) {
            var divColumna = document.createElement('div');
            divColumna.className = 'col-md-6 mb-3';

            var label = document.createElement('label');
            label.className = 'form-label';
            label.textContent = columna.nombre;

            var input = document.createElement('input');
            input.type = 'text';
            input.className = 'form-control';
            input.placeholder = `Nuevo valor para ${columna.nombre}`;
            input.name = columna.nombre; // Agrega un nombre al input

            divColumna.appendChild(label);
            divColumna.appendChild(input);

            columnasContainer.appendChild(divColumna);
        });
    }
}

// Función para obtener los datos del formulario de actualización
function obtenerDatosFormulario(tablas, tablaSeleccionada) {
    var datosActualizados = {};

    // Encuentra la tabla seleccionada
    var tabla = tablas.find(function (t) {
        return t.nombre === tablaSeleccionada;
    });

    // Obtiene los valores de los campos del formulario
    if (tabla && tabla.columnas) {
        tabla.columnas.forEach(function (columna) {
            var input = document.querySelector(`input[name="${columna.nombre}"]`); // Utiliza el nombre del input
            if (input) {
                datosActualizados[columna.nombre] = input.value;
            }
        });
    }

    return datosActualizados;
}

// Función para llenar el formulario de inserción con los campos de la tabla seleccionada// Función para llenar el formulario de inserción con los campos de la tabla seleccionada
function llenarFormularioInsercion(tablas, tablaSeleccionada) {
    // Encuentra la tabla seleccionada
    var tabla = tablas.find(function (t) {
        return t.nombre === tablaSeleccionada;
    });

    // Llena el formulario de inserción con los campos respectivos
    var columnasContainerInsertar = document.getElementById('columnasContainerInsertar');
    columnasContainerInsertar.innerHTML = ''; // Limpia el contenido actual

    if (tabla && tabla.columnas) {
        tabla.columnas.forEach(function (columna) {
            var divColumna = document.createElement('div');
            divColumna.className = 'col-md-6 mb-3';

            var label = document.createElement('label');
            label.className = 'form-label';
            label.textContent = columna.nombre;

            var input = document.createElement('input');
            input.type = 'text';
            input.className = 'form-control';
            input.placeholder = `Nuevo valor para ${columna.nombre}`;
            input.name = columna.nombre; // Agrega un nombre al input

            divColumna.appendChild(label);
            divColumna.appendChild(input);

            columnasContainerInsertar.appendChild(divColumna);
        });
    }
}

// Función para obtener los datos del formulario de inserción
function obtenerDatosFormularioInsercion(tablas, tablaSeleccionada) {
    var datosInsercion = {};

    // Encuentra la tabla seleccionada
    var tabla = tablas.find(function (t) {
        return t.nombre === tablaSeleccionada;
    });

    // Obtiene los valores de los campos del formulario de inserción
    if (tabla && tabla.columnas) {
        tabla.columnas.forEach(function (columna) {
            var input = document.querySelector(`input[name="${columna.nombre}"]`); // Utiliza el nombre del input
            if (input) {
                datosInsercion[columna.nombre] = input.value;
            }
        });
    }

    return datosInsercion;
}
