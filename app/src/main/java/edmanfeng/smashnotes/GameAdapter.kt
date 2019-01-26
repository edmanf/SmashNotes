package edmanfeng.smashnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

public class GameAdapter(games : List<GameRecord>) : RecyclerView.Adapter<GameAdapter.GameHolder>() {
    private val mGames = games

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

    fun setGames(games: LiveData<List<GameRecord>>) {

    }

    public class GameHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var mItemView = itemView
        private lateinit var mGame : GameRecord

        fun bindGame(game : GameRecord) {
            mGame = game
            mItemView.findViewById<TextView>(R.id.player_character).text = mGame.playerCharacter
            mItemView.findViewById<TextView>(R.id.opponent_character).text = mGame.opponentCharacter
            mItemView.findViewById<TextView>(R.id.stage).text = mGame.stage
            mItemView.findViewById<TextView>(R.id.result).text = if (mGame.result == "Win") "W" else "L"
        }
    }
}