package edmanfeng.smashnotes.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.match_record_view.view.*
import java.lang.NumberFormatException
import edmanfeng.smashnotes.*


class MatchRecordFragment : Fragment() {
    private lateinit var mGameViewModel: GameViewModel
    private val args: MatchRecordFragmentArgs by navArgs()
    private var mGameRecord: GameRecord = GameRecord.newGame()
    private var mVictorySelected = false
    private var mResultSelected = false

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

    override fun onResume() {
        super.onResume()
        mSessionHistory.adapter?.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGameViewModel = ViewModelProviders
            .of(this)
            .get(GameViewModel::class.java)

        if (args.id == GameRecord.NEW_GAME_ID) {
            mGameRecord = GameRecord.newGame()
            mResultSelected = false
        } else {
            val gameRecordLiveData = mGameViewModel.getGameRecord(args.id)
            gameRecordLiveData.observe(this, Observer {
                val gameRecord = gameRecordLiveData.value
                if (gameRecord != null) {
                    mGameRecord = gameRecord
                }
                populateViews()
            })
            mResultSelected = true
        }

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
            mVictorySelected = true
            mResultSelected = true
            setButtonStyle(false)
        }
        mLossButton = v.loss_button
        mLossButton.setOnClickListener {
            mVictorySelected = false
            mResultSelected = true
            setButtonStyle(false)
        }

        mGameSpinner = v.game_spinner
        val gameSpinnerAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            getAdapterList(null)
        )
        gameSpinnerAdapter.setDropDownViewResource(
            R.layout.support_simple_spinner_dropdown_item
        )
        mGameSpinner.adapter = gameSpinnerAdapter


        val stageArrayId : Int
        val charArrayId : Int
        when (mGameRecord.game) {
            Game.SSBU -> {
                stageArrayId = R.array.stagesUltimate
                charArrayId = R.array.charactersUltimate
                mGSPView.visibility = View.VISIBLE
            }
            Game.SSB4 -> {
                stageArrayId = R.array.stages4wiiu
                charArrayId = R.array.characters4
                mGSPView.visibility = View.INVISIBLE
            }
            Game.SSBB -> {
                stageArrayId = R.array.stagesBrawl
                charArrayId = R.array.charactersBrawl
                mGSPView.visibility = View.INVISIBLE
            }
            Game.SSBM -> {
                stageArrayId = R.array.stagesMelee
                charArrayId = R.array.charactersMelee
                mGSPView.visibility = View.INVISIBLE
            }
            Game.SSB64 -> {
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
        // Sessions are only started when you are recording new games
        if (mGameRecord.isNewGame()) {
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
        val menuSave = menu.findItem(R.id.save_record_menu_item)

        // Can't use mGameRecord.isNewGame() - this may run before the observe
        if (args.id == GameRecord.NEW_GAME_ID) {
            menuSave.icon = resources.getDrawable(R.drawable.ic_save_new, null)
        } else {
            menuSave.icon = resources.getDrawable(R.drawable.ic_save, null)
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
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        var game = sharedPref.getString(SharedPrefs.GAME_SHARED_PREF_KEY, Game.SSBU.toString())!!
        if (game == "All") {
            // Came from "All" in overview fragment, start with default game
            game = Game.SSBU.toString()
        }
        val pos = (mGameSpinner.adapter as ArrayAdapter<String>)
            .getPosition(game)
        mGameSpinner.setSelection(pos)

        if (args.id == GameRecord.NEW_GAME_ID) {
            // Get previously used values
            mPlayerCharacterView.setText(
                sharedPref.getString(SharedPrefs.CHAR_SHARED_PREF_KEY, "")
            )
            mStageView.setText(
                sharedPref.getString(SharedPrefs.STAGE_SHARED_PREF_KEY, "")
            )
        } else {
            mPlayerCharacterView.setText(mGameRecord.playerCharacter)
            mOpponentCharacterView.setText(mGameRecord.opponentCharacter)
            mOpponentTagView.setText(mGameRecord.opponentTag)
            mHazardsCheck.isChecked = mGameRecord.hazards
            mStageView.setText(mGameRecord.stage)
            val gsp = mGameRecord.gsp
            if (gsp != 0) {
                mGSPView.setText("%d".format(gsp))
            }
            mNotes.setText(mGameRecord.notes)
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

        if (!error && mResultSelected) {
            saveRecord()
            if (args.id == GameRecord.NEW_GAME_ID) {
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
        val normalTextColorResource = Utils.getColorResourceVersionSafe(resources, android.R.color.white)
        val fadedTextColorResource = Utils.getColorResourceVersionSafe(resources, android.R.color.darker_gray)
        when {
            reset -> {
                mWinButton.setTextColor(normalTextColorResource)
                mLossButton.setTextColor(normalTextColorResource)
                mWinButton.background.alpha = opaque
                mLossButton.background.alpha = opaque
            }
            mVictorySelected -> {
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
        var game: String = sharedPref.getString(
            SharedPrefs.GAME_SHARED_PREF_KEY, "All"
        )!!
        if (game != "All") {
            game = mGameSpinner.selectedItem as String
        }
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
        if (mGameRecord.isNewGame()) {
            mGameViewModel.insert(record)
            mSessionHistory.smoothScrollToPosition(mSessionHistory.adapter!!.itemCount)
        } else {
            mGameViewModel.updateGame(record)
        }
        mSessionHistory.adapter?.notifyDataSetChanged()

    }

    private fun makeGameRecord(): GameRecord {
        val game = mGameSpinner.selectedItem as String
        val gsp = try {
            mGSPView.text?.toString()?.toInt() ?: 0
        } catch (error: NumberFormatException) {
            // likely because user left input blank, so its an empty string
            0
        }
        val id = if (mGameRecord.isNewGame()) 0 else mGameRecord.id
        return GameRecord(
            id,
            mPlayerCharacterView.text.toString(),
            mOpponentCharacterView.text.toString(),
            mOpponentTagView.text?.toString() ?: "N/A",
            mStageView.text?.toString() ?: "N/A",
            mHazardsCheck.isChecked,
            if (mVictorySelected) GameRecord.Result.VICTORY else GameRecord.Result.LOSS,
            gsp,
            mNotes.text?.toString() ?: "",
            Game.valueOf(game)
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