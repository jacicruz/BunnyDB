document.addEventListener('DOMContentLoaded', function () {
    // Obtén el parámetro de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const nombreBDParam = urlParams.get('base');

    var basesDeDatos = JSON.parse(localStorage.getItem('basesDeDatos')) || [];
    var basesDropdown = document.getElementById('basesDropdown');
    var tablasContainer = document.getElementById('tablasContainer');
    var tablasList = document.getElementById('tablasList');
    var modalNombreTabla = document.getElementById('modalNombreTabla');
    var modalTipoDato = document.getElementById('modalTipoDato');
    var modalLongitud = document.getElementById('modalLongitud');
    var modalAutoincrementable = document.getElementById('modalAutoincrementable');
    var modalNulo = document.getElementById('modalNulo');
    var modalLlavePrimaria = document.getElementById('modalLlavePrimaria');
    var guardarTablaBtn = document.getElementById('guardarTablaBtn');
    var mostrarFormBtn = document.getElementById('mostrarFormBtn');
    var agregarTablaForm = document.getElementById('agregarTablaForm');

    // Itera sobre las bases de datos y agrega cada una al menú desplegable
    basesDeDatos.forEach(function (bd) {
        var option = document.createElement('option');
        option.text = bd.nombre;
        basesDropdown.add(option);

        // Selecciona la base de datos si coincide con el parámetro de la URL
        if (bd.nombre === nombreBDParam) {
            option.selected = true;
            // Muestra las tablas para la base de datos seleccionada
            mostrarTablas(bd);
        }
    });

    // Agrega un evento al cambio en el menú desplegable
    basesDropdown.addEventListener('change', function () {
        // Busca la base de datos seleccionada
        var selectedBD = basesDeDatos.find(function (bd) {
            return bd.nombre === basesDropdown.value;
        });

        // Muestra las tablas para la base de datos seleccionada
        mostrarTablas(selectedBD);
    });

    // Agrega un evento al botón "Mostrar Formulario"
    mostrarFormBtn.addEventListener('click', function () {
        agregarTablaForm.classList.toggle('d-none'); // Alterna la visibilidad del formulario

        // Cambia el texto del botón según la visibilidad del formulario
        mostrarFormBtn.textContent = agregarTablaForm.classList.contains('d-none') ? 'Crear tabla' : 'Cancelar';
    });

    // Agrega un evento al botón "Guardar Tabla" en el formulario
    guardarTablaBtn.addEventListener('click', function () {
        // Lógica para guardar la nueva tabla
        agregarTabla();
        // Oculta el formulario después de guardar la tabla
        agregarTablaForm.classList.add('d-none');
        // Restaura el texto del botón a "Mostrar Formulario"
        mostrarFormBtn.textContent = 'Mostrar Formulario';
    });

    // Función para mostrar las tablas de una base de datos
    function mostrarTablas(baseDatos) {
        // Limpia el contenido actual de la lista de tablas
        tablasList.innerHTML = '';
    
        // Verifica si la base de datos tiene tablas
        if (baseDatos && baseDatos.tablas && baseDatos.tablas.length > 0) {
            // Crea la tabla y los encabezados
            var tabla = document.createElement('table');
            tabla.classList.add('table', 'table-bordered');
    
            var encabezados = document.createElement('thead');
            encabezados.innerHTML = `
                <tr>
                    <th>Nombre</th>
                    <th>Tipo de Dato</th>
                    <th>Longitud</th>
                    <th>Autoincrementable</th>
                    <th>Nulo</th>
                    <th>Llave Primaria</th>
                </tr>
            `;
    
            tabla.appendChild(encabezados);
    
            // Itera sobre las tablas y agrega las filas a la tabla
            var cuerpoTabla = document.createElement('tbody');
            baseDatos.tablas.forEach(function (tabla) {
                var fila = document.createElement('tr');
                fila.innerHTML = `
                    <td>${tabla.nombre}</td>
                    <td>${tabla.tipoDato}</td>
                    <td>${tabla.longitud}</td>
                    <td>${tabla.autoincrementable}</td>
                    <td>${tabla.nulo}</td>
                    <td>${tabla.llavePrimaria}</td>
                `;
                cuerpoTabla.appendChild(fila);
            });
    
            tabla.appendChild(cuerpoTabla);
            tablasList.appendChild(tabla);
        } else {
            // Si no hay tablas, muestra un mensaje de advertencia
            var mensaje = document.createElement('div');
            mensaje.textContent = 'No se encontraron tablas para esta Base de datos';
            mensaje.classList.add('alert', 'alert-warning');
            tablasList.appendChild(mensaje);
        }
    }
    

    // Función para agregar una nueva tabla
    function agregarTabla() {
        // Lógica para agregar una nueva tabla
        // Puedes personalizar el formulario modal y la lógica según tus necesidades

        // Ejemplo de obtener valores del formulario
        var nombreTabla = modalNombreTabla.value;
        var tipoDato = modalTipoDato.value;
        var longitud = modalLongitud.value;
        var autoincrementable = modalAutoincrementable.checked;
        var nulo = modalNulo.checked;
        var llavePrimaria = modalLlavePrimaria.checked;

        // Busca la base de datos seleccionada
        var selectedBD = basesDeDatos.find(function (bd) {
            return bd.nombre === basesDropdown.value;
        });

        // Agrega la nueva tabla a la base de datos
        if (!selectedBD.tablas) {
            selectedBD.tablas = [];
        }

        // Puedes personalizar los detalles de la tabla según tus necesidades
        var nuevaTabla = {
            nombre: nombreTabla,
            tipoDato: tipoDato,
            longitud: longitud,
            autoincrementable: autoincrementable,
            nulo: nulo,
            llavePrimaria: llavePrimaria
        };

        selectedBD.tablas.push(nuevaTabla);

        // Muestra las tablas actualizadas
        mostrarTablas(selectedBD);

        // Guarda la base de datos actualizada en el localStorage
        localStorage.setItem('basesDeDatos', JSON.stringify(basesDeDatos));
    }
});
