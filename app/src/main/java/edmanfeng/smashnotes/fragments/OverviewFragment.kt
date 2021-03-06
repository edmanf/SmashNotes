package edmanfeng.smashnotes.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edmanfeng.smashnotes.*
import edmanfeng.smashnotes.repo.GameRecord
import edmanfeng.smashnotes.viewmodels.OverviewViewModel
import kotlinx.android.synthetic.main.overview_fragment.view.*

class OverviewFragment : Fragment() {


    private lateinit var mOverviewViewModel : OverviewViewModel

    companion object {
        fun newInstance() : OverviewFragment {
            return OverviewFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOverviewViewModel = ViewModelProviders
            .of(this)
            .get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.overview_fragment, container, false)

        val sharedPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        // getString is nullable because defValue is nullable
        // because it is set to an immutable, the !! operator is still null-safe
        val game = sharedPrefs.getString(
            SharedPrefs.GAME_SHARED_PREF_KEY, "All"
        )!!

        setupGameRecyclerView(v, game)

        setupGameSpinner(v, game)

        val fab: FloatingActionButton = v.findViewById(R.id.add_button)
        fab.setOnClickListener {
            val action = NavGraphDirections.actionGlobalMatchRecordFragment(GameRecord.NEW_GAME_ID)
            findNavController().navigate(action)
        }

        return v
    }

    /**
     * Sets up the gameRecyclerView and connects it to the given game
     */
    private fun setupGameRecyclerView(v: View, game: String) {
        val gamesRecyclerView = v.game_history_recyclerview
        val adapter = GameAdapter(mOverviewViewModel.getGame(game))

        mOverviewViewModel.allGames.observe(this, Observer {
            // update games whenever any game gets added/removed
            adapter.setGames(mOverviewViewModel.getGame(game))
        })

        gamesRecyclerView.adapter = adapter
        val manager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        manager.reverseLayout = true
        gamesRecyclerView.layoutManager = manager


        scrollGamesRecyclerView(gamesRecyclerView)
    }

    private fun setupGameSpinner(v: View, game: String) {
        val gameSpinner = v.findViewById<Spinner>(R.id.game_spinner)
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            getAdapterList("All")
        )
        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        gameSpinner.adapter = spinnerAdapter
        val pos = spinnerAdapter.getPosition(game)
        gameSpinner.setSelection(pos)
        gameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as String
                val gamesRecyclerView = v.game_history_recyclerview
                val adapter = gamesRecyclerView.adapter as GameAdapter
                adapter.setGames(mOverviewViewModel.getGame(item))
                scrollGamesRecyclerView(gamesRecyclerView)

                with (requireActivity().getPreferences(Context.MODE_PRIVATE).edit()) {
                    putString(SharedPrefs.GAME_SHARED_PREF_KEY, item)
                    apply()
                }
            }
        }
    }

    private fun scrollGamesRecyclerView(gamesRecyclerView: RecyclerView) {
        if (gamesRecyclerView.adapter != null) {
            // Scroll to the top (most recent games) of the recyclerview
            // must happen after both game spinner (to get the correct count)
            // and the gamerecyclerview have been set up
            val itemCount = (gamesRecyclerView.adapter as GameAdapter).itemCount
            gamesRecyclerView.smoothScrollToPosition(itemCount)
        }
    }
}