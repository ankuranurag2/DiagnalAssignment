package ankuranurag.diagnal.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import ankuranurag.diagnal.R

/**
 * created by ankur on 15/8/20
 */
@BindingAdapter("setPoster")
fun setPoster(view: ImageView, posterName: String) {
    val context = view.context

    //Json has name as "poster1.jpg", but we only need "poster1" here.
    val resName=posterName.split(".").first()

    val resId = context.resources.getIdentifier(resName, "drawable", context.packageName)
    if (resId != 0)
        view.setImageResource(resId)
    else
        view.setImageResource(R.drawable.placeholder_for_missing_posters)
}