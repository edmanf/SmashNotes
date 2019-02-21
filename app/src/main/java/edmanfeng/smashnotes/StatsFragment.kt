package edmanfeng.smashnotes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

/**
 * This fragment is for displaying the
 */
class StatsFragment : Fragment() {
    companion object {
        fun newInstance() : StatsFragment {
            return StatsFragment()
        }

        private lateinit var mGame : Game
    }

    lateinit var mGameViewModel : GameViewModel
    lateinit var mRatingChart : LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGameViewModel = ViewModelProviders
            .of(this)
            .get(GameViewModel::class.java)

        val game = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString(SharedPrefs.GAME_SHARED_PREF_KEY, Game.SSBU.toString())
        mGame = Game.valueOf(game ?: Game.SSBU.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val context = requireContext()
        val v = inflater.inflate(R.layout.stats_fragment, container, false)
        val gameRecyclerView = v.findViewById<RecyclerView>(R.id.game_history_recyclerview)
        val gameAdapter = GameAdapter(mGameViewModel.allGames.value)
        gameRecyclerView.adapter = gameAdapter
        val gameRecyclerViewManager = LinearLayoutManager(context)
        gameRecyclerViewManager.stackFromEnd = true
        gameRecyclerViewManager.reverseLayout = true
        gameRecyclerView.layoutManager = gameRecyclerViewManager

        mRatingChart = v.findViewById(R.id.rating_chart)
        mGameViewModel.allGames.observe(this, Observer {
            updateChart(mGameViewModel.getGame(mGame))
            gameAdapter.setGames(mGameViewModel.getGame(mGame))
        })

        val gameSpinner = v.findViewById<Spinner>(R.id.game_spinner)
        val gameSpinnerAdapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            Game.getAdapterList(null)
        )


        gameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gameSpinner.adapter = gameSpinnerAdapter
        val pos = gameSpinnerAdapter.getPosition(mGame.toString())
        gameSpinner.setSelection(pos)
        gameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as String
                gameAdapter.setGames(mGameViewModel.getGame(item))
                updateChart(mGameViewModel.getGame(item))
            }
        }
        val characterSpinner = v.findViewById<Spinner>(R.id.player_character_spinner)

        return v
    }

    private fun updateChart(gameRecords: List<GameRecord>) {
        val gspOnly = gameRecords.filter {it.gsp > 0}
        if (gspOnly.isEmpty()) {
            mRatingChart.visibility = View.GONE
            return
        } else {
            mRatingChart.visibility = View.VISIBLE
        }

        val entries = mutableListOf<Entry>()
        for (i in 0.until(gspOnly.size)) {
            entries.add(Entry(i.toFloat(), gspOnly.get(i).gsp.toFloat()))
        }
        val dataSet = LineDataSet(entries, "Label")
        val lineData = LineData(dataSet)
        mRatingChart.data = lineData
        mRatingChart.invalidate() // refresh chart
    }
}