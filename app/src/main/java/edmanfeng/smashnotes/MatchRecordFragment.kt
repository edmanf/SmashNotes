package edmanfeng.smashnotes

import android.content.Context
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
import java.lang.IllegalStateException
import java.lang.NumberFormatException

class MatchRecordFragment : Fragment() {
    private lateinit var mGameViewModel: GameViewModel
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
    private lateinit var mGameSpinner: Spinner

    companion object {
        private const val ARG_GAME_NAME = "game"

        fun newInstance(name : String) : MatchRecordFragment {
            val frag = MatchRecordFragment()
            val args = Bundle()
            args.putSerializable(ARG_GAME_NAME, name)

            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGameViewModel = ViewModelProviders
            .of(this)
            .get(GameViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = requireContext()
        val v = inflater.inflate(R.layout.match_record_view, container, false)
        val player1 = v.findViewById(R.id.player1) as ConstraintLayout
        val player2 = v.findViewById(R.id.player2) as ConstraintLayout

        val game = arguments
            ?.getString(MatchRecordFragment.ARG_GAME_NAME) ?: ""

        mPlayerTagView = player1.findViewById(R.id.player_name)
        mOpponentTagView = player2.findViewById(R.id.player_name)
        mHazardsCheck = v.findViewById(R.id.hazards_checkbox)
        mGSPView = v.findViewById(R.id.gsp)
        mNotes = v.findViewById(R.id.match_notes)

        mSaveButton = v.findViewById(R.id.save_button)
        mSaveButton.setOnClickListener{
            saveMatch()
            clearInput()
            savePreferences()
        }


        mGameSpinner = v.findViewById(R.id.game_spinner)
        val gameSpinnerAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            Game.getAdapterList()
        )
        gameSpinnerAdapter.setDropDownViewResource(
            R.layout.support_simple_spinner_dropdown_item
        )
        mGameSpinner.adapter = gameSpinnerAdapter
        val pos = gameSpinnerAdapter.getPosition(game)
        mGameSpinner.setSelection(pos)

        val stageArrayId : Int
        val charArrayId : Int
        when (game) {
            Game.SSBU.toString() -> {
                stageArrayId = R.array.stagesUltimate
                charArrayId = R.array.charactersUltimate
            }
            Game.SSB4.toString() -> {
                stageArrayId = R.array.stages4wiiu
                charArrayId = R.array.characters4
            }
            Game.SSBB.toString() -> {
                stageArrayId = R.array.stagesBrawl
                charArrayId = R.array.charactersBrawl
            }
            Game.SSBM.toString() -> {
                stageArrayId = R.array.stagesMelee
                charArrayId = R.array.charactersMelee
            }
            Game.SSB64.toString() -> {
                stageArrayId = R.array.stages64
                charArrayId = R.array.characters64
            }
            else -> {
                // throw IllegalStateException("Game string was not in correct format")
                // TODO: add PM, SSF2
                stageArrayId = R.array.stagesUltimate
                charArrayId = R.array.charactersUltimate
            }
        }

        val stageAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(stageArrayId)
        )
        mStageView = v.findViewById(R.id.stage_choice)
        mStageView.setAdapter(stageAdapter)

        val characterAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(charArrayId)
        )
        mPlayerCharacterView = player1.findViewById(R.id.character)
        mPlayerCharacterView.setAdapter(characterAdapter)

        mOpponentCharacterView = player2.findViewById(R.id.character)
        mOpponentCharacterView.setAdapter(characterAdapter)



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

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return v
        mPlayerCharacterView.setText(
            sharedPref.getString(SharedPrefs.CHAR_SHARED_PREF_KEY, "")
        )
        mStageView.setText(
            sharedPref.getString(SharedPrefs.STAGE_SHARED_PREF_KEY, "")
        )

        return v
    }

    private fun savePreferences() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(SharedPrefs.GAME_SHARED_PREF_KEY, mGameSpinner.selectedItem as String)
            putString(SharedPrefs.CHAR_SHARED_PREF_KEY, mPlayerCharacterView.text.toString())
            putString(SharedPrefs.STAGE_SHARED_PREF_KEY, mStageView.text.toString())
            apply()
        }
    }

    private fun saveMatch() {
        val game = mGameSpinner.selectedItem as String
        if (game == "All") {
            // TODO: Add a spinner to allow game choice, then make a toast that says to choose a game
            return
        }
        var gsp : Int
        try {
            gsp = mGSPView.text?.toString()?.toInt() ?: 0
        } catch (error: NumberFormatException) {
            // likely because user left input blank, so its an empty string
            gsp = 0
        }

        val record = GameRecord(
            0,
            mPlayerCharacterView.text.toString(),
            mOpponentCharacterView.text.toString(),
            mOpponentTagView.text?.toString() ?: "N/A",
            mStageView.text?.toString() ?: "N/A",
            mHazardsCheck.isChecked,
            mResultView.selectedItem.toString(),
            gsp,
            mNotes.text?.toString() ?: "",
            game
        )
        mGameViewModel.insert(record)
        mSessionHistory.adapter?.notifyDataSetChanged()
    }

    /**
     * Clears the non-repeating user input.
     * Currently this means GSP and notes
     */
    private fun clearInput() {
        mNotes.setText("")
        mGSPView.setText("")
    }
}