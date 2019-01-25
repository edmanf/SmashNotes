package edmanfeng.smashnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MatchRecordFragment : Fragment() {

    private lateinit var mGameViewModel: GameViewModel
    private lateinit var mCharacter : String

    private lateinit var mSaveButton : Button
    private lateinit var mPlayerTagView : EditText
    private lateinit var mOpponentTagView : EditText
    private lateinit var mPlayerCharacterView : AutoCompleteTextView
    private lateinit var mOpponentCharacterView : AutoCompleteTextView
    private lateinit var mStageView : AutoCompleteTextView
    private lateinit var mHazardsCheck : CheckBox
    private lateinit var mResultView : Spinner
    private lateinit var mGSPView : EditText
    private lateinit var mNotes : EditText
    private lateinit var mSessionHistory : RecyclerView

    //private var mGamesList = mutableListOf<GameRecord>()

    companion object {
        private const val ARG_CHARACTER_NAME = "character_name"

        fun newInstance(name : String) : MatchRecordFragment {
            val frag = MatchRecordFragment()
            val args = Bundle()
            args.putSerializable(ARG_CHARACTER_NAME, name)

            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCharacter = arguments?.getString(ARG_CHARACTER_NAME) as String
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.match_record_view, container, false)
        val player1 = v.findViewById(R.id.player1) as ConstraintLayout
        val player2 = v.findViewById(R.id.player2) as ConstraintLayout


        mPlayerTagView = player1.findViewById(R.id.player_name)
        mOpponentTagView = player2.findViewById(R.id.player_name)
        mHazardsCheck = v.findViewById(R.id.hazards_checkbox)
        mGSPView = v.findViewById(R.id.gsp)
        mNotes = v.findViewById(R.id.match_notes)


        mSaveButton = v.findViewById(R.id.save_button)
        mSaveButton.setOnClickListener{ saveMatch() }
        val characterAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.characters)
        )
        mPlayerCharacterView = player1.findViewById(R.id.character)
        mPlayerCharacterView.setText(mCharacter)
        mPlayerCharacterView.setAdapter(characterAdapter)

        mOpponentCharacterView = player2.findViewById(R.id.character)
        mOpponentCharacterView.setAdapter(characterAdapter)

        val stageAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.stages)
        )

        mStageView = v.findViewById(R.id.stage_choice)
        mStageView.setAdapter(stageAdapter)

        val resultAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.win_loss)
        )
        mResultView = v.findViewById(R.id.match_result)
        mResultView.adapter = resultAdapter

        mSessionHistory = v.findViewById(R.id.session_history)
        mSessionHistory.adapter = GameAdapter(mGameViewModel.sessionGames)
        val manager = LinearLayoutManager(context)

        // make RecyclerView insert at the top, old items go offscreen
        manager.stackFromEnd = true
        manager.reverseLayout = true
        mSessionHistory.layoutManager = manager

        return v
    }

    private fun saveMatch() {
        val game = GameRecord(
            0,
            mPlayerCharacterView.text.toString(),
            mOpponentCharacterView.text.toString(),
            mOpponentTagView.text.toString(),
            mStageView.text.toString(),
            mHazardsCheck.isChecked,
            mResultView.selectedItem.toString(),
            mGSPView.text.toString().toInt(),
            mNotes.text.toString()
        )
        mGameViewModel.insert(game)
        mSessionHistory.adapter?.notifyDataSetChanged()
    }

    private inner class GameHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
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

    private inner class GameAdapter(games : List<GameRecord>) : RecyclerView.Adapter<GameHolder>() {
        private val mGames = games

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
            val v = LayoutInflater.from(context).inflate(R.layout.saved_match_item, parent, false)
            return GameHolder(v)
        }

        override fun getItemCount(): Int {
            return mGames.size
        }

        override fun onBindViewHolder(holder: GameHolder, position: Int) {
            holder.bindGame(mGames[position])
        }
    }
}