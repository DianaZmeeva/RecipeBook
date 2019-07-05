package com.example.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso

class RecipeAdapter(internal var context: Context, internal var recipeList: List<Recipe>) :
    ArrayAdapter<Recipe>(context, 0, recipeList) {
    private val mInflater: LayoutInflater

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    override fun getItem(position: Int): Recipe? {
        return recipeList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val vh: ViewHolder
        if (convertView == null) {
            val view = mInflater.inflate(R.layout.layout_row_view, parent, false)
            vh = ViewHolder.create(view as RelativeLayout)
            view.setTag(vh)
        } else {
            vh = convertView.tag as ViewHolder
        }

        val item = getItem(position)
        vh.textViewName.text = item!!.getName()
        vh.textViewDescription.text = item.getDescription()
        Picasso.with(context).load(item.getImages()!![0]).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
            .into(vh.imageView)

        return vh.rootView
    }

    private class ViewHolder private constructor(
        val rootView: RelativeLayout,
        val imageView: ImageView,
        val textViewName: TextView,
        val textViewDescription: TextView
    ) {
        companion object {

            fun create(rootView: RelativeLayout): ViewHolder {
                val imageView = rootView.findViewById<View>(R.id.imageView) as ImageView
                val textViewName = rootView.findViewById<View>(R.id.textViewName) as TextView
                val textViewDescription = rootView.findViewById<View>(R.id.textViewDescription) as TextView
                return ViewHolder(rootView, imageView, textViewName, textViewDescription)
            }
        }
    }
}