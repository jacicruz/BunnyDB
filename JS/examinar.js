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
    var nombreColumnaInput = document.getElementById('nombreColumnaInput');
    var agregarColumnaBtn = document.getElementById('agregarColumnaBtn');
    var quitarColumnaBtn = document.getElementById('quitarColumnaBtn');
    var columnasContainer = document.getElementById('columnasContainer');
    var columnasList = document.getElementById('columnasList');

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

        // Si se muestra el formulario, crea dinámicamente los botones
        if (!agregarTablaForm.classList.contains('d-none')) {
            // Crear botón "Agregar Columna" dinámicamente
            var agregarColumnaBtn = document.createElement('button');
            agregarColumnaBtn.textContent = 'Agregar Columna';
            agregarColumnaBtn.className = 'btn btn-success';
            agregarColumnaBtn.id = 'agregarColumnaBtn';

            // Agrega un evento al botón "Agregar Columna"
            agregarColumnaBtn.addEventListener('click', function () {
                // Lógica para agregar columna (puedes usar la misma lógica que tenías antes)
                // ...

                // Puedes ajustar la lógica según tus necesidades
            });

            // Crear botón "Quitar Columna" dinámicamente
            var quitarColumnaBtn = document.createElement('button');
            quitarColumnaBtn.textContent = 'Quitar Columna';
            quitarColumnaBtn.className = 'btn btn-danger';
            quitarColumnaBtn.id = 'quitarColumnaBtn';

            // Agrega un evento al botón "Quitar Columna"
            quitarColumnaBtn.addEventListener('click', function () {
                // Lógica para quitar columna (puedes usar la misma lógica que tenías antes)
                // ...

                // Puedes ajustar la lógica según tus necesidades
            });

            // Agrega los botones al contenedor de columnas
            columnasContainer.appendChild(agregarColumnaBtn);
            columnasContainer.appendChild(quitarColumnaBtn);
        } else {
            // Si se oculta el formulario, elimina los botones
            var agregarColumnaBtn = document.getElementById('agregarColumnaBtn');
            var quitarColumnaBtn = document.getElementById('quitarColumnaBtn');

            if (agregarColumnaBtn) {
                columnasContainer.removeChild(agregarColumnaBtn);
            }

            if (quitarColumnaBtn) {
                columnasContainer.removeChild(quitarColumnaBtn);
            }
        }
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

    // Maneja el evento del botón "Agregar Columna"
    agregarColumnaBtn.addEventListener('click', function () {
        // Crea un div para contener la información de la columna
        var columnaDiv = document.createElement('div');
        columnaDiv.classList.add('columna-item', 'mb-2');

        // Crea un elemento de texto para mostrar la información de la columna
        var columnaText = document.createTextNode(`${nombreColumnaInput.value} - Tipo: ${modalTipoDato.value} - Autoincrementable: ${modalAutoincrementable.checked} - Nulo: ${modalNulo.checked} - Llave Primaria: ${modalLlavePrimaria.checked}`);
        columnaDiv.appendChild(columnaText);

        // Agrega la columna al contenedor
        columnasList.appendChild(columnaDiv);

        // Limpia los campos del formulario
        nombreColumnaInput.value = '';
        modalAutoincrementable.checked = false;
        modalNulo.checked = false;
        modalLlavePrimaria.checked = false;
    });

    // Maneja el evento del botón "Quitar Columna"
    quitarColumnaBtn.addEventListener('click', function () {
        // Elimina la última columna del contenedor
        var ultimaColumna = columnasList.lastChild;
        if (ultimaColumna) {
            columnasList.removeChild(ultimaColumna);
        }
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
                    <td class="tabla-nombre" data-nombre="${tabla.nombre}">${tabla.nombre}</td>
                    <td>${tabla.tipoDato}</td>
                    <td>${tabla.longitud}</td>
                    <td>${tabla.autoincrementable}</td>
                    <td>${tabla.nulo}</td>
                    <td>${tabla.llavePrimaria}</td>
                `;

                // Agrega un evento de clic a la fila para redirigir a la página de consultas
                fila.addEventListener('click', function () {
                    // Obtén el nombre de la tabla desde el atributo data-nombre
                    var nombreTabla = fila.getAttribute('data-nombre');

                    // Redirige a la página de consultas con el parámetro en la URL
                    window.location.href = `consultas.html?base=${basesDropdown.value}&tabla=${nombreTabla}`;
                });

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
