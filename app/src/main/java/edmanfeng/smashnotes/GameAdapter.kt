package edmanfeng.smashnotes

import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import edmanfeng.smashnotes.fragments.MatchRecordFragment

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

        override fun onClick(v: View?) {
            val frag = MatchRecordFragment.newInstance(mGame)
            val fm = (v?.context as AppCompatActivity).supportFragmentManager
            fm.beginTransaction()
                .replace(R.id.fragment_container, frag)
                .addToBackStack(null)
                .commit()
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