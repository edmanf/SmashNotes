package edmanfeng.smashnotes

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.saved_match_item.view.*

class GameAdapter(games : List<GameRecord>?) : RecyclerView.Adapter<GameAdapter.GameHolder>() {
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


    class GameHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var mItemView = itemView
        private lateinit var mGame : GameRecord

        init {
            mItemView.setOnClickListener(this)
        }

        fun bindGame(game : GameRecord) {
            mGame = game
            mItemView.gsp.text = mGame.gsp.toString()
            mItemView.findViewById<TextView>(R.id.player_character).text = mGame.playerCharacter
            mItemView.findViewById<TextView>(R.id.opponent_character).text = mGame.opponentCharacter
            mItemView.findViewById<TextView>(R.id.stage).text = mGame.stage
            if (mGame.isVictory()) {
                mItemView.findViewById<TextView>(R.id.player_character).setTypeface(null, Typeface.BOLD)
                mItemView.findViewById<TextView>(R.id.opponent_character).setTypeface(null, Typeface.NORMAL)
            } else {
                mItemView.findViewById<TextView>(R.id.opponent_character).setTypeface(null, Typeface.BOLD)
                mItemView.findViewById<TextView>(R.id.player_character).setTypeface(null, Typeface.NORMAL)
            }

            val resources = mItemView.context.resources
            mItemView.setBackgroundColor( when(mGame.game) {
                Game.SSB64 -> Utils.getColorResourceVersionSafe(resources, R.color.SSB64)
                Game.SSBM -> Utils.getColorResourceVersionSafe(resources, R.color.SSBM)
                Game.SSBB -> Utils.getColorResourceVersionSafe(resources, R.color.SSBB)
                Game.SSB4 -> Utils.getColorResourceVersionSafe(resources, R.color.SSB4)
                Game.SSBU -> Utils.getColorResourceVersionSafe(resources, R.color.SSBU)
                else -> Utils.getColorResourceVersionSafe(resources, R.color.design_default_color_background)
            })
        }

        override fun onClick(v: View?) {
            val action = NavGraphDirections.actionGlobalMatchRecordFragment(mGame.id)
            v?.findNavController()?.navigate(action)
        }


    }
}