import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recycleview.youtubemusic.Cancion
import com.recycleview.youtubemusic.R
import com.squareup.picasso.Picasso

class ArtistaAdapter(
    val artistasLits: ArrayList<Cancion>,
    val context: Context
) : RecyclerView.Adapter<ArtistaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_artistas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artista = artistasLits[position]
        holder.bind(artista)
    }

    override fun getItemCount(): Int {
        return artistasLits.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nuevoTitulo: TextView = itemView.findViewById(R.id.idArtistaTitulo)
        private val nuevoNombreArtista: TextView = itemView.findViewById(R.id.idNombreArtista)
        private val nuevoImagenView: ImageView = itemView.findViewById(R.id.idImagenes)

        fun bind(artista: Cancion) {
            nuevoTitulo.text = artista.titulo
            nuevoNombreArtista.text = artista.artista
            Picasso.get().load(artista.nuevoImagen).into(nuevoImagenView)
        }
    }
}
