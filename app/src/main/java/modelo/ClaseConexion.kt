package modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun CadenaConexion(): Connection? {
        try {
            val url = "jdbc:oracle:thin:@10.10.1.110:1521:xe"
            val user = "system"
            val contrasena = "desarrollo"
            val connection = DriverManager.getConnection(url, user, contrasena)
            return connection
        }
        catch (e:Exception) {
            println("Este es tu error: $e")
            return null
        }
    }
}