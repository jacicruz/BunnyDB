// Script para cargar las bases de datos en el menú desplegable
document.addEventListener('DOMContentLoaded', function () {
    var basesDeDatos = JSON.parse(localStorage.getItem('basesDeDatos')) || [];
    var basesDropdown = document.getElementById('basesDropdown');

    basesDeDatos.forEach(function (bd) {
        var option = document.createElement('option');
        option.text = bd.nombre;
        basesDropdown.add(option);
    });
});
