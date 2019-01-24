package edmanfeng.smashnotes

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharacterListFragment : Fragment() {

    companion object {
        private const val TAG = "CharacterListFragment"

        fun newInstance() : CharacterListFragment {
            return CharacterListFragment()
        }
    }

    private lateinit var mCharacterRecyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(
            R.layout.character_list_fragment,
            container,
            false)

        mCharacterRecyclerView = v.findViewById(
            R.id.character_list_recyclerview
        ) as RecyclerView

        mCharacterRecyclerView.layoutManager = LinearLayoutManager(Activity())


        mCharacterRecyclerView.adapter = CharacterAdapter(
            resources.getStringArray(R.array.characters)
        )

        return v
    }

    private inner class CharacterHolder(itemView : View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var mItemView = itemView as TextView

        init {
            mItemView.setOnClickListener(this)
        }

        fun bindCharacter(character: String) {
            mItemView.text = character
        }

        override fun onClick(v: View?) {
            val frag = CharacterDetailFragment.newInstance(mItemView.text as String)

            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, frag)
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private inner class CharacterAdapter(characters : Array<String>) :
        RecyclerView.Adapter<CharacterHolder>() {

        private var mCharacters = characters

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.character_item, parent, false)


            return CharacterHolder(view)
        }

        override fun getItemCount() : Int {
            return mCharacters.size
        }

        override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
            val name = mCharacters[position]
            holder.bindCharacter(name)
        }
    }
}