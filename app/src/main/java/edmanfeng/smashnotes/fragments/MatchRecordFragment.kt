package edmanfeng.smashnotes.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.match_record_view.view.*
import java.lang.NumberFormatException
import edmanfeng.smashnotes.*


class MatchRecordFragment : Fragment() {
    private lateinit var mGameViewModel: GameViewModel

    private lateinit var mWinButton : MaterialButton
    private lateinit var mLossButton : MaterialButton
    private lateinit var mPlayerTagView : EditText
    private lateinit var mOpponentTagView : EditText
    private lateinit var mPlayerCharacterView : AutoCompleteTextView
    private lateinit var mOpponentCharacterView : AutoCompleteTextView
    private lateinit var mStageView : AutoCompleteTextView
    private lateinit var mHazardsCheck : SwitchMaterial
    private lateinit var mGSPView : EditText
    private lateinit var mNotes : EditText
    private lateinit var mSessionHistory : RecyclerView
    private lateinit var mGameSpinner: Spinner

    private lateinit var mGame: String
    private var mIsVictory: Boolean = false
    private var mNewGame: Boolean = true

    companion object {
        private const val ARG_ID = "id"
        private const val ARG_GAME_NAME = "game"
        private const val ARG_NEW_GAME = "new_game"
        private const val ARG_PLAYER_NAME = "p_name"
        private const val ARG_OPP_NAME = "o_name"
        private const val ARG_PLAYER_CHAR = "player_char"
        private const val ARG_OPP_CHAR = "opp_char"
        private const val ARG_RESULT = "result"
        private const val ARG_STAGE = "stage"
        private const val ARG_HAZARDS = "hazards"
        private const val ARG_GSP = "gsp"
        private const val ARG_NOTES = "notes"


        fun newInstance(name : String) : MatchRecordFragment {
            val frag = MatchRecordFragment()
            val args = Bundle()
            args.putSerializable(ARG_GAME_NAME, name)
            args.putBoolean(ARG_NEW_GAME, true)

            frag.arguments = args
            return frag
        }

        fun newInstance(game: GameRecord) : MatchRecordFragment {
            val frag = MatchRecordFragment()
            val args = Bundle()
            args.putLong(ARG_ID, game.id)
            args.putSerializable(ARG_GAME_NAME, game.game)
            args.putBoolean(ARG_NEW_GAME, false)
            args.putString(ARG_OPP_NAME, game.opponentTag)
            args.putString(ARG_PLAYER_CHAR, game.playerCharacter)
            args.putString(ARG_OPP_CHAR, game.opponentCharacter)
            args.putString(ARG_RESULT, game.result)
            args.putString(ARG_STAGE, game.stage)
            args.putBoolean(ARG_HAZARDS, game.hazards)
            args.putInt(ARG_GSP, game.gsp)
            args.putString(ARG_NOTES, game.notes)

            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGameViewModel = ViewModelProviders
            .of(this)
            .get(GameViewModel::class.java)

        mGame = arguments
            ?.getString(ARG_GAME_NAME) ?: ""
        if (mGame == "All") {
            mGame = "SSBU"
        }

        mNewGame = arguments
            ?.getBoolean(ARG_NEW_GAME) ?: true

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = requireContext()
        val v = inflater.inflate(R.layout.match_record_view, container, false)

        mPlayerTagView = v.player_name
        mOpponentTagView = v.opponent_name

        mGSPView = v.gsp
        mNotes = v.match_notes

        mWinButton = v.win_button
        mWinButton.setOnClickListener{
            mIsVictory = true
            setButtonStyle(false)
        }
        mLossButton = v.loss_button
        mLossButton.setOnClickListener {
            mIsVictory = false
            setButtonStyle(false)
        }

        mGameSpinner = v.game_spinner
        val gameSpinnerAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            Game.getAdapterList(null)
        )
        gameSpinnerAdapter.setDropDownViewResource(
            R.layout.support_simple_spinner_dropdown_item
        )
        mGameSpinner.adapter = gameSpinnerAdapter


        val stageArrayId : Int
        val charArrayId : Int
        when (mGame) {
            Game.SSBU.toString() -> {
                stageArrayId = R.array.stagesUltimate
                charArrayId = R.array.charactersUltimate
                mGSPView.visibility = View.VISIBLE
            }
            Game.SSB4.toString() -> {
                stageArrayId = R.array.stages4wiiu
                charArrayId = R.array.characters4
                mGSPView.visibility = View.INVISIBLE
            }
            Game.SSBB.toString() -> {
                stageArrayId = R.array.stagesBrawl
                charArrayId = R.array.charactersBrawl
                mGSPView.visibility = View.INVISIBLE
            }
            Game.SSBM.toString() -> {
                stageArrayId = R.array.stagesMelee
                charArrayId = R.array.charactersMelee
                mGSPView.visibility = View.INVISIBLE
            }
            Game.SSB64.toString() -> {
                stageArrayId = R.array.stages64
                charArrayId = R.array.characters64
                mGSPView.visibility = View.INVISIBLE
            }
            else -> {
                // throw IllegalStateException("Game string was not in correct format")
                // TODO: add PM, SSF2
                stageArrayId = R.array.stagesUltimate
                charArrayId = R.array.charactersUltimate
                mGSPView.visibility = View.INVISIBLE
            }
        }

        val stageAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(stageArrayId)
        )

