package modelo

import java.util.UUID

data class dataClassProductos
    (
    val uuid: String,
    var nombreProducto: String,
    var precio: Int,
    var cantidad: Int
)
