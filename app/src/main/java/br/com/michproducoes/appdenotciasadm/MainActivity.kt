package br.com.michproducoes.appdenotciasadm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.michproducoes.appdenotciasadm.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btPublicarNoticia.setOnClickListener {
            val titulo = binding.editTituloNoticia.text.toString()
            val noticias = binding.editNoticias.text.toString()
            val data = binding.editDataNoticia.text.toString()
            val autor = binding.editAutorNoticia.text.toString()

            if (titulo.isEmpty() || noticias.isEmpty() || data.isEmpty() || autor.isEmpty()) {
                if (titulo.isEmpty()) binding.editTituloNoticia.error = "Campo obrigatório"
                if (noticias.isEmpty()) binding.editNoticias.error = "Campo obrigatório"
                if (data.isEmpty()) binding.editDataNoticia.error = "Campo obrigatório"
                if (autor.isEmpty()) binding.editAutorNoticia.error = "Campo obrigatório"

                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener


            } else {
                salvarNoticia(titulo, noticias, data, autor)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun salvarNoticia(titulo: String, noticias: String, data: String, autor: String) {
        val dados = hashMapOf(
            "titulo" to titulo,
            "noticias" to noticias,
            "data" to data,
            "autor" to autor
        )

        db.collection("noticias")
            .add(dados)
            .addOnSuccessListener {
                Toast.makeText(this, "Notícia publicada com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao publicar: ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun limparCampos() {
        binding.editTituloNoticia.setText("")
        binding.editNoticias.setText("")
        binding.editDataNoticia.setText("")
        binding.editAutorNoticia.setText("")


    }


}


