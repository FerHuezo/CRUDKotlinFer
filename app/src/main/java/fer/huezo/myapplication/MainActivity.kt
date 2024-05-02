package fer.huezo.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val Nombre = findViewById<EditText>(R.id.txtNombre)
        val Precio = findViewById<EditText>(R.id.txtPrecio)
        val Cantidad = findViewById<EditText>(R.id.txtCantidad)
        val btn = findViewById<Button>(R.id.btnIngresar)

        btn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){

                val claseConexion = ClaseConexion().CadenaConexion()

                val addProd = claseConexion?.prepareStatement("insert into tbProductos values(?,?,?)")!!
                addProd.setString(1, Nombre.text.toString())
                addProd.setInt(2, Precio.text.toString().toInt())
                addProd.setInt(3, Cantidad.text.toString().toInt())
                addProd.executeUpdate()

                Nombre.setText("")
                Precio.setText("")
                Cantidad.setText("")

            }
        }
    }
}