package com.example.foosball.presentation.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foosball.R
import com.example.foosball.domain.entity.LeaderboardItemEntity
import com.example.foosball.utils.autoNotify
import kotlin.properties.Delegates

class LeaderboardsAdapter : RecyclerView.Adapter<LeaderboardsAdapter.ViewHolder>() {

    private var items: List<LeaderboardItemEntity> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.userName == n.userName }
    }

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
        items = results
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