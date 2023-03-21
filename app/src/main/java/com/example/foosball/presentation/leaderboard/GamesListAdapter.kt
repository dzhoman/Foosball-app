package com.example.foosball.presentation.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foosball.R
import com.example.foosball.domain.entity.GameResultEntity
import com.example.foosball.utils.autoNotify
import kotlin.properties.Delegates

class GamesListAdapter : RecyclerView.Adapter<GamesListAdapter.ViewHolder>() {

    private var items: List<GameResultEntity> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_game, parent, false
        )
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun update(results: List<GameResultEntity>) {
        items = results
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: GameResultEntity) = with(itemView) {
            val tvResult = findViewById<TextView>(R.id.tvResult)
            tvResult.text = context.getString(
                R.string.game_result_template,
                item.firstPerson,
                item.firstScore,
                item.secondScore,
                item.secondPerson
            )
        }
    }
}