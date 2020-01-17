package com.cabal.app.Utils

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.util.AttributeSet

class NonScrollRecyclerView: RecyclerView {

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int): super(context, attributeSet, defStyle)

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val heightmeasuerspecCustom = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 1, MeasureSpec.AT_MOST)
        super.onMeasure(widthSpec, heightmeasuerspecCustom)
    }
}