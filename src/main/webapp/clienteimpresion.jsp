<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles.css">
        <title>Impresiones</title>
    </head>
    <body>
        <header>
            <nav class="navbar">
                <div class="logo">
                    <img src="logo.png" alt="ImprimeYa Logo">
                    <span>ImprimeYa</span>
                </div>
                <ul class="nav-links">
                    <li><a href="index.html">Inicio</a></li>
                </ul>
                <a href="index.html" class="Ingresar">Ingresar</a>
            </nav>
        </header>

        <!-- Sección de opciones de impresión -->
        <div class="options-section">
            <label for="copias">Copias</label>
            <input type="number" id="copias" name="copias" value="1" min="1" onchange="calcularPrecioTotal()">

            <label for="tipo-impresion">Tipo de Impresión</label>
            <select id="tipo-impresion" name="tipo-impresion" onchange="calcularPrecioTotal()">
                <option value="normal">Normal</option>
                <option value="calidad">Alta Calidad</option>
            </select>

            <label for="material">Material</label>
            <select id="material" name="material" onchange="calcularPrecioTotal()">
                <option value="Papel">Papel</option>
                <option value="Papel Fotografico">Papel Fotográfico</option>
                <option value="Afiche">Afiche</option>
            </select>

            <div class="checkbox-group">
                <label><input type="checkbox" id="double-sided" name="double-sided" onchange="calcularPrecioTotal()"> Ambos Lados</label>
                <label><input type="checkbox" id="color" name="color" onchange="toggleCheckbox('color')"> Color</label>
                <label><input type="checkbox" id="bn" name="bn" onchange="toggleCheckbox('bn')"> Blanco y Negro</label>
            </div>

            <div class="price-group">
                <div>
                    <label for="unit-price">Valor Unitario</label>
                    <span id="unit-price">$700</span>
                </div>
                <div>
                    <label for="total-price">Valor Total</label>
                    <span id="total-price">$700</span>
                </div>
            </div>

            <button class="pay-button" onclick="imprimir()">Imprimir</button>
        </div>

        <script>
            function toggleCheckbox(selected) {
                var colorCheckbox = document.getElementById("color");
                var bnCheckbox = document.getElementById("bn");

                // Si se selecciona "Color", deselecciona "Blanco y Negro" y viceversa
                if (selected === 'color') {
                    bnCheckbox.checked = false;
                } else if (selected === 'bn') {
                    colorCheckbox.checked = false;
                }

                calcularPrecioTotal();
            }

            function calcularPrecioTotal() {
                let copias = parseInt(document.getElementById("copias").value);
                let colorChecked = document.getElementById("color").checked;
                let bnChecked = document.getElementById("bn").checked;
                let doubleSidedChecked = document.getElementById("double-sided").checked;

                let unitPrice = 500;  // Precio base para blanco y negro
                let doubleSidedPrice = 0;  // Incremento de precio para ambos lados

                // Ajustar el precio según las opciones seleccionadas
                if (colorChecked) {
                    unitPrice = 600;  // Precio base para color
                }

                // Si ambos lados está seleccionado, sumar 100 al precio unitario
                if (doubleSidedChecked) {
                    doubleSidedPrice = 100;
                }

                // Calcular el precio unitario final sumando el valor de ambos lados
                let finalUnitPrice = unitPrice + doubleSidedPrice;

                // Calcular el precio total multiplicando el precio unitario por la cantidad de copias
                let totalPrice = finalUnitPrice * copias;

                // Actualizar el valor en la interfaz
                document.getElementById("unit-price").textContent = "$" + finalUnitPrice;
                document.getElementById("total-price").textContent = "$" + totalPrice;
            }

            function imprimir() {
                // Mostrar mensaje de impresión en proceso
                alert("Impresión en proceso...");

                // Redirigir a cliente.jsp después de 2 segundos (2000 ms)
                setTimeout(function () {
                    window.location.href = "cliente.jsp";
                }, 2000);
            }

            // Calcular el precio inicial al cargar la página
            window.onload = function () {
                calcularPrecioTotal();
            };
        </script>
    </body>
</html>
