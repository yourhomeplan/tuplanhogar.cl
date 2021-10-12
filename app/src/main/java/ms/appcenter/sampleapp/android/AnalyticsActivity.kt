package ms.appcenter.sampleapp.android

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.microsoft.appcenter.analytics.Analytics
import java.util.*

class AnalyticsActivity : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(
                R.layout.analytics_root, container, false) as ViewGroup
        val eventButton = rootView.findViewById<Button>(R.id.customEventButton)
        eventButton.setOnClickListener {
            val eventDialog: DialogFragment = EventDialog()
            activity?.let { it1 -> eventDialog.show(it1.supportFragmentManager, "eventDialog") }
        }
        val colorButton = rootView.findViewById<Button>(R.id.customColorButton)
        colorButton.setOnClickListener {
            val colorDialog: DialogFragment = ColorDialog()
            activity?.let { it1 -> colorDialog.show(it1.supportFragmentManager, "colorDialog") }
        }
        return rootView
    }

    class EventDialog : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            Analytics.trackEvent("Sample event")
            val builder = AlertDialog.Builder(requireActivity())
            builder.setMessage("Event sent").setPositiveButton("OK") { _: DialogInterface?, _: Int -> }
            return builder.create()
        }
    }

    class ColorDialog : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(requireActivity())
            val colors = arrayOf<CharSequence>("Yellow", "Blue", "Red")
            builder.setTitle("Pick a color").setItems(colors) { _: DialogInterface?, index: Int ->
                val properties: MutableMap<String, String> = HashMap()
                when (index) {
                    0 -> properties["Color"] = "Yellow"
                    1 -> properties["Color"] = "Blue"
                    2 -> properties["Color"] = "Red"
                }
                Analytics.trackEvent("Color event", properties)
            }
            return builder.create()
        }
    }
}