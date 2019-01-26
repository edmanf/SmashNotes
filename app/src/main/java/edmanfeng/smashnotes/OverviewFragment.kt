package edmanfeng.smashnotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OverviewFragment : Fragment() {
    private lateinit var mGamesRecyclerView : RecyclerView
    private lateinit var mGameViewModel : GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.overview_fragment, container, false)

        mGamesRecyclerView.findViewById<RecyclerView>(R.id.game_history_recyclerview)
        mGamesRecyclerView.adapter = GameAdapter(mGameViewModel.sessionGames)


        val manager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        manager.reverseLayout = true
        mGamesRecyclerView.layoutManager = manager

        return v
    }
}