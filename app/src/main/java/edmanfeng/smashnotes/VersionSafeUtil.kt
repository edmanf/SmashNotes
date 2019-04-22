package edmanfeng.smashnotes

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.core.widget.TextViewCompat


class VersionSafeUtil {
    companion object {
        /**
         * Sets the background color of the view to the specified color
         * resource.
         */
        fun setBackgroundColor(v: View, color: Int) {
            val resources = v.context.resources
            if (Build.VERSION.SDK_INT >= 23) {
                v.setBackgroundColor(resources.getColor(color, null))
            } else {
                v.setBackgroundColor(resources.getColor(color))
            }
        }

        fun setTextColor(v: TextView, color: Int) {
            val resources = v.context.resources
            if (Build.VERSION.SDK_INT >= 23) {
                v.setTextColor(resources.getColor(color, null))
            } else {
                v.setTextColor(resources.getColor(color))
            }
        }
    }

}