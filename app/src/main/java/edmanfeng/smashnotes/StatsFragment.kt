package edmanfeng.smashnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
    }

    lateinit var mGameViewModel : GameViewModel
    lateinit var mRatingChart : LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGameViewModel = ViewModelProviders
            .of(this)
            .get(GameViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.stats_fragment, container, false)
        mRatingChart = v.findViewById(R.id.rating_chart)
        mGameViewModel.allGames.observe(this, Observer {
            updateChart(it)
        })


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