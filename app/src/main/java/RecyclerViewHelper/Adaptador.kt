package RecyclerViewHelper

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import fer.huezo.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassProductos

class Adaptador(private var Datos: List<dataClassProductos>) : RecyclerView.Adapter<ViewHolder>()
{
    fun actualizarLista(newList: List<dataClassProductos>)
    {
        Datos = newList
        notifyDataSetChanged()
    }
    fun eliminarRegistro(nombreProducto: String, posicion: Int){

        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().CadenaConexion()
            val delProducto = objConexion?.prepareStatement("delete tbProductos where nombreProducto = ?")!!
            delProducto.setString(1, nombreProducto)
            delProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        notifyItemChanged(1)
        notifyDataSetChanged()
    }

    fun actualizarRegistro(nombreProducto: String, uuid: String){


        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().CadenaConexion()
            val actProducto = objConexion?.prepareStatement("update tbProductos set nombreProducto = ? where uuid = ? ")!!
            actProducto.setString(1, nombreProducto)
            actProducto.setString(2, uuid)
            actProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }
    override fun getItemCount() = Datos.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val prod = Datos[position]
        holder.textView.text = prod.nombreProducto
        val item = Datos[position]

        holder.imgBorrar.setOnClickListener {
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Desea eliminar el registro?")
            builder.setPositiveButton("Si"){ dialog, which ->
                eliminarRegistro(item.nombreProducto, position)
            }
            builder.setNegativeButton("No"){ dialog, which ->

            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
holder.imgEditar.setOnClickListener {
    val context = holder.itemView.context

    val builder = AlertDialog.Builder(context)
    builder.setTitle("Editar nombre")
    val newNombre = EditText(context)
    newNombre.setHint(item.nombreProducto)
    builder.setView(newNombre)
    builder.setPositiveButton("Actualizar"){ dialog, which ->
        actualizarRegistro(newNombre.text.toString(), item.uuid)
    }
    builder.setNegativeButton("No"){ dialog, which ->

    }

}
    }
}