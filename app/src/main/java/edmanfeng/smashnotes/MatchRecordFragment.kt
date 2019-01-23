package edmanfeng.smashnotes

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MatchRecordFragment private constructor() : Fragment() {

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

    companion object {
        private val ARG_CHARACTER_NAME="character_name"

        fun newInstance(name : String) : MatchRecordFragment {
            val frag = MatchRecordFragment()
            val args = Bundle()
            args.putSerializable(ARG_CHARACTER_NAME, name)

            frag.setArguments(args)
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCharacter = arguments?.getString(ARG_CHARACTER_NAME) as String
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
        mSessionHistory = v.findViewById(R.id.session_history)


        mSaveButton = v.findViewById(R.id.save_button)
        mSaveButton.setOnClickListener({ v->
            saveMatch()

        })

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

        return v
    }

    private fun saveMatch() {

    }

    private inner class MatchHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindMatch(match : GameRecord) {

        }
    }

    private inner class MatchAdapter(matches : List<GameRecord>) : RecyclerView.Adapter<MatchHolder>() {
        private val mMatches = matches

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
            val v = LayoutInflater.from(context).inflate(R.layout.saved_match_item, parent, false)
            return MatchHolder(v)
        }

        override fun getItemCount(): Int {
            return mMatches.size
        }

        override fun onBindViewHolder(holder: MatchHolder, position: Int) {
            holder.bindMatch(mMatches[position])
        }
    }
}