package edmanfeng.smashnotes

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.NullPointerException

class OverviewFragment : Fragment() {


    private lateinit var mGameViewModel : GameViewModel
    private lateinit var mDrawerLayout : DrawerLayout

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
        mDrawerLayout = activity?.findViewById(R.id.drawer_layout) ?: throw NullPointerException()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = requireContext()
        val v = inflater.inflate(R.layout.overview_fragment, container, false)

        val toolbar = v.findViewById<Toolbar>(R.id.toolbar)
        val compatActivity = activity as AppCompatActivity
        compatActivity.setSupportActionBar(toolbar)
        val actionbar: ActionBar? = compatActivity.supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE)
        val game = sharedPrefs?.getString(
            SharedPrefs.GAME_SHARED_PREF_KEY, Game.SSBU.toString()
        ) ?: Game.SSBU.toString()


        val gamesRecyclerView = v
            .findViewById<RecyclerView>(R.id.game_history_recyclerview)
        val adapter = GameAdapter(mGameViewModel.allGames.value)
        mGameViewModel.allGames.observe(this, Observer { _ ->
            // update games whenever any game gets added/removed
            adapter.setGames(mGameViewModel.getGame(game))
        })
        gamesRecyclerView.adapter = adapter
        val manager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        manager.reverseLayout = true
        gamesRecyclerView.layoutManager = manager


        val gameSpinner = v.findViewById<Spinner>(R.id.game_spinner)
        val games = mutableListOf<String>("All")
        enumValues<Game>().forEach { games.add(it.toString())}
        val spinnerAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            games
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
                adapter.setGames(mGameViewModel.getGame(item))

                val editor = sharedPrefs?.edit()
                editor?.putString(SharedPrefs.GAME_SHARED_PREF_KEY, item)
                editor?.apply()
            }
        }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                mDrawerLayout?.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.drawer_view, menu)
    }
}