package RecyclerViewHelper

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fer.huezo.myapplication.R

class ViewHolder(view:View) : RecyclerView.ViewHolder(view)
{
    val textView: TextView = view.findViewById(R.id.txtProdCard)
    val imgEditar: ImageButton = view.findViewById(R.id.imgEdit)
    val imgBorrar: ImageButton = view.findViewById(R.id.imgDelete)
}