package ru.artem_torpedo.diabetesdiary.ui.reminders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.android.material.switchmaterial.SwitchMaterial
import android.widget.TextView
import ru.artem_torpedo.diabetesdiary.R
import ru.artem_torpedo.diabetesdiary.data.local.entity.ReminderEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReminderAdapter(
    private val context: Context,
    private var reminders: List<ReminderEntity>,
    private val onToggle: (ReminderEntity, Boolean) -> Unit,
) : BaseAdapter() {

    private val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    fun submitList(newList: List<ReminderEntity>) {
        reminders = newList
        notifyDataSetChanged()
    }

    override fun getCount(): Int = reminders.size

    override fun getItem(position: Int): ReminderEntity = reminders[position]

    override fun getItemId(position: Int): Long = reminders[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_reminder, parent, false)

        val reminder = reminders[position]

        val titleText = view.findViewById<TextView>(R.id.reminderTitleText)
        val dateText = view.findViewById<TextView>(R.id.reminderDateText)
        val noteText = view.findViewById<TextView>(R.id.reminderNoteText)
        val switch = view.findViewById<SwitchMaterial>(R.id.reminderSwitch)

        titleText.text = reminder.title

        val repeatText = if (reminder.repeatDaily) "ежедневно" else "однократно"
        dateText.text = "${formatter.format(Date(reminder.triggerAtMillis))}, $repeatText"

        if (reminder.note.isNullOrBlank()) {
            noteText.visibility = View.GONE
        } else {
            noteText.visibility = View.VISIBLE
            noteText.text = reminder.note
        }

        switch.setOnCheckedChangeListener(null)
        switch.isChecked = reminder.enabled

        switch.setOnCheckedChangeListener { _, isChecked ->
            onToggle(reminder, isChecked)
        }

        return view
    }
}