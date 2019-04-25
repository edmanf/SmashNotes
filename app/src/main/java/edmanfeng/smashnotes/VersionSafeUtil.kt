package edmanfeng.smashnotes

import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.core.widget.TextViewCompat


class VersionSafeUtil {
    companion object {
        /**
         * Returns a color resource for the given color, resource, and theme based
         * on the sdk version
         */
        fun getColorResource(resources: Resources, color: Int, theme: Resources.Theme? = null) : Int {
            return if (Build.VERSION.SDK_INT >= 23) {
                resources.getColor(color, theme)
            } else {
                resources.getColor(color)
            }
        }
    }

}