package com.example.pokedex.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.model.PokeInfoViewModel
import com.example.pokedex.api.PokeResult
import com.example.pokedex.database.FavoritePokemon
import com.example.pokedex.database.FavoritePokemonRepository
import com.example.pokedex.database.MySingleton.currentUserId
import com.example.pokedex.database.UserDatabase
import com.example.pokedex.database.UserRepository
import com.example.pokedex.databinding.PokeListBinding
import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class PokeListAdapter(private val pokemonClick: (Int) -> Unit): RecyclerView.Adapter<PokeListAdapter.SearchViewHolder>() {
    private var pokemonList: List<PokeResult> = emptyList<PokeResult>()
    private lateinit var context: Context

    fun setData(list: List<PokeResult>) {
        pokemonList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = PokeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val binding = holder.binding
        val pokemonList = pokemonList[position]
        binding.pokemonText.text = "#${position + 1} - ${pokemonList.name}"
        Glide.with(holder.itemView.context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${position + 1}.png").into(binding.pokeView)
        holder.itemView.setOnClickListener { pokemonClick(position + 1) }

        binding.btnFavorite.setOnClickListener {
            val userDatabase = UserDatabase.getInstance(context)
            val currentUser = userDatabase.userDao()

            val userRepository = UserRepository(currentUser)
            run {
                val pokemon = userRepository.getUserByEmail(currentUserId)?.let {
                    FavoritePokemon(
                        pokemonId = position + 1,
                        userId = it.id
                    )
                }
                val favoritePokemonRepository = FavoritePokemonRepository.getInstance(context)
                favoritePokemonRepository.addFavoritePokemon(currentUser.userId, pokemon)
                Toast.makeText(context, "Pokemon favoritado!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class SearchViewHolder(val binding: PokeListBinding): RecyclerView.ViewHolder(binding.root)
}