        mStageView = v.stage_choice
        mStageView.setAdapter(stageAdapter)
        mStageView.setDropDownBackgroundDrawable(requireContext().getDrawable(R.drawable.autocomplete_dropdown))


        mHazardsCheck = v.hazards_checkbox
        setHazardsLabel()
        mHazardsCheck.setOnClickListener {
            setHazardsLabel()
        }

        mPlayerCharacterView = v.player_character
        mPlayerCharacterView.setAdapter(ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(charArrayId)
        ))

        mOpponentCharacterView = v.opponent_character
        mOpponentCharacterView.setAdapter(ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(charArrayId)
        ))

        mSessionHistory = v.session_history
        if (mNewGame) {
            mSessionHistory.adapter = GameAdapter(mGameViewModel.sessionGames)
            val manager = LinearLayoutManager(context)

            // make RecyclerView insert at the top, old items go offscreen
            manager.stackFromEnd = true
            manager.reverseLayout = true
            mSessionHistory.layoutManager = manager
        } else {
            mSessionHistory.visibility = View.GONE
        }

        populateViews()
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.match_record_menu, menu)
        if (mNewGame) {
            val menuSave = menu.findItem(R.id.save_record_menu_item)
            menuSave.icon = resources.getDrawable(R.drawable.ic_save_new, null)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_record_menu_item -> {
                saveButtonAction()
                true
            }
            R.id.delete_record_menu_item -> {
                mGameViewModel.delete(makeGameRecord())
                requireActivity().supportFragmentManager
                    .popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Populates views based on last used options if this fragment was
     * initiated as a new game, or uses the old game's values if it was
     * not.
     */
    private fun populateViews() {
        val pos = (mGameSpinner.adapter as ArrayAdapter<String>).getPosition(mGame)
        mGameSpinner.setSelection(pos)

        if (mNewGame) {
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            mPlayerCharacterView.setText(
                sharedPref.getString(SharedPrefs.CHAR_SHARED_PREF_KEY, "")
            )
            mStageView.setText(
                sharedPref.getString(SharedPrefs.STAGE_SHARED_PREF_KEY, "")
            )
        } else {
            mPlayerCharacterView.setText(
                arguments?.getString(ARG_PLAYER_CHAR)
            )
            mOpponentCharacterView.setText(
                arguments?.getString(ARG_OPP_CHAR)
            )
            mOpponentTagView.setText(
                arguments?.getString(ARG_OPP_NAME)
            )
            mHazardsCheck.isChecked = arguments?.getBoolean(ARG_HAZARDS) ?: false
            mStageView.setText(
                arguments?.getString(ARG_STAGE)
            )
            val gsp = arguments?.getInt(ARG_GSP)
            if (gsp != 0) {
                mGSPView.setText("%d".format(gsp))
            }

            mNotes.setText(
                arguments?.getString(ARG_NOTES)
            )
            mIsVictory = arguments?.getString(ARG_RESULT).equals("Win")
            setButtonStyle(false)
        }
    }

    private fun saveButtonAction() {
        var error = false

        if (mPlayerCharacterView.text.isBlank()) {
            mPlayerCharacterView.error = "Player character required"
            error = true
        }
        if (mOpponentCharacterView.text.isBlank()) {
            mOpponentCharacterView.error = "Opponent character required"
            error = true
        }
        if (mStageView.text.isBlank()) {
            mStageView.error = "Stage required"
            error = true
        }

        if (!error) {
            saveRecord()
            if (mNewGame) {
                resetViews()
            }
            savePreferences()
        }
    }

    /**
     * If reset is true, both win and loss buttons are styled as if no option has been chosen.
     * If it is false, it is styled based on whether or not the currently chosen option is
     * a win or loss.
     */
    private fun setButtonStyle(reset: Boolean) {
        val opaque = 255
        val transparent = 100

        val resources = requireContext().resources
        val normalTextColorResource = VersionSafeUtil.getColorResource(resources, android.R.color.white)
        val fadedTextColorResource = VersionSafeUtil.getColorResource(resources, android.R.color.darker_gray)
        when {
            reset -> {
                mWinButton.setTextColor(normalTextColorResource)
                mLossButton.setTextColor(normalTextColorResource)
                mWinButton.background.alpha = opaque
                mLossButton.background.alpha = opaque
            }
            mIsVictory -> {
                mWinButton.setTextColor(normalTextColorResource)
                mLossButton.setTextColor(fadedTextColorResource)
                mWinButton.background.alpha = opaque
                mLossButton.background.alpha = transparent
            }
            else -> {
                mWinButton.setTextColor(fadedTextColorResource)
                mLossButton.setTextColor(normalTextColorResource)
                mWinButton.background.alpha = transparent
                mLossButton.background.alpha = opaque
            }
        }
    }

    private fun savePreferences() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val game: String = if (mGame != "All") mGameSpinner.selectedItem as String else "All"
        with (sharedPref.edit()) {
            putString(SharedPrefs.GAME_SHARED_PREF_KEY, game)
            putString(SharedPrefs.CHAR_SHARED_PREF_KEY, mPlayerCharacterView.text.toString())
            putString(SharedPrefs.STAGE_SHARED_PREF_KEY, mStageView.text.toString())
            apply()
        }
    }

    private fun saveRecord() {
        val game = mGameSpinner.selectedItem as String
        if (game == "All") {
            // TODO: Add a spinner to allow game choice, then make a toast that says to choose a game
            return
        }

        val record = makeGameRecord()
        if (mNewGame) {
            mGameViewModel.insert(record)
            mSessionHistory.adapter?.notifyDataSetChanged()
            mSessionHistory.smoothScrollToPosition(mSessionHistory.adapter!!.itemCount)
        } else {
            mGameViewModel.updateGame(record)
        }

    }

    private fun makeGameRecord(): GameRecord {
        val game = mGameSpinner.selectedItem as String
        val gsp = try {
            mGSPView.text?.toString()?.toInt() ?: 0
        } catch (error: NumberFormatException) {
            // likely because user left input blank, so its an empty string
            0
        }
        return GameRecord(
            arguments?.getLong(ARG_ID) ?: 0,
            mPlayerCharacterView.text.toString(),
            mOpponentCharacterView.text.toString(),
            mOpponentTagView.text?.toString() ?: "N/A",
            mStageView.text?.toString() ?: "N/A",
            mHazardsCheck.isChecked,
            if (mIsVictory) "Win" else "Loss",
            gsp,
            mNotes.text?.toString() ?: "",
            game
        )
    }

    /**
     * Clears the non-repeating user input.
     * Currently this means GSP and notes
     */
    private fun resetViews() {
        mNotes.setText("")
        mGSPView.setText("")
        setButtonStyle(true)
    }

    private fun setHazardsLabel() {
        val context = requireContext()
        if (mHazardsCheck.isChecked) {
            mHazardsCheck.text = context.getString(R.string.hazards_on_switch_label)
        } else {
            mHazardsCheck.text = context.getString(R.string.hazards_off_switch_label)
        }
    }
}