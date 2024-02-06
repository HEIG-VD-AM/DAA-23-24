/**
 * Authors: Alexis Martins and Pablo Saez
 * Date: 05.12.2023
 *
 * @brief:
 * RecyclerAdapter is a RecyclerView adapter responsible for binding NoteAndSchedule data
 * to corresponding views. It differentiates between items with and without a schedule, using
 * different layouts for each case. The adapter uses a DiffUtil to efficiently update the UI
 * when the underlying data set changes. It also provides methods for customizing view colors
 * based on the state of the Note and the time difference of the associated schedule.
 */

package com.example.daa_lab04.views

import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.example.daa_lab04.R
import com.example.daa_lab04.models.NoteAndSchedule
import com.example.daa_lab04.models.State
import com.example.daa_lab04.models.Type
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    // List of items to display in the RecyclerView
    var items = listOf<NoteAndSchedule>()
        set(value) {
            // Calculate the difference between old and new item lists
            val diffCallback = NotesDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            // Dispatch updates to the adapter
            diffResult.dispatchUpdatesTo(this)
        }

    // Get the total number of items in the RecyclerView
    override fun getItemCount() = items.size

    // Define view types for items with and without clocks
    override fun getItemViewType(position: Int): Int {
        return if (items[position].schedule == null) NOCLOCK else CLOCK
    }

    // Create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Determine the layout resource based on the view type
        val layoutRes = if (viewType == CLOCK) R.layout.item_time else R.layout.item
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Views within the ViewHolder
        private val title: TextView = view.findViewById(R.id.item_title)
        private val text: TextView = view.findViewById(R.id.item_text)
        private val img: ImageView = view.findViewById(R.id.item_image)
        private val months: TextView? = view.findViewById(R.id.text_months)
        private val clock: ImageView? = view.findViewById(R.id.clock_image)

        // Initialize ViewHolder
        init {
            // Set an onClickListener for the item view
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Show a toast when an item is clicked
                    Toast.makeText(itemView.context, "You clicked on item #${position + 1}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Bind data to the views
        fun bind(note: NoteAndSchedule) {
            // Bind data from the NoteAndSchedule object to views
            img.setImageResource(getImageResourceForType(note.note.type))
            title.text = note.note.title
            text.text = note.note.text

            // Set image color based on the note state
            setImgColorByState(note.note.state)

            // Handle schedule-related data if available
            note.schedule?.let {
                val timeDiff = ChronoUnit.DAYS.between(Calendar.getInstance().toInstant().atZone(ZoneOffset.UTC), it.date.time.toInstant().atZone(ZoneOffset.UTC))
                setScheduleClockColor(timeDiff)
                months?.text = getFormattedTimeDifference(timeDiff)
            }
        }

        // Set image color based on note state
        private fun setImgColorByState(state: State) {
            // Define primary and secondary colors
            val primaryColor = ContextCompat.getColor(itemView.context, R.color.CUSTOMBLUE)
            val secondaryColor = ContextCompat.getColor(itemView.context, R.color.CUSTOMGRAY)

            // Set image color based on the note state
            img.setColorFilter(if (state == State.DONE) primaryColor else secondaryColor)
        }

        // Set clock image color based on time difference
        private fun setScheduleClockColor(timeDiff: Long) {
            clock?.setColorFilter(
                when {
                    timeDiff < 0 -> Color.RED
                    timeDiff == 0L -> Color.YELLOW
                    else -> Color.BLACK
                }
            )
        }

        // Get image resource based on note type
        private fun getImageResourceForType(type: Type): Int {
            return when (type) {
                Type.FAMILY -> R.drawable.family
                Type.TODO -> R.drawable.todo
                Type.SHOPPING -> R.drawable.shopping
                Type.WORK -> R.drawable.work
                else -> R.drawable.ic_launcher_foreground
            }
        }

        // Format the time difference based on the number of days
        private fun getFormattedTimeDifference(timeDiff: Long): String {
            return when {
                timeDiff < 0 -> itemView.resources.getString(R.string.late)
                timeDiff == 0L -> itemView.resources.getString(R.string.today)
                timeDiff >= 30 -> String.format(itemView.resources.getString(R.string.months), timeDiff / 30)
                timeDiff >= 7 -> String.format(itemView.resources.getString(R.string.weeks), timeDiff / 7)
                else -> String.format(itemView.resources.getString(R.string.days), timeDiff)
            }
        }
    }
}

// DiffUtil callback to calculate item differences
class NotesDiffCallback(private val oldList: List<NoteAndSchedule>, private val newList: List<NoteAndSchedule>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Check if items have the same noteId
        return oldList[oldItemPosition].note.noteId == newList[newItemPosition].note.noteId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Check if the contents of items are the same
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old::class == new::class
                && old.note.title == new.note.title
                && old.note.text == new.note.text
                && old.note.state == new.note.state
                && old.note.noteId == new.note.noteId
                && old.schedule?.date == new.schedule?.date
                && old.schedule?.scheduleId == new.schedule?.scheduleId
                && old.schedule?.ownerId == new.schedule?.ownerId
    }
}

// Constants for view types
private const val CLOCK = 1
private const val NOCLOCK = 2
