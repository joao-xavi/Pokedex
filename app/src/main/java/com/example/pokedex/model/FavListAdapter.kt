package com.example.pokedex.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.api.PokeResult
import com.example.pokedex.databinding.FavoritePokemonListBinding

class FavListAdapter(private var context: Context, private val pokemonClick: (Int) -> Unit): RecyclerView.Adapter<FavListAdapter.FavListViewHolder>() {

    private var favoritePokemonList: List<PokeResult> = emptyList<PokeResult>()

    fun setData(list: List<PokeResult>) {
        favoritePokemonList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavListViewHolder {
        val binding = FavoritePokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoritePokemonList.size
    }

    override fun onBindViewHolder(holder: FavListViewHolder, position: Int) {
        val binding = holder.binding
        val pokemonList = favoritePokemonList[position]

        binding.pokemonText.text = "#${position + 1} - ${pokemonList.name}"
        Glide.with(holder.itemView.context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${position + 1}.png").into(binding.pokeView)
        holder.itemView.setOnClickListener { pokemonClick(position + 1) }
    }

    class FavListViewHolder(val binding: FavoritePokemonListBinding): RecyclerView.ViewHolder(binding.root)
}
