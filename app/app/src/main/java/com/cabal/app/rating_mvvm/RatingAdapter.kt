package com.cabal.app.rating_mvvm

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cabal.app.R
import com.cabal.app.database.entities.User

class RatingAdapter(private val viewModel: RatingViewModel) : RecyclerView.Adapter<RatingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(R.layout.user_rating_item, parent, false))

    override fun getItemCount() = viewModel.usersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nick = holder.userDescription.context.getString(R.string.rate_user, viewModel.usersList[position].nick)
        holder.userDescription.text = nick
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userDescription: TextView = view.findViewById(R.id.rateUserDescription)
        private val smile: ImageView = view.findViewById(R.id.rateSmile)
        private val meh: ImageView = view.findViewById(R.id.rateMeh)
        private val frown: ImageView = view.findViewById(R.id.rateFrown)

        init {
            smile.setOnClickListener{choose(smile)}
            meh.setOnClickListener{choose(meh)}
            frown.setOnClickListener{choose(frown)}
        }

        private fun setGreyed(view: ImageView) {
            val matrix = ColorMatrix().also { it.setSaturation(0f) }
            val colorF = ColorMatrixColorFilter(matrix)
            view.run {
                colorFilter = colorF
                imageAlpha = 64
            }
        }

        private fun setUngreyed(view: ImageView) = view.run {
            colorFilter = null
            imageAlpha = 255
        }

        private fun choose(view: ImageView) {
            when (view) {
                smile -> {
                    setGreyed(meh)
                    setGreyed(frown)
                    setUngreyed(smile)
                    viewModel.setLikeForUser(adapterPosition,1)
                }
                frown -> {
                    setGreyed(meh)
                    setGreyed(smile)
                    setUngreyed(frown)
                    viewModel.setLikeForUser(adapterPosition,-1)
                }
                meh -> {
                    setGreyed(smile)
                    setGreyed(frown)
                    setUngreyed(meh)
                    viewModel.setLikeForUser(adapterPosition,0)
                }
            }
        }

    }
}
