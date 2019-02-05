package edmanfeng.smashnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OverviewFragment : Fragment() {


    private lateinit var mGameViewModel : GameViewModel

    companion object {
        private const val TAG = "OverviewFragment"
        fun newInstance() : OverviewFragment {
            return OverviewFragment()
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
        val v = inflater.inflate(R.layout.overview_fragment, container, false)

        val gamesRecyclerView = v
            .findViewById<RecyclerView>(R.id.game_history_recyclerview)

        val adapter = GameAdapter(mGameViewModel.allGames.value)
        mGameViewModel.allGames.observe(this, Observer { games ->
            games?.let{adapter.setGames(games)} // safe call for non null games
        })
        gamesRecyclerView.adapter = adapter


        val manager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        manager.reverseLayout = true
        gamesRecyclerView.layoutManager = manager

        val gameSpinner = v.findViewById<Spinner>(R.id.game_spinner)
        val spinnerAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            enumValues<Game>()
        )
        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        gameSpinner.adapter = spinnerAdapter


        val fab = v.findViewById<FloatingActionButton>(R.id.add_button)
        fab.setOnClickListener {
            val frag = MatchRecordFragment
                .newInstance(gameSpinner.selectedItem.toString())
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, frag)
                ?.addToBackStack(null)
                ?.commit()
        }

        return v
    }
}