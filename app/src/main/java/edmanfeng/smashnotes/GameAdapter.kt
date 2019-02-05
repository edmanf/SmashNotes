package edmanfeng.smashnotes

import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.RecyclerView

public class GameAdapter(games : List<GameRecord>?) : RecyclerView.Adapter<GameAdapter.GameHolder>() {
    private var mGames : List<GameRecord>

    init {
        mGames = games ?: mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.saved_match_item, parent, false)
        return GameHolder(v)
    }

    override fun getItemCount(): Int {
        return mGames.size
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bindGame(mGames[position])
    }

    fun setGames(games: List<GameRecord>) {
        mGames = games
        notifyDataSetChanged()
    }


    public class GameHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var mItemView = itemView
        private lateinit var mGame : GameRecord

        fun bindGame(game : GameRecord) {
            mGame = game
            mItemView.findViewById<TextView>(R.id.player_character).text = mGame.playerCharacter
            mItemView.findViewById<TextView>(R.id.opponent_character).text = mGame.opponentCharacter
            mItemView.findViewById<TextView>(R.id.stage).text = mGame.stage
            if (mGame.result == "Win") {
                mItemView.findViewById<TextView>(R.id.player_character).setTypeface(null, Typeface.BOLD)
                mItemView.findViewById<TextView>(R.id.opponent_character).setTypeface(null, Typeface.NORMAL)
            } else {
                mItemView.findViewById<TextView>(R.id.opponent_character).setTypeface(null, Typeface.BOLD)
                mItemView.findViewById<TextView>(R.id.player_character).setTypeface(null, Typeface.NORMAL)
            }
            when(mGame.game) {
                Game.SSB64.toString() -> setColorVersionSafe(R.color.SSB64)
                Game.SSBM.toString() -> setColorVersionSafe(R.color.SSBM)
                Game.SSBB.toString() -> setColorVersionSafe(R.color.SSBB)
                Game.SSB4.toString() -> setColorVersionSafe(R.color.SSB4)
                Game.SSBU.toString() -> setColorVersionSafe(R.color.SSBU)
                else -> setColorVersionSafe(R.color.design_default_color_background)
            }
        }

        /**
         * Sets the background color of the holder to the specified color
         * resource.
         */
        private fun setColorVersionSafe(color: Int) {
            val resources = mItemView.context.resources
            if (Build.VERSION.SDK_INT >= 23) {
                mItemView.setBackgroundColor(resources.getColor(color, null))
            } else {
                mItemView.setBackgroundColor(resources.getColor(color))
            }
        }
    }
}