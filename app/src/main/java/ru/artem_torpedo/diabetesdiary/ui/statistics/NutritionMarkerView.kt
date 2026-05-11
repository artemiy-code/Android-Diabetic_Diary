package ru.artem_torpedo.diabetesdiary.ui.statistics

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import ru.artem_torpedo.diabetesdiary.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NutritionMarkerView(
    context: Context,
    layoutResource: Int,
    private val points: List<DailyNutritionPoint>
) : MarkerView(context, layoutResource) {

    private val dateText: TextView = findViewById(R.id.nutritionMarkerDateText)
    private val valueText: TextView = findViewById(R.id.nutritionMarkerValueText)

    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e != null) {
            val index = e.x.toInt()

            if (index in points.indices) {
                val point = points[index]

                dateText.text = formatter.format(Date(point.dayStartMillis))

                valueText.text = buildString {
                    append("Калории: ${fmt(point.totalCalories)}\n")
                    append("Белки: ${fmt(point.totalProtein)}\n")
                    append("Жиры: ${fmt(point.totalFat)}\n")
                    append("Углеводы: ${fmt(point.totalCarbs)}")
                }
            }
        }

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(
            -(width / 1f),
            -( height/ 1f)
        )
    }

    private fun fmt(value: Float): String {
        return String.format(Locale.getDefault(), "%.1f", value)
    }
}