package com.example.foosball.presentation.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foosball.R
import com.example.foosball.domain.entity.LeaderboardItemEntity

class LeaderboardsAdapter(private var items: List<LeaderboardItemEntity>) :
    RecyclerView.Adapter<LeaderboardsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_leaderboard, parent, false
        )
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun update(results: List<LeaderboardItemEntity>) {
        //todo improve with DiffUtils
        items = results
        notifyItemRangeChanged(0, items.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: LeaderboardItemEntity, position: Int) = with(itemView) {
            val place = position + 1
            val tvPlace = findViewById<TextView>(R.id.tvPlace)
            val tvPlayer = findViewById<TextView>(R.id.tvPlayer)
            val tvGamesCount = findViewById<TextView>(R.id.tvGamesCount)
            val tvGamesWon = findViewById<TextView>(R.id.tvGamesWon)
            tvPlace.text = place.toString()
            tvPlayer.text = item.userName
            tvGamesCount.text = "${item.gamesCount}"
            tvGamesWon.text = "${item.gamesWon}"
        }
    }
}