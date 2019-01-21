package edmanfeng.smashnotes

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CharacterListFragment() : Fragment() {
    private val TAG = "CharacterListFragment"

    companion object {
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

        Log.d("TAG", "onCreateView called")
        val v = inflater.inflate(
            R.layout.character_list_fragment,
            container,
            false)

        mCharacterRecyclerView = v.findViewById(
            R.id.character_list_recyclerview
        ) as RecyclerView

        mCharacterRecyclerView.setLayoutManager(
            LinearLayoutManager(Activity())
        )

        mCharacterRecyclerView.setAdapter(
            CharacterAdapter(Characters.smashUltimate)
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
            mItemView.setText(character)
        }

        override fun onClick(v: View?) {
            var frag = RecordFragment.newInstance(mItemView.text as String)

            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, frag)
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private inner class CharacterAdapter(characterList : List<String>) :
        RecyclerView.Adapter<CharacterHolder>() {

        private var mCharacterList = characterList

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
            val inflater = LayoutInflater.from(getActivity())
            val view = inflater.inflate(R.layout.character_view, parent, false)


            return CharacterHolder(view)
        }

        override fun getItemCount() : Int {
            return mCharacterList.size
        }

        override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
            val name = mCharacterList[position]
            holder.bindCharacter(name)
        }
    }
}